package org.macademia.algs;  import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: research
 * Date: 6/11/13
 * Time: 4:55 PM
 */
public class Kmeans {

    private Cluster[] clusters;
    private Point[] centroids;
    private ArrayList<Point> data;
    private int k;


    /**
     * Use this constructor to create Kmeans object
     * run compute() function to get the resulting clusters
     * @param data 2-D float array that represents the matrix
     * @param k number of clusters
     */
    public Kmeans(float[][] data, int k) {
        this.data = new ArrayList<Point>();
        this.k = k;
        this.clusters = new Cluster[k];
        this.centroids = new Point[k];

        for(int i=0;i<k;i++){
            this.centroids[i] = new Point();
            this.clusters[i] = new Cluster();
        }

        for(int i=0;i<data.length;i++) {
            this.data.add(new Point(i, data[i]));
        }
    }

    public ArrayList<Point> getData() {
        return data;
    }

    public void setData(float[][] data) {
        for(int i=0;i<data.length;i++) {
            this.data.add(new Point(i, data[i]));
        }
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public Cluster[] getClusters() {
        return clusters;
    }

    public Point[] getCentroids() {
        return centroids;
    }

    private class Cluster {

        private ArrayList<Point> points = new ArrayList<Point>();

        private Cluster() {

        }

        private Cluster(ArrayList<Point> points) {
            this.points = points;
        }

        private ArrayList<Point> getPoints() {
            return points;
        }

        private void setPoints(ArrayList<Point> points) {
            this.points = points;
        }

        public String toString(){
            String result = "Cluster \n";
            result+=points.toString();
            return result;
        }

    }

    private static class Point {

        private float[] data;

        private int id;

        private Point() {
            return;
        }

        private Point(float[] data) {
            this.data = data;
        }

        private Point(int id, float[] data){
            this.id = id;
            this.data = data;
        }

        private float[] getData() {
            return data;
        }

        private void setData(float[] data) {
            this.data = data;
        }

        public String toString(){
            String result = "" + id;
            return result;
        }

    }

    /**
     * Calculate the distance between the two points
     * @param p1 the Point object for the first point
     * @param p2 the Point object for the second point
     * @return the distance between the two points
     */
    public double getDistance(Point p1, Point p2) {
        double sum = getSumOfSquares(p1,p2);
        return Math.sqrt(sum);
    }

    /**
     * Calculate the sum of squares of the two points
     * @param p1 the Point object for the first point
     * @param p2 the Point object for the second point
     * @return the sum of squares between the two points
     */
    public double getSumOfSquares(Point p1, Point p2){
        float[] coordinates1 = p1.data;
        float[] coordinates2 = p2.data;

        double sum = 0;
        for (int i=0; i < coordinates1.length; i++) {
            sum += Math.pow(Math.abs(coordinates1[i]-coordinates2[i]), 2);
        }
        return sum;

    }

    /**
     * Return the centroids of the clusters computed using Lloyd's algorithm
     * @param iterations
     * @param tolerance
     * @return centroids
     */
    public Point[] compute(int iterations, double tolerance) {

        //Initialize the centers
        centroids = getKRandomPoints(data, k);

        //Place Points into clusters
        for (Point p: data) {
            clusters[getBestClusterForPoint(p, centroids)].points.add(p);
        }


        double prevVariance = Double.POSITIVE_INFINITY;
        int c = 0;

        while (c < iterations) {

            //Clear the clusters
            for (Cluster cluster:clusters) {
                cluster.points.clear();
            }
            //Place in clusters
            for (Point p: data) {
                clusters[getBestClusterForPoint(p, centroids)].points.add(p);
            }
            //Redefine centroids
            centroids = getCentroids(clusters);

            //Calculate variance
            double curVariance = getVariance(clusters, centroids);

            if (Math.abs(curVariance-prevVariance) < tolerance) {
                return centroids;

            }

            prevVariance = curVariance;

            System.out.println("Variance:");
            System.out.println(curVariance);
            System.out.println("New Clusters");

            //Print current centroids
//            for (Point point: centroids) {
//                System.out.println(Arrays.toString(point.data));
//            }

            c++;
        }

        return centroids;

    }

    /**
     * returns k random points from within the data
     * @param data
     * @param k
     * @return an array of k points
     */
    public Point[] getKRandomPoints(ArrayList<Point> data, int k) {

        int max = data.size();

        //Generating random numbers
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random rand = new Random();
        while (numbers.size() < k) {
            int number = rand.nextInt(max);
            if (!numbers.contains(number)) {
                numbers.add(number);
            }
        }

        Point[] centroids = new Point[k];

        //Placing the points in the return array
        for (int i = 0; i < k; i++) {
            centroids[i] = new Point(data.get(numbers.get(i)).getData());
        }

        return centroids;
    }

    /**
     * Returns the index of the centroid nearest to a given point
     * Noted that the index of the centroid is the same as the index of the cluster
     * @param point
     * @param centroids
     * @return the index of the cluster
     */
    public int getBestClusterForPoint(Point point, Point[] centroids) {

        double min = Double.POSITIVE_INFINITY;
        int index = 0;

        for (int i=0; i < centroids.length; i++) {
            double temp = getDistance(centroids[i], point);
            if (min > temp) {
                min = temp;
                index = i;
            }
        }

        return index;
    }

    /**
     * Computes the intra-cluster variance over all clusters
     * @param clusters
     * @param centroids
     * @return the intra-cluster variance over all clusters
     */
    public double getVariance(Cluster[] clusters, Point[] centroids){
        int k = clusters.length;
        double result = 0;

        for(int i=0; i<k; i++){ //for each clusters
            double sum = 0;
            ArrayList<Point> points = clusters[i].points;
            Point center = centroids[i];
            for(Point p:points){ //for each point inside the cluster
                double sumSquare = getSumOfSquares(p, center);
                sum +=sumSquare;
            }
            result+=sum;
        }

        return result;
    }

    /**
     * Computes the centroids of each cluster
     * @param clusters
     * @return an array of centroid points corresponding to the array of clusters
     */
    public Point[] getCentroids(Cluster[] clusters){

        Point[] centroids = new Point[clusters.length];

        //Loops through all of the clusters
        for (int i = 0; i < clusters.length; i++) {
            Cluster c = clusters[i];
            int m = clusters[0].points.get(0).data.length; //number of entries in a point

            Point temp = new Point();

            //Initialize temp for each new cluster
            temp.setData(new float[m]);

            //Loops through all of the points inside of a cluster
            for (Point row : c.points) {

                //Sums all of the points
                for (int k = 0; k < m; k++) {
                    temp.data[k] += row.getData()[k];
                }

            }

            //Divide each entry by number of points in the cluster to calculate the mean
            for (int l = 0; l < m; l++) {
                temp.data[l] /= c.points.size();
            }

            centroids[i] = temp;

        }

        return centroids;
    }

    /**
     * Create file containing the calculation result of the algorithm
     * @param clusters an array of clusters after our calculation
     */
    public void clusterToFile(Cluster[] clusters, String path){

        try{
            FileWriter out = new FileWriter(path);

            for(int i=0;i<clusters.length;i++){
                    out.append("Cluster "+i+"\n");
                for(Point point: clusters[i].points){
                    out.append(point.id+"\n");
                }
            }

            out.flush();
            out.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Get n best points from each cluster and put them into the file
     * You need to run compute() before running this method
     * @param clusters the resulting clusters after running compute()
     * @param path output file path
     * @param n the number of best points from each clusters
     * @param centroids the cetroids array after running compute()
     */
    public void bestSamplePointsFromClustersToFile(Cluster[] clusters, String path, int n, Point[] centroids) {

        try{
            FileWriter out = new FileWriter(path);

            for (int i = 0; i<k; i++) {
                ArrayList<Point> points = getBestSamplePointsFromCluster(clusters[i], n, centroids[i]);
                out.append("Cluster "+i+"\n");
                for(Point point: points) {
                    out.append(point.id+"\n");
                }
            }
            out.flush();
            out.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Get i best points from a given cluster
     * @param cluster
     * @param n
     * @return an array of points with length k
     */
    public ArrayList<Point> getBestSamplePointsFromCluster(Cluster cluster, int n, Point centroid) {

        SortedSet<Map.Entry<Integer, Double>> sortedset = new TreeSet<Map.Entry<Integer, Double>>(
                new Comparator<Map.Entry<Integer, Double>>() {
                    public int compare(Map.Entry<Integer, Double> e1,
                                       Map.Entry<Integer, Double> e2) {
                        return e1.getValue().compareTo(e2.getValue());
                    }
                });


        SortedMap<Integer, Double> myMap = new TreeMap<Integer, Double>();

        for (Point p: cluster.points) {
            myMap.put(p.id, getDistance(p, centroid));
        }

        sortedset.addAll(myMap.entrySet());

        ArrayList<Point> points = new ArrayList<Point>();

        // Example for Integers
        Iterator<Map.Entry<Integer, Double>> it = sortedset.iterator();
        Map.Entry<Integer, Double> current;

        int i = 0;

        while (i < n && it.hasNext()) {
           current = it.next();
           points.add(data.get(current.getKey()));
           i++;
        }


        System.out.println("points:" + points);

        return points;
    }

    public static void main(String args[]) throws IOException {

        int NUM_CLUSTERS = 50;

//        double SAMPLES[][] = new double[][] {{1.0, 1.0},
//                {1.5, 2.0},
//                {3.0, 4.0},
//                {5.0, 7.0},
//                {3.5, 5.0},
//                {4.5, 5.0},
//                {3.5, 4.5}};

        SimilarityMatrix sm = new SimilarityMatrix(new File("dat/similarity.matrix"));
        float SAMPLES[][] = sm.getFloatMatrix();

        Kmeans test = new Kmeans(SAMPLES, NUM_CLUSTERS);
        Point[] centroids = test.compute(10000, .001);
        test.clusterToFile(test.clusters,"dat/clusters.txt");
//        test.getBestSamplePointsFromCluster(test.clusters[0],5,test.centroids[0]);
        test.bestSamplePointsFromClustersToFile(test.clusters, "dat/clusters.txt", 10, test.centroids);



        Parse_Clusters.printClusters("dat/phrases.tsv","dat/clusters.txt");



//        int i = 1;
//
//        for (Cluster c: test.clusters) {
//            System.out.println("Cluster " + i);
//            for (int j = 0; j < 10; j++) {
//                Point p = c.points.get(j);
//                System.out.println(p.toString());
//            }
//            i++;
//        }


//        double min = Double.POSITIVE_INFINITY;
//        double dist;
//        int[] centers = new int[NUM_CLUSTERS];
//
//        for (int j = 0; j < NUM_CLUSTERS; j++) {
//
//            Cluster cluster = test.clusters[j];
//            min = Double.POSITIVE_INFINITY;
//
//            for (Point p:  cluster.getPoints()) {
//
//                dist = test.getDistance(p, test.centroids[j]);
//
//                if (dist < min) {
//                    min = dist;
//                    centers[j] = p.id;
//                }
//            }
//
//        }
//
//        System.out.println(Arrays.toString(centers));




//        test.centroids = test.getKRandomPoints(test.getData(), test.getK());
//
//        for (Point point: test.centroids) {
//            System.out.println(Arrays.toString(point.data));
//        }
//
//        for (int i = 0; i < test.data.length; i++) {
//
////            System.out.println(Arrays.toString(test.data[i]));
//
//            Point p = new Kmeans.Point(test.data[i]);
//            test.clusters[test.getBestClusterForPoint(p, test.centroids)].points.add(p);
//        }
//
//
//
//        for (int z = 0; z< 100; z++) {
//
//            test.centroids = test.getCentroids(test.clusters);
//
//            System.out.println("New Clusters");
//
//            //Print current centroids
//            for (Point point: test.centroids) {
//
//
//                System.out.println(Arrays.toString(point.data));
//            }
//
//
//            //Redefine Centroids
//
//            for (Cluster cluster:test.clusters) {
//                cluster.points.clear();
//            }
//
//
//            for (int i = 0; i < test.data.length; i++) {
//
//                Point p = new Point(test.data[i]);
//                test.clusters[test.getBestClusterForPoint(p, test.centroids)].points.add(p);
//            }
//
//        }


    }


}
