package org.macademia.sr;

import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import org.apache.commons.io.FileUtils;
import org.macademia.algs.Tokenizer;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PmiCalculator {
    private org.macademia.algs.Tokenizer tokenizer = new Tokenizer();
    private Map<String, TIntSet> postings;
    private List<String> common;
    private int numDocs = 0;

    public PmiCalculator(File corpusDir) throws IOException {
        readPostings(corpusDir);
    }

    public void getPairwise(File phraseFile) throws IOException {
        Set<String> phrases = getPhrases(phraseFile);
        for (String phrase1 : phrases) {
            for (String phrase2 : phrases) {
                if (phrase1.equals(phrase2)) {
                    continue;
                }
                if (!postings.containsKey(phrase1)) {
                    System.err.println("unknown phrase: " + phrase1);
                    continue;
                }
                if (!postings.containsKey(phrase2)) {
                    System.err.println("unknown phrase: " + phrase2);
                    continue;
                }
                double pmi = getPmi(postings.get(phrase1), postings.get(phrase2));
                System.out.println(phrase1 + "\t" + phrase2 + "\t" + pmi);
            }
        }
    }

    public void readPostings(File dir) throws IOException {
        postings = new HashMap<String, TIntSet>();
        File txtFiles[] = FileUtils.listFiles(dir, new String [] {"txt"}, true).toArray(new File[0]);
        for (int i = 0; i < txtFiles.length; i++) {
            if (i % 10 == 0) {
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

        common = new ArrayList<String>();
        for (String word : postings.keySet()) {
            int n = postings.get(word).size();
            if (n >= numDocs / 5 && n < numDocs / 2) {
                common.add(word);
            }
        }
    }

    public void sample(int n) {
        List<String[]> samplePairs = new ArrayList<String[]>();
        for (int i = 0; i < n; i++) {
            String pair[] = new String[] { pickWord(), pickWord() };
            double pmi = getPmi(postings.get(pair[0]), postings.get(pair[1]));
            System.out.println(pair[0] + "\t" + pair[1] + "\t" + pmi);
        }
    }

    public String pickWord() {
        return common.get(new Random().nextInt(common.size() - 1));
    }

    public Set<String> getPhrases(File file) throws IOException {
        Set<String> interests = new HashSet<String>();
        for (String line : FileUtils.readLines(file, "UTF-8")) {
            interests.add(tokenizer.normalize(line));
        }
        return interests;
    }

    public double getPmi(TIntSet docs1, TIntSet docs2) {
        if (docs1.size() > docs2.size()) {
            TIntSet tmp = docs1;
            docs1 = docs2;
            docs2 = tmp;
        }
        int overlap = 0;
        for (int i : docs1.toArray()) {
            if (docs2.contains(i)) {
                overlap++;
            }
        }
        return Math.log10(
                (1.0 * overlap / numDocs) /
                ((1.0 * docs1.size() / numDocs) * (1.0 * docs2.size() / numDocs)));
    }

    public static void main(String args[]) throws IOException {
        PmiCalculator pc = new PmiCalculator(new File("dat/papers/Biology"));
//        text.getPairwise(new File("dat/department/Biology.txt"));
        pc.sample(500);
    }
}