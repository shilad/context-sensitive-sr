//package org.macademia.algs;
//
//import weka.classifiers.misc.SerializedClassifier;
//import weka.clusterers.EM;
//import weka.core.Instances;
//import weka.core.converters.CSVLoader;
//import weka.filters.Filter;
//import weka.filters.unsupervised.attribute.ClusterMembership;
//
//import java.io.File;
//
//
//
//
//
//
///**
// * Created with IntelliJ IDEA.
// * User: Ben
// * Date: 6/11/13
// * Time: 2:17 PM
// * To change this template use File | Settings | File Templates.
// */
//public class Cluster {
//    public static void main(String[] args) throws Exception {
//
////        //Loading the data
////        CSVLoader loader = new CSVLoader();
////        loader.setSource(new File("/Users/research/Desktop/similiar.csv"));
////        Instances data = loader.getDataSet();
////
//////        //Removing the classes
//////        Remove filter = new Remove();
//////        filter.setAttributeIndices("" + (data.classIndex()) + 1);
//////        filter.setInputFormat(data);
//////        Instances dataClusterer = filter.useFilter(data, filter);
////
////        //Building the Clusterer
////        EM clusterer = new EM();	// new instance of clusterer
//        Filter.filterFile(new ClusterMembership(), new String[]{
//                "W", "weka.clusterers.EM"
//                "I", "last" \\                 # we want to ignore the class attribute\n" +
//                        "    -i anneal.arff \\\n" +
//                        "    -o out.arff \\\n" +
//                        "    -- \\                      # additional options for EM follow after the --\n" +
//                        "    -I 10"
//        });
//
//        SerializedClassifier classifier = new SerializedClassifier();
//        classifier.setModelFile(new File("/Users/research/DensityResultModel.model"));
//
//
//
//
//
//    }
//
//    public static void loadClusterer(String s) {
//
//    }
//}
