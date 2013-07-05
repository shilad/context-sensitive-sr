package srsurvey

class PersonService {
    def getForSession(session) {
        Person p = null
        if (session.person) {
            p = Person.get(session.person)
        }
        if (p == null) {
            p = new Person()
            Survey s = new Survey()
            p.setSurvey(s)
            p.save(flush: true)
            session.person = p.id
        }
        return p
    }

    def create(String email) {
        Person person = new Person()
        person.setEmail(email)
        person.save(flush: true)
    }

    def addInterest(Person p, String text) {
        def interest = Interest.findByText(text)
        if (interest == null && !text.equals(""))
        {
            interest = new Interest(text)
            interest.save(flush: true)
        }
        PersonToInterest pi = new PersonToInterest(p, interest)
        pi.save(flush: true)
    }
}
