package srsurvey

class ExpertiseController {
    def personService
    def srService
    def loggingService

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
        if (params['gender-no']) {
            p.gender = "NO_RESPONSE"
        } else {
            p.gender = params.gender
        }
        p.education = params.education

        p.save(flush : true)
        loggingService.append(p, request, "scholar\t${p.scholar}")
        loggingService.append(p, request, "gender\t${p.gender}")
        loggingService.append(p, request, "education\t${p.education}")
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
                    i = new Interest(text: (String)params[key])
                    i.save(flush : true)
                }
                p.setProperty(key, i)
            }
        }

        loggingService.append(p, request, "interests\t${params.primary}\t${params.secondary}\t${params.tertiary}")
        srService.assignGroup(p)
        loggingService.append(p, request, "groups\t${p.group.name}\t${p.survey.group1.name}\t${p.survey.group2.name}")

        redirect(controller: 'rating', action: 'show')
    }

}