package srsurvey

import static org.junit.Assert.*
import org.junit.*

class ExperimentalGroupIntegrationTests {

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testRelations() {
        ExperimentalGroup g = ExperimentalGroup.findAll().get(0)
        Person p = Person.get(0)
        g.person = p
        g.save()
    }
}
