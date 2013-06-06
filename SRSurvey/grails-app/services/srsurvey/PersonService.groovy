package srsurvey

class PersonService {

     Person person

    PersonService(Person person){
        this.person = person
    }

    def serviceMethod() {

    }

    def create(String email) {
        Person person = new Person()
        person.setEmail(email)
        person.save(flush: true)
    }

    def addInterest(String text) {
        def interest = Interest.findByText(text)
        if (interest == null)
        {
            interest = new Interest(text)
            interest.save(flush: true)
        }
        PersonToInterest pi = new PersonToInterest(person, interest)
        pi.save(flush: true)
    }
}
