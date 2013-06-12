package org.macademia.algs;  import java.util.ArrayList;
import java.util.Random;

/** * Created with IntelliJ IDEA. * User: research * Date: 6/11/13 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Kmeans {

    private Cluster[] clusters;
    private float[][] data;
    private Point[] centroids;

    public Kmeans(float[][] data) {
        this.data = data;
    }

    public float[][] getData() {
        return data;
    }

    public void setData(float[][] data) {
        this.data = data;
    }

    private class Cluster {
        private int id;

        private Point[] points;

        private Cluster() {

        }

        private Cluster(int id, Point[] points) {
            this.id = id;
            this.points = points;
        }

        private int getId() {
            return id;
        }

        private void setId(int id) {
            this.id = id;
        }

        private Point[] getPoints() {
            return points;
        }

        private void setPoints(Point[] points) {
            this.points = points;
        }
    }

    private class Point {

        private float [] data;
        private int id;
        private int cluster;

        private Point() {
            return;
        }

        private Point(int cluster, float[] data, int id) {
            this.cluster = cluster;
            this.data = data;
            this.id = id;
        }

        private float[] getData() {
            return data;
        }

        private void setData(float[] data) {
            this.data = data;
        }

        private int getId() {
            return id;
        }

        private void setId(int id) {
            this.id = id;
        }

        private int getCluster() {
            return cluster;
        }

        private void setCluster(int cluster) {
            this.cluster = cluster;
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
        float [] coordinates1 = p1.data;
        float [] coordinates2 = p2.data;

        double sum = 0;
        for (int i=0; i < coordinates1.length; i++) {
            sum += Math.pow(Math.abs(coordinates1[i]-coordinates2[i]), 2);
        }
        return sum;

    }

    /**
     * Calculate the sum of squares of the two points
     * @param coordinate1 the coordinate for the first point
     * @param coordinate2 the coordinate for the second point
     * @return the sum of squares between the two points
     */
    public double getSumOfSquares(float[] coordinate1, float[] coordinate2){
        double sum = 0;
        for (int i=0; i < coordinate1.length; i++) {
            sum += Math.pow(Math.abs(coordinate1[i]-coordinate2[i]), 2);
        }
        return sum;

    }

    /**
     * Return the clusters computed using Lloyd's algorithm
     * @param data a matrix of floats
     * @param k the desired number of clusters
     * @return an array of k cluster ids
     */
    public int[] getClusters(float[][] data, int k) {

        return new int[0];

    }

    /**
     * returns k random points from within the data
     * @param data
     * @param k
     * @return an array of k points
     */
    public Point[] getKRandomPoints(float[][] data, int k) {

        int MAX = data.length;

        //Generating random numbers
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random rand = new Random();
        while (numbers.size() < k) {
            int number = rand .nextInt(MAX);
            if (!numbers.contains(number)) {
                numbers.add(number);
            }
        }

        Point[] centroids = new Point[k];

        //Placing the points in the return array
        for (int i = 0; i < k; i++) {
            centroids[i] = new Point(i, data[numbers.get(i)], numbers.get(i));
        }

        return centroids;
    }



    /**
     *
     * returns the id of the center that is closest to the given point
     * @param point
     * @param centroids
     * @return the id of the cluster
     */
    public int getBestClusterForPoint(Point point, Point[] centroids){
        return 0;
    }

    /**
     * Computes the intra-cluster variance over all clusters
     * @param clusters
     * @param centroids
     * @return an array of variances
     */
    public double[] getVariance(Cluster[] clusters, Point[] centroids){
        int k = clusters.length;
        double [] vars = new double [k];
        for(int i=0; i<k; i++){ //for each clusters
            double sum = 0;
            Point[] points = clusters[i].points;
            Point center = centroids[i];
            for(Point p:points){ //for each point inside the cluster
                double sumSquare = getSumOfSquares(p, center);
                sum +=sumSquare;
            }
            vars[i] = sum;
        }

        return vars;
    }

    /**
     * Computes the centroids of each cluster
     * @param clusters
     * @return an array of centroid points corresponding to the array of clusters
     */
    public Point[] computeCentroids(Cluster[] clusters){

        return new Point[0];


    }







}
