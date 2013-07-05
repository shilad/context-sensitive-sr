package srsurvey

import org.junit.*

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
        SrService srService = new SrService()
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
