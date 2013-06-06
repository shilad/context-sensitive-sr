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
        for(i in params['checks']){
            print("add this into database for 'I don't know' checkbox")
        }
        for(q in params){
            if(q.key!="action"&&q.key!="controller"){
                print("This is the question number "+q.key+" and this is the score "+q.value+". Put these into the database.")
            }
        }
    }

    def index() {
        render(view:'ratings')
    }
}
