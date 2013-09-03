package srsurvey

class MturkService {

    List<String[]> hits = []

    public def init() {
        Set<String> usedIds = new HashSet<String>(Person.findAllByMturkIdIsNotNull().mturkId)
        for (String line : new File("dat/hits.txt")) {
            String [] tokens = line.split()
            String mturkId = tokens[0].trim()
            String code = tokens[1].trim()
            if (!usedIds.contains(mturkId)) {
                hits.add([mturkId, code] as String[])
            }
        }
        println("loaded ${hits.size()} remaining hits")
    }

    public synchronized def makeIdAndCode() {
        return hits.remove(0)
    }
}
