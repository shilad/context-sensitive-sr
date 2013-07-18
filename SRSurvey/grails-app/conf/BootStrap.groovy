import srsurvey.*

class BootStrap {
    def srService

    def init = { servletContext ->
        for (String field : ['biology', 'psychology', 'history', 'general']) {
            addGroup(field)
            for (String line : new File("dat/${field}.txt")) {
                String [] tokens = line.trim().split('\t')
                Interest i1 = addInterest(tokens[0])
                Interest i2 = addInterest(tokens[1])
                double sim = tokens[2] as double
                srService.addPair(field, new SrPair(phrase1: i1.text, phrase2: i2.text, sim: sim))
            }
        }
        addGroup('scholar')
        addGroup('mturk')

        for (String name : srService.pairs.keySet()) {
            println("for group $name created ${srService.pairs[name].size()} pairs")
        }
    }

    def addGroup(String name) {
        if (ExperimentalGroup.findByName(name) == null) {
            ExperimentalGroup e = new ExperimentalGroup(name : name)
            e.save(flush : true)
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

    def destroy = {

    }
}
