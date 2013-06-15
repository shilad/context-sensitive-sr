package org.macademia.algs;


import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TestPeople {

    public static void main(String args[]) throws IOException {
        FileWriter out=null;
        try{
            out = new FileWriter("dat/personMatrix.txt");
        }
        catch (IOException e){
            e.printStackTrace();
        }

        ArrayList<People> people=People_Interests.makePeopleInterests("dat/people.txt","dat/phrases.tsv","dat/people_interests.txt");
        People target = people.get(0);
        People candidate = people.get(107);                  //0 is Shilad and 107 is Danny Kaplan in list
                                                            //findPersonByEmail(people,"shoop@macalester.edu")

        for(int i=0;i<people.size();i++){          //Cycles through each person in list using them as the target to
            scoresForAllCandidate(people, out, people.get(i));            //compile full matrix of scores
        }

        out.flush();
        out.close();

    }
    //SINGLE PERSON TEST - Prints out the result of the target person to the candidate
    public static void singleTestDistance(People target, People candidate){
        double dis = People_Distance.findDistance(target,candidate);
        System.out.println("The distance between "+target.getEmail()+" and "+candidate.getEmail()+" is "+dis);
    }

    //Finds a person by their email address - returns a people object
    public static People findPersonByEmail(ArrayList<People> people,String email){
        People peep = null;
        for(int i=1;i<people.size();i++){
            if(people.get(i).getEmail().equals(email))
                peep=people.get(i);
        }
        return peep;
    }

    //Finds a person by their people id - returns a people object
    public static People findPersonByID(ArrayList<People> people,String id){
        People peep = null;
        for(int i=1;i<people.size();i++){
            if(people.get(i).getID().equals(id))
                peep=people.get(i);
        }
        return peep;
    }

    //Prints out every email to interest in the People list
    public static void printAllList(ArrayList<People> people){
        for(int i=0;i<people.size();i++){
            for (int j=0;j<people.get(i).getInterest().size();j++){
                if(people.get(i)!=null&&people.get(i).getInterest().get(j)!=null)
                    System.out.println(people.get(i).getEmail()+"--"+people.get(i).getInterest().get(j).getName());
            }

        }
    }

    //Finds scores for all candidates relative to the target person
    // - Take in full People array and file writer object along with target person
    public static void scoresForAllCandidate(ArrayList<People> people,FileWriter out, People target){
        double a = 0;
        SortedMap<String,Double> scoreMap = new TreeMap<String, Double>();

        for(int i=0;i<people.size();i++){                               //Maps each person to a distance score
            a = People_Distance.findDistance(target,people.get(i));
            scoreMap.put(people.get(i).getID(),a);
        }
                                                                         //Transforms scoreMap to SortedSet
        SortedSet<Map.Entry<String, Double>> sortedset = new TreeSet<Map.Entry<String, Double>>(
                new Comparator<Map.Entry<String, Double>>() {
                    public int compare(Map.Entry<String, Double> e1,
                                       Map.Entry<String, Double> e2) {
                        return e1.getValue().compareTo(e2.getValue());
                    }
                });
        sortedset.addAll(scoreMap.entrySet());


        try{              //Appends to file the sorted set -  TargetID:[CandidateID1=val1,CandidateID2=val2,...]
            out.append(target.getID()+":"+sortedset.toString()+"\n");
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}
