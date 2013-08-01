package org.macademia.sr;

/**
 */
public class Interest {

    String name;
    String macademiaID;
    String denseID;

    Interest(String name, String macademiaID, String denseID){
        this.name=name;
        this.macademiaID=macademiaID;
        this.denseID=denseID;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setMacademiaID(String macademiaID){
        this.macademiaID=macademiaID;
    }

    public void setDenseID(String denseID){
        this.denseID=denseID;
    }

    public String getName(){
        return this.name;
    }

    public String getMacademiaID(){
        return this.macademiaID;
    }
    public String getDenseID(){
        return this.denseID;
    }

    @Override
    public String toString() {
        return "Interest{" +
                "name='" + name + '\'' +
                ", macademiaID='" + macademiaID + '\'' +
                '}';
    }
}
