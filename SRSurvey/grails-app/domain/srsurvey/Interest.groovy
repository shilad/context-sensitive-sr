package srsurvey

class Interest {

    String text
    Integer unknown = 0  //count how many time the interest is unknown to raters

    static hasMany =
        [personToInterest:PersonToInterest,
                interestToGroup:InterestToGroup,
                question1:Question,
                question2:Question]
    static mappedBy = [question1: 'interest1',question2: 'interest2']

    static constraints = {
        unknown nullable: true
    }

    Interest(String text) {
        this.text = text
    }
}