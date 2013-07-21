package srsurvey

class FinishController {
    def personService

    def show() {
        Person p = personService.getForSession(session)
        render(view: "show", model: [person:p])
    }

    def save() {
        Person p = personService.getForSession(session)
        p.survey.comment = params.comments
        p.save(flush : true)
        String s = params.submit
        if (s.toLowerCase().startsWith("sure")) {
            redirect(controller : 'rating', action : 'show')
        } else {
            render(view: "thanks")
        }
    }

}


