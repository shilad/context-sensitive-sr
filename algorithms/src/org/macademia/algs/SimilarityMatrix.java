package org.macademia.algs;

/**
 * Created with IntelliJ IDEA.
 * User: zixiao
 * Date: 6/11/13
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */

import edu.macalester.wpsemsim.matrix.DenseMatrix;
import edu.macalester.wpsemsim.matrix.MemoryMappedMatrix;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SimilarityMatrix extends DenseMatrix{
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
        super(path,maxOpenPages,maxPageSize);
    }

}
