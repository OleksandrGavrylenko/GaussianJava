package org.kpi.mmsa.util;

import org.kpi.mmsa.mvc.view.Model;

import static java.lang.String.format;

public class PrintUtils {
    public static String printMatrices(final double[][] matrixA, double[] matrixB, int n) {
        StringBuilder sb = new StringBuilder("\n\t======================================\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0) {
                    sb.append(format("\t%2s", "|"));
                }
                sb.append(format("%7.2f ", matrixA[i][j]));
                if (j == n - 1) {
                    sb.append(format("%s", "|"));
                }
            }
            sb.append(format("\t%8s", "|"));
            sb.append(format("%7.2f", matrixB[i]));
            sb.append(format("%s\n", "|"));
        }
        sb.append("\t======================================\n");
        return sb.toString();
    }

    public static String printResult(final Model matrix) {
        StringBuilder sb = new StringBuilder("\n\t======================================\n");
        for (int i = 0; i < matrix.getN(); i++) {
            sb.append(format("\n\tX[%d] = %5.2f;\n", i + 1, matrix.getX()[i]));
        }
        return sb.toString();
    }

    public static String prettyPrint(Model model) {
        double[] x = model.getX();
        String result = "";
        for (int i = 0; i < model.getN(); i++) {
            result += String.format("\n\tX[%d] = %5.2f; \n", (i + 1), x[i]);
        }
        return result;
    }
}
