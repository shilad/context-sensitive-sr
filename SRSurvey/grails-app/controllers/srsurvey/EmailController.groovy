package srsurvey

class EmailController {

    PersonService ps = new PersonService()

    def invite = {

        //TODO: Presumably the parameters we need to send emails, needs verification
        String body = "invite"
        String email = params.email
        String baseUrl = params.baseUrl
        String subj = params.subject

        //Add person to our database
        ps.create(email)

        //Create the person object after the person has been created
        Person p = Person.findByEmail(email)

        sendMail {
            to email
            subject subj
            body (view:"http://localhost:8080/SRSurvey/email/inviteTemplate", model:[name:"Chris"])
        }
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
