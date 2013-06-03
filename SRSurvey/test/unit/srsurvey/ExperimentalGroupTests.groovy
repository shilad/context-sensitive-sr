package srsurvey



import grails.test.mixin.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ExperimentalGroup)
class ExperimentalGroupTests {

    void testSomething() {
        ExperimentalGroup g = new ExperimentalGroup()
        g.name = "Biology"
        g.save(flush: true)
        assertEquals(g.name,"Biology")
        assertNotSame(g.name,"Bio")
    }
}
