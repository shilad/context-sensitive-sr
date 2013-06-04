package srsurvey



import grails.test.mixin.*
import org.hibernate.validator.constraints.impl.SizeValidatorForArraysOfLong
import org.junit.*

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

        //test relations

        //Survey

        ExperimentalGroup g = new ExperimentalGroup()
        g.setName("Math")
        g.save(flush:true)
        assertEquals(g.name, "Math")

        /*Interest interest1 = Interest("Sports")
        Interest interest2 = Interest("Movies")

        Question q = new Question(23.23, 2, interest1, interest2)
        q.save(flush:true)
        assertEquals(q.questionNumber,2)

        Survey s = new Survey(p, g)
        s.save(flush:true)

        assertEquals(s.experimentalGroup, g)
        assertEquals(s.person, p)





















        g.save(flush:true)

        q.save(flush:true)

        s.setPerson(p)
        s.setGroup(g)
        s.setQuestion(q)

        s.save(flush:true)


        assertEquals(s.getPerson(), p)
        Person a = new Person()
        a.save(flush:true)
        assertNotSame(s.getPerson(), a)

        assertEquals(s.getGroup(), g)
        ExperimentalGroup b = new ExperimentalGroup()
        b.save(flush:true)
        assertNotSame(s.getGroup(), b)

        assertEquals(s.getQuestion(), q)
        ExperimentalGroup m = new ExperimentalGroup()
        m.save(flush:true)
        assertNotSame(s.getQuestion(), m)

        //Person to Interest
        PersonToInterest PtI = new PersonToInterest()
        Interest i = new Interest()
        i.save(flush:true)

        PtI.setPerson(p)
        PtI.setInterest(i)
        PtI.save(flush:true)

        assertEquals(PtI.getPerson(),p)
        assertNotSame(PtI.getPerson(),a)

        assertEquals(PtI.getInterest(),i)
        Interest j = new Interest()
        j.save(flush:true)
        assertNotSame(PtI.getInterest(),j) */
    }
}
