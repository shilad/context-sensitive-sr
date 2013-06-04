package srsurvey

class Survey {

    static belongsTo = [person: Person, experimentalGroup:ExperimentalGroup]
    static hasMany = [question:Question]

    Date dateCreated

    static constraints = {
    }

    public Survey(Person person, ExperimentalGroup experimentalGroup){
        this.person = person
        this.experimentalGroup = experimentalGroup
    }
}
