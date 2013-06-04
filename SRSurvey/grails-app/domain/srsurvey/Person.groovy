package srsurvey

class Person {

    String email
    String firstName
    String lastName
    String title
    Boolean isRegistered

    static hasOne = [survey:Survey]
    static hasMany = [personToInterest:PersonToInterest]

    static constraints = {
        survey nullable: true
        survey unique: true
    }

    Person(String email, String firstName, String lastName, String title, String isRegistered){
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.title = title
        this.isRegistered = isRegistered
    }
}
