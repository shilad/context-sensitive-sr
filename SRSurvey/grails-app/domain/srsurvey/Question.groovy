package srsurvey

class Question {

    int round
    int page    // which page of questions this will appear on
    Double result
    Date lastUpdated
    Integer questionNumber
    Interest interest1
    Interest interest2
    Boolean interest1Known
    Boolean interest2Known
    String groupName    // name of the experimental ingroup

    static belongsTo = [survey:Survey]

    static constraints = {
        result nullable: true
        survey nullable: true
        interest1Known nullable: true
        interest2Known nullable: true
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

    Question(Integer questionNumber, Interest interest1, Interest interest2, Survey survey) {
        this.questionNumber = questionNumber
        this.interest1 = interest1
        this.interest2 = interest2
        this.survey = survey
    }

    public SrPair toSrPair() {
        return new SrPair(phrase1: interest1.text, phrase2: interest2.text)
    }

    public boolean hasAnswer() {
        return result != null || interest1Known != null || interest2Known != null
    }
}
