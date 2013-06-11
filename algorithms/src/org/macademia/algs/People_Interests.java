package org.macademia.algs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * User: jesse
 * Date: 6/11/13
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class People_Interests {
    private static BufferedReader peopleFile=null;

    public People_Interests(String peoplePath, String interestPath){
        try {
            peopleFile = new BufferedReader(new FileReader(peoplePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
