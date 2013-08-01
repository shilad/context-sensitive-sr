package org.macademia.sr;

import java.util.ArrayList;

public class Person {
    private String email="";
    private String id="";
    private ArrayList<String> department=new ArrayList<String>();
    private ArrayList<Interest> interest=new ArrayList<Interest>();

    public Person(String id, String email) {
        this.id=id;
        this.email=email;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id=id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public ArrayList<Interest> getInterest() {
        return interest;
    }

    public void addInterest(Interest interest) {
        this.interest.add(interest);
    }

    public void addDepartment(String dept) {
        this.department.add(dept);
    }

    public ArrayList<String> getDepartment() {
        return department;
    }

    public void setDepartment(ArrayList<String> department) {
        this.department = department;
    }
}
