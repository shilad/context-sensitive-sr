package org.macademia.algs;

/**
 */
public class Interest {

    String name;
    String id;

    Interest(String name, String id){
        this.name=name;
        this.id=id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setID(String id){
        this.id=id;
    }

    public String getName(){
        return this.name;
    }

    public String getID(){
        return this.id;
    }
}
