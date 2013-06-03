package srsurvey

import static org.junit.Assert.*
import org.junit.*

class InterestToGroupIntegrationTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testSomething() {
        InterestToGroup ig = new InterestToGroup()
        Interest interest = new Interest()
        interest.setText("Darwin")
        ExperimentalGroup g = new ExperimentalGroup()
        g.setName("Biology")
        g.save(flush: true, failOnError: true)
        interest.save(flush: true,failOnError: true)
        ig.setGroup(g)
        ig.setInterest(interest)

        ig.save(flush: true,failOnError: true)

        assertEquals(ig.findAll().size(), 1)
    }
}
