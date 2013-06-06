package srsurvey

class RatingController {

    def ratings(){

        //TODO: Get the person from the session

        Person p = Person.findByEmail("zwang@macalester.edu")
        print(p)

        SRService srService = new SRService()
        srService.assignGroup(p, new ArrayList<Interest>())

        List<Question> questions = new SRService().getQuestions(p.group)

        render(view:'ratings', model:[questions:questions])
    }

    def processForm(){
        print(params)
    }

    def index() {
        render(view:'ratings')
    }
}
