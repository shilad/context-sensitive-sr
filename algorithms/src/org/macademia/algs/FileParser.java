package org.macademia.algs;


import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jesse
 * Date: 6/10/13
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileParser {
    public static HashMap<String,SortedMap<String,Double>> deserializePeopleMatrix(String path){
        HashMap<String,SortedMap<String,Double>> e = null;
        try
        {
            FileInputStream fileIn =
                    new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (HashMap<String,SortedMap<String,Double>>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();

        }catch(ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();

        }
        return e;
    }

}
