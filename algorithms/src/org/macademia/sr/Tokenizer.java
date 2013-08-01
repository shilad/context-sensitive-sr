package org.macademia.sr;

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
        Tokenization t = new Tokenization(text.replaceAll("[^\\w\\s]", ""), tokenNGramTokenizerFactory);
        for (String token : t.tokens()) {
            tokens.add(normalize(token));
        }
        return tokens;
    }

    public String clean(String text) {
        return StringUtils.join(text.split("\\s+"), " ").trim();
    }

    public String stem(String text) {
        return PorterStemmerTokenizerFactory.stem(text.replaceAll("[^\\w\\s]", "")).toLowerCase();
    }

    public String normalize(String text) {
        return clean(stem(text));
    }
}
