package srsurvey
import grails.test.mixin.*
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([Survey, PersonToInterest, Interest, Question, ExperimentalGroup])
@TestFor(Person)
class PersonTests {
    void testPerson()
    {
        //test variables
        Person p = new Person()
        p.email="johnSmith@aol.com"
        p.firstName="John"
        p.setIsRegistered(Boolean.TRUE)
        p.lastName="Smith"
        p.setTitle("Professor")
        p.save(flush:true)
        assertEquals(p.email,"johnSmith@aol.com")
        assertNotSame(p.email,"derp@derp.com")
        assertEquals(p.firstName,"John")
        assertNotSame(p.firstName,"Georgie")
        assertEquals(p.isRegistered,Boolean.TRUE)
        assertNotSame(p.isRegistered,Boolean.FALSE)
        assertEquals(p.lastName,"Smith")
        assertNotSame(p.lastName,"Fry")
        assertEquals(p.title,"Professor")
        assertNotSame(p.title,"President")
    }
}
