package srsurvey

class Interest {

    String text

    static hasMany =
        [personToInterest:PersonToInterest,
                interestToGroup:InterestToGroup,
                question1:Question,
                question2:Question]
    static mappedBy = [question1: 'interest1',question2: 'interest2']


    //question1:Question, question2: Question


    static constraints = {
    }
}
