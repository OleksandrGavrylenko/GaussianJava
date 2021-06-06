package org.kpi.mmsa.util;

import org.kpi.mmsa.model.Matrix;

public class PrintUtils {
    public static void printMatrices(final double[][] matrixA, double[] matrixB, int n) {
        System.out.println("\n\t======================================\n");
//        int n = matrix.getN();
//        double[][] matrixA = matrix.getA();
//        double[] matrixB = matrix.getB();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    System.out.printf("\t%2s", "|");
                }
                System.out.printf("%7.2f ", matrixA[i][j]);
                if (j == n - 1) {
                    System.out.printf("%s", "|");
                }
            }
            System.out.printf("\t%8s", "|");
            System.out.printf("%7.2f", matrixB[i]);
            System.out.printf("%s\n", "|");
        }
        System.out.println("\t======================================\n");
    }

    public static void printResult(final Matrix matrix) {
        System.out.println("\n\t======================================\n");
        for (int i = 0; i < matrix.getN(); i++) {
            System.out.printf("\n\tX[%d] = %5.2f;\n", i+1, matrix.getX()[i]);
        }
    }
}
