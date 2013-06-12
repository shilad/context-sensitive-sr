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
     *
     * @param p1 the Point object for the first point
     * @param p2 the Point object for the second point
     * @return the distance between the two points
     */
    public double getDistance(Point p1, Point p2) {

        float [] coordinates1 = p1.data;
        float [] coordinates2 = p2.data;

        double sum = 0;
        for (int i=0; i < coordinates1.length; i++) {
            sum += Math.pow(Math.abs(coordinates1[i]-coordinates2[i]), 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Return the clusters computed using Lloyd's algorithm
     * @param data a matrix of floats
     * @param k the desired number of clusters
     * @return an array of k cluster ids
     */
    public int[] getClusters(float[][] data, int k) {

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

    }

    /**
     * Computes the intra-cluster variance over all clusters
     * @param clusters
     * @param centroids
     * @return
     */
    public double getVariance(Cluster[] clusters, Point[] centroids){

    }

    /**
     * Computes the centroids of each cluster
     * @param clusters
     */
    public void computeCentroids(Cluster[] clusters){

    }







}
