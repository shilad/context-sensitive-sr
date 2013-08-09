package srsurvey

class FinishController {
    def personService
    def loggingService

    def show() {
        Person p = personService.getForSession(session)
        int maxRound = p.survey.questions*.round.max()
        println("maxRound is " + maxRound)
        render(view: "show", model: [person:p])
    }

    def save() {
        Person p = personService.getForSession(session)
        p.survey.comment = params.comments
        p.save(flush : true)
        loggingService.append(p, request, "comments\t" + params.comments)
        String s = params.submit
        if (s.toLowerCase().startsWith("sure")) {
            loggingService.append(p, request, 'rateMore')
            redirect(controller : 'rating', action : 'show')
        } else {
            loggingService.append(p, request, 'finished')
            render(view: "thanks")
        }
    }
}


