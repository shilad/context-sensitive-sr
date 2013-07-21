package srsurvey



import grails.test.mixin.*
import org.junit.Test

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SrService)
@Mock([ExperimentalState, Interest, Survey, ExperimentalGroup, Person, Question])
class SrServiceTests {

    @Test
    void testMakeState() {
        // biology should now start at counter 3
        new ExperimentalState(name : 'biology', counter : 2).save()

        service.init()
        for (int i : 0..10) {
            ExperimentalState e = service.makeState('general', false)
            assertEquals(e.name, 'general')
            assertEquals(e.questionsPerRound, 10)
            assertEquals(e.inGroup, false)
            assertEquals(e.randomSeed, (int)(i / 5))
            assertEquals(e.counter, i)
            assertEquals(e.roundOffset, i % 5)
            assertEquals(e.maxRounds, 5)
        }

        for (int i : 3..10) {
            ExperimentalState e = service.makeState('biology', false)
            assertEquals(e.name, 'biology')
            assertEquals(e.questionsPerRound, 25)
            assertEquals(e.inGroup, false)
            assertEquals(e.randomSeed, (int)(i / 2))
            assertEquals(e.counter, i)
            assertEquals(e.roundOffset, i % 2)
            assertEquals(e.maxRounds, 2)
        }
    }

    @Test
    void testGroupAssignment() {
        setupGroups()
        service.init()

        def groupCounts = [:]
        def offsetCounts = [:]
        def seedCounts = [:]
        for (String n : SrService.FIELDS) {
            groupCounts[n] = 0
        }

        // Test people without primary groups who are scholars
        for (int i : 0..29) {
            Person p = new Person(scholar : true, survey: new Survey(), primary : 'foobar')
            service.assignGroup(p)
            assertNotNull(p.survey.group1)
            assertNotNull(p.survey.group2)
            assertNotNull(p.survey.general)
            assertFalse(p.survey.group1.inGroup)
            assertFalse(p.survey.group2.inGroup)
            groupCounts[p.survey.group1.name]++
            groupCounts[p.survey.group2.name]++
            assertFalse(p.survey.group1.name.equals(p.survey.group2.name))
            for (ExperimentalState e : [p.survey.group1, p.survey.group2]) {
                if (e.name == 'biology') {
                    offsetCounts[e.roundOffset] = offsetCounts.get(e.roundOffset, 0) + 1
                    seedCounts[e.randomSeed] = seedCounts.get(e.randomSeed, 0) + 1
                }
            }
        }

        assertEquals(groupCounts.size(), 3)
        for (String g : groupCounts.keySet()) {
            assertTrue(SrService.FIELDS.contains(g))
            assertEquals(groupCounts[g], 20)
        }

        assertEquals(offsetCounts.size(), 2)
        assertEquals(offsetCounts[0], 10)
        assertEquals(offsetCounts[1], 10)

        assertEquals(seedCounts.size(), 10)
        assertEquals(seedCounts[0], 2)
        assertEquals(seedCounts[1], 2)



        // Test people who are biologists
        groupCounts.clear()
        for (String n : SrService.FIELDS) {
            groupCounts[n] = 0
        }
        for (int i : 0..29) {
            Person p = new Person(scholar : true, survey: new Survey(), primary : 'biology')
            service.assignGroup(p)
            assertNotNull(p.survey.group1)
            assertNotNull(p.survey.group2)
            assertNotNull(p.survey.general)
            assertTrue(p.survey.group1.inGroup)
            assertFalse(p.survey.group2.inGroup)
            groupCounts[p.survey.group1.name]++
            groupCounts[p.survey.group2.name]++
        }
        assertEquals(groupCounts.size(), 3)
        assertEquals(groupCounts['biology'], 30)
        assertEquals(groupCounts['psychology'], 15)
        assertEquals(groupCounts['history'], 15)
    }

    @Test
    def void testQuestions() {
        setupGroups()
        service.init()
        Person p = new Person(scholar : true, survey: new Survey(), primary : 'biology')
        service.assignGroup(p)
        List<Question> qs = service.getQuestions(p)
        assertEquals(qs.size(), 60)
        def pairs = [:]
        for (Question q : qs) {
            p.survey.addToQuestions(q)
            if (!pairs.containsKey(q.groupName)) {
                pairs[q.groupName] = []
            }
            assertFalse(pairs[q.groupName].contains(q.toSrPair()))
            pairs[q.groupName].add(q.toSrPair())
        }

        String group2 = p.survey.group2.name
        assertEquals(pairs.size(), 3)
        assertEquals(pairs['biology'].size(), 25)
        assertEquals(pairs['general'].size(), 10)
        assertEquals(pairs[group2].size(), 25)
        assert(group2 != 'biology')


        qs = service.getQuestions(p)
        assertEquals(qs.size(), 60)
        for (Question q : qs) {
            p.survey.addToQuestions(q)
            assertFalse(pairs[q.groupName].contains(q.toSrPair()))
            pairs[q.groupName].add(q.toSrPair())
        }

        assertEquals(pairs.size(), 3)
        assertEquals(pairs['biology'].size(), 50)
        assertEquals(pairs['general'].size(), 20)
        assertEquals(pairs[group2].size(), 50)
    }

    def void setupGroups() {
        for (String g : SrService.FIELDS + ['general', 'mturk', 'scholar']) {
            new ExperimentalGroup(name : g).save(flush : true)
        }
    }
}
