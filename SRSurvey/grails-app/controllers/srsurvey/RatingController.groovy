package srsurvey

class RatingController {
    static public final int QUESTIONS_PER_PAGE = 10

    def personService
    def srService


    def show(){
        Person p = personService.getForSession(session)
        if (!p.hasConsented || p.group == null) {
            redirect(url : '/')
            return
        }
        if (params.page == null || params.page == '') {
            redirectToNextUnfinishedPage(p)
            return
        }

        println("questions are " + p.survey.questions.size())

        int page = params.page as int
        List<Question> toAsk = p.survey.questions.findAll({it.page == page })
        if (toAsk.isEmpty()) throw new IllegalStateException()

        def round = toAsk[0].round
        def inRound = p.survey.questions.findAll({it.round == round})
        def roundBeg = inRound[0].page
        def roundEnd = inRound[-1].page

        render(view:'show', model:[
                questions: toAsk,
                round : round,
                page: page,
                roundBegPage : roundBeg,
                roundEndPage : roundEnd,
        ])
    }

    def redirectToNextUnfinishedPage(Person p) {
        if (p.numAnswers() == p.survey.questions.size()) {
            List<Question> questions = srService.getQuestions(p.group)
            if (questions.isEmpty()) {
                redirect(controller : 'finish', action: 'show', params: ['noMoreQuestions' : 'true'])
                return
            }

            // find the next page of questions
            int maxPage = -1
            int maxRound = -1
            if (!p.survey.questions.isEmpty()) {
                maxPage = p.survey.questions*.page.max()
                maxRound = p.survey.questions*.round.max()
            }

            for (int i = 0; i < questions.size(); i++) {
                Question q = questions[i]
                q.page = 1 + maxPage + i / QUESTIONS_PER_PAGE
                q.round = maxRound + 1
                p.survey.addToQuestions(q)
            }
            p.save(flush : true)
        }
        for (Question q : p.survey.questions) {
            if (!q.hasAnswer()) {
                println("redirecting to " + q.page)
                redirect(action: 'show', params: [page : q.page])
                return
            }
        }
        throw new IllegalStateException()
    }

    def save(){
        Person p = personService.getForSession(session)
        if (!p.hasConsented || p.group == null) {
            redirect(url : '/')
            return
        }

        int page = params.page as int
        List<Question> toAsk = p.survey.questions.findAll({it.page == page })
        if (toAsk.isEmpty()) throw new IllegalStateException()

        for (qparam in params){
            if(qparam.key.startsWith("radio")){
                //"This is the question id "+q.key+" and
                // this is the score "+q.value+". Put these into the database."
                String [] tokens = qparam.key.split("_")
                int qid = tokens[1] as int
                Question question = Question.get(qid)
                question.result = qparam.value as int
                question.interest1Known = true
                question.interest2Known = true
                question.save()
            }
            if (qparam.key.startsWith("unknown")) {
                String [] tokens = qparam.key.split("_")
                int qid = tokens[1] as int
                int iid = tokens[2] as int
                Question question = Question.get(qid)
                if (iid == question.interest1.id) {
                    question.interest1Known = false
                } else if (iid == question.interest2.id) {
                    question.interest2Known = false
                } else {
                    throw new IllegalArgumentException("question " + qid + " has no id " + iid)
                }
                question.result = -1.0
                question.save()
            }
        }

        def round = toAsk[0].round
        def inRound = p.survey.questions.findAll({it.round == round})
        def roundBeg = inRound[0].page
        def roundEnd = inRound[-1].page

        if (page == roundEnd) {
            redirect(controller: 'finish', action: 'show')
        } else {
            redirect(action: 'show', params: [page: page+1])
        }
    }

    def index() {
        redirect(action: 'show')
    }
}
