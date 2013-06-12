package org.macademia.algs;
import edu.macalester.wpsemsim.matrix.DenseMatrix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jesse
 * Date: 6/12/13
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class People_Distance {

    public static Float findDistance(People p1, People p2){
        float distance = 0;
        ArrayList<Integer> p1IDs = getInterestIDs(p1);
        ArrayList<Integer> p2IDs = getInterestIDs(p2);
        ArrayList<Float> distances=null;
        try {
            distances = getAllDistances(p1IDs,p2IDs);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return distance;
    }
    private static ArrayList<Integer> getInterestIDs(People p){
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<Interest> interests=p.getInterest();
        for(int i=0;i<interests.size();i++){
            ids.add(Integer.parseInt(interests.get(i).getDenseID()));
             //System.out.println(interests.get(i).getDenseID());
        }
        return ids;
    }
    private static ArrayList<Float> getAllDistances(ArrayList<Integer> p1IDs,ArrayList<Integer> p2IDs) throws IOException {
        ArrayList<Float> distances = new ArrayList<Float>();
        DenseMatrix matrix = null;
        try {
            matrix = new DenseMatrix(new File("dat/similarity.matrix"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        int count=0;
        for(int i=0;i<p1IDs.size();i++){
            for(int j=0;j<p2IDs.size();j++){
                //distances.add(matrix.getRow(p1IDs.get(i)).getColValue(p2IDs.get(j)));//get value from matrix
                if (matrix.getRow(p1IDs.get(i)) != null) {
                    System.out.println(p1IDs.get(i)+"--"+p2IDs.get(j)+"---"+matrix.getRow(p1IDs.get(i)));

                System.out.println(matrix.getRow(p1IDs.get(i)).getValues()[p2IDs.get(j)]+"--"+p2IDs.get(j));
                }
                else{
                    count++;                                //WHY ARE 39 ROWS NULL??????
                }
//                    System.out.println("ID 1:\t"+p1IDs.get(i)+"\tID 2:\t"+p2IDs.get(j)+
//                        "\t\tMatrix value:\t"+matrix.getRow(p1IDs.get(i)).getIndexForId(p2IDs.get(j)));
            }
        }
        System.out.println(count);
        return distances;
    }
}
