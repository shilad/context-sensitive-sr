package srsurvey

/**
 * @author Shilad Sen
 */
class SrPair {
    String phrase1
    String phrase2
    double sim

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        SrPair srPair = (SrPair) o

        if (phrase1 == srPair.phrase1 && phrase2 == srPair.phrase2) return true
        if (phrase1 == srPair.phrase2 && phrase2 == srPair.phrase1) return true

        return false
    }

    int hashCode() {
        if (phrase1 < phrase2)
            return (phrase1+phrase2).hashCode()
        else
            return (phrase2+phrase1).hashCode()
    }


    @Override
    public String toString() {
        return "SrPair{" +
                "phrase1='" + phrase1 + '\'' +
                ", phrase2='" + phrase2 + '\'' +
                '}';
    }
}
