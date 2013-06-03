package srsurvey

class Person {

    String email
    String first_name
    String last_name
    String title
    Boolean isRegistered

    static hasOne = [survey:Survey]
    static hasMany = [personToInterest:PersonToInterest]

    static constraints = {
    }
}
