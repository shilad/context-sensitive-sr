package srsurvey

/**
 * To access actions in the administrator controller you must
 * 1) Execute the resetKey action.
 * 2) Go to the logs and read out the new access key
 * 3) Execute a new action with the newly generated key
 *
 * It is crucial that all actions call checkAuth() first.
 */
class EmailController {

    def personService

    def invite = {
        ['template', 'email', 'baseUrl', 'subject'].each({
            if (!params[it]) {
                throw new IllegalArgumentException("missing ${it} parameter")
            }
        })

        //TODO: Presumably the parameters we need to send emails, needs verification
        String template = params.template
        String email = params.email
        String baseUrl = params.baseUrl
        String subj = params.subject

        //Add person to our database
        personService.create(email)

        //Create the person object after the person has been created
        Person p = Person.findByEmail(email)


        if (p) {
            String body = g.render(
                        template: "${template}",
                        model : [person : p, password : password, baseUrl : baseUrl]

                )
            sendMail {
                to "${email}"
                subject "${subj}"
                html "${body}"
            }
            render('okay')
        } else {
            render("unknown person: ${email}")
        }

    }

}
