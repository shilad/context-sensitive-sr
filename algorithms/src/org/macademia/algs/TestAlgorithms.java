package org.macademia.algs;


import edu.macalester.wpsemsim.matrix.DenseMatrixRow;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;

import java.io.*;
import java.util.*;

public class TestAlgorithms {
    public static SimilarityMatrix matrix=null;
    //0 is Shilad and 107 is Danny Kaplan in list
    //findPersonByEmail(people,"shoop@macalester.edu")
    public static void main(String args[]) throws IOException {
        ArrayList<People> people=People_Interests.makePeopleInterests("dat/people.txt","dat/phrases.tsv","dat/people_interests.txt","dat/person_departments.csv");
        //People_Distance.serializeVectorMap(people,"dat/peopleVectors.ser");

        if(matrix==null){
            try {
                System.out.println("Loading Similarity Matrix");
                matrix = new SimilarityMatrix(new File("dat/similarity.matrix"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


              /*
              Political Theory 5688	207
              US politics 7029	12031
              Biochemsitry 7052 738
              general biology   6252	11104
              Computer Science 2836 15495
              Applied Math 1201 293
              Psychology 7163 890
              */
        int[] ids = {207,12031,738,11104,15495,293,890};
        String[] names = {"Political Theory","US Politics","Biochemsitry","General Biology",
                "Computer Science","Applied Math","Psychology"};
//        findGroupsByInterestingPeople(ids,names,200,people);
        String[] deptNames = {"Philosophy","Political Science","Biology","Chemistry","Theatre Arts","Visual Arts","Media Studies",
                "History","Psychology","Enviromental Studies","Computer Science","Mathematics","Sociology","Anthropology",
                "Religious Studies","Neuroscience","Physics","Astronomy","Music","English","Education Studies","Classics","Geology",
                "Geography","Engineering","Linguistics","Health Sciences","Physical Ed. Recreation & Athletics","Gender and Sexuality Studies"};
        for(String s:deptNames){
            System.out.println(s);
            for(Interest i: getInterestsOfDept(people,s,3)){
                System.out.println("\t"+i.getName());
            }
        }
        //interestsToFile(getInterestsOfDept(people,"Computer Science",3),"dat/compSciInterests.txt");
    }
    public static void interestsToFile(ArrayList<Interest> interests, String path){
        try{
            FileWriter out = new FileWriter(path);
            for(Interest i:interests){
                out.append(i.getName()+"\n");
            }
            out.flush();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static ArrayList<Interest> getInterestsOfDept(ArrayList<People> people, String department, int minNumOccurrences){


        ArrayList<People> deptPeople = new ArrayList<People>();
        for(int i=0;i<people.size();i++){
            for(int j=0;j<people.get(i).getDepartment().size();j++){
                if(people.get(i).getDepartment().get(j).toLowerCase().equals(department.toLowerCase())){
                    deptPeople.add(people.get(i));
                }
            }
        }


        System.out.println(deptPeople.size());
        final HashMap<Interest,Integer> scores = new HashMap<Interest, Integer>();

        for(People p:deptPeople){

            for(Interest i:p.getInterest()) {
                if (i != null) {
                    if(scores.containsKey(i)){
                        scores.put(i,scores.get(i)+1);
                    }
                    else{
                        scores.put(i,1);
                    }
                }
            }
        }

//        TreeMap<Interest,Integer> sortedMap = new TreeMap<Interest, Integer>(
//                new Comparator<Interest>() {
//                    public int compare(Interest i, Interest i2) {
//                        return scores.get(i2).compareTo(scores.get(i));
//                    }
//                }
//        );                                                                                     fuck it I'm doing it the shitty way
//        for(Interest i:scores.keySet()){
//            sortedMap.put(i,scores.get(i));
//        }
//        ArrayList<Interest> interests = new ArrayList<Interest>();
//
//        for(Interest current:sortedMap.keySet()){
//            if(sortedMap.get(current)>1){
//                interests.add(current);
//                System.out.println(current.getName()+"--"+sortedMap.get(current));
//            }
//
//        }
        ArrayList<Interest> interests = new ArrayList<Interest>();
        int count=0;
        int cur=0;
        int best=-1;
        Interest bestInterest=null;
        boolean found;
        do{
            found=false;
            for(Interest current:scores.keySet()){
                cur=scores.get(current);
                if(cur>=minNumOccurrences&&cur>best&&!interests.contains(current)){
                    best=cur;
                    bestInterest=current;
                    found=true;
                }

            }
            if(found)
                interests.add(bestInterest);

            cur=0;
            best=0;
            count++;
        }while(found);
        return interests;
    }
    public static void findGroupsByInterestingPeople(int[] ids, String[] names, int num_interest, int num_people, ArrayList<People> people) throws IOException {
        ArrayList<People> interestingPeople = new ArrayList<People>();

        for(int i=0;i<ids.length;i++){
            interestingPeople.add(new People(String.valueOf(i),names[i]));
            interestingPeople.get(i).setInterest(getTopNInterests(ids[i],num_interest));
        }

        SortedSet<Map.Entry<People, Double>> sortedset = null;
        HashMap<People,Double> scores = null;
        for(People p:interestingPeople){
            sortedset = new TreeSet<Map.Entry<People, Double>>(
                    new Comparator<Map.Entry<People, Double>>() {
                        public int compare(Map.Entry<People, Double> e1,
                                           Map.Entry<People, Double> e2) {
                            return e2.getValue().compareTo(e1.getValue());
                        }
                    });
            scores=new HashMap<People, Double>();
            for(int i=0;i<people.size();i++){
                scores.put(people.get(i),People_Distance.findDistance(p,people.get(i)));
            }
            sortedset.addAll(scores.entrySet());

            Iterator<Map.Entry<People, Double>> it = sortedset.iterator();
            Map.Entry<People, Double> current;

            int i = 0;

            while (i < num_people && it.hasNext()) {
                current = it.next();
                System.out.println("Num:"+i+": "+current.getKey().getEmail()+"\tPerson\'s Score: "+current.getValue());
                if(current.getKey().getInterest().size()!=0){
                    for(Interest interest:current.getKey().getInterest()){
                        if(interest!=null)
                            System.out.println("\t\t"+interest.getName());
                    }
                }
                i++;
            }
            scores.clear();
            sortedset.clear();
        }
    }
    public static ArrayList<Interest> getTopNInterests(int interestID, int n) throws IOException {
        ArrayList<Interest> interests = new ArrayList<Interest>();
        SortedSet<Map.Entry<Integer, Float>> sortedset = new TreeSet<Map.Entry<Integer, Float>>(
                new Comparator<Map.Entry<Integer, Float>>() {
                    public int compare(Map.Entry<Integer, Float> e1,
                                       Map.Entry<Integer, Float> e2) {
                        return e2.getValue().compareTo(e1.getValue());
                    }
                });


        DenseMatrixRow row = matrix.getRow(interestID);
        HashMap<Integer,Float> map =row.asMap();

        sortedset.addAll(map.entrySet());
        BufferedReader file = null;
        try{
        file =new BufferedReader(new FileReader("dat/phrases.tsv"));
        }catch(IOException e){
            e.printStackTrace();
        }
        HashMap<String, Interest> interestMap =People_Interests.makeInterestMap(file);

        Iterator<Map.Entry<Integer, Float>> it = sortedset.iterator();
        Map.Entry<Integer, Float> current;

        int i = 0;

        while (i < n && it.hasNext()) {
            current = it.next();
            interests.add(interestMap.get(current.getKey().toString()));
            i++;
        }
        return interests;
    }

    /**
     * We make fake people vectors containing top interests from popular areas
     * We use those fake people vector to be the center of the cluster and divide people into different clusters
     *
     */
    public static void fakePeopleClusteringTest(){
        //Get the original people list
        ArrayList<People> newPeople=People_Interests.makePeopleInterests("dat/people.txt","dat/phrases.tsv","dat/people_interests.txt","dat/person_departments.csv");

        //Adding the fake people in
        ArrayList<People> fakePeople = new ArrayList<People>();
        newPeople.addAll(fakePeople);

        try{
            createSerializedMatrix(newPeople);

            HashMap<String,SortedMap<String,Double>> map;
            map= deserializePeopleMatrix("dat/peopleMatrix.ser");

            //Running Kmeans for one round
            float[][] newMatrix = createMatrixArray(map,newPeople);
            Kmeans kmeans = new Kmeans(newMatrix, 6);
            kmeans.compute(1,0.001);

        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * The basic test that finds 100 clusters for a group of people vectors
     */
    public static void basicClusteringTest(ArrayList<People> people){

        //createSerializedMatrix(people);
        HashMap<String,SortedMap<String,Double>> map;
        map= deserializePeopleMatrix("dat/peopleMatrix.ser");
        float[][] matrix = createMatrixArray(map, people);

        //Running Kmeans algorithm for k random center points
        System.out.println("Finding the centroids for clusters");
        Kmeans kmeans = new Kmeans(matrix,100);
        Kmeans.Point[] centers = kmeans.compute(4000,.001);
        kmeans.bestSamplePointsFromClusterToFileWithNames(kmeans.getClusters(),"dat/fullPeopleClusters.txt",10,kmeans.getCentroids(),people);
    }

    /**
     * First finds the centers of the 100 clusters for a group of people vectors
     * Then use the centers as the data points of the new clusters (clustering the centers)
     * Finally use the centers of the new clusters to classify people
     *
     */
    public static void clusteringTheClusterTest(){
        ArrayList<People> people=People_Interests.makePeopleInterests("dat/people.txt","dat/phrases.tsv","dat/people_interests.txt","dat/person_departments.csv");
        //createSerializedMatrix(people);
        HashMap<String,SortedMap<String,Double>> map;
        map= deserializePeopleMatrix("dat/peopleMatrix.ser");
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

        for(int i=0;i<people.size();i++){          //Cycles through each person in list using them as the target to
            allMap.put(people.get(i).getID(),scoresForAllCandidate(people, people.get(i)));            //compile full matrix of scores
        }

        out.writeObject(allMap);
        out.close();
        file.close();

    }

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
