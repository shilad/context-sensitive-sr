package org.macademia.algs;

import java.io.BufferedReader;
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

    private static ArrayList<Person> peopleList = new ArrayList<Person>();
    private static HashMap interestMap = new HashMap();

    //Creates an ArrayList of people objects with their interests given the paths to the data files
    public static ArrayList<Person> readPeople(String peoplePath, String interestPath, String people_interestPath, String deptPath) throws IOException {
        BufferedReader peopleFile = new BufferedReader(new FileReader(peoplePath));
        BufferedReader interestFile = new BufferedReader(new FileReader(interestPath));
        BufferedReader people_interestFile = new BufferedReader(new FileReader(people_interestPath));
        BufferedReader deptFile = new BufferedReader(new FileReader(deptPath));

        peopleList = makePeopleList(peopleFile);
        interestMap = makeInterestMap(interestFile);
        peopleList = addInterests(peopleList,people_interestFile);
        peopleList = addDepartments(peopleList, deptFile);

        return peopleList;
    }

    //Reads people file and creates an ArrayList of Person objects
    private static ArrayList<Person> makePeopleList(BufferedReader peopleFile) throws IOException{
        ArrayList<Person> temp = new ArrayList<Person>();
        String line = null;
        Person p;
        while ((line = peopleFile.readLine()) != null) {
            String[] lineSplit=line.split("\t");
            p=new Person(lineSplit[0], lineSplit[1]);
            temp.add(p);

        }
        return temp;
    }

    //Reads in file and returns a HashMap of Interest objects
    public static HashMap makeInterestMap(BufferedReader interestFile) throws IOException{
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

    //Reads in person to interest file and creates a hashmap of the connections
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

    //Adds the interest objects to the people objects
    private static ArrayList<Person> addInterests(ArrayList<Person> people, BufferedReader connectionFile) {
        ArrayList<Person> peopleArray = people;

        HashMap peopleMap= null;
        try {
            peopleMap = makePeopleHash(connectionFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> interestStrings = new ArrayList<String>();
        ArrayList<Interest> temp;

        for(int i = 0; i < peopleArray.size(); i++){                                     //For each person in person array
            if(peopleMap.get(peopleArray.get(i).getID())!=null){                         //If person in array exists in peopleMap
                interestStrings = (ArrayList<String>) peopleMap.get(peopleArray.get(i).getID());     //Get length on interest strings from map
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


    private static ArrayList<Person> addDepartments(ArrayList<Person> people,BufferedReader departmentFile) throws IOException{
//        ArrayList<Person> temp = people;
        String line = null;
        String[] lineSplit=null;
        ArrayList<String> dept= null;
        String s="";
        while ((line = departmentFile.readLine()) != null) {
            dept=new ArrayList<String>();
            lineSplit=line.toLowerCase().split(",");
            for(int i=0;i<people.size();i++){
                if(people.get(i).getID().equals(lineSplit[1])){
                    if(lineSplit.length>2){
                        if(lineSplit[2].charAt(0)=='"'){
                            lineSplit[2]=lineSplit[2].substring(1,lineSplit[2].length());
                            for(int j=2;j<lineSplit.length;j++){
                                dept.add(lineSplit[j]);
                            }
                            s=dept.get(dept.size()-1).substring(0,dept.get(dept.size()-1).length()-1);
                            dept.remove(dept.size()-1);
                            dept.add(s);
                            people.get(i).setDepartment(dept);

                        }
                        else{
                            dept.add(lineSplit[2]);
                            //System.out.println(dept.toString());
                            people.get(i).setDepartment(dept);
                        }
                        //System.out.println(people.get(i).getDepartments().toString());
                    }
                }
            }


        }
        return people;
    }

}
