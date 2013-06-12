package org.macademia.algs;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: research
 * Date: 6/11/13
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Kmeans {

    private static class Centroid {

        private float [] data;
        private int id;

        public Centroid() {
            return;
        }

        public Centroid(float [] data, int id) {
            this.data = data;
            this.id = id;
        }

        private int getId() {
            return id;
        }

        private void setId(int id) {
            this.id = id;
        }

        private float[] getData() {
            return data;
        }

        private void setData(float[] data) {
            this.data = data;
        }

        public double getDistance(float [] point) {
            double sum = 0;
            for (int i=0; i < point.length; i++) {
                sum += Math.pow(data[i] - point[i], 2);
            }
            return Math.sqrt(sum);
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


}
