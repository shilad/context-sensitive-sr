package org.macademia.algs;

import java.io.*;
import com.aliasi.lm.TokenizedLM;
import com.aliasi.tokenizer.*;
import com.aliasi.util.Files;
import com.aliasi.util.ScoredObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: jesse
 * Date: 6/21/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Intersect_Texts {
//    private static int NGRAM = 20;
//    private static int MIN_COUNT = 5;
//    private static int MAX_NGRAM_REPORTING_LENGTH = 10;
//    private static int NGRAM_REPORTING_LENGTH = 2;
//    private static int MAX_COUNT = 1000;

    private static File BACKGROUND_DIR
            = new File("dat/background");
    private static File FOREGROUND_DIR
            = new File("dat/foreground");

    public static void main(String args[]) throws IOException {
        BufferedReader phrases=null;
        try {
            phrases = new BufferedReader(new FileReader("dat/foreground/biology.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }



        TokenizerFactory tokenizerFactory = IndoEuropeanTokenizerFactory.INSTANCE;
        TokenNGramTokenizerFactory tokenNGramTokenizerFactory = new TokenNGramTokenizerFactory(tokenizerFactory,1,3);
        HashMap<String, HashSet<String>> words = new HashMap<String, HashSet<String>>();
        Tokenization t = null;
        Integer docCount=0;
        HashSet<String> innerSet=null;
        String[] fileStrings = BACKGROUND_DIR.list();
        File[] files=new File[fileStrings.length];
        for(int i=0;i<fileStrings.length;i++){
            files[i] = new File(BACKGROUND_DIR,fileStrings[i]);
        }
        for(File file:files){
            fileStrings=file.list();
            for (String file_string:fileStrings) {
                docCount++;
                String text = Files.readFromFile(new File(file,
                        file_string),
                        "UTF8");
                t = new Tokenization(text.replaceAll("[^\\w\\s]", ""),tokenNGramTokenizerFactory);
                for(String s:t.tokenList()) {
                    s= PorterStemmerTokenizerFactory.stem(s).toLowerCase();

                    innerSet = words.get(s);
                    if(innerSet == null) {
                        innerSet=new HashSet<String>();
                        innerSet.add(file.getName()+file_string);
                        words.put(s, innerSet);
                    } else {
                        innerSet.add(file.getName()+file_string);
                        words.put(s, innerSet);
                    }
                }


            }
        }
        String line="";
        HashMap<String, HashSet<String>> interestingWords = new HashMap<String, HashSet<String>>();
        while ((line = phrases.readLine()) != null) {
            line=PorterStemmerTokenizerFactory.stem(line);
            if(words.get(line)!=null){
                interestingWords.put(line,words.get(line));
            }
        }
        Set<String> docs1;
        Set<String> docs2;
        Set<String> intersection;
        HashMap<String,Integer> jointScores=new HashMap<String, Integer>();
        for(String key1:interestingWords.keySet()){
            for(String key2:interestingWords.keySet()){
                docs1=words.get(key1);
                docs2=words.get(key2);
                intersection=new HashSet<String>(docs1);
                intersection.retainAll(docs2);
                jointScores.put(key1+"_"+key2,intersection.size());

            }
        }

        SortedSet<Map.Entry<String,Double>> sortedscores = new TreeSet<Map.Entry<String, Double>>(
                new Comparator<Map.Entry<String, Double>>() {
                    public int compare(Map.Entry<String, Double> e1,
                                       Map.Entry<String, Double> e2) {
                        return e2.getValue().compareTo(e1.getValue());
                    }
                });
        String[] terms;
        HashMap<String,Double> pmi = new HashMap<String, Double>();
        Double score=0.0;
        for(String pair:jointScores.keySet()){
             terms=pair.split("_");
             score=Math.log10((jointScores.get(pair).doubleValue()/docCount)/
                     ((words.get(terms[0]).size()/docCount.doubleValue())*
                             (words.get(terms[1]).size()/docCount.doubleValue())));
             pmi.put(pair,score);
        }


        sortedscores.addAll(pmi.entrySet());
        Iterator<Map.Entry<String, Double>> it = sortedscores.iterator();
        Map.Entry<String, Double> current;

        int i = 0;
        String[] s;
        while (i < sortedscores.size() && it.hasNext()) {
            current=it.next();
            s=current.getKey().split("_");
            System.out.println("Key1: "+s[0]+"\tKey2: "+s[1]+"\tValue: "+current.getValue());
        }
//        while ((line = phrases.readLine()) != null) {
//            String s=PorterStemmerTokenizerFactory.stem(line.split("\t")[3]);
//            if(words.get(PorterStemmerTokenizerFactory.stem(line.split("\t")[3]))!=null)
//                System.out.println(s+"\t\t\t"+words.get(PorterStemmerTokenizerFactory.stem(line.split("\t")[3])).size());
//        }
    }


    private static TokenizedLM buildModel(TokenizerFactory tokenizerFactory,
                                          int ngram,
                                          File directory)
            throws IOException {

        String[] trainingFiles = directory.list();
        TokenizedLM model =
                new TokenizedLM(tokenizerFactory,
                        ngram);
        System.out.println("Training on "+directory);

        for (int j = 0; j < trainingFiles.length; ++j) {
            String text = Files.readFromFile(new File(directory,
                    trainingFiles[j]),
                    "ISO-8859-1");
            model.handle(text);
        }
        return model;
    }
}
