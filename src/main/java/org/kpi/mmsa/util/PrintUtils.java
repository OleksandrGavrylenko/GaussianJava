package org.kpi.mmsa.util;

import org.kpi.mmsa.model.Matrix;

public class PrintUtils {
    public static void printMatrices(final Matrix matrix) {
        System.out.println("\n\t======================================\n");
        for (int i = 0; i < matrix.getN(); i++) {
            for (int j = 0; j < matrix.getN(); j++) {
                if (j == 0) {
                    System.out.printf("\t%2s", "|");
                }
                System.out.printf("%7.2f ", matrix.getA()[i][j]);
                if (j == matrix.getN() - 1) {
                    System.out.printf("%s", "|");
                }
            }
            System.out.printf("\t%8s", "|");
            System.out.printf("%7.2f", matrix.getB()[i]);
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
