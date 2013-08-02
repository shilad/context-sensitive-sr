package org.macademia.phrasepairs;

import com.aliasi.tokenizer.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shilad Sen
 */
public class Tokenizer {
    private final TokenizerFactory tokenizerFactory = IndoEuropeanTokenizerFactory.INSTANCE;
    private final TokenNGramTokenizerFactory tokenNGramTokenizerFactory;

    public Tokenizer() {
        this(3);
    }

    public Tokenizer(int maxWordsPerToken) {
        tokenNGramTokenizerFactory = new TokenNGramTokenizerFactory(tokenizerFactory,1,maxWordsPerToken);
    }

    public List<String> tokenize(String text) {
        List<String> tokens = new ArrayList<String>();
        Tokenization t = new Tokenization(text, tokenNGramTokenizerFactory);
        for (String token : t.tokens()) {
            tokens.add(normalize(token));
        }
        return tokens;
    }

    public String normalize(String text) {
        String tokens[] = text.toLowerCase().replaceAll("[^\\w\\s]", "").split("\\s+");
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = PorterStemmerTokenizerFactory.stem(tokens[i]);
        }
        return StringUtils.join(tokens, " ").trim();
    }
}
