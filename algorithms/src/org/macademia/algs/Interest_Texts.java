package org.macademia.algs;

import com.aliasi.lm.TokenizedLM;
import com.aliasi.tokenizer.IndoEuropeanTokenizerFactory;
import com.aliasi.tokenizer.TokenNGramTokenizerFactory;
import com.aliasi.tokenizer.Tokenization;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.Files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        Tokenization t = null;
        while ((line = phrases.readLine()) != null) {
            for (int j = 0; j < files.length; ++j) {
                String text = Files.readFromFile(new File(BACKGROUND_DIR,
                        files[j]),
                        "UTF8");
                t=new Tokenization(text,tokenNGramTokenizerFactory);
                for(String s:t.tokenList()){
                    if(s.equals(line))
                        System.out.println(s);
                }

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