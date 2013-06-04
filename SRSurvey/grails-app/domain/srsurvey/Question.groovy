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

    Question(Double result, Integer questionNumber){
        this.result = result
        this.questionNumber = questionNumber
    }

    Question(Double result, Integer questionNumber, Interest interest1, Interest interest2){
        this.result = result
        this.questionNumber = questionNumber
        this.interest1 = interest1
        this.interest2 = interest2
    }
}
