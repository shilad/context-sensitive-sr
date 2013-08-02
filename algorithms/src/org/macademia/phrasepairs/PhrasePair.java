package org.macademia.phrasepairs;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Shilad Sen
 */
public class PhrasePair {
    private String phrase1;
    private String phrase2;
    private double score;

    public PhrasePair(String phrase1, String phrase2, double score) {
        this.phrase1 = phrase1;
        this.phrase2 = phrase2;
        this.score = score;
    }

    public String getPhrase1() {
        return phrase1;
    }

    public String getPhrase2() {
        return phrase2;
    }

    public double getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhrasePair that = (PhrasePair) o;

        if (!phrase1.equals(that.phrase1)) return false;
        if (!phrase2.equals(that.phrase2)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phrase1.hashCode();
        result = 31 * result + phrase2.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PhrasePair{" +
                "phrase1='" + phrase1 + '\'' +
                ", phrase2='" + phrase2 + '\'' +
                ", score=" + score +
                '}';
    }

    public static void sortByScore(List<PhrasePair> pairs) {
        Collections.sort(pairs, new Comparator<PhrasePair>() {
            public int compare(PhrasePair p1, PhrasePair p2) {
                if (p1.score > p2.score) {
                    return -1;
                } else if (p1.score < p2.score) {
                    return +1;
                } else {
                    return 0;
                }
            }
        });
    }
}
