package org.macademia.algs;

/**
 * Created with IntelliJ IDEA.
 * User: zixiao
 * Date: 6/11/13
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */

import edu.macalester.wpsemsim.matrix.DenseMatrix;
import edu.macalester.wpsemsim.matrix.DenseMatrixRow;
import edu.macalester.wpsemsim.matrix.MemoryMappedMatrix;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class SimilarityMatrix extends DenseMatrix{
    /**
     * Create a similarity matrix based on the data in a particular file.
     * Using the default MAX_VALUE and MAX_PAGE_SIZE
     * @param path Path to the matrix data file
     * @throws IOException
     */
    public SimilarityMatrix(File path) throws IOException {
        this(path, Integer.MAX_VALUE, DEFAULT_MAX_PAGE_SIZE);
    }

    /**
     * Create a similarity matrix based on the data in a particular file.
     * @param path Path to the matrix data file.
     * @param maxOpenPages The maximum number of memory mapped pages that can be open at once.
     * @param maxPageSize The maximum size of a memory mapped page.
     * @throws IOException
     */
    public SimilarityMatrix(File path, int maxOpenPages, int maxPageSize) throws IOException {
        super(path, maxOpenPages, maxPageSize);
    }

    /**
     *
     * @return a 2-D array that has all the scores in the similarity matrix
     */
    public float[][] getFloatMatrix(){
        int row_num = this.getNumRows();
        float[][] scores = new float[row_num][row_num];

        int i = 0;
        for (DenseMatrixRow row:this){
            scores[i] = row.getValues();
            i++;
        }

        return scores;
    }

    /**
     * Output a CSV file based on the similarity matrix
     * The headers are 0 to getNumRows - 1
     * @param path the output path for the CSV file
     */
    public void matrixToCSV(String path){

        try {
            FileWriter writer = new FileWriter(path);

            //Writing headers using the interest id
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

            //Writing headers 0 to length-1
            StringBuffer header = new StringBuffer();

            for(int i=0;i<this.getNumRows();i++){
                header.append(i+"");
                header.append(" , ");
            }
            header.delete(header.length()-3,header.length()-1);
            header.append('\n');
            writer.append(header.toString());

            for(DenseMatrixRow row:this){
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
            e.printStackTrace();
        }

    }

    public static void main(String args[]){
        try{
            SimilarityMatrix sm = new SimilarityMatrix(new File("dat/similarity.matrix"));
            float[][] matrix = sm.getFloatMatrix();
            System.out.print("This lenghth of the matrix is "+matrix.length);
            System.out.print(Arrays.deepToString(matrix));
        }catch (IOException ex){
            ex.printStackTrace();
        }


    }


}
