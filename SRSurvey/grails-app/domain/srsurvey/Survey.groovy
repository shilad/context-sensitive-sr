package srsurvey

class Survey {

    static belongsTo = [person: Person, group:ExperimentalGroup]
    static hasMany = [question:Question]

    Date dateCreated

    static constraints = {
    }
}
