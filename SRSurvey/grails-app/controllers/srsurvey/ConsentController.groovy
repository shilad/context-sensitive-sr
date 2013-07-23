package srsurvey

class ConsentController {
    def personService
    def loggingService

    def show() {
        Person p = personService.getForSession(session)
        String email = params.email ? params.email : p?.email
        if (email != null) {
            p.email = email
            p.save(flush : true)
        }
        render(view:'show', model: [person : p])
    }

    def save() {
        if (!params.email) {
            redirect(action: 'show')
            return
        }
        Person p = personService.getForSession(session)
        p.email = params.email
        p.hasConsented = true
        p.save()

        loggingService.append(p, request, "consent")

        redirect(controller: 'expertise', action: 'showCareer')
    }
}
