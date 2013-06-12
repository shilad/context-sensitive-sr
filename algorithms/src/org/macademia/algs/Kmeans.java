package org.macademia.algs;  import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: research
 * Date: 6/11/13
 * Time: 4:55 PM
 */
public class Kmeans {

    private Cluster[] clusters;
    private Point[] centroids;
    private int k;
    private float[][] data;

    public Kmeans(float[][] data, int k) {
        this.data = data;
        this.k = k;
        this.clusters = new Cluster[k];
        this.centroids = new Point[k];

        for(int i=0;i<k;i++){
            this.centroids[i] = new Point();
            this.clusters[i] = new Cluster();
        }
    }

    public float[][] getData() {
        return data;
    }

    public void setData(float[][] data) {
        this.data = data;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
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



    }

    private static class Point {

        private float[] data;

        private Point() {
            return;
        }

        private Point(float[] data) {
            this.data = data;
        }

        private float[] getData() {
            return data;
        }

        private void setData(float[] data) {
            this.data = data;
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
     * Calculate the sum of squares of the two points
     * @param coordinate1 the coordinate for the first Point
     * @param coordinate2 the coordinate for the second Point
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
     * @param iterations
     * @param tolerance
     * @return centroids
     */
    public Point[] getClusters(int iterations, double tolerance) {

        //Initialize the centers
        centroids = getKRandomPoints(data, k);

        //Place Points into clusters
        for (int i = 0; i < data.length; i++) {
            Point p = new Point(data[i]);
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
            for (int i = 0; i < data.length; i++) {
                Point p = new Point(data[i]);
                clusters[getBestClusterForPoint(p, centroids)].points.add(p);
            }
            //Redefine centroids
            centroids = computeCentroids(clusters);

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
//
//
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
    public Point[] getKRandomPoints(float[][] data, int k) {

        int max = data.length;

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
            centroids[i] = new Point(data[numbers.get(i)]);
        }

        return centroids;
    }

    /**
     * Returns the index of the centroid nearest to a given point
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
    public Point[] computeCentroids(Cluster[] clusters){

        Point[] centroids = new Point[clusters.length];

        //Loops through all of the clusters
        for (int i = 0; i < clusters.length; i++) {
            Cluster c = clusters[i];
            int m = clusters[0].points.get(0).data.length;

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

    public int[] bestPointsForCluster(Point[] centroids) {
        int[] indexes = new int[k];
        double min = Double.POSITIVE_INFINITY;
        int index = 0;


        for (int i = 0; i < k; i++) {
            Point c = centroids[i];

            for(int j = 0; j < data.length; k++) {
                Point p = new Point(data[j]);
                double temp = getDistance(p, c);

                if (temp < min) {
                   min = temp;
                   index = i;
                }
            }
            indexes[i] = index;

        }
        return indexes;
    }

    public static void main(String rgs[]) throws IOException {

        int NUM_CLUSTERS = 10;

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

        Point[] c = test.getClusters(10, 1);

        System.out.println(Arrays.toString(test.bestPointsForCluster(c)));

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
//            test.centroids = test.computeCentroids(test.clusters);
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
