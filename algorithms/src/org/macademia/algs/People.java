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
    private static String email="";
    private static String id="";
    private static ArrayList<Interests> interests=new ArrayList<Interests>();

    public People(String id, String email, ArrayList<Interests> interests){
        id=this.id;
        email=this.email;
        interests=this.interests;
    }

    public String getID(){

        return id;
    }
    public void setID(String id){

        id=this.id;
    }
    public String getEmail(){

        return email;
    }
    public void setEmail(String email){

        email=this.email;
    }
    public ArrayList<Interests> getInterests(){

        return interests;
    }
    public void setInterests(ArrayList<Interests> interests){

        interests=this.interests;
    }
}
