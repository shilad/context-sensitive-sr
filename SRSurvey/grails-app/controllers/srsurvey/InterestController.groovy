package srsurvey

class InterestController {

<<<<<<< HEAD
=======

>>>>>>> 04723f8d3d4a4d536a8b45775a137b5add2229be
    //links to the interest page
    def interest() {

        render(view: 'interest')

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

            redirect(action: "interest", params: [emails:params.emails])
        } else {
            redirect(url: "/")
        }
    }

<<<<<<< HEAD
    def test()
    {
        if (params.email == null)
        {
            Person p = Person.findByEmail("bhillman@macalester.edu")
            params.email = p.email
        }
        redirect(action: 'interest', params: [emails:params.email])
    }


=======
>>>>>>> 04723f8d3d4a4d536a8b45775a137b5add2229be

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
        redirect(action: 'interest', params: [emails:params.email])
    }

    //Links Consent to Interest
    def update(){

        //Find the survey based off the person in session
        Survey s = Survey.findByPerson(Person.findById(session.person))

        render(view: "interest")
    }


}