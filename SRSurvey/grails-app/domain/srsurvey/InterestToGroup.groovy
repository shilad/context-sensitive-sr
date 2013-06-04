package srsurvey

class InterestToGroup {

    static belongsTo = [interest:Interest, group:ExperimentalGroup]

    static constraints = {
    }

    InterestToGroup(Interest interest, ExperimentalGroup group) {
        this.interest = interest
        this.group = group
    }
}
