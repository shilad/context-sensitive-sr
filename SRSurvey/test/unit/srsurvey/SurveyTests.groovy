package srsurvey



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([Interest,Question])
@TestFor(Survey)
class SurveyTests {

    void testSurvey() {

        //Setting up question 1
        Question q1 = new Question(0.2,1)

        Interest interest1 = new Interest()
        interest1.text = "interest1"
        interest1.save(flush: true)

        Interest interest2 = new Interest()
        interest2.text = "interest2"
        interest2.save(flush: true)

        q1.setInterest1(interest1)
        q1.setInterest2(interest2)

        Survey s1 = new Survey()

        q1.setSurvey(s1)
        q1.save(flush: true, failOnError: true)

        assertEquals(Interest.count,2)
        assertEquals(Question.count,1)


        //Setting up question 2
        Question q2 = new Question(0.5,2)

        Interest interest3 = new Interest()
        interest3.text = "interest1"
        interest3.save(flush: true)

        Interest interest4 = new Interest()
        interest4.text = "interest2"
        interest4.save(flush: true)

        q2.setInterest1(interest3)
        q2.setInterest2(interest4)

        Survey s2 = new Survey()

        q2.setSurvey(s2)
        q2.save(flush: true, failOnError: true)

        assertEquals(Interest.count,4)
        assertEquals(Question.count,2)


        //Testing survey.css, each survey.css can have many questions
        Survey s =  new Survey()
        Person p = new Person()
        ExperimentalGroup g = new ExperimentalGroup()
        s.setQuestion([q1,q2] as Set<Question>)
        s.setPerson(p)
        s.setExperimentalGroup(g)
        s.save(failOnError: true, flush: true)

        //Testing delete
        //Question resultQ = Question.findByQuestionNumber(2) //find question 2
        //resultQ.delete()

        Survey.findAll()[0].delete()
        assertEquals(Survey.count,0)

        assertEquals(Question.count,0)

    }
}
