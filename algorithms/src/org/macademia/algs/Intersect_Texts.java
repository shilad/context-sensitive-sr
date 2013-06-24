package org.macademia.algs;

import java.io.*;
import com.aliasi.lm.TokenizedLM;
import com.aliasi.tokenizer.*;
import com.aliasi.util.Files;
import com.aliasi.util.ScoredObject;

import java.util.HashMap;
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
    private static int NGRAM = 20;
    private static int MIN_COUNT = 5;
    private static int MAX_NGRAM_REPORTING_LENGTH = 10;
    private static int NGRAM_REPORTING_LENGTH = 2;
    private static int MAX_COUNT = 1000;

    private static File BACKGROUND_DIR
            = new File("dat/Computer Science/computer science");
    private static File FOREGROUND_DIR
            = new File("dat/foreground");

    public static void main(String args[]) throws IOException {
        BufferedReader phrases=null;
        BufferedReader twoCities=null;
        try {
            phrases = new BufferedReader(new FileReader("dat/phrases.tsv"));
            twoCities = new BufferedReader(new FileReader("dat/foreground/crimeandpunishment.txt"));
        }catch (IOException e){
            e.printStackTrace();
        }


        String line="";
        String[] files = BACKGROUND_DIR.list();
        TokenizerFactory tokenizerFactory = IndoEuropeanTokenizerFactory.INSTANCE;
        TokenNGramTokenizerFactory tokenNGramTokenizerFactory = new TokenNGramTokenizerFactory(tokenizerFactory,1,3);
        HashMap<String, HashMap<String,Integer>> words = new HashMap<String, HashMap<String,Integer>>();
        Tokenization t = null;
        Integer i=0;
        HashMap<String,Integer> innerMap=null;
        for (int j = 0; j < files.length; ++j) {
            String text = Files.readFromFile(new File(BACKGROUND_DIR,
                    files[j]),
                    "UTF8");
            t = new Tokenization(text.replaceAll("[^\\w\\s]", ""),tokenNGramTokenizerFactory);

            for(String s:t.tokenList()) {
                s= PorterStemmerTokenizerFactory.stem(s).toLowerCase();

                innerMap = words.get(s);
                if(innerMap == null) {
                    innerMap=new HashMap<String, Integer>();
                    innerMap.put(s+"_"+files[j],1);
                    words.put(s, innerMap);

                } else {
                    if(innerMap.get(s+"_"+files[j])==null){
                        innerMap.put(s+"_"+files[j],1);
                    }else{
                        innerMap.put(s+"_"+files[j],innerMap.get(s+"_"+files[j])+1);
                        words.put(s, innerMap);
                    }

                    }
                }

            }
        while ((line = phrases.readLine()) != null) {
            String s=PorterStemmerTokenizerFactory.stem(line.split("\t")[3]);
            if(words.get(PorterStemmerTokenizerFactory.stem(line.split("\t")[3]))!=null)
                System.out.println(s+"\t\t\t"+words.get(PorterStemmerTokenizerFactory.stem(line.split("\t")[3])).size());
        }

//        System.out.println(words.toString());


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
