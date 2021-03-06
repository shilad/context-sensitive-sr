package org.macademia.algs;


import edu.macalester.wpsemsim.matrix.DenseMatrixRow;

import java.io.*;
import java.util.*;

public class TestAlgorithms {
    public static SimilarityMatrix matrix=null;
    //0 is Shilad and 107 is Danny Kaplan in list
    //findPersonByEmail(people,"shoop@macalester.edu")
    public static void main(String args[]) throws IOException {
        ArrayList<Person> people=People_Interests.readPeople("dat/people.txt", "dat/phrases.tsv", "dat/people_interests.txt", "dat/person_departments.csv");
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
        String[] deptNames = {"Biology", "English","Chemistry",
                "Psychology","Mathematics","History","Music",
                "Political Science","Visual Arts","Philosophy",
                "Physical Ed. Recreation & Athletics",
                "Sociology","Media Studies","Computer Science",
                "Education Studies","Theatre Arts","Anthropology"};
//        for(String s:deptNames){
//            System.out.println("");
//            System.out.println(s);
//            for(Interest i: getInterestsOfDept(people,s,2)){
//                System.out.println(i.getName());
//            }
//        }
        //interestsToFile(getInterestsOfDept(people,"Computer Science",3),"dat/compSciInterests.txt");
        createDepartmentFiles(deptNames,people);
    }

    /**
     * This method produces necessary files for the download program
     * @param deptNames
     * @param people
     */
    public static void createDepartmentFiles(String[] deptNames, ArrayList<Person> people){

        try{

            File theDir = new File("dat/department");

            // if the directory does not exist, create it
            if (!theDir.exists())
            {
                boolean result = theDir.mkdir();
                if(result){
                    System.out.println("DIR created");
                }

            }

            FileWriter departOut = new FileWriter("dat/department/department.txt");

            for(String s:deptNames){
                System.out.println("");
                System.out.println(s);
                departOut.write(s+"\n");

                String name = "dat/department/"+s+".txt";
                FileWriter interestOut = new FileWriter(name);
                for(Interest i: getInterestsOfDept(people,s,3)){
                    String interestName = i.getName();
                    System.out.println(interestName);
                    if(!interestName.equals("")){
                        interestOut.write(i.getName()+"\n");
                    }
                }
                interestOut.flush();
                interestOut.close();
            }

            departOut.flush();
            departOut.close();
        } catch (IOException ex) {
            System.out.println("Unable to create file");
            ex.printStackTrace();
        }

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
    public static ArrayList<Interest> getInterestsOfDept(ArrayList<Person> people, String department, int minNumOccurrences){


        ArrayList<Person> deptPeople = new ArrayList<Person>();
        for(int i=0;i<people.size();i++){
            for(int j=0;j<people.get(i).getDepartment().size();j++){
                if(people.get(i).getDepartment().get(j).toLowerCase().equals(department.toLowerCase())){
                    deptPeople.add(people.get(i));
                }
            }
        }


        System.out.println(deptPeople.size());
        final HashMap<Interest,Integer> scores = new HashMap<Interest, Integer>();

        for(Person p:deptPeople){

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
    public static void findGroupsByInterestingPeople(int[] ids, String[] names, int num_interest, int num_people, ArrayList<Person> people) throws IOException {
        ArrayList<Person> interestingPeople = new ArrayList<Person>();

        for(int i=0;i<ids.length;i++){
            interestingPeople.add(new Person(String.valueOf(i),names[i]));
            interestingPeople.get(i).setInterest(getTopNInterests(ids[i],num_interest));
        }

        SortedSet<Map.Entry<Person, Double>> sortedset = null;
        HashMap<Person,Double> scores = null;
        for(Person p:interestingPeople){
            sortedset = new TreeSet<Map.Entry<Person, Double>>(
                    new Comparator<Map.Entry<Person, Double>>() {
                        public int compare(Map.Entry<Person, Double> e1,
                                           Map.Entry<Person, Double> e2) {
                            return e2.getValue().compareTo(e1.getValue());
                        }
                    });
            scores=new HashMap<Person, Double>();
            for(int i=0;i<people.size();i++){
                scores.put(people.get(i),People_Distance.findDistance(p,people.get(i)));
            }
            sortedset.addAll(scores.entrySet());

            Iterator<Map.Entry<Person, Double>> it = sortedset.iterator();
            Map.Entry<Person, Double> current;

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
    public static void fakePeopleClusteringTest() throws IOException {
        //Get the original people list
        ArrayList<Person> newPeople=People_Interests.readPeople("dat/people.txt", "dat/phrases.tsv", "dat/people_interests.txt", "dat/person_departments.csv");

        //Adding the fake people in
        ArrayList<Person> fakePeople = new ArrayList<Person>();
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
    public static void basicClusteringTest(ArrayList<Person> people){

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
    public static void clusteringTheClusterTest() throws IOException {
        ArrayList<Person> people=People_Interests.readPeople("dat/people.txt", "dat/phrases.tsv", "dat/people_interests.txt", "dat/person_departments.csv");
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

    public static float[][] createMatrixArray(HashMap<String,SortedMap<String,Double>> map,ArrayList<Person> people){
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
    public static void createSerializedMatrix(ArrayList<Person> people) throws IOException {
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
    public static void singleTestDistance(Person target, Person candidate){
        double dis = People_Distance.findDistance(target,candidate);
        System.out.println("The distance between "+target.getEmail()+" and "+candidate.getEmail()+" is "+dis);
    }

    //Finds a person by their email address - returns a people object
    public static Person findPersonByEmail(ArrayList<Person> people,String email){
        Person peep = null;
        for(int i=1;i<people.size();i++){
            if(people.get(i).getEmail().equals(email))
                peep=people.get(i);
        }
        return peep;
    }

    //Finds a person by their people id - returns a people object
    public static Person findPersonByID(ArrayList<Person> people,String id){
        Person peep = null;
        for(int i=1;i<people.size();i++){
            if(people.get(i).getID().equals(id))
                peep=people.get(i);
        }
        return peep;
    }

    //Prints out every email to interest in the Person list
    public static void printAllList(ArrayList<Person> people){
        for(int i=0;i<people.size();i++){
            for (int j=0;j<people.get(i).getInterest().size();j++){
                if(people.get(i)!=null&&people.get(i).getInterest().get(j)!=null)
                    System.out.println(people.get(i).getEmail()+"--"+people.get(i).getInterest().get(j).getName());
            }

        }
    }

    //Finds scores for all candidates relative to the target person
    // - Take in full Person array and file writer object along with target person
    public static SortedMap<String,Double> scoresForAllCandidate(ArrayList<Person> people, Person target){
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