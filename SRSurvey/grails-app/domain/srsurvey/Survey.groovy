package srsurvey

class Survey {

    String comment
    List questions

    static belongsTo = [person: Person]
    static hasMany = [questions:Question]

    Date dateCreated

    static constraints = {
        comment nullable: true
    }

    public Survey(Person person){
        this.person = person
    }
}
