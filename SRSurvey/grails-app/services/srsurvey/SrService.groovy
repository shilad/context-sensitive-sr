package srsurvey

class SrService {
    public static final int NUM_QUESTIONS_PER_FIELD = 25
    public static final int NUM_QUESTIONS_GENERAL = 10
    public static final int NUM_DUPLICATES = 5
    public static final int MIN_DUP_SPACING = 15
    public static final String [] FIELDS = ['biology', 'psychology', 'history']
    public static final String GROUP_GENERAL = 'general'
    public static final String GROUP_VALIDATION = 'validation'

    // randomly ordered list of experimental group names as shown in FIELDS
    List<String> randomOrder = []

    Map<String, Integer> inGroupCounters = [:]
    Map<String, Integer> outGroupCounters = [:]
    Map<String, List<SrPair>> pairs = [:]

    def init() {
        for (int i : 0..1000) {
            List<String> shuffled = []
            shuffled.addAll(FIELDS)
            Collections.shuffle(shuffled)
            randomOrder.addAll(shuffled)
        }
        for (String g : FIELDS + [GROUP_GENERAL]) {
            inGroupCounters[g] = 0
            outGroupCounters[g] = 0
        }
        for (ExperimentalState e : ExperimentalState.list()) {
            if (e.inGroup) {
                inGroupCounters[e.name] = Math.max(inGroupCounters[e.name], e.counter+1)
            } else {
                outGroupCounters[e.name] = Math.max(outGroupCounters[e.name], e.counter+1)
            }
        }
        readPairs()
    }

    def readPairs() {
        Map<String, String> cleaned = [:]
        for (String line : new File("dat/cleaned_phrases.txt")) {
            String [] tokens = line.split('\t')
            cleaned[tokens[0]] = tokens[1].trim()
        }
        for (String field : FIELDS + [GROUP_GENERAL, GROUP_VALIDATION]) {
            for (String line : new File("dat/${field}.txt")) {
                String [] tokens = line.trim().split('\t')
                if (!cleaned.containsKey(tokens[0])) {
                    throw new IllegalStateException("couldn't find phrase '${tokens[0]}'")
                }
                if (!cleaned.containsKey(tokens[1])) {
                    throw new IllegalStateException("couldn't find phrase '${tokens[1]}'")
                }
                Interest i1 = addInterest(cleaned[tokens[0]])
                Interest i2 = addInterest(cleaned[tokens[1]])
                double sim = tokens[2] as double
                addPair(field, new SrPair(phrase1: i1.text, phrase2: i2.text, sim: sim))
            }
            log.info("read ${pairs[field].size()} for $field")
        }
        for (String g : pairs.keySet()) {
            if (g == GROUP_VALIDATION) {
                continue
            }
            int n = (g == GROUP_GENERAL) ? NUM_QUESTIONS_GENERAL : NUM_QUESTIONS_PER_FIELD
            if (pairs[g].size() % n != 0) {
                throw new IllegalStateException("$g, ${pairs[g].size()}, $n")
            }
        }
    }

    def addInterest(String text) {
        Interest i = Interest.findByText(text)
        if (i == null) {
            i = new Interest(text: text)
            i.save(flush : true)
        }
        return i
    }

    def addPair(String group, SrPair pair) {
        if (pairs.containsKey(group)) {
            pairs[group].add(pair)
        } else {
            pairs[group] = [pair]
        }
    }

    String pickRandomGroup(String groupToSkip=null) {
        if (groupToSkip == null) {
            return randomOrder.remove(0)
        }
        for (int i = 0; i < randomOrder.size(); i++) {
            if (randomOrder[i] != groupToSkip) {
                return randomOrder.remove(i)
            }
        }
        throw new IllegalStateException()
    }

    //Assigning people to group based on a list of interests
    def assignGroup(Person p) {
        for (String key : ['primary', 'secondary', 'tertiary']) {
            String area = ((Interest)p.getProperty(key))?.text?.toLowerCase()
            if (FIELDS.contains(area)) {
                p.group = ExperimentalGroup.findByName(area)
                break
            }
        }
        if (p.group == null) {
            if (p.scholar) {
                p.group = ExperimentalGroup.findByName('scholar')
            } else {
                p.group = ExperimentalGroup.findByName('mturk')
            }
        }
        if (p.group == null) {
            throw new IllegalStateException()
        }
        if (FIELDS.contains(p.group.name)) {
            p.survey.group1 = makeState(p.group.name, true)
        } else {
            p.survey.group1 = makeState(pickRandomGroup(), false)
        }
        p.survey.group2 = makeState(pickRandomGroup(p.survey.group1.name), false)
        p.survey.general = makeState(GROUP_GENERAL, false)
        p.survey.group1.save()
        p.survey.group2.save()
        p.survey.general.save()
        p.save(flush : true)
    }

    //Input a persons group generate 64 (first time) or 60 questions for them
    def List<Question> getQuestions(Person p) {
        List<Question> newQs = []
        for (ExperimentalState e : [p.survey.group1, p.survey.group2, p.survey.general]) {
            Set<SrPair> seen = p.survey.getSeenPairs(e.name)
            if (seen.size() % e.questionsPerRound != 0) {
                log.warn("for person ${p.id}, group ${e.name} seen was ${seen.size()}")
            }
            int round = seen.size() / e.questionsPerRound
            for (SrPair pair : getQuestionsInRound(e, round)) {
                if (seen.contains(pair)) {
                    log.warn("for person ${p.id}, group ${e.name} skipping seen pair ${pair}")
                } else {
                    newQs.add(new Question(
                                    round : round,
                                    interest1 : Interest.findByText(pair.phrase1),
                                    interest2 : Interest.findByText(pair.phrase2),
                                    pmi : pair.sim,
                                    groupName : e.name
                            ))
                }
            }
        }

        // Stratified shuffle
        newQs.sort(true, {q1, q2 -> q1.pmi.compareTo(q2.pmi)})

        // create buckets with increasing pmi values
        int numBins = 5
        def buckets = []
        for (int i : 0..(numBins-1)) buckets.add([])
        for (int i : 0..(newQs.size()-1)) {
            buckets[i*numBins/newQs.size() as int].add(newQs[i])
        }
        // shuffle buckets
        for (def bucket : buckets) {
            Collections.shuffle(bucket)
        }
        // reconstruct stratified shuffle over buckets
        def shuffled = []
        for (int i = 0; shuffled.size() < newQs.size(); i++) {
            if (!buckets[i%numBins].isEmpty()) {
                shuffled.add(buckets[i%numBins].remove(0))
            }
        }

        addDuplicates(shuffled, NUM_DUPLICATES)

        // randomly add validation questions
        if (p.survey.getSeenPairs(null).isEmpty()) {
            for (SrPair pair : pairs[GROUP_VALIDATION]) {
                int i = new Random().nextInt(newQs.size())
                shuffled.add(i, new Question(
                        round : 0,
                        interest1 : Interest.findByText(pair.phrase1),
                        interest2 : Interest.findByText(pair.phrase2),
                        groupName : GROUP_VALIDATION,
                        pmi : pair.sim
                ))
            }
        }
        for (Question q : shuffled) {
            q.maybeSwap()
        }

        return shuffled
    }

    /**
     * Drops in n duplicate questions.
     *
     * The procedure is as follows:
     * - divide up the questions into n+2 segments
     * - for each segment i in [0...n)
     *      - select one question
     *      - drop it somewhere in segment i+1 or after
     *
     * The procedure goes from back to front so questions are only duplicated once.
     *
     * @param qs
     */
    def void addDuplicates(List<Question> qs, int n) {
        double ss = 1.0 * qs.size() / (n+2);     // segment size
        for (int i = n-1; i >= 0; i--) {
            int from = randBetween((int)(i * ss), (int)((i+1)*ss));
            int to = randBetween(from + MIN_DUP_SPACING, qs.size());
            Question q = new Question()
            q.properties = qs[from].properties
            qs.add(to, q);
        }
    }

    def int randBetween(int beg, int end) {
        return beg + new Random().nextInt(end-beg);
    }

    def List<SrPair> getQuestionsInRound(ExperimentalState state, int round) {
        List<SrPair> pairs = new ArrayList(this.pairs[state.name])
        Collections.shuffle(pairs, new Random(state.randomSeed))
        int numRounds = pairs.size() / state.questionsPerRound
        int i = (state.roundOffset + round) % numRounds
        def results = []
        for (; i < pairs.size(); i += numRounds) {
            results.add(pairs[i])
        }
        return results
    }

    def ExperimentalState makeState(String groupName, boolean inGroup) {
        int questionsPerRound = (groupName == GROUP_GENERAL) ? NUM_QUESTIONS_GENERAL : NUM_QUESTIONS_PER_FIELD
        if (pairs[groupName].size() % questionsPerRound != 0) {
            throw new IllegalStateException()
        }
        int maxRounds = pairs[groupName].size() / questionsPerRound
        Map<String, Integer> counter = inGroup ? inGroupCounters : outGroupCounters
        ExperimentalState state = new ExperimentalState(name : groupName, inGroup : inGroup, questionsPerRound : questionsPerRound, maxRounds: maxRounds)
        synchronized (counter) {
            state.counter = counter.get(groupName, 0)
            state.randomSeed = counter[groupName] / maxRounds
            state.roundOffset = counter[groupName] % maxRounds
            counter[groupName] = state.counter+1
        }
        return state
    }
}
