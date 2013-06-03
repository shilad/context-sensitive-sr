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
    }
}
