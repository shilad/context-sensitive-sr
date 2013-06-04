package srsurvey



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@Mock([Person, Interest])
@TestFor(PersonToInterest)
class PersonToInterestTests {

    void testPersonToInterest() {
        Person p = new Person()
        p.setFirstName("Ken")
        p.setLastName("Wang")
        p.setIsRegistered(false)
        p.setTitle("Student")
        p.setEmail("demo@macademia")
        p.save(failOnError: true, flush: true)

        Person p2 = new Person("p.jackson@demo","Peter","Jackson","Director",false)
        p2.save(failOnError: true, flush: true)

        assertEquals(Person.count, 2)
        assertNotNull(Person.findByLastName("Wang"))
        assertNotNull(Person.findByLastName("Jackson"))


        Interest i = new Interest()
        i.setText("Computer Science")
        i.save(failOnError: true, flush: true)
        assertEquals(Interest.count, 1)
    }
}
