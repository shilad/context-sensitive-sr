package srsurvey

class ConsentController {
    def personService
    def show() {
        Person p = personService.getForSession(session)
        String email = params.email ? params.email : p?.email
        if (email != null) {
            p.email = email
            p.save(flush : true)
        }
        render(view:'show', model: [person : p])
    }

    def save() {

        if(params.emails!=null)
        {
            String email = params.emails
            //print(email)
            Person p = Person.findByEmail(email)
            //print(p)

            if(Survey.findByPerson(p) == null) {
                p = new Person(email : params.emails)
                Survey s = new Survey()
                p.setSurvey(s)
                p.save(flush: true)
            }


            //put the person into session
            if(session.person==null)
            {
                session.person = p.id
            }

            if(session.survey==null){
                Survey s = Survey.findByPerson(p)
                session.survey = s.id
            }

            render(view:'show')
        }

    }
}
