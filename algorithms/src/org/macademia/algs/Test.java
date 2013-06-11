package org.macademia.algs;

import edu.macalester.wpsemsim.matrix.DenseMatrix;

import java.io.File;
import java.io.IOException;
import static java.lang.System.*;

public class Test {
    public static void main(String args[]) throws IOException {
        DenseMatrix matrix = new DenseMatrix(new File("dat/similarity.matrix"));

        out.println(matrix.getRow(100).asMap());




        out.println(matrix.getNumRows());

        out.println(matrix.getRowIds());
    }
}
