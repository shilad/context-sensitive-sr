package srsurvey



import grails.test.mixin.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */

@Mock([InterestToGroup, Survey, Interest])
@TestFor(ExperimentalGroup)
class ExperimentalGroupTests {

    void testExpGroup() {

        //Testing the variable
        ExperimentalGroup g = new ExperimentalGroup()
        g.name = "Biology"
        g.save(flush: true)
        assertEquals(g.name,"Biology")
        assertNotSame(g.name,"Bio")

        //Testing InterestToGroup
        Interest interest = new Interest()
        interest.setText("Computer Science")
        interest.save(flush: true)
        assertEquals(interest.text, "Computer Science")

        InterestToGroup interestToGroup = new InterestToGroup()
        interestToGroup.setInterest(interest)
        interestToGroup.setGroup(g)
        interestToGroup.save(flush: true)

        assertEquals(InterestToGroup.count(),1)
        assertEquals(Interest.count(),1)
        assertEquals(ExperimentalGroup.count(),1)

        //Testing Survey
        Survey s1 = new Survey()
        Survey s2 = new Survey()
        Survey s3 = new Survey()

        g.setSurvey([s1,s2,s3])

        assertEquals(Survey.count,3)
    }
}
