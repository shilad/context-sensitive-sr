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
//        questions.add(q)

        Person p = Person.findByEmail("bhillman@macalester.edu")
        print(p)
        Survey s = Survey.findByPerson(p)
        print(s)
        List<Question> questions = Question.findAllBySurvey(s)
        print(questions)

        render(view:'ratings', model:[questions:questions])
    }

    def index() {
        render(view:'ratings')
    }
}
