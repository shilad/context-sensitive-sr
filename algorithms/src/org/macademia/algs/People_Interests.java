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
    private static HashMap interestMap = new HashMap();

    public static ArrayList<People> makePeopleInterests(String peoplePath, String interestPath, String people_interestPath,String departmentPath){
        BufferedReader peopleFile=null;
        BufferedReader interestFile=null;
        BufferedReader people_interestFile=null;
        BufferedReader departmentFile=null;
        try {
            peopleFile = new BufferedReader(new FileReader(peoplePath));
            interestFile = new BufferedReader(new FileReader(interestPath));
            people_interestFile = new BufferedReader(new FileReader(people_interestPath));
            departmentFile = new BufferedReader(new FileReader(departmentPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            peopleList=makePeopleList(peopleFile);
            peopleList=addDepartments(departmentFile,peopleList);
            interestMap=makeInterestMap(interestFile);
            peopleList=addInterests(peopleList,people_interestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return peopleList;
    }

    private static ArrayList<People> makePeopleList(BufferedReader peopleFile) throws IOException{
        ArrayList<People> temp = new ArrayList<People>();
        String line = null;
        People p;
        while ((line = peopleFile.readLine()) != null) {
            String[] lineSplit=line.split("\t");
            p=new People(lineSplit[0], lineSplit[1]);
            temp.add(p);

        }
        return temp;
    }
    private static ArrayList<People> addDepartments(BufferedReader departmentFile,ArrayList<People> people) throws IOException{
        ArrayList<People> temp = people;
        String line = null;
        String dept="";
        while ((line = departmentFile.readLine()) != null) {
            String[] lineSplit=line.split(",");
            for(int i=0;i<temp.size();i++){
                if(temp.get(i).getID().equals(lineSplit[1])){
                    if(lineSplit.length>2)
                        if(lineSplit[2].charAt(0)=='"'){
                            for(int j=2;j<lineSplit.length;j++){
                                dept+=lineSplit[j];
                            }
                            dept=dept.substring(1,dept.length()-1);
                            temp.get(i).setDepartment(dept);
                            dept="";
                        }
                        else
                            temp.get(i).setDepartment(lineSplit[2]);
                }
            }

        }
        return temp;
    }
    private static HashMap makeInterestMap(BufferedReader interestFile) throws IOException{
        HashMap temp = new HashMap();
        String line = null;
        Interest i;
        while ((line = interestFile.readLine()) != null) {
            String[] lineSplit=line.split("\t");
            i = new Interest(lineSplit[3], lineSplit[1],lineSplit[0]);
            temp.put(lineSplit[1], i);

        }
        return temp;
    }
    private static HashMap makePeopleHash(BufferedReader file) throws IOException {
        String line = null;
        HashMap map= new HashMap();
        ArrayList<String> ar;
        while ((line = file.readLine()) != null) {
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
        return map;
    }
    private static ArrayList<People> addInterests(ArrayList<People> people, BufferedReader connectionFile) {
        ArrayList<People> peopleArray = people;

        HashMap peopleMap= null;
        try {
            peopleMap = makePeopleHash(connectionFile);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        ArrayList<String> interestStrings = new ArrayList<String>();
        ArrayList<Interest> temp;
        for(int i = 0; i < peopleArray.size(); i++){                                     //For each person in person array
            if(peopleMap.get(peopleArray.get(i).getID())!=null){                         //If person in array exists in peopleMap
                interestStrings = (ArrayList<String>) peopleMap.get(peopleArray.get(i).getID());     //Get length on interest strings from map
                //System.out.println(peopleMap.get(peopleArray.get(i).getID()));
            }
            if(interestStrings!=null){                                                     //If there are interest strings
                temp = new ArrayList<Interest>();
                for(int j=0;j< interestStrings.size();j++){                                  //for each in the list
                    Interest interest = (Interest) interestMap.get(interestStrings.get(j));  //get the interest object for it
                    temp.add( interest );
                                                                                             //and add it to temp list
                }
                peopleArray.get(i).setInterest(temp);
                temp=null;
            }
            interestStrings=null;

        }

        return peopleArray;
    }
}
