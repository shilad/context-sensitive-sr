package org.macademia.algs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jesse
 * Date: 6/11/13
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class People_Interests {

    private static ArrayList<People> peopleList = new ArrayList<People>();
    private static HashMap interestList = new HashMap();

    public static ArrayList<People> makePeopleInterests(String peoplePath, String interestPath, String people_interestPath){
        BufferedReader peopleFile=null;
        BufferedReader interestFile=null;
        BufferedReader people_interestFile=null;
        try {
            peopleFile = new BufferedReader(new FileReader(peoplePath));
            interestFile = new BufferedReader(new FileReader(interestPath));
            people_interestFile = new BufferedReader(new FileReader(people_interestPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            peopleList=makePeopleList(peopleFile);
            interestList=makeInterestList(interestFile);
            peopleList=addInterests(peopleList,people_interestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return peopleList;
    }

    private static ArrayList<People> makePeopleList(BufferedReader peopleFile) throws IOException{
        ArrayList<People> temp = new ArrayList<People>();
        String line = null;
        while ((line = peopleFile.readLine()) != null) {
            String[] lineSplit=line.split("\t");
            temp.add(new People(lineSplit[0], lineSplit[1]));
        }
        return temp;
    }
    private static HashMap makeInterestList(BufferedReader interestFile) throws IOException{
        HashMap temp = new HashMap();
        String line = null;
        while ((line = interestFile.readLine()) != null) {
            String[] lineSplit=line.split("\t");
            temp.put(lineSplit[0], new Interest(lineSplit[1], lineSplit[3]));
        }
        return temp;
    }
    private static ArrayList<People> addInterests(ArrayList<People> people, BufferedReader connectionFile) throws IOException {
        ArrayList<People> temp = people;
        String line = null;
        HashMap map= new HashMap();
        ArrayList<String> ar;
        while ((line = connectionFile.readLine()) != null) {
            String[] lineSplit=line.split("\t");
            if(map.containsKey(lineSplit[0])){
                ArrayList<String> al = (ArrayList<String>) map.get(lineSplit[0]);
                al.add(lineSplit[1]);
                map.put(lineSplit[0],al);
            }
            else{
                ar = new ArrayList<String>();
                ar.add(lineSplit[1]);
                map.put(lineSplit[0],ar);
            }
        }
        ArrayList<String> len = new ArrayList<String>();
        for(int i = 0; i < temp.size(); i++){
            if(map.get(temp.get(i).getID())!=null){
                len = (ArrayList<String>) map.get(temp.get(i).getID());
            }
            if(len!=null){
                for(int j=0;j< ((String[]) map.get(temp.get(i).getID())).length;j++){
                    System.out.println(map.get(temp.get(i).getID())[j]);
                    //interestList.get(map.get(temp.get(i).getID())[j]);

                }
                temp.get(i).setInterest((ArrayList<Interest>)map.get(temp.get(i).getID()));
            }

        }

        return temp;
    }
}
