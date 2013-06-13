package org.macademia.algs;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jesse
 * Date: 6/11/13
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class People {
    private String email="";
    private String id="";
    private ArrayList<Interest> interest=new ArrayList<Interest>();
    private String department;

    public People(String id, String email){
        this.id=id;
        this.email=email;

    }
    public People(String id, String email,String department){
        this.id=id;
        this.email=email;
        this.department=department;

    }
    public String getID(){

        return id;
    }
    public void setID(String id){

        this.id=id;
    }
    public String getEmail(){

        return email;
    }
    public void setEmail(String email){

        this.email=email;
    }
    public ArrayList<Interest> getInterest(){

        return interest;
    }
    public void setInterest(ArrayList<Interest> interest){

        this.interest=interest;
    }
    public void setDepartment(String dept){
        this.department=dept;
    }
    public String getDepartment(){
        return department;
    }
}
