package org.example;

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Matrix {
    private final double [] elements;

    private int threadLimit=2;

    public void setThreadLimit(int threadLimit) {
        this.threadLimit = threadLimit;
    }

    private int cols;
    private int rows;

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }


    public void setElement(int row, int col, double element){
        elements[row * cols + col] =element;
    }

    public double getElement(int row, int col)
    {
        return elements[row*cols+col];
    }

    public Matrix multiplyBy(Matrix right) throws InterruptedException {
        final int newRows = rows;
        final int newCols = right.cols;
        final int intermediateVectorNumber = cols;
        final double [] multipliedVectors = new double [newRows*intermediateVectorNumber*newCols];

        ExecutorService mapOne = Executors.newFixedThreadPool(threadLimit);

        int offset=0;
        for(int i=0;i<newCols;i++){
            for(int j=0;j<intermediateVectorNumber;j++){
                final int currentOffset = offset;
                final int currentJ = j;
                final double scalar = right.getElement(j, i);
                mapOne.execute(()->{
                    for(int k=0;k<newRows;k++){
                        multipliedVectors[currentOffset+k] =  scalar*getElement(k, currentJ);
                    }
                });
                offset+=newRows;
            }
        }
        mapOne.shutdown();
        mapOne.awaitTermination(2, TimeUnit.SECONDS);
        ExecutorService mapTwo = Executors.newFixedThreadPool(threadLimit);
        Matrix result = new Matrix(newRows, newCols);
        for(int i=0; i<newCols;i++){
            final int currentI = i;
            mapTwo.execute(()->{
                for(int j=0;j<newRows;j++){
                    double sum = 0;
                    for(int k=0;k<intermediateVectorNumber;k++){
                        sum+=multipliedVectors[currentI*(intermediateVectorNumber*newRows)+k*newRows+j];
                    }
                    result.setElement(j, currentI, sum);
                }
            });
        }
        mapTwo.shutdown();
        mapTwo.awaitTermination(2, TimeUnit.SECONDS);
        return result;
    }

    public Matrix(int rows, int cols) {
        this.cols = cols;
        this.rows = rows;
        elements = new double [cols*rows];
    }

    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("[");
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                result.append(getElement(i, j));
                if(j!=(cols-1)){
                    result.append(", ");
                }
            }
            if(i!=(rows-1)){
                result.append("; ");
            }
        }
        result.append("]");
        return result.toString();
    }


}
