package org.macademia.algs;


import Jama.Matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

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
    private static HashMap<String,Matrix> vectorMap = null;

    /**
     * Serialize the vectorMap that contains the people vectors
     * @param people
     * @param path
     * @return a HashMap, key: people's id, value: the matrix that represents the vector of a person
     */
    public static HashMap<String,Matrix> serializeVectorMap(ArrayList<People> people, String path){

        if(matrix==null){                       //Checks to see if static matrixes have been created yet
            try {
                System.out.println("Loading Similarity Matrix");
                matrix = new SimilarityMatrix(new File("dat/similarity.matrix"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(jamaMatrix==null){
            jamaMatrix= new Matrix(matrix.getDoubleMatrix());
        }


        HashMap<String,Matrix> peopleVectorMap = new HashMap<String, Matrix>();
        try{

            // constructing the the vectorMap
            for (People p:people){
                //System.out.println("Finding vector for "+p.getEmail()+" with ID "+p.getID());
                ArrayList<Integer> interestIDs = getInterestIDs(p);
                Matrix vector = getPersonVector(interestIDs);
                peopleVectorMap.put(p.getID(),vector);
            }

            // serilaizing the Vector Map
            FileOutputStream file = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(peopleVectorMap);
            out.close();

        } catch (IOException ex){
            ex.printStackTrace();
        }

        return peopleVectorMap;
    }

    /**
     *
     * deserialize the vectorMap that contains the people vectors
     * @param path the path for serialized file
     * @return a HashMap, key: people's id, value: the matrix that represents the vector of a person
     */
    public static HashMap<String,Matrix> deSerializeVectorMap(String path){

        if(matrix==null){                       //Checks to see if static matrixes have been created yet
            try {
                System.out.println("Loading Similarity Matrix");
                matrix = new SimilarityMatrix(new File("dat/similarity.matrix"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(jamaMatrix==null){
            jamaMatrix= new Matrix(matrix.getDoubleMatrix());
        }

        HashMap<String,Matrix> peopleVectorMap = new HashMap<String, Matrix>();

        try
        {
            FileInputStream fileIn =
                    new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            peopleVectorMap = (HashMap<String,Matrix>) in.readObject();
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
        return peopleVectorMap;
    }



    //Given two people objects returns the distance between them
    public static double findDistance(People p1, People p2){

        if(matrix==null){                       //Checks to see if static matrixes have been created yet
            try {
                System.out.println("Loading Similarity Matrix");
                matrix = new SimilarityMatrix(new File("dat/similarity.matrix"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(jamaMatrix==null){
            jamaMatrix= new Matrix(matrix.getDoubleMatrix());
        }
        if(vectorMap==null){
            vectorMap = deSerializeVectorMap("dat/peopleVectors.ser");
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
