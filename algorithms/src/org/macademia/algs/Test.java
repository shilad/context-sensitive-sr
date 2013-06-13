package org.macademia.algs;

import edu.macalester.wpsemsim.matrix.DenseMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {

    //@SuppressWarnings("all")
    public static void main(String args[]) throws IOException {
        ArrayList<People> people=People_Interests.makePeopleInterests("dat/people.txt","dat/phrases.tsv","dat/people_interests.txt");
        People target = people.get(107);
        People candidate = people.get(1822);                   //0 is Shilad and 107 is Danny Kaplan
                                                               //Lepczyk is 1961 and Eric Palmer is 1822
        Parse_Clusters.printClusters("dat/phrases.tsv","dat/clusters.txt");

            //SINGLE PERSON TEST
//        float dis = People_Distance.findDistance(target,candidate);
//        System.out.println("The distance between "+target.getEmail()+" and "+candidate.getEmail()+" is "+dis);

            //Find a person
//        for(int i=1;i<people.size();i++){
//            if(people.get(i).getEmail().equals("epalmer@allegheny.edu"))
//               System.out.print(i);
//        }


        //FIND TOP FIVE PEOPLE
//        float d = 0;
//        float a = 0;
//        int count = 0;
//        int index=0;
//        ArrayList<People> peeps=new ArrayList<People>();
//        ArrayList<Float> peepScores=new ArrayList<Float>();
//
//        while(count<5){
//        for(int i=1;i<people.size();i++){
//
//            a=People_Distance.findDistance(target,people.get(i));
//            if(a>d){
//                d=a;
//                candidate=people.get(i);
//                index=i;
//            }
//        }
//            peeps.add(candidate);
//            peepScores.add(d);
//            people.remove(index);
//        count++;
//        d=0;
//        a=0;
//        }
//        for(int i=0;i<peeps.size();i++){
//            System.out.println("The distance between "+target.getEmail()+" and "+peeps.get(i).getEmail()+" is "+peepScores.get(i));
//
//        }

            //PRINT OUT EVERY EMAIL WITH INTERESTS
//        for(int i=0;i<people.size();i++){
//            for (int j=0;j<people.get(i).getInterest().size();j++){
//                if(people.get(i)!=null&&people.get(i).getInterest().get(j)!=null)
//                    System.out.println(people.get(i).getEmail()+"--"+people.get(i).getInterest().get(j).getName());
//            }
//
//        }
    }
}
