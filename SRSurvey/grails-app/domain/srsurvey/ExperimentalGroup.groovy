package srsurvey

class ExperimentalGroup {

    String name

    static hasMany = [interestToGroup:InterestToGroup, survey:Survey]

    static constraints = {
    }

    ExperimentalGroup(String name){
        this.name = name
    }

}
