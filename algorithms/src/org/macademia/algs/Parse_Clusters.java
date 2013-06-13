package org.macademia.algs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jesse
 * Date: 6/13/13
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class Parse_Clusters {

    public static HashMap makeInterestMap(BufferedReader interestFile) throws IOException {
        HashMap temp = new HashMap();
        String line = null;
        Interest i;
        while ((line = interestFile.readLine()) != null) {
            String[] lineSplit=line.split("\t");
            i = new Interest(lineSplit[3], lineSplit[1],lineSplit[0]);
            //System.out.println(Arrays.toString(lineSplit));
            temp.put(lineSplit[0], i);

        }
        return temp;
    }

    public static void printClusters(String pathPhrases, String pathClusters) throws IOException {
        BufferedReader interestFile=null;
        interestFile = new BufferedReader(new FileReader(pathPhrases));
        HashMap<String,Interest> map = new HashMap();
        try {
            map=makeInterestMap(interestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line="";
        interestFile= new BufferedReader(new FileReader(pathClusters));
        while ((line = interestFile.readLine()) != null) {
            if(line.contains("Cluster")){
                System.out.println("\n"+line);
            }
            else{
                if(map.get(line)!=null)
                    System.out.println(map.get(line).getName());
            }

        }
    }
    public static void printClustersFromList(String pathPhrases, int[] list) throws IOException {
        BufferedReader interestFile=null;
        interestFile = new BufferedReader(new FileReader(pathPhrases));
        HashMap<String,Interest> map = new HashMap();
        try {
            map=makeInterestMap(interestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<list.length;i++){

            if(map.get(""+list[i])!=null){
                System.out.println(map.get(""+list[i]).getName());
            }

        }
    }
}
