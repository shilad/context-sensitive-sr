package srsurvey

class ExperimentalState {
    String name         // name of group
    boolean inGroup     // whether this is an ingroup for the subject
    int counter
    int randomSeed
    int roundOffset
    int questionsPerRound
    int maxRounds

    static constraints = {
    }
}
