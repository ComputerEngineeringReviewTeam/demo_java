package org.example;

public class MatrixMultiplication {
    public static void matrixMultiplication() throws InterruptedException {
        System.out.println();
        System.out.println("========================");
        System.out.println("------------------------");
        System.out.println("Mnozenie macierzy:");
        System.out.println("------------------------");
        System.out.println();

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

        System.out.println("Macierz m1: "+m1);
        System.out.println("Macierz m2: "+m2);

        Matrix m3 = m1.multiplyBy(m2);
        Matrix m4 = m2.multiplyBy(m1);

        System.out.println();
        System.out.println("m1*m2: "+m3);
        System.out.println("m2*m1: "+m4);
        System.out.println();
        System.out.println("========================");
    }

}
