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

        //TODO: Utworz i inicjalizuj pierwszego ThreadPoola tutaj. Maksymalna liczba watkow jest w istniejacym polu "threadLimit"

        //MAP 1
        int offset=0;
        for(int i=0;i<newCols;i++){
            for(int j=0;j<intermediateVectorNumber;j++){

                final double scalar = right.getElement(j, i);

                //TODO: Ponizsza petla mapuje wektor macierzy, na wektor pomnozony przez odpowiedni skalar. Wynik umieszcza w odpowiednim miejscu tablicy.
                // Umiesc ta petle w funkcji lambda i przekaz do wykonania dla pierwszego Thread Poola.
                // Zauwaz, iz zmienne "j" i "offset" zmieniaja wartosc w kazdej iteracji petli, nalezy wiec utworzyc kopie tych zmiennych, opatrzone slowem kluczowym ,,final"
                // i to je wykorzystac w funkcji lambda przekazanej do Thread Poola.
                for(int k=0;k<newRows;k++){
                    multipliedVectors[offset+k] =  scalar*getElement(k, j);
                }

                offset+=newRows;
            }
        }

        //TODO: W tym miejscu zamknij kolejke pierwszego Thread Poola i nakaz mu zakonczenie pracy.
        //TODO: W tym miejscu poczekaj (maksymalnie przez 3 sekundy), az pierwszy Thread Pool wykona wszystkie zadania.

        //TODO: Utworz i inicjalizuj drugiego ThreadPoola tutaj. Maksymalna liczba watkow jest w istniejacym polu "threadLimit"

        //REDUCE + MAP 2
        Matrix result = new Matrix(newRows, newCols);
        for(int i=0; i<newCols;i++){

            //TODO: Ponizsza petla redukuje grupy wektorow do jednego wektora, a nastepnie mapuje wektory do macierzy wynikowej.
            // Umiesc ta petle w funkcji lambda i przekaz do wykonania dla drugiego Thread Poola.
            // Zauwaz, iz zmiena "i" zmienia wartosc w kazdej iteracji petli, nalezy wiec utworzyc kopie tej zmiennej, opatrzona slowem kluczowym ,,final"
            // i to ja wykorzystac w funkcji lambda przekazanej do Thread Poola.
            for(int j=0;j<newRows;j++){
                double sum = 0;
                for(int k=0;k<intermediateVectorNumber;k++){
                    sum+=multipliedVectors[i*(intermediateVectorNumber*newRows)+k*newRows+j];
                }
                result.setElement(j, i, sum);
            }

        }
        //TODO: W tym miejscu zamknij kolejke drugiego Thread Poola i nakaz mu zakonczenie pracy.
        //TODO: W tym miejscu poczekaj (maksymalnie przez 3 sekundy), az drugi Thread Pool wykona wszystkie zadania.

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
