package srsurvey

class EmailController {



    def invite = {

        //TODO: Presumably the parameters we need to send emails, needs verification
        String body = "invite"
        String email = params.email
        String baseUrl = params.baseUrl
        String subj = params.subject



        //Create the person object after the person has been created
        Person p = Person.findByEmail(email)

        //To view the documentation for the plugin go here
        //http://gpc.github.io/grails-mail/docs/guide/index.html
        sendMail {
            to email
            subject subj
            html view: "invite"
        }

        //Add person to our database
        PersonService ps = new PersonService()
        ps.create(email)

        render('okay')




    }

    def test = {
        if (params.email == null) {
            Person p = Person.findByEmail("bhillman@macalester.edu")
            params.email = p.email
            params.baseUrl = ""
            params.subject = "It works!"

        }
        redirect(action: 'invite', params: [email:params.email, baseUrl: params.baseUrl, subject: params.subject])
    }

}
