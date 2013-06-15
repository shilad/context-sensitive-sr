package org.macademia.algs;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TestPeople {

    //@SuppressWarnings("all")
    public static void main(String args[]) throws IOException {
        FileWriter out=null;
        try{
            out = new FileWriter("dat/personMatrix.txt");
        }
        catch (IOException e){
            e.printStackTrace();
        }

        ArrayList<People> people=People_Interests.makePeopleInterests("dat/people.txt","dat/phrases.tsv","dat/people_interests.txt","dat/person_departments.csv");
        People target = people.get(0);
        People candidate = people.get(107);
        //System.out.print(target.getEmail());                  //0 is Shilad and 107 is Danny Kaplan
        //scoresForAllCandidate(people, target);                  //Lepczyk is 1961 and Eric Palmer is 1822
        for(int i=0;i<people.size();i++){
            scoresForAllCandidate(people, out, people.get(i));         //findPersonByEmail(people,"shoop@macalester.edu")
        }
        out.flush();
        out.close();
        //Parse_Clusters.printClusters("dat/phrases.tsv","dat/clusters.txt");
        //Parse_Clusters.printClustersFromList("dat/phrases.tsv",list);
        //System.out.println(target.getDepartment());
    }
    //SINGLE PERSON TEST
    public static void singleTestDistance(People target, People candidate){
        double dis = People_Distance.findDistance(target,candidate);
        System.out.println("The distance between "+target.getEmail()+" and "+candidate.getEmail()+" is "+dis);
    }

    //Find a person
    public static People findPersonByEmail(ArrayList<People> people,String email){
        People peep = null;
        for(int i=1;i<people.size();i++){
            if(people.get(i).getEmail().equals(email))
                peep=people.get(i);
        }
        return peep;
    }
    //Find a person
    public static People findPersonByID(ArrayList<People> people,String id){
        People peep = null;
        for(int i=1;i<people.size();i++){
            if(people.get(i).getID().equals(id))
                peep=people.get(i);
        }
        return peep;
    }


    //Finds scores for all candidates
    public static void scoresForAllCandidate(ArrayList<People> people,FileWriter out, People target){
        double d = 0;
        double a = 0;
        int count = 0;
        int index=0;

        SortedMap<String,Double> scoreMap = new TreeMap<String, Double>();

        ArrayList<People> peeps=new ArrayList<People>();
        //ArrayList<Double> peepScores=new ArrayList<Double>();

        for(int i=0;i<people.size();i++){
            a = People_Distance.findDistance(target,people.get(i));
            scoreMap.put(people.get(i).getID(),a);
        }

        SortedSet<Map.Entry<String, Double>> sortedset = new TreeSet<Map.Entry<String, Double>>(
                new Comparator<Map.Entry<String, Double>>() {
                    public int compare(Map.Entry<String, Double> e1,
                                       Map.Entry<String, Double> e2) {
                        return e1.getValue().compareTo(e2.getValue());
                    }
                });

        sortedset.addAll(scoreMap.entrySet());


        try{

            out.append(target.getID()+":"+sortedset.toString()+"\n");

        }
        catch (IOException e){
            e.printStackTrace();
        };


//        for(int i=0;i<peeps.size();i++){
//            System.out.println("The distance between "+target.getEmail()+" and "+peeps.get(i).getEmail()+" is "+peepScores.get(i));
//
//        }
    }
    //PRINT OUT EVERY EMAIL WITH INTERESTS
    public static void printAllList(ArrayList<People> people){
        for(int i=0;i<people.size();i++){
            for (int j=0;j<people.get(i).getInterest().size();j++){
                if(people.get(i)!=null&&people.get(i).getInterest().get(j)!=null)
                    System.out.println(people.get(i).getEmail()+"--"+people.get(i).getInterest().get(j).getName());
            }

        }
    }


}
