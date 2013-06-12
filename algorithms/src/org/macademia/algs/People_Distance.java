package org.macademia.algs;

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
            e.printStackTrace();
        }
        distance=combineDistances(distances);

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

        SimilarityMatrix sm = new SimilarityMatrix(new File("dat/similarity.matrix"));

        float[][] matrix = sm.getFloatMatrix();
        for(int i=0;i<p1IDs.size();i++){
            for(int j=0;j<p2IDs.size();j++){
                distances.add(matrix[p1IDs.get(i)][p2IDs.get(j)]);
            }
        }


        return distances;
    }

    private static float combineDistances(ArrayList<Float> list){
        float distance=0;

        //Maybe do something fancier, but for now, return the average.

        for(int i=0;i<list.size();i++){
            distance+=list.get(i);
        }
        return (distance/list.size());
    }

}
