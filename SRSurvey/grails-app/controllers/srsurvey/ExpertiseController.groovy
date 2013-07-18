package srsurvey

class ExpertiseController {
    def personService
    def srService

    def showCareer() {
        Person p = personService.getForSession(session)
        if (!p.hasConsented) {
            redirect(url : '/')
            return
        }
        render(view: 'showCareer', model: [person: p])
    }

    def saveCareer() {
        Person p = personService.getForSession(session)
        if (p == null) {
            throw new NullPointerException();
        }
        p.scholar = Boolean.valueOf((String)params.scholar)
        p.save(flush : true)
        redirect(action: 'showExpertise')
    }

    def showExpertise() {
        Person p = personService.getForSession(session)
        if (!p.hasConsented) {
            redirect(url : '/')
            return
        }
        if (p.scholar) {
            render(view: 'showExpertise', model: [person: p])
        } else {
            //Assign Group (will happen on previous page)
            srService.assignGroup(p)
            redirect(controller: 'rating', action: 'show')
        }
    }

    def saveExpertise() {
        Person p = personService.getForSession(session)
        if (!p.hasConsented) {
            redirect(url : '/')
            return
        }

        for (String key: ['primary', 'secondary', 'tertiary']) {
            if (params[key] != 'none') {
                Interest i = Interest.findByText(params[key])
                if (i == null) {
                    i = new Interest(text: params[key])
                    i.save(flush : true)
                }
                p.setProperty(key, i)
            }
        }
        p.save(flush : true)

        //Assign Group (will happen on previous page)
        srService.assignGroup(p)

        redirect(controller: 'rating', action: 'show')
    }

}