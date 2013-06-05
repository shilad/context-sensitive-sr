package srsurvey

class Person {

    String email
    String firstName
    String lastName
    String title
    Boolean isRegistered

    static hasOne = [survey:Survey, group:ExperimentalGroup]
    static hasMany = [personToInterest:PersonToInterest]

    static constraints = {
        survey nullable: true
        survey unique: true
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
