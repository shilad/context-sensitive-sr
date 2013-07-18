package srsurvey

class SrService {
    Map<String, List<SrPair>> pairs = [:]


    def addPair(String group, SrPair pair) {
        if (pairs.containsKey(group)) {
            pairs[group].add(pair)
        } else {
            pairs[group] = [pair]
        }
    }

    //Return a ranking for a pair of interests
    def double getRating(Interest interest1, Interest interest2) {
        Random rand = new Random()
        int max = 10
        double num = rand.nextInt(max)/10.0
        return num
    }

    //Assigning people to group based on a list of interests
    def assignGroup(Person person) {
        List<String> fields = ['history', 'psychology', 'biology']
        for (String key : ['primary', 'secondary', 'tertiary']) {
            String area = ((String)person.getProperty(key))?.toLowerCase()
            if (fields.contains(area)) {
                person.group = ExperimentalGroup.findByName(area)
                break
            }
        }
        if (person.group == null && person.scholar) {
            person.group = ExperimentalGroup.findByName('scholar')
        } else {
            person.group = ExperimentalGroup.findByName('mturk')
        }
        person.save(flush : true)
    }

    //Input is a group and output is a set of interests
    def List<Interest> getInterests(ExperimentalGroup g) {
        List<Interest> interests = InterestToGroup.findAllByGroup(g)
        return interests
    }

    //Input a persons group generate 50 questions for them
    def List<Question> getQuestions(ExperimentalGroup g) {

        List<Question> questions = new ArrayList<Question>()

        //Find the survey the questions belong to
        //Survey s = new Survey(Person.findByGroup(g))
        Survey s = Survey.findByPerson(Person.findByGroup(g))

        List<InterestToGroup> personInterests = InterestToGroup.findAllByGroup(g)

        //TODO: Code for generating the cross group to generate questions when ready
        List<InterestToGroup> crossInterests = InterestToGroup.findAllByGroupNotEqual(g)

        //TODO: Code for the random other questions not related to a specific group
        List<Interest> allInterests = Interest.findAll()

        //Set up the random number generator
        Random rand = new Random()
        int num1
        int num2

        //Counter also used for questions number
        int i = 0

        //First 20 generated Questions where Interests are from within the same Group
        while (i++ <= 20) {
            num1 = rand.nextInt(personInterests.size())
            num2 = rand.nextInt(personInterests.size())

            Question q = new Question(i, personInterests.get(num1).interest, personInterests.get(num2).interest, s)
            questions.add(q)
            }
        //Next 20 generated Questions where Interests are from Persons Group and all other Group
        while(i++ <= 40) {
            num1 = rand.nextInt(personInterests.size())
            num2 = rand.nextInt(crossInterests.size())
            Question q = new Question(i, personInterests.get(num1).interest, crossInterests.get(num2).interest, s)
            questions.add(q)
        }
        //Last 10 totally random generated questions
        while(i++ < 50) {
            num1 = rand.nextInt(allInterests.size())
            num2 = rand.nextInt(allInterests.size())
            Question q = new Question(i, allInterests.get(num1), allInterests.get(num2), s)
            questions.add(q)
        }

         return questions
    }

}
