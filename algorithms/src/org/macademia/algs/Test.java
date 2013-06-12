package org.macademia.algs;

import edu.macalester.wpsemsim.matrix.DenseMatrix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void main(String args[]) throws IOException {
        ArrayList<People> people=People_Interests.makePeopleInterests("dat/people.txt","dat/phrases.tsv","dat/people_interests.txt");

        Float d = People_Distance.findDistance(people.get(0),people.get(1));
        System.out.println(people.get(0).getInterest().get(0).getDenseID()+"---"+people.get(1).getInterest().get(0).getDenseID());

//        for(int i=0;i<people.size();i++){
//            for (int j=0;j<people.get(i).getInterest().size();j++){
//                if(people.get(i)!=null&&people.get(i).getInterest().get(j)!=null)
//                    System.out.println(people.get(i).getEmail()+"--"+people.get(i).getInterest().get(j).getName());
//            }
//
//        }
    }
}
