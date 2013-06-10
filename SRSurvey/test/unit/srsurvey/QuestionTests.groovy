package srsurvey



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([Interest])
@TestFor(Question)
class QuestionTests {

    void testQuestion() {
        Question q1 = new Question(0.2,1)

        Interest interest1 = new Interest()
        interest1.text = "interest1"
        interest1.save(flush: true)

        Interest interest2 = new Interest()
        interest2.text = "interest2"
        interest2.save(flush: true)

        q1.setInterest1(interest1)
        q1.setInterest2(interest2)

        Survey s = new Survey()

        q1.setSurvey(s)
        q1.save(flush: true, failOnError: true)

        assertEquals(Interest.count,2)
        assertEquals(Question.count,1)
    }
}
