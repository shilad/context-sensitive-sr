package srsurvey

class RatingController {

    def ratings(){

        //TODO: Get the person from the session, delete this part! Testing only!

        if(session.person==null){
            Person p = Person.findByEmail("zwang@macalester.edu")
            session.person = p.id
            Survey s = new Survey()
            p.survey = s
            p.save(flush: true)
            print(s.id)
            session.survey = s.id
            SRService srService = new SRService()
            srService.assignGroup(p, new ArrayList<Interest>())
        }
        Person p = Person.findById(session.person)

        List<Question> questions = new SRService().getQuestions(p.group)

        //Saving the questions
        for (q in questions){
            if (q.id == null){
                q.survey = p.survey
                q.save(flush: true)
            }
        }

        //TODO: Change here to send in all questions
        render(view:'ratings', model:[questions:questions[0..5]])
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
        for(qparam in params){
            if(qparam.key[0..4]=="radio"){
                //"This is the question id "+q.key+" and
                // this is the score "+q.value+". Put these into the database."
                String id = qparam.key[6..-1]
                Question question = Question.get(Integer.parseInt(id))
                question.result = qparam.value
                question.save(flush: true)
            }
        }
        redirect(controller: 'comment', action: 'show')
    }

    def index() {
        render(view:'ratings')
    }
}
