package org.macademia.algs;

import edu.macalester.wpsemsim.matrix.DenseMatrix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void main(String args[]) throws IOException {
        ArrayList<People> people=People_Interests.makePeopleInterests("dat/people.txt","dat/phrases.tsv","dat/people_interests.txt");
        People p1 = people.get(0);
        People p2 = people.get(30);
        float d = People_Distance.findDistance(p1,p2);
        System.out.println("The average distance between "+p1.getEmail()+" and "+p2.getEmail()+" is "+d);

//        for(int i=0;i<people.size();i++){
//            for (int j=0;j<people.get(i).getInterest().size();j++){
//                if(people.get(i)!=null&&people.get(i).getInterest().get(j)!=null)
//                    System.out.println(people.get(i).getEmail()+"--"+people.get(i).getInterest().get(j).getName());
//            }
//
//        }
    }
}
