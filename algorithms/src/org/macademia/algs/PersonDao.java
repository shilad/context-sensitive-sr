package org.macademia.algs;

import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;

import java.io.IOException;
import java.util.*;

/**
 * @author Shilad Sen
 */
public class PersonDao {
    private final Tokenizer tokenizer;
    private final ArrayList<Person> people;

    public PersonDao() throws IOException {
        this.people = People_Interests.readPeople(
                            "dat/people.txt",
                            "dat/phrases.tsv",
                            "dat/people_interests.txt",
                            "dat/person_departments.csv");
        this.tokenizer = new Tokenizer();
    }

    public List<Person> getPeople() {
        return people;
    }

    public Map<String, String> getCanonicalMap(List<Person> people) {
        Map<String, Map<String, Integer>> normalizedCounts = new HashMap<String, Map<String, Integer>>();
        for (Person p : people) {
            for (Interest i : p.getInterest()) {
                if (i == null) {
                    continue;
                }
                String raw = i.getName();
                String cleaned = tokenizer.normalize(raw);
                if (cleaned.isEmpty()) {
                    continue;
                }
                if (!normalizedCounts.containsKey(cleaned)) {
                    normalizedCounts.put(cleaned, new HashMap<String, Integer>());
                }
                Integer c = normalizedCounts.get(cleaned).get(raw);
                c = (c == null) ? 0 : c;
                normalizedCounts.get(cleaned).put(raw, c + 1);
            }
        }

        Map<String, String> canonical = new HashMap<String, String>();
        for (String cleaned : normalizedCounts.keySet()) {
            Map<String, Integer> versions = normalizedCounts.get(cleaned);
            String mostCommon = null;
            for (String raw : versions.keySet()) {
                if (mostCommon == null || versions.get(raw) > versions.get(mostCommon)) {
                    mostCommon = raw;
                }
            }
            canonical.put(cleaned, mostCommon);
        }

        return canonical;
    }

    public LinkedHashMap<String, Integer> topInterests(String dept, int minPeople) {
        final TObjectIntMap<String> counts = new TObjectIntHashMap<String>();
        for (Person person : people) {
            if (person.getDepartment().contains(dept.toLowerCase())) {
                for (Interest i : person.getInterest()) {
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

        Map<String, String> canonical = getCanonicalMap(people);
        LinkedHashMap<String, Integer> result = new LinkedHashMap<String, Integer>();
        for (String i : interests) {
            if (counts.get(i) >= minPeople) {
                result.put(canonical.get(i), counts.get(i));
            }
        }
        return result;
    }

    public static void main(String args[]) throws IOException {
        PersonDao di = new PersonDao();
        for (String dept : new String[] {"history", "psychology", "biology"}) {
            System.out.println("top interests for " + dept);
            LinkedHashMap<String, Integer> counts = di.topInterests(dept, 2);
            int i = 0;
            for (String interest : counts.keySet()) {
                System.out.println("\t" + (++i) + "\t" + counts.get(interest) + "\t" + interest);
            }
        }
    }
}
