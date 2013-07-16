package srsurvey

class InterestController {
    def personService
    def srService

    def show() {
        Person p = personService.getForSession(session)
        if (!p.hasConsented) {
            redirect(url : '/')
            return
        }
        render(view: 'interest', model: [person: p])
    }

    def save() {
        Person p = personService.getForSession(session)
        if (!p.hasConsented) {
            redirect(url : '/')
            return
        }

        List<String> inputs = params.get("interest_inputs") as List<String>

        for (interest in inputs){
            if(interest!=""){
                personService.addInterest(p, interest)
            }
        }


        redirect(controller: 'rating', action: 'show')
    }

}