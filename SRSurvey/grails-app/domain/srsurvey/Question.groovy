package srsurvey

class Question {

    Double result
    Date lastUpdated
    Integer question_number
    Interest interest1
    Interest interest2

    static belongsTo = [survey:Survey]

    static constraints = {
    }
}
