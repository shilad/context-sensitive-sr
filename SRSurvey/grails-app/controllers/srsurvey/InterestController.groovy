package srsurvey

class InterestController {
    def create() {

        if(params.emails!=null) {
            String email = params.emails
            print(email)
            Person p = Person.findByEmail(email)
            print(p)

            //put the person into session
            if(session.person==null){
                session.person = p.id
            }

            render(view:'create')
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
                session.person = p.id
            }

            redirect(action: "create", params: [emails:params.emails])
        } else {
            redirect(url: "/")
        }
    }

    //TODO: Create connect from email to consent should be like create
    def consent() {

        if(params.emails!=null) {
            String email = params.emails
            print(email)
            Person p = Person.findByEmail(email)
            print(p)

            //put the person into session
            if(session.person==null){
                session.person = p.id
            }

            render(view:'consent')
        } else {
            redirect(url: "/")
        }

    }
    //TODO: alter create to link from consent interest
}
