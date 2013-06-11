package org.macademia.algs;

/**
 * Created with IntelliJ IDEA.
 * User: research
 * Date: 6/11/13
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Interest {

    String name;
    int id;

    Interest(String name, int id){
        this.name=name;
        this.id=id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setID(int id){
        this.id=id;
    }

    public String getName(){
        return this.name;
    }

    public int getID(){
        return this.id;
    }
}
