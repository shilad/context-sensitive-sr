package srsurvey

import static org.junit.Assert.*
import org.junit.*
import srsurvey.*

class SRserviceIntegrationTests {

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
        SRService srService = new SRService()
        List<Question> questions = srService.getQuestions(ExperimentalGroup.findByName("Grass"))
        for (i in questions) {
            print i.interest1.text
            print i.interest2.text
            print i.questionNumber
        }
        Person p = Person.findByEmail("bhillman@macalester.edu")
        srService.assignGroup(p, new ArrayList<Interest>())
    }
}
