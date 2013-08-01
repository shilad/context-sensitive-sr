package org.macademia.sr;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private final String email;
    private final String id;
    private List<String> department=new ArrayList<String>();
    private List<Interest> interest=new ArrayList<Interest>();

    public Person(String id, String email) {
        this.id=id;
        this.email=email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public List<Interest> getInterests() {
        return interest;
    }

    public void addInterest(Interest interest) {
        this.interest.add(interest);
    }

    public void addDepartment(String dept) {
        this.department.add(dept);
    }

    public List<String> getDepartments() {
        return department;
    }
}
