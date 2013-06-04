package srsurvey

class PersonToInterest {

    static belongsTo = [person:Person, interest:Interest]

    static constraints = {
    }

    PersonToInterest(Person person, Interest interest) {
        this.person = person
        this.interest = interest
    }
}
