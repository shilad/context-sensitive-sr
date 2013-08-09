package srsurvey

class EmailController {
    def loggingService
    def personService

    def invite = {

        Person p = Person.findByEmail(params.email)
        if (p == null) {
            p = personService.create(params.email)
        }

        def model = [
                email : params.email,
                name: params.name,
                inDept: params.inDept,
                baseUrl: 'http://macademia.macalester.edu/SRSurvey'
        ]

        sendMail {
            to params.email
            subject "Take a short Macademia survey!"
            html view: "invite", model: model
        }

        loggingService.append(p, request, "invite")

        render('Okay')
    }

    def test = {
        render(view: 'invite', model: [
                email:'ssen@macalester.edu',
                name:'Shilad Sen',
                inDept: true,
                baseUrl: 'http://macademia.macalester.edu/SRSurvey'
        ])
    }

    def demo = {

        if (params.email == null) {
            Person p = Person.findByEmail("jrussel1@macalester.edu")
            params.email = p.email
            params.baseUrl = "http://localhost:8080/SRSurvey/interest/consent"
            params.subject = "It works!"
        }

        //TODO: Presumably the parameters we need to send emails, needs verification
        String email = params.email
        String subj = params.subject

        //Create the person object after the person has been created
        Person p = Person.findByEmail(email)

        sendMail {
            to email
            subject subj
            html view: "invite", model: [email:params.email, baseUrl: params.baseUrl]
        }

        //Add person to our database
        PersonService ps = new PersonService()
        ps.create(email)

        render(view:'invite')

    }

}
