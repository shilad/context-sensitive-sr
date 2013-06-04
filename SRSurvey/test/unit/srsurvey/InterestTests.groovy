package srsurvey



import grails.test.mixin.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock ([ExperimentalGroup, InterestToGroup, Person, PersonToInterest, Question, Survey])
@TestFor(Interest)
class InterestTests {

    void testSomething() {

        //Test for add new Interest
        Interest i = new Interest("Biology")
        i.save(flush: true, failOnError: true)
        assertTrue(i.text.equals("Biology"))
        assertNotSame("Bio", i.text)

        //Test for Interest many-to-many interaction with ExperimentalGroup via InterestToGroup
        ExperimentalGroup g1 = new ExperimentalGroup("Tree")
        ExperimentalGroup g2 = new ExperimentalGroup("Flower")
        ExperimentalGroup g3 = new ExperimentalGroup("Animal")

        InterestToGroup ig1 = new InterestToGroup(i, g1)
        InterestToGroup ig2 = new InterestToGroup(i, g2)
        InterestToGroup ig3 = new InterestToGroup(i, g3)

        assertTrue(ig1.interest == i && ig1.group == g1)
        assertTrue(ig2.interest == i && ig2.group == g2)
        assertTrue(ig3.interest == i && ig3.group == g3)

        //Test for Interest many-to-many interaction with Person via PersonToInterest
        Person p1 = new Person()
        Person p2 = new Person()
        Person p3 = new Person()

        PersonToInterest pi1 = new PersonToInterest(p1, i)
        PersonToInterest pi2 = new PersonToInterest(p2, i)
        PersonToInterest pi3 = new PersonToInterest(p3, i)

        assertTrue(pi1.person == p1 && pi1.interest == i)
        assertTrue(pi2.person == p2 && pi2.interest == i)
        assertTrue(pi3.person == p3 && pi3.interest == i)

        //Test for bidirectional mapping and the many-to-one relationship with Question
        Interest i2 = new Interest("Hydrology")
        i2.save(flush: true, failOnError: true)

        Survey s = new Survey()

        Question q1 = new Question()
        q1.setResult(1)
        q1.setQuestionNumber(1)
        q1.setInterest1(i)
        q1.setInterest2(i2)
        q1.setSurvey(s)
        q1.save(flush: true, failOnError: true)

        assertTrue(q1.interest1.text == "Biology" && q1.interest2.text == "Hydrology")

        Question q2 = new Question()
        q2.setResult(2)
        q2.setQuestionNumber(2)
        q2.setInterest1(i2)
        q2.setInterest2(i)
        q2.setSurvey(s)
        q2.save(flush: true, failOnError: true)

        assertTrue(q2.interest1.text == "Hydrology" && q2.interest2.text == "Biology")

    }
}