import srsurvey.*

class BootStrap {
    def srService

    def init = { servletContext ->
        for (String group : ['biology', 'psychology', 'history', 'general', 'scholar', 'mturk']) {
            if (ExperimentalGroup.findByName(group) == null) {
                ExperimentalGroup e = new ExperimentalGroup(name : group)
                e.save(flush : true)
            }
        }
        srService.readPairs()
        for (String name : srService.pairs.keySet()) {
            println("for group $name created ${srService.pairs[name].size()} pairs")
        }
    }

    def destroy = {

    }
}
