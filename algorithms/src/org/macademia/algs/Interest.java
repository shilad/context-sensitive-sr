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
    int idNum;

    Interest(String name, int idNum){
        this.name=name;
        this.idNum=idNum;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setidNum(int idNum){
        this.idNum=idNum;
    }

    public String getName(){
        return this.name;
    }

    public int getidNum(){
        return this.idNum;
    }





}
