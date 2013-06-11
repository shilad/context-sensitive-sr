package org.macademia.algs;

/**
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
