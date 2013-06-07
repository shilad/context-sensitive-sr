package srsurvey

class RatingController {

    def ratings(){
        //List<Question> questions = new ArrayList<Question>()
//        Question q = new Question()
//        Interest interest1 = new Interest("Experiment")
//        interest1.save(flush: true)
//        Interest interest2 = new Interest("Computer Science")
//        interest2.save(flush: true)
//        q.setInterest1(interest1)
//        q.setInterest2(interest2)
//        q.setQuestionNumber(1)
//        q.save(flush: true)
//        questions.add

        print(params)

        Person p = Person.findByEmail("bhillman@macalester.edu")
        //print(p)
        Survey s = Survey.findByPerson(p)
        //print(s)
        List<Question> questions = Question.findAllBySurvey(s)
        //print(questions)

        render(view:'ratings', model:[questions:questions])
    }

    def processForm(){
        print(params)
        for(i in params['checks']){
            print("add this into database for 'I don't know' checkbox")
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
