package srsurvey

class ExperimentalGroup {

    String name

    static hasMany = [interestToGroup:InterestToGroup, person:Person]

    static constraints = {
    }

    ExperimentalGroup(String name){
        this.name = name
    }

}