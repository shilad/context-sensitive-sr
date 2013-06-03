package srsurvey

class Question {

    Double result
    Date lastUpdated
    Integer questionNumber
    Interest interest1
    Interest interest2

    static belongsTo = [survey:Survey]

    static constraints = {
    }
}
