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
        for(int i=0;i<p1IDs.size();i++){
            for(int j=0;j<p2IDs.size();j++){
                //distances.add(matrix.getRow(p1IDs.get(i)).getColValue(p2IDs.get(j)));//get value from matrix
                //System.out.println(matrix.iterator().next().getValueForId(p1IDs.get(i)));

                System.out.println("ID 1:"+p1IDs.get(i)+" ID 2:"+p2IDs.get(j)+" Matrix value:"+matrix.getRow(p1IDs.get(i)).getValueForId(p2IDs.get(j)));
            }
        }
        return distances;
    }
}
