package srsurvey

class Survey {

    static belongsTo = [person: Person]
    static hasMany = [question:Question]

    Date dateCreated

    static constraints = {
    }

    public Survey(Person person, ExperimentalGroup experimentalGroup){
        this.person = person
        this.experimentalGroup = experimentalGroup
    }
}
