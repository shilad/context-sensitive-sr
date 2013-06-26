package org.macademia.algs;

import java.io.*;
import com.aliasi.lm.TokenizedLM;
import com.aliasi.tokenizer.*;
import com.aliasi.util.Files;
import com.aliasi.util.ScoredObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
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
        String line="";
        HashSet<String> innerSet=null;
        String[] fileStrings = BACKGROUND_DIR.list();
        File[] files=new File[fileStrings.length];


        for(int i=0;i<fileStrings.length;i++){
            files[i] = new File(BACKGROUND_DIR,fileStrings[i]);
        }

        for(File file:files) {
            System.err.println("doing " + file);
            for (String file_string:file.list()) {
                System.err.println("doing " + file_string);
                String text = Files.readFromFile(new File(file,
                        file_string),
                        "UTF8");
                System.out.println("here 1");
                t = new Tokenization(text.replaceAll("[^\\w\\s]", ""),tokenNGramTokenizerFactory);

                System.out.println("here 2");
                for(String s:t.tokenList()) {
                    s = PorterStemmerTokenizerFactory.stem(s).toLowerCase();
                    if(words.containsKey(s)) {
                        words.get(s).add(file_string);
                    } else {
                        innerSet = new HashSet<String>();
                        innerSet.add(file_string);
                        words.put(s, innerSet);
                    }
                }
                System.out.println("here 3");
            }
            System.out.println("finished " + file);
        }
        System.err.println("after loop");

        Set<String> docs1;
        Set<String> docs2;
        Set<String> intersection;
        HashMap<String,Integer> jointScores=new HashMap<String, Integer>();
        for(String key1:phrases){
            for(String key2:words.keySet()){
                docs1=words.get(key1);
                docs2=words.get(key2);
                intersection=new HashSet<String>(docs1);
                intersection.retainAll(docs2);
                jointScores.put(key1+"_"+key2,intersection.size());
                System.err.println("here");
            }
        }
        System.err.println("after loop");

        while ((line = phrases.readLine()) != null) {
            String s=PorterStemmerTokenizerFactory.stem(line.split("\t")[3]);
            if(words.get(PorterStemmerTokenizerFactory.stem(line.split("\t")[3]))!=null)
                System.out.println(s+"\t\t\t"+words.get(PorterStemmerTokenizerFactory.stem(line.split("\t")[3])).size());
        }
    }

        //StreamTokenizer stream = new StreamTokenizer();

        //System.out.println(backgroundModel.toString());


//        BufferedReader interestFile=null;
//        try{
//            interestFile = new BufferedReader(new FileReader("dat/phrases.tsv"));
//            String line="";
//            String phrase="";
//            while ((line = interestFile.readLine()) != null) {
//                for(String s: line.split("\t")[3].toLowerCase().split(" ")){
//                   phrase+=" "+PorterStemmerTokenizerFactory.stem(s);
//                }
//                phrase=phrase.substring(1,phrase.length());
//
//                System.out.println(phrase);
//
//                phrase="";
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }


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
