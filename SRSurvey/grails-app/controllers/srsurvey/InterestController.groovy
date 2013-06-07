package srsurvey

class InterestController {


    //Links Consent to Interest page

    def interest() {

        //Find the survey based off the person in session
        Survey s = Survey.findByPerson(Person.findById(session.person))

        render(view: 'interest')

    }

    def process() {
        List<String> inputs = params.get("interest_inputs")
        //print(inputs)

        //Find the person
        Person p = Person.findById(session.person)

        //Associate the person with the interest
        PersonService ps = new PersonService(p)
        //print(inputs+"here")
        for (interest in inputs){
            if(interest!=""){
                ps.addInterest(interest)
            }
        }

        //Assign Group
        SRService sr = new SRService()
        sr.assignGroup(p, inputs)

        redirect(controller: 'rating', action: 'ratings')

    }

    def index() {

        if(params.emails!= null){
            String email = params.emails

            Person p = Person.findByEmail(email)

            //put the person into session
            if(session.person==null){
                session.person = p.id
            }

            redirect(action: "interest", params: [emails:params.emails])
        } else {
            redirect(url: "/")
        }
    }

    // Create connect from email to consent should be like create
    def consent()
    {

        if(params.emails!=null)
        {
            String email = params.emails
            print(email)
            Person p = Person.findByEmail(email)
            print(p)

            //put the person into session
            if(session.person==null)
            {
                session.person = p.id
            }

            render(view:'consent')
        } else
        {
            redirect(url: "/")
        }
    }



}