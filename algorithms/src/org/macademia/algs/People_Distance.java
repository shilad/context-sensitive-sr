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
            distances = getBestAdjustedDistances(p1IDs,p2IDs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        distance=combineBestAdjustedDistances(distances);

        return distance;
    }

    private static ArrayList<Integer> getInterestIDs(People p){
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<Interest> interests=p.getInterest();
        for(int i=0;i<interests.size();i++){
            if(interests.get(i)!=null)
                ids.add(Integer.parseInt(interests.get(i).getMacademiaID()));
        }
        return ids;
    }


    private static float averageDistances(ArrayList<Float> list){
        float distance=0;

        //Maybe do something fancier, but for now, return the average.

        for(int i=0;i<list.size();i++){
            distance+=list.get(i);
        }
        return (distance/list.size());
    }
    private static float combineBestAdjustedDistances(ArrayList<Float> list){
        float distance=0;
        float best=0;
        float score=0;
        int cur=-1;
        double total=list.size();
        while(list.size()>0){
            for(int i=0;i<list.size();i++){
                score=list.get(i);

                if(score>best){
                    best=score;
                    cur=i;
                }

            }
            if(list.get(cur)>.7){                           //list.size()>(total*.5)
                //System.out.println(list.get(cur));
                distance+=list.get(cur)*((list.size())/total);
                //System.out.println(list.get(cur)*((list.size())/total)+"\t"+list.size());
            }
            if(list.get(cur)<=.7&&list.get(cur)>.5){
                distance+=(list.get(cur)*.4)*(list.size()/total);
                //System.out.println(-list.get(cur)*((list.size())/total)+"\t"+list.size());
            }
            if(list.get(cur)<=.5){
                distance-=list.get(cur);
                //System.out.println(-list.get(cur)*((list.size())/total)+"\t"+list.size());
            }
            //distance+=list.get(cur)*(list.size()-(total/5.0))/total;
            //System.out.println(distance);
            best=0;
            score=0;
            list.remove(cur);
        }


        return distance;
    }
    private static float combineBestDistances(ArrayList<Float> list){
        float distance=0;
        for(int i=0;i<list.size();i++){
            distance+=list.get(i);

        }




        return distance;
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
            }

        }

        return distances;
    }
    private static ArrayList<Float> getBestDistances(ArrayList<Integer> p1IDs,ArrayList<Integer> p2IDs) throws IOException {
        ArrayList<Float> distances = new ArrayList<Float>();
        DenseMatrix matrix = null;
        try {
            matrix = new DenseMatrix(new File("dat/similarity.matrix"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DenseMatrixRow row;
        LinkedHashMap<Integer,Float> map=null;
        float score=0;
        float best=0;

        for(int i=0;i<p2IDs.size();i++){
            row=matrix.getRow(p2IDs.get(i));
            map=row.asMap();
            for(int j=0;j<p1IDs.size();j++){
                score=map.get(p1IDs.get(j));
                if(score>best){
                    best=score;
                }
            }
            distances.add(best);
            best=0;
        }

        return distances;
    }
    private static ArrayList<Float> getBestAdjustedDistances(ArrayList<Integer> p1IDs,ArrayList<Integer> p2IDs) throws IOException {
        ArrayList<Float> distances = new ArrayList<Float>();
        DenseMatrix matrix = null;
        try {
            matrix = new DenseMatrix(new File("dat/similarity.matrix"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DenseMatrixRow row;
        LinkedHashMap<Integer,Float> map=null;
        float score=0;
        float best=0;

        for(int i=0;i<p2IDs.size();i++){
            row=matrix.getRow(p2IDs.get(i));
            map=row.asMap();
            for(int j=0;j<p1IDs.size();j++){
                score=map.get(p1IDs.get(j));
                if(score>best){
                    best=score;
                }
                else{

                    //distances.add(penalty);
                }
            }
            distances.add(best);
            best=0;
        }

        return distances;
    }
}
