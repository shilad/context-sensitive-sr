package srsurvey

class Survey {

    String comment

    static belongsTo = [person: Person]
    static hasMany = [question:Question]

    Date dateCreated

    static constraints = {
        comment nullable: true
    }

    public Survey(Person person){
        this.person = person
    }
}
