package org.macademia.algs;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: research
 * Date: 6/14/13
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class CosinePeople {

    //@SuppressWarnings("all")
    public static void main(String args[]) throws IOException {



    }


    //Create a person vector
    public static void createPersonVector() {
        ArrayList<People> people=People_Interests.makePeopleInterests("dat/people.txt","dat/phrases.tsv","dat/people_interests.txt");
    }

}
