package srsurvey

class InterestController {
    def interest() {

        if(params.emails!=null) {
            String email = params.emails

            Person p = Person.findByEmail(email)

            //put the person into session
            if(session.person==null){
                session.person = p.id
            }

            render(view:'interest')
        } else {
            redirect(url: "/")
        }

    }

    def process() {
        List<String> inputs = params.get("interest_inputs")

        //Find the person
        Person p = Person.findById(session.person)

        //Associate the person with the interest
        PersonService ps = new PersonService(p)
        for (interest in inputs){
            ps.addInterest(interest)
        }

        //Assign Group
        SRService sr = new SRService()
        sr.assignGroup(p, inputs)

    }

    def index() {

        if(params.emails!= null){
            String email = params.emails

            Person p = Person.findByEmail(email)

            //put the person into session
            if(session.person==null){
                session.person= p.id
            }

            redirect(action: "interest", params: [emails:params.emails])
        } else {
            redirect(url: "/")
        }
    }

    def test() {
        if (params.email == null) {
            Person p = Person.findByEmail("bhillman@macalester.edu")
            params.email = p.email
        }
        redirect(action: 'interest', params: [emails:params.email])
    }


}
