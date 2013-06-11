package org.macademia.algs;

import edu.macalester.wpsemsim.matrix.DenseMatrix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
    public static void main(String args[]) throws IOException {
        ArrayList<People> people = new ArrayList<People>();
        people=People_Interests.makePeopleInterests("dat/people.txt","dat/phrases.tsv","dat/people_interests.txt");
        for(int i=0;i<people.size();i++){
            System.out.println(people.get(i).getInterest());
        }
    }
}
