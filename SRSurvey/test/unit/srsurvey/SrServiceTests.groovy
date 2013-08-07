package srsurvey



import grails.test.mixin.*
import org.junit.Before
import org.junit.Test

/**
 */
@TestFor(SrService)
@Mock([ExperimentalState, Interest, Survey, ExperimentalGroup, Person, Question])
class SrServiceTests {


    boolean serviceWasInited = false
    def initService() {
        if (!serviceWasInited) {
            setupGroups()
            service.init()
            serviceWasInited = true
        }
    }

    @Before
    void reset() {
        serviceWasInited = false
    }

    @Test
    void testMakeState() {
        // biology should now start at counter 3
        new ExperimentalState(name : 'biology', counter : 2).save()
        initService()

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
        initService()

        def groupCounts = [:]
        def offsetCounts = [:]
        def seedCounts = [:]
        for (String n : SrService.FIELDS) {
            groupCounts[n] = 0
        }

        // Test people without primary groups who are scholars
        for (int i : 0..(3 * 2 * 5 - 1)) {
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
        for (int i : 0..(3 * 2 * 10 - 1)) {
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
        assertEquals(groupCounts['biology'], 60)
        assertEquals(groupCounts['psychology'], 30)
        assertEquals(groupCounts['history'], 30)
    }

    @Test
    def void testQuestionsRepeated() {
        for (int i : 0..100) {
            println("testing $i")
            testQuestions()
        }
    }

    @Test
    def void testQuestions() {
        initService()
        Person person = new Person(scholar : true, survey: new Survey(), primary : 'biology')
        service.assignGroup(person)
        List<Question> qs = service.getQuestions(person)
        assertEquals(qs.size(), 69)
        def pairs = [:]
        def dupes = [:]
        def indexes = [:]
        int i = 0
        int j = 0
        def pmis = []
        for (Question q : qs) {
            q.questionNumber = j++
            person.survey.addToQuestions(q)
            if (!pairs.containsKey(q.groupName)) {
                pairs[q.groupName] = []
            }
            pmis.add([ pmi : q.pmi, questionNumber : q.questionNumber])
            def p = q.toSrPair()
            if (!pairs[q.groupName].contains(p)) {
                if (q.groupName != 'validation')
                    indexes[q] = i++      // indexes for original qs
                pairs[q.groupName].add(q.toSrPair())
            } else {
                assertFalse(dupes.containsKey(p))
                def orig = qs.find({it.toSrPair().equals(p)})
                assertNotSame(q, orig)
                dupes[p] =[orig, q]
            }
        }

        // make sure the dupes seem reasonable
        def segments = []
        for (def pair : dupes.keySet()) {
            assertEquals(dupes[pair].size(), 2)
            Question first = dupes[pair][0]
            Question second = dupes[pair][1]
            assert(second.questionNumber - first.questionNumber >= 15)
            segments.add(getSegment(indexes[first]))
        }
        Collections.sort(segments)
        assertEquals(segments, [0,1,2,3,4])

        // make sure pmis seem reasonable by summing the ordinal rankings for each bin
        pmis.sort(true, {pmi1, pmi2-> pmi1.pmi.compareTo(pmi2.pmi)})
        def ranks = []
        def numBins = 5
        for (int b : 0..(numBins-1)) {ranks.add([]) }
        for (i = 0; i < pmis.size(); i++) {
            ranks[i * numBins / pmis.size() as int].add(pmis[i].questionNumber)
        }
        def means = ranks.collect({1.0 * it.sum() / it.size()})
        assert(means.min() / means.max() >= 0.50)

        String group2 = person.survey.group2.name
        assertEquals(pairs.size(), 4)
        assertEquals(pairs['biology'].size(), 25)
        assertEquals(pairs['general'].size(), 10)
        assertEquals(pairs['validation'].size(), 4)
        assertEquals(pairs[group2].size(), 25)
        assert(group2 != 'biology')

        qs = service.getQuestions(person)
        assertEquals(qs.size(), 65)
        for (Question q : qs) {
            person.survey.addToQuestions(q)
            if (!pairs[q.groupName].contains(q.toSrPair())) {
                pairs[q.groupName].add(q.toSrPair())
            }
        }

        assertEquals(pairs.size(), 4)
        assertEquals(pairs['validation'].size(), 4)
        assertEquals(pairs['biology'].size(), 50)
        assertEquals(pairs['general'].size(), 20)
        assertEquals(pairs[group2].size(), 50)
    }

    def int getSegment(int i) {
        double ss = 1.0 * 60 / 7;     // segment size
        for (int s = 4; s >= 0; s--) {
            if (i >= (int)(ss * s)) {
                return s
            }
        }
        throw new IllegalArgumentException()
    }

    def void setupGroups() {
        for (String g : SrService.FIELDS +
                ['general', 'mturk', 'scholar']) {
            if (ExperimentalGroup.findByName(g) == null) {
                new ExperimentalGroup(name : g).save(flush : true)
            }
        }
    }
}
