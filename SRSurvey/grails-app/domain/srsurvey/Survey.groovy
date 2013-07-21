package srsurvey

class Survey {

    String comment
    List questions

    ExperimentalState group1
    ExperimentalState group2
    ExperimentalState general

    static belongsTo = [person: Person]
    static hasMany = [questions: Question]

    static transients = ['seenPairs']

    Date dateCreated

    static constraints = {
        comment nullable: true
        group1 nullable: true
        group2 nullable: true
        general nullable: true
    }

    public Survey(Person person){
        this.person = person
    }

    Set<SrPair> getSeenPairs(String group) {
        Set<SrPair> seen = new HashSet<SrPair>()
        for (Question q : questions) {
            if (group == null || q.groupName == group) {
                seen.add(q.toSrPair())
            }
        }
        return seen
    }
}
