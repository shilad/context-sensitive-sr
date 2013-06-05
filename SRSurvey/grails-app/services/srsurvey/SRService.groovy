package srsurvey
import java.util.Random

class SRService {

    //Return a ranking for a pair of interests
    def double getRating(Interest interest1, Interest interest2) {
        Random rand = new Random()
        int max = 10
        double num = rand.nextInt(max)/10.0
        return num
    }

    //Assigning people to group based on a list of interests
    def assignGroup(Person person, List<Interest> interests) {

        Random rand = new Random()
        int max = 2
        int num = rand.nextInt(max)

        person.setExperimentalGroup(ExperimentalGroup.findAll().get(num))
        person.save(flush: true)

    }
}
