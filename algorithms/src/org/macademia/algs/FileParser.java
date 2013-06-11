package org.macademia.algs;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jesse
 * Date: 6/10/13
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileParser {
    public static void main(String args[]) throws IOException {
        HashMap phrases=reader("dat/phrases.tsv",1,3,"\t");
        Set s = phrases.keySet();
        Object[] a=s.toArray();        //Creates HashMap and array of keys for phrase list

        HashMap people=reader("dat/people.txt",0,1,"\t");
        Set s2 = people.keySet();
        Object[] a2=s2.toArray();      //Creates HashMap and array of keys for people list

        HashMap peopleInterests=reader("dat/people_interests.txt",0,1,"\t");
        Set s3 = peopleInterests.keySet();       //Creates HashMap of People IDs to a list of Interest IDs
        Object[] a3=s3.toArray();              // along with a list of the People IDs with interests attached to them


        ArrayList<String> len = new ArrayList<String>();  //Prints each person's email and interests that
        for(int i = 0; i < s2.size(); i++){                        // correspond with those people
            if(peopleInterests.get(a2[i])!=null){
                len = (ArrayList<String>) peopleInterests.get(a2[i]);
            }
            if(len!=null){
                for(int j = 0; j < len.size();j++){
                    if(people.get(a2[i])!=null&&phrases.get( len.get(j) )!=null)
                        System.out.println( people.get(a2[i]) +"--"+ phrases.get( len.get(j) ) );
                }
            }

        }


    }
    public static HashMap reader(String path, int keyCol, int valCol, String splitOn) throws IOException {
                                         //Splits lines from given path on splitOn string and then inserts
        HashMap temp= new HashMap();     //the key column and value column into a HashMap with string keys and [] values
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = null;
        ArrayList<String> ar;
        while ((line = reader.readLine()) != null) {
            String[] lineSplit=line.split(splitOn);
            if(temp.containsKey(lineSplit[keyCol])){
                ArrayList<String> al = (ArrayList<String>) temp.get(lineSplit[keyCol]);
                al.add(lineSplit[valCol]);
                temp.put(lineSplit[keyCol],al);
            }
            else{
                ar = new ArrayList<String>();
                ar.add(lineSplit[valCol]);
                temp.put(lineSplit[keyCol],ar);
            }
        }
        return temp;
    }

}
