package srsurvey

class RatingController {

    def ratings(){

        //TODO: Get the person from the session

        Person p = Person.findByEmail("zwang@macalester.edu")
        print(p)

        SRService srService = new SRService()
        srService.assignGroup(p, new ArrayList<Interest>())

        List<Question> questions = new SRService().getQuestions(p.group)

        render(view:'ratings', model:[questions:questions.get(0)])
    }

    def processForm(){
        for(i in params['checks']){
            //"add this into database for 'I don't know' checkbox"
            //i is the id for interest
            Interest interest = Interest.get(i)
            int count = interest.unknown
            interest.unknown++
            interest.save(flush: true)
        }
        for(q in params){
            print(q.key[0..4])
            if(q.key[0..4]=="radio"){
                print("This is the question number "+q.key+" and this is the score "+q.value+". Put these into the database.")
            }
        }
    }

    def index() {
        render(view:'ratings')
    }
}
