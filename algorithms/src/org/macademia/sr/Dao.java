package org.macademia.sr;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author Shilad Sen
 */
public class Dao {
    private static final Logger LOG = Logger.getLogger(Dao.class.getName());

    private final Tokenizer tokenizer;
    private final Map<String, Person> people;
    private final Map<String, Interest> interests;
    private final Map<String, Interest> denormalized;

    public Dao() throws IOException {
        this.tokenizer = new Tokenizer();
        this.interests = readInterests("dat/phrases.tsv");
        this.people = readPeople("dat/people.txt");
        addPersonInterests("dat/people_interests.txt");
        addPersonDepts("dat/person_departments.csv");
        denormalized = makeCanonicalMap();
    }

    public Collection<Person> getPeople() {
        return people.values();
    }

    public Person getPerson(String id) {
        return people.get(id);
    }

    public Interest getInterest(String id) {
        return interests.get(id);
    }

    public Interest getFromNormalizedText(String normalized) {
        return denormalized.get(normalized);
    }

    public Map<String, Interest> makeCanonicalMap() {
        Map<String, HashMap<Interest, Integer>> normalizedCounts = new HashMap<String, HashMap<Interest, Integer>>();
        for (Person p : people.values()) {
            for (Interest i : p.getInterests()) {
                if (i == null) {
                    continue;
                }
                String cleaned = tokenizer.normalize(i.getName());
                if (cleaned.isEmpty()) {
                    continue;
                }
                if (!normalizedCounts.containsKey(cleaned)) {
                    normalizedCounts.put(cleaned, new HashMap<Interest, Integer>());
                }
                Integer c = normalizedCounts.get(cleaned).get(i);
                c = (c == null) ? 0 : c;
                normalizedCounts.get(cleaned).put(i, c + 1);
            }
        }

        Map<String, Interest> canonical = new HashMap<String, Interest>();
        for (String cleaned : normalizedCounts.keySet()) {
            Map<Interest, Integer> versions = normalizedCounts.get(cleaned);
            Interest mostCommon = null;
            for (Interest raw : versions.keySet()) {
                if (mostCommon == null || versions.get(raw) > versions.get(mostCommon)) {
                    mostCommon = raw;
                }
            }
            canonical.put(cleaned, mostCommon);
        }

        return canonical;
    }

    public LinkedHashMap<Interest, Integer> topInterests(String dept, int minPeople) {
        final TObjectIntMap<String> counts = new TObjectIntHashMap<String>();
        for (Person person : people.values()) {
            if (person.getDepartments().contains(dept.toLowerCase())) {
                for (Interest i : person.getInterests()) {
                    if (i != null) {
                        String text = tokenizer.normalize(i.getName());
                        if (text.length() > 0) {
                            counts.adjustOrPutValue(text, 1, 1);
                        }
                    }
                }
            }
        }

        List<String> interests = new ArrayList<String>(counts.keySet());
        Collections.shuffle(interests);     // break ties randomly
        Collections.sort(interests, new Comparator<String>() {
            @Override
            public int compare(String i1, String i2) {
                return counts.get(i2) - counts.get(i1);
            }
        });

        LinkedHashMap<Interest, Integer> result = new LinkedHashMap<Interest, Integer>();
        for (String i : interests) {
            if (counts.get(i) >= minPeople) {
                result.put(getFromNormalizedText(i), counts.get(i));
            }
        }
        return result;
    }

    private Map<String, Person> readPeople(String peoplePath) throws IOException {
        Map<String, Person> people = new HashMap<String, Person>();
        for (String line : FileUtils.readLines(new File(peoplePath))) {
            String tokens[] = line.split("\t");
            people.put(tokens[0].trim(), new Person(tokens[0].trim(), tokens[1].trim()));
        }
        return people;
    }

    private Map<String, Interest> readInterests(String interestPath) throws IOException {
        Map<String, Interest> interests = new HashMap<String, Interest>();
        for (String line : FileUtils.readLines(new File(interestPath))) {
            String tokens[] = line.split("\t");
            interests.put(tokens[1], new Interest(tokens[3], tokens[1], tokens[0]));
        }
        return interests;
    }

    private void addPersonInterests(String personInterestPath) throws IOException {
        for (String line : FileUtils.readLines(new File(personInterestPath))) {
            String tokens[] = line.split("\t");
            String pid = tokens[0].trim();
            String iid = tokens[1].trim();
            if (!people.containsKey(pid)) {
                LOG.warning("unknown pid: '" + pid + "'");
            } else if (!interests.containsKey(iid)) {
                LOG.warning("unknown iid: " + iid + "'");
            } else {
                people.get(pid).addInterest(interests.get(iid));
            }
        }
    }

    private void addPersonDepts(String deptPath) throws IOException {
        for (String line : FileUtils.readLines(new File(deptPath))) {
            String tokens[] = line.split(",");
            String pid = tokens[1].trim();
            if (!people.containsKey(pid)) {
                LOG.warning("unknown pid: '" + pid + "'");
                continue;
            }
            for (String dept : Arrays.copyOfRange(tokens, 2, tokens.length)) {
                dept = dept.replace("\"", "").trim().toLowerCase();
                people.get(pid).addDepartment(dept);
            }
        }
    }

    public static void main(String args[]) throws IOException {
        Dao di = new Dao();
        for (String dept : new String[] {"history", "psychology", "biology"}) {
            System.out.println("top interests for " + dept);
            LinkedHashMap<Interest, Integer> counts = di.topInterests(dept, 2);
            int i = 0;
            for (Interest interest : counts.keySet()) {
                System.out.println("\t" + (++i) + "\t" + counts.get(interest) + "\t" + interest.getName());
            }
        }
    }
}
