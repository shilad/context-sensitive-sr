package srsurvey

class EmailController {

    def invite = {

        //TODO: Presumably the parameters we need to send emails, needs verification
        String email = params.email
        String subj = params.subject



        //Create the person object after the person has been created
        Person p = Person.findByEmail(email)

        //To view the documentation for the plugin go here
        //http://gpc.github.io/grails-mail/docs/guide/index.html
        sendMail {
            to email
            subject subj
            html view: "invite", model: [email:params.email, baseUrl: params.baseUrl]
        }

        //Add person to our database (do we need this?)
        PersonService ps = new PersonService()
        ps.create(email)

        render('Okay')
    }

    def test = {
        if (params.email == null) {
            Person p = Person.findByEmail("jrussel1@macalester.edu")
            params.email = p.email
            params.baseUrl = "http://localhost:8080/SRSurvey/interest/consent"
            params.subject = "It works!"

        }
        redirect(action: 'invite', params: [email:params.email, baseUrl: params.baseUrl, subject: params.subject])
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
