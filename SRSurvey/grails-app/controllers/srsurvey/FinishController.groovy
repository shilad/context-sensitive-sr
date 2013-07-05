package srsurvey

class FinishController {

    def show() {
        render(view: "comment")
    }

    def process() {
        //TODO: limiting the size of the input string on javascript

        //Find the survey based off the person in session
        Survey s = Survey.findByPerson(Person.findById(session.person))


        //Set the comment for a particular survey
        s.setComment(params["text-area"])
        s.save(flush: true)

        //TODO: change this to the final view when created
        render(view: "final")
    }

    def test() {
        if (!session.person) {
            Person p = Person.findByEmail("bhillman@macalester.edu")
            session.person = p.id
        }
        redirect(action: 'show')
    }

}


