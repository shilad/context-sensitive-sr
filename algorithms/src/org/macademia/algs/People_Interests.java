package org.macademia.algs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jesse
 * Date: 6/11/13
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class People_Interests {

    private static ArrayList<People> peopleList = new ArrayList<People>();
    private static ArrayList<Interest> interestList = new ArrayList<Interest>();

    public People_Interests(String peoplePath, String interestPath){
        BufferedReader peopleFile=null;
        BufferedReader interestFile=null;

        try {
            peopleFile = new BufferedReader(new FileReader(peoplePath));
            interestFile = new BufferedReader(new FileReader(interestPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            peopleList=makePeopleList(peopleFile);
            interestList=makeInterestList(interestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<People> makePeopleList(BufferedReader peopleFile) throws IOException{
        ArrayList<People> temp = new ArrayList<People>();
        String line = null;
        while ((line = peopleFile.readLine()) != null) {
            String[] lineSplit=line.split("\t");
            temp.add(new People(lineSplit[0],lineSplit[1]));
        }
        return temp;
    }
    private ArrayList<Interest> makeInterestList(BufferedReader interestFile) throws IOException{
        ArrayList<Interest> temp = new ArrayList<Interest>();
        String line = null;
        while ((line = interestFile.readLine()) != null) {
            String[] lineSplit=line.split("\t");
            temp.add(new Interest(lineSplit[1],lineSplit[3]));
        }
        return temp;
    }
}
