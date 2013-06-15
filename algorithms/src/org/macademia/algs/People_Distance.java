package org.macademia.algs;


import Jama.Matrix;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jesse
 * Date: 6/12/13
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class People_Distance {
    private static SimilarityMatrix matrix = null;
    private static Matrix jamaMatrix=null;
    private static HashMap<String,Matrix> vectorMap = new HashMap<String, Matrix>();

    //Given two people objects returns the distance between them
    public static double findDistance(People p1, People p2){

        if(matrix==null){                       //Checks to see if static matrixes have been created yet
            try {
                matrix = new SimilarityMatrix(new File("dat/similarity.matrix"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(jamaMatrix==null){
            jamaMatrix= new Matrix(matrix.getDoubleMatrix());
        }

        double distance = 0;
        ArrayList<Integer> p1IDs = getInterestIDs(p1);
        ArrayList<Integer> p2IDs = getInterestIDs(p2);
        Matrix p1Distances=null;
        Matrix p2Distances=null;

        try {                                            //Checks to see if vector has been created before
            if(!vectorMap.containsKey(p1.getID())){
                p1Distances = getPersonVector(p1IDs);           //Else it calculates the person's vector
                vectorMap.put(p1.getID(),p1Distances);
            }
            else{
                p1Distances=vectorMap.get(p1.getID());
            }
            if(!vectorMap.containsKey(p2.getID())){
                p2Distances = getPersonVector(p2IDs);
                vectorMap.put(p2.getID(),p2Distances);
            }
            else{
                p2Distances=vectorMap.get(p2.getID());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        distance=cosineSimilarity(p1Distances, p2Distances);      //Calculates cosine similarity of the two vectors

        return distance;
    }

    //Returns a list of interest IDs given a people object
    private static ArrayList<Integer> getInterestIDs(People p){
        ArrayList<Integer> ids = new ArrayList<Integer>();
        ArrayList<Interest> interests=p.getInterest();
        for(int i=0;i<interests.size();i++){
            if(interests.get(i)!=null)
                ids.add(Integer.parseInt(interests.get(i).getDenseID()));
        }
        return ids;
    }

    //Calculates a person vector from their interest IDs and the similarity matrix
    private static Matrix getPersonVector(ArrayList<Integer> personIDs) throws IOException {

        double[][] row = new double[matrix.getNumRows()][1];   //Creates empty vector

        for(int i=0;i<matrix.getNumRows();i++){          //Sets it all to 0
            row[i][0]=0;
        }

        for(int i=0;i<personIDs.size();i++){         //Sets the locations of the interest IDs to 1
            row[personIDs.get(i)][0]=1;
        }

        Matrix personVector = new Matrix(row);      //Makes Matrix object out of vector

        Matrix resultVector=jamaMatrix.times(personVector);     //Makes a resultant vector with 0s and scores from matrix

        return resultVector;
    }

    //Returns the cosine similarity between two people given their vectors
    private static double cosineSimilarity(Matrix person1, Matrix person2) {

        double[][] dotProduct = person1.arrayTimes(person2).getArray();       //Calculates the dot product
        double total=0;
        for(int i =0;i<dotProduct.length;i++){
            total+=dotProduct[i][0];
        }
        double euclideanDist = person1.normF() * person2.normF();    //Calculates the euclidean distance
        return total / euclideanDist;
    }


}
