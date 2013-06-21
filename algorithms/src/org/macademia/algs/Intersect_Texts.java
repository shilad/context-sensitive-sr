package org.macademia.algs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import com.aliasi.lm.TokenizedLM;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.Files;
import com.aliasi.util.ScoredObject;
import java.util.SortedSet;
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
            = new File("dat/background");
    private static File FOREGROUND_DIR
            = new File("dat/foreground");

    public static void main(String args[]) throws IOException {

        TokenizerFactory tokenizerFactory = IndoEuropeanTokenizerFactory.INSTANCE;

        System.out.println("Training background model");
        TokenizedLM backgroundModel = buildModel(tokenizerFactory,NGRAM,BACKGROUND_DIR);
        tokenizerFactory.tokenizer("jgvkdsjhbfksdhabflkhadsbfljhabvkjhbdvkjhbdvhjbkhbdfvkhjd".toCharArray(),2,2);
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
