package org.macademia.algs;

import edu.macalester.wpsemsim.matrix.DenseMatrix;
import edu.macalester.wpsemsim.matrix.DenseMatrixRow;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: zixiao
 * Date: 6/11/13
 * Time: 9:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class MatrixParser {

    public static void matrixToCSV(DenseMatrix matrix, String path){

        try {
            FileWriter writer = new FileWriter(path);

            //Writing headers
//            StringBuffer header = new StringBuffer();
//
//            DenseMatrixRow row1 = matrix.getRow(matrix.getRowIds()[0]);
//            for(Integer interest:row1.asMap().keySet()){
//                header.append(interest.toString());
//                header.append(" , ");
//            }
//            header.delete(header.length()-3,header.length()-1);
//            header.append('\n');
//            writer.append(header.toString());
//            //System.out.print(header.toString());

            StringBuffer header = new StringBuffer();

            for(int i=0;i<matrix.getNumRows();i++){
                header.append(i+"");
                header.append(" , ");
            }
            header.delete(header.length()-3,header.length()-1);
            header.append('\n');
            writer.append(header.toString());

            for(DenseMatrixRow row:matrix){
                LinkedHashMap<Integer,Float> rowMap = row.asMap();

                //Writing similarity scores
                StringBuffer scoresOut = new StringBuffer();
                for(Float score:rowMap.values()){
                    scoresOut.append(score.toString());
                    scoresOut.append(" , ");
                }
                scoresOut.delete(scoresOut.length()-3,scoresOut.length()-1);
                scoresOut.append('\n');
                writer.append(scoresOut.toString());
            }

            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static double [][] convertToInterestArray(DenseMatrix matrix){
        int row_num = matrix.getNumRows();
        double [][] interests = new double[row_num][row_num];

        //need some implementations

        return interests;
    }

    public static void main(String args[]) throws IOException {
        // Both row and column ids are the interest ids in phrase.txt
        DenseMatrix matrix = new DenseMatrix(new File("dat/similarity.matrix"));
        String path = "/Users/zixiao/Desktop/similiar.csv";
        matrixToCSV(matrix,path);


        //System.out.println(Arrays.toString(matrix.getRowIds()));
        //(matrix.getRowIds());
        //System.out.println(matrix.getRow(16298).asMap());
        //System.out.println(matrix.getRow(1103).asMap());
    }
}
