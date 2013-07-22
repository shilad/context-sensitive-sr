package org.macademia.algs;

import com.aliasi.tokenizer.*;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Shilad Sen
 */
public class MakeFinalResults {
    private final Map<String, String> stemMap = new HashMap<String, String>();

    public MakeFinalResults() throws IOException {
        readPhrases();
    }

    public TIntIntMap readInterestCounts() throws IOException {
        TIntIntHashMap counts = new TIntIntHashMap();
        for (String line : FileUtils.readLines(new File("results/people_interests.txt"))) {
            int personId = Integer.valueOf(line.split("\t")[0]);
            int interestId = Integer.valueOf(line.split("\t")[1]);
            counts.adjustOrPutValue(interestId, 1, 1);
        }
        return counts;
    }

    public void readPhrases() throws IOException {
        TIntIntMap interestCounts = readInterestCounts();
        Map<String, Map<String, Integer>> stemmedCounts = new HashMap<String, Map<String, Integer>>();
        for (String line : FileUtils.readLines(new File("results/phrases.tsv"))) {
            String phrase = line.split("\t")[3];
            int id = Integer.valueOf(line.split("\t")[1]);
            String cleaned = clean(phrase);
            String stemmed = stem(phrase);
            int count = interestCounts.get(id);
            if (!stemmedCounts.containsKey(stemmed)) {
                stemmedCounts.put(stemmed, new HashMap<String, Integer>());
            }
            if (!stemmedCounts.get(stemmed).containsKey(cleaned)) {
                stemmedCounts.get(stemmed).put(cleaned, count);
            } else {
                stemmedCounts.get(stemmed).put(cleaned, stemmedCounts.get(stemmed).get(cleaned) + count);
            }
        }

        for (String stemmed : stemmedCounts.keySet()) {
            Map<String, Integer> versions = stemmedCounts.get(stemmed);
            String mostCommon = null;
            for (String cleaned : versions.keySet()) {
                if (mostCommon == null || versions.get(cleaned) > versions.get(mostCommon)) {
                    mostCommon = cleaned;
                }
            }
            stemMap.put(stemmed, mostCommon);
        }
    }

    static final Pattern PMI_LINE = Pattern.compile("Key1: (.*)\tKey2: (.*)\tValue: (.*)$");
    public void writePairs(String inputFile, int n, String outputFile) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
        List<String> lines = FileUtils.readLines(new File(inputFile));
        for (int i = 0; i < n; i++) {
            int index = (int) Math.floor(1.0 * i / n * lines.size());
//            System.out.println("selecting line " + index + " of " + lines.size());
            Matcher matcher = PMI_LINE.matcher(lines.get(index));
            if (matcher.matches()) {
                String phrase1 = stemMap.get(matcher.group(1));
                String phrase2 = stemMap.get(matcher.group(2));
                out.write(phrase1 + "\t" + phrase2 + "\t" + matcher.group(3) + "\n");
            } else {
                System.err.println("invalid line: " + lines.get(index));
            }
        }
        out.close();
    }

    public String clean(String text) {
        return StringUtils.join(text.split("\\s+"), " ").trim();
    }

    public String stem(String text) {
        return PorterStemmerTokenizerFactory.stem(text.replaceAll("[^\\w\\s]", "")).toLowerCase();
    }

    public static void main(String args[]) throws IOException {
        MakeFinalResults mfr = new MakeFinalResults();
        for (String field : new String[] { "biology", "psychology", "history"}) {
            mfr.writePairs("results/" + field + "_raw.txt", 60, "results/" + field + ".txt");
        }
    }
}
