package srsurvey

class Person {

    // Their state in the survey
    boolean hasConsented = false

    Boolean scholar

    String email
    String firstName
    String lastName
    String title
    Boolean isRegistered
    Interest primary
    Interest secondary
    Interest tertiary

    static hasOne = [
            survey:Survey,
            group:ExperimentalGroup
    ]
    static hasMany = [personToInterest:PersonToInterest]

    static constraints = {
        email nullable: true
        survey nullable: true
        group nullable: true
        scholar nullable: true
        firstName nullable: true
        lastName nullable: true
        title nullable: true
        isRegistered nullable: true
        primary nullable: true
        secondary nullable: true
        tertiary nullable: true
    }

    Person(String email, String firstName, String lastName, String title, String isRegistered){
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.title = title
        this.isRegistered = isRegistered
    }

    public int numAnswers() {
        int n = 0
        for (Question q : survey.questions) {
            if (q.hasAnswer()) {
                n++
            }
        }
        return n
    }
}
