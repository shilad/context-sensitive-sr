package org.macademia.algs;

import edu.macalester.wpsemsim.matrix.DenseMatrix;
import edu.macalester.wpsemsim.matrix.DenseMatrixRow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
            ids.add(Integer.parseInt(interests.get(i).getMacademiaID()));
        }
        return ids;
    }


    private static float combineDistances(ArrayList<Float> list){
        float distance=0;

        //Maybe do something fancier, but for now, return the average.

        for(int i=0;i<list.size();i++){
            distance+=list.get(i);
        }
        return (distance/list.size());
    }
    private static ArrayList<Float> getAllDistances(ArrayList<Integer> p1IDs,ArrayList<Integer> p2IDs) throws IOException {
        ArrayList<Float> distances = new ArrayList<Float>();
        DenseMatrix matrix = null;
        try {
            matrix = new DenseMatrix(new File("dat/similarity.matrix"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DenseMatrixRow row;
        LinkedHashMap<Integer,Float> map=null;
        for(int i=0;i<p1IDs.size();i++){
            row=matrix.getRow(p1IDs.get(i));
            map=row.asMap();
            for(int j=0;j<p2IDs.size();j++){
                distances.add(map.get(p2IDs.get(j)));
                //System.out.println(map.get(p2IDs.get(j)));
            }

        }

        return distances;
    }
}
