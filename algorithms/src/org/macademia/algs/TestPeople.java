package org.macademia.algs;


import java.io.*;
import java.util.*;

public class TestPeople {
    //0 is Shilad and 107 is Danny Kaplan in list
    //findPersonByEmail(people,"shoop@macalester.edu")
    public static void main(String args[]) throws IOException {
        ArrayList<People> people=People_Interests.makePeopleInterests("dat/people.txt","dat/phrases.tsv","dat/people_interests.txt");
        //createSerializedMatrix(people);
        HashMap<String,SortedMap<String,Double>> map;
        map=FileParser.deserializePeopleMatrix("dat/peopleMatrix.ser");
        float[][] matrix = createMatrixArray(map, people);

        //Running Kmeans algorithm for k random center points
        System.out.println("Finding the centroids for clusters");
        Kmeans kmeans = new Kmeans(matrix,100);
        Kmeans.Point[] centers = kmeans.compute(4000,.001);
        kmeans.bestSamplePointsFromClusterToFileWithNames(kmeans.getClusters(),"dat/fullPeopleClusters.txt",10,kmeans.getCentroids(),people);

        System.out.println("Finding the centers of the centroids");
        int m = centers.length;
        int n = centers[0].getData().length;
        float[][] data = new float[m][n];
        for(int i=0; i<m;i++){
            data[i] = centers[i].getData();
            System.out.println(Arrays.toString(centers[i].getData()));
        }

        Kmeans kmeansCluster = new Kmeans(data,10);
        Kmeans.Point[] initialPoints = kmeansCluster.compute(100,.000000000000000001);

        System.out.println("Use the centers of the centroids as the initial points");
        Kmeans kmeansNew = new Kmeans(matrix,100);
        kmeansNew.computeUsingPoints(1,.0000001,initialPoints);
        kmeansNew.bestSamplePointsFromClusterToFileWithNames(
                kmeansNew.getClusters(),"dat/peopleClusters.txt",
                100,kmeansNew.getCentroids(),people);

//        kmeansCluster.bestSamplePointsFromClustersToFile(kmeansCluster.getClusters(),"dat/peopleClusters.txt",10,kmeansCluster.getCentroids());
//        kmeansCluster.bestSamplePointsFromClusterToFileWithNames(kmeans.getClusters(),"dat/peopleClusters.txt",10,kmeans.getCentroids(),people);

        //Running Kmeans algorithm for k predefined center points
//        int k = 6;
//        Kmeans kmeans = new Kmeans(matrix,6);
//        Kmeans.Point[] points = new Kmeans.Point[k];
//        points[0] = kmeans.createPoint(624,matrix[624]);
//        points[1] = kmeans.createPoint(1457,matrix[1457]);
//        points[2] = kmeans.createPoint(1761,matrix[1761]);
//        points[3] = kmeans.createPoint(1853,matrix[1853]);
//        points[4] = kmeans.createPoint(2027,matrix[2027]);
//        points[5] = kmeans.createPoint(1933,matrix[1933]);
//        kmeans.computeUsingPoints(1,.0000001,points);
//        kmeans.bestSamplePointsFromClusterToFileWithNames(kmeans.getClusters(),"dat/peopleClusters.txt",10,kmeans.getCentroids(),people);




    }
    public static float[][] createMatrixArray(HashMap<String,SortedMap<String,Double>> map,ArrayList<People> people){
        int size=map.keySet().size();
        float[][] matrix = new float[size][size];
        float temp = 0;
        for(int i=0;i<people.size();i++){
            for(int j=0;j<people.size();j++){
                temp=map.get(people.get(i).getID()).get(people.get(j).getID()).floatValue();
                if (Float.isNaN(temp)) {
                    matrix[i][j]=0;
                }
                else{
                    matrix[i][j]=temp;
                }
            }
        }

        return matrix;
    }
    public static void createSerializedMatrix(ArrayList<People> people) throws IOException {
        FileOutputStream file=null;
        ObjectOutputStream out=null;
        try{
            file = new FileOutputStream("dat/peopleMatrix.ser");
            out = new ObjectOutputStream(file);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        HashMap<String,SortedMap<String,Double>> allMap = new HashMap<String, SortedMap<String, Double>>();

        //Cycles through each person in list using them as the target to
        for(int i=0;i<people.size();i++){
            //Compile full matrix of scores
            allMap.put(people.get(i).getID(),scoresForAllCandidate(people, people.get(i)));
        }

        out.writeObject(allMap);
        out.close();
        file.close();

    }
    //SINGLE PERSON TEST - Prints out the result of the target person to the candidate
    public static void singleTestDistance(People target, People candidate){
        double dis = People_Distance.findDistance(target,candidate);
        System.out.println("The distance between "+target.getEmail()+" and "+candidate.getEmail()+" is "+dis);
    }

    //Finds a person by their email address - returns a people object
    public static People findPersonByEmail(ArrayList<People> people,String email){
        People peep = null;
        for(int i=1;i<people.size();i++){
            if(people.get(i).getEmail().equals(email))
                peep=people.get(i);
        }
        return peep;
    }

    //Finds a person by their people id - returns a people object
    public static People findPersonByID(ArrayList<People> people,String id){
        People peep = null;
        for(int i=1;i<people.size();i++){
            if(people.get(i).getID().equals(id))
                peep=people.get(i);
        }
        return peep;
    }

    //Prints out every email to interest in the People list
    public static void printAllList(ArrayList<People> people){
        for(int i=0;i<people.size();i++){
            for (int j=0;j<people.get(i).getInterest().size();j++){
                if(people.get(i)!=null&&people.get(i).getInterest().get(j)!=null)
                    System.out.println(people.get(i).getEmail()+"--"+people.get(i).getInterest().get(j).getName());
            }

        }
    }

    //Finds scores for all candidates relative to the target person
    // - Take in full People array and file writer object along with target person
    public static SortedMap<String,Double> scoresForAllCandidate(ArrayList<People> people, People target){
        double a = 0;
        SortedMap<String,Double> scoreMap = new TreeMap<String, Double>();

        System.out.println("Target person "+target.getEmail()+" with ID "+target.getID());

        for(int i=0;i<people.size();i++){                               //Maps each person to a distance score
            a = People_Distance.findDistance(target,people.get(i));
            scoreMap.put(people.get(i).getID(),a);
        }
                                                                         //Transforms scoreMap to SortedSet
//        SortedSet<Map.Entry<String, Double>> sortedset = new TreeSet<Map.Entry<String, Double>>(
//                new Comparator<Map.Entry<String, Double>>() {
//                    public int compare(Map.Entry<String, Double> e1,
//                                       Map.Entry<String, Double> e2) {
//                        return e1.getValue().compareTo(e2.getValue());
//                    }
//                });

//        sortedset.addAll(scoreMap.entrySet());


        return scoreMap;
    }


}
