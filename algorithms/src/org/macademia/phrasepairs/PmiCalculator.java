package org.macademia.phrasepairs;

import java.io.*;

import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import org.apache.commons.io.FileUtils;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class PmiCalculator {
    private static final Logger LOG = Logger.getLogger(PmiCalculator.class.getName());
    private static final int NUM_INTEREST_PAIRS_PER_FIELD = 50;

    private final Dao dao;

    private Tokenizer tokenizer = new Tokenizer();
    private Map<String, TIntSet> postings;  // stemmed phrase to doc ids
    private List<String> common;            // stemmed phrases that are reasonably common
    private List<Double> pmiSamples;
    private int numDocs = 0;

    public PmiCalculator(Dao dao, File corpusDir) throws IOException {
        this.dao = dao;
        readPostings(corpusDir);
        createCommon();
        samplePmi(10000);
    }

    public void readPostings(File dir) throws IOException {
        postings = new HashMap<String, TIntSet>();
        File txtFiles[] = FileUtils.listFiles(dir, new String [] {"txt"}, true).toArray(new File[0]);
        for (int i = 0; i < txtFiles.length; i++) {
            if (i % 100 == 0) {
                System.err.println("reading " + i + " of " + txtFiles.length);
            }
            String text = FileUtils.readFileToString(txtFiles[i], "UTF-8");
            for (String token : tokenizer.tokenize(text)) {
                if (!postings.containsKey(token)) {
                    postings.put(token, new TIntHashSet());
                }
                postings.get(token).add(numDocs);
            }
            numDocs++;
        }
    }

    private void createCommon() {
        common = new ArrayList<String>();
        for (String phrase : postings.keySet()) {
            if (isNormalWord(phrase)) {
                int n = postings.get(phrase).size();
                if (n >= numDocs / 5 && n < numDocs / 2) {
                    common.add(phrase);
                }
            }
        }
    }

    private static final Pattern ALPHA_PATTERN = Pattern.compile("^[a-zA-Z]+$");
    private boolean isNormalWord(String phrase) {
        if (phrase.split(" ").length > 1) {
            return false;
        } else {
            return ALPHA_PATTERN.matcher(phrase).matches();
        }

    }

    private void samplePmi(int n) {
        pmiSamples = new ArrayList<Double>();
        for (int i = 0; i < n; i++) {
            String p1 = pickWord();
            String p2 = pickWord();
            double pmi = getPmi(p1, p2);
            pmiSamples.add(pmi);
            if (i % 1000 == 0) {
                LOG.info("sample " + i + " of " + n + " is " + p1 + ", " + p2 + ", pmi=" + pmi);
            }
        }
        Collections.sort(pmiSamples);
    }

    public String pickWord() {
        return common.get(new Random().nextInt(common.size() - 1));
    }

    public List<List<PhrasePair>> getPairwiseBuckets(Collection<Interest> interests) {
        List<List<PhrasePair>> buckets = new ArrayList<List<PhrasePair>>();
        for (int i = 0; i < 10; i++) {
            buckets.add(new ArrayList<PhrasePair>());
        }

        for (Interest i1 : interests) {
            for (Interest i2 : interests) {
                if (i1 != i2 && i1.getMacademiaID().compareTo(i2.getMacademiaID()) < 0) {
                    String p1 = tokenizer.normalize(i1.getName());
                    String p2 = tokenizer.normalize(i2.getName());
                    if (!postings.containsKey(p1) || !postings.containsKey(p2)) {
                        continue;
                    }
                    if (postings.get(p1).size() < 10 || postings.get(p2).size() < 10) {
                        continue;
                    }
                    PhrasePair pp = new PhrasePair(i1.getName(), i2.getName(), getPmi(p1, p2));
                    int b = (int)(getPercentileForPmi(pp.getScore()) * buckets.size());
                    b = Math.max(0, Math.min(buckets.size() - 1, b));
                    buckets.get(b).add(pp);
                }
            }
        }
        return buckets;
    }

    private double getPercentileForPmi(double pmi) {
        int i = Collections.binarySearch(pmiSamples, pmi);
        return 1.0 * Math.abs(i) / pmiSamples.size();
    }

    public Set<String> getPhrases(File file) throws IOException {
        Set<String> interests = new HashSet<String>();
        for (String line : FileUtils.readLines(file, "UTF-8")) {
            interests.add(tokenizer.normalize(line));
        }
        return interests;
    }

    public double getPmi(String normalized1, String normalized2) {
        TIntSet docs1 = postings.get(normalized1);
        TIntSet docs2 = postings.get(normalized2);
        if (docs1 == null || docs2 == null) {
            return 0.0;
        }
        if (docs1.size() > docs2.size()) {
            TIntSet tmp = docs1;
            docs1 = docs2;
            docs2 = tmp;
        }
        double overlap = 0.1;   // for smoothing
        for (int i : docs1.toArray()) {
            if (docs2.contains(i)) {
                overlap++;
            }
        }
        double s1 = docs1.size() + 1;  // for smoothing
        double s2 = docs2.size() + 1;  // for smoothing
        return Math.log10((overlap / numDocs)  / (s1 / numDocs * s2 / numDocs));
    }

    public static void main(String args[]) throws IOException {
        Dao dao = new Dao();

        // calculate the minimum over all fields: of # interests used at least twice

        FileWriter log = new FileWriter("results/phrase_log.txt");

        int topN = Integer.MAX_VALUE;
        for (String field : Arrays.asList("biology", "psychology", "history")) {
            topN = Math.min(dao.topInterests(field, 2).size(), topN);
        }
        log.write("keeping top " + topN + " interests\n");

        for (String field : Arrays.asList("biology", "psychology", "history")) {
            PmiCalculator pc = new PmiCalculator(dao, new File("dat/papers/" + field));
            List<Interest> common = new ArrayList<Interest>(dao.topInterests(field, 2).keySet());
            if (common.size() > topN) { common = common.subList(0, topN); }

            List<List<PhrasePair>> buckets = pc.getPairwiseBuckets(common);
            if (NUM_INTEREST_PAIRS_PER_FIELD % buckets.size() != 0) {
                throw new IllegalStateException();
            }
            int numAppearing = 0;
            List<PhrasePair> sample = new ArrayList<PhrasePair>();
            for (int i = 0; i < buckets.size(); i++) {
                List<PhrasePair> b = buckets.get(i);
                PhrasePair.sortByScore(b);
                numAppearing += b.size();
                log.write("bucket " + i + " has: " + b.size());
                log.write(" from " + b.get(0) + " to " + b.get(b.size() - 1) + "\n");

                // choose a random sample
                Collections.shuffle(b);
                int n = NUM_INTEREST_PAIRS_PER_FIELD / buckets.size();
                sample.addAll(b.subList(0, n));
            }
            log.write("found " + numAppearing + " out of " + (common.size()*common.size()/2) + " possible interests pairs in text\n");

            PhrasePair.sortByScore(sample);

            FileWriter writer = new FileWriter("results/" + field + ".txt");
            for (PhrasePair p : sample) {
                writer.write(p.getPhrase1() + "\t" + p.getPhrase2() + "\t" + p.getScore() + "\n");
            }
            writer.close();
        }
        log.close();
    }
}