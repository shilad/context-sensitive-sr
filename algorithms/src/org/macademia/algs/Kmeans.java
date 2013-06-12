package org.macademia.algs;  import java.util.ArrayList;  /** * Created with IntelliJ IDEA. * User: research * Date: 6/11/13 * Time: 4:55 PM
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

    private static class Cluster {
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

    private static class Point {

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
     * @param p1
     * @param p2
     * @return
     */
    public double getDistance(Point p1, Point p2) {

//        double sum = 0;
//        for (int i=0; i < point.length; i++) {
//            sum += Math.pow(data[i] - point[i], 2);
//        }
//        return Math.sqrt(sum);
    }

    /**
     *
     * @param data
     * @param k
     * @return an array of k cluster ids
     */
    public int[] getClusters(float[][] data, int k){

    }

    /**
     * returns k points, where the value of each dimension of the points is
     * bounded by the minimum and maximum of that dimension over all points
     * @param data
     * @param k
     * @return an array of k points
     */
    public Point[] getKRandomPoints(float[][] data, int k){

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
