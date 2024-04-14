package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        Matrix m1 = new Matrix(3, 2);
        Matrix m2 = new Matrix(2, 3);
        double sum=1;

        for(int i=0;i<m1.getRows();i++){
            for(int j=0;j<m1.getCols();j++)
            {
                m1.setElement(i, j, sum);
                sum+= 1.0;
            }
        }
        sum=1.0;
        for(int i=0;i<m2.getRows();i++){
            for(int j=0;j<m2.getCols();j++)
            {
                m2.setElement(i, j, sum);
                sum+= 1.0;
            }
        }
        m1.setThreadLimit(4);
        Matrix m3 = m2.multiplyBy( m1);
        System.out.println(m3);
    }
}