package srsurvey

class Person {

    // Their state in the survey
    boolean hasConsented = false

    String email
    String firstName
    String lastName
    String title
    Boolean isRegistered

    static hasOne = [survey:Survey, group:ExperimentalGroup]
    static hasMany = [personToInterest:PersonToInterest]

    static constraints = {
        email nullable: true
        survey nullable: true
        group nullable: true
        firstName nullable: true
        lastName nullable: true
        title nullable: true
        isRegistered nullable: true

    }

    Person(String email, String firstName, String lastName, String title, String isRegistered){
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.title = title
        this.isRegistered = isRegistered
    }
}
