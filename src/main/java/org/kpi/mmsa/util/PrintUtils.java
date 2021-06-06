package org.kpi.mmsa.util;

import org.kpi.mmsa.mvc.view.Model;

public class PrintUtils {
    public static String printMatrices(final double[][] matrixA, double[] matrixB, int n) {
        String result = "\n\t======================================\n";

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    result += String.format("\t%2s", "|");
                }
                result += String.format("%7.2f ", matrixA[i][j]);
                if (j == n - 1) {
                    result += String.format("%s", "|");
                }
            }
            result += String.format("\t%8s", "|");
            result += String.format("%7.2f", matrixB[i]);
            result += String.format("%s\n", "|");
        }
        result += "\t======================================\n";
        return result;
    }

    public static String printResult(final Model matrix) {
        String result = "\n\t======================================\n";
        for (int i = 0; i < matrix.getN(); i++) {
            result += String.format("\n\tX[%d] = %5.2f;\n",  i+1, matrix.getX()[i]);
        }
        return result;
    }
}
