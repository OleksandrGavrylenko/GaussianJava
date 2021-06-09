package org.kpi.mmsa.core;

import org.kpi.mmsa.model.Result;
import org.kpi.mmsa.mvc.Model;

import static java.lang.Math.abs;
import static org.kpi.mmsa.util.PrintUtils.printMatrices;
import static org.kpi.mmsa.util.PrintUtils.printResult;

public class Solver {
    private final static double ZERO = 10e-7;
    private StringBuilder sb = new StringBuilder();

    public Result solve(Model matrix) {
        sb = new StringBuilder();
        if (matrix == null) {
            sb.append("\tMatrix is not initialized.\n");
            return new Result(matrix, sb.toString());
        }

        int singularFlag = forwardElimination(matrix);

        if(isSingular(singularFlag, matrix)) {
            return new Result(null, sb.toString());
        }

        singularFlag = backSubstitution(matrix);
        if (isSingular(singularFlag, matrix)) {
            return new Result(null, sb.toString());
        }

        sb.append("\n\tThe Gaussian forward stroke:");
        sb.append(printMatrices(matrix.getA(), matrix.getB(), matrix.getN()));
        sb.append("\n\tThe result of the Gaussian Elimination is:");
        sb.append(printResult(matrix));

        return new Result(matrix, sb.toString());
    }

    private int forwardElimination(Model matrix) {
        int nextRow = 0;
//    changed to matrix->n-1 for not to show last step
        int n = matrix.getN();
        double[][] matrixA = matrix.getA();
        double[] matrixB = matrix.getB();

        for (int column = 0; column < n-1; column++) {
            int maxRow = column;

            double maxValue = matrixA[maxRow][column];

            for (int row = nextRow; row < n; row++) {
                if (abs(matrixA[row][column]) > maxValue) {
                    maxValue = matrixA[row][column];
                    maxRow = row;
                }
            }

            if (abs(matrixA[column][maxRow]) < ZERO) {
                if (isZeroRow(matrixA, n, maxRow)) {
                    return column;
                }
            } else {
                sb.append("\n\n\tStep " + (column + 1));

                if(maxRow != column) {
                    swapRow(matrixA, matrixB, n, nextRow, maxRow);
                }

                for (int i = column+1; i < n; i++) {
                    double divider = matrixA[column][column];
                    if (abs(divider) > ZERO) {
                        double f = matrixA[i][column] / divider;

                        for (int j = column+1; j < n; j++) {
                            matrixA[i][j] -= matrixA[column][j] * f;
                        }

                        matrixB[i] -= matrixB[column] * f;
                    }

                    matrixA[i][column] = 0;
                }
                sb.append(printMatrices(matrixA, matrixB, n));
                nextRow++;
            }
        }
        matrix.setA(matrixA);
        matrix.setB(matrixB);
        return -1;
    }

    private int backSubstitution(Model matrix) {
        int n = matrix.getN();
        double[] matrixB = matrix.getB();
        double[][] matrixA = matrix.getA();
        double[] matrixX = matrix.getX();

        for (int i = n - 1; i >= 0; i--) {
            if (isZeroRow(matrixA, n, i)) {
                return i;
            }
            matrixX[i] = matrixB[i];
            for (int j = i+1; j< n; j++) {
                matrixX[i] -= matrixA[i][j] * matrixX[j];
            }
            matrixX[i] = matrixX[i] / matrixA[i][i];
        }

        matrix.setA(matrixA);
        matrix.setB(matrixB);
        matrix.setX(matrixX);
        return -1;
    }

    boolean isSingular(int singularFlag, final Model matrix) {
        if (singularFlag == -1) {
            return false;
        }
        sb.append("\tThe Matrix is Singular.\n");

        if (matrix.getB()[singularFlag] > ZERO){
            sb.append("\tInconsistent System.");
        } else {
            sb.append("\tMay have infinitely many solutions.");
        }
        return true;
    }

    private void swapRow(double[][] matrixA, double[] matrixB, int n, int row1, int row2) {
        sb.append(String.format("\tSwapped rows %d and %d\n", row1+1, row2+1));

        for (int k = 0; k < n; ++k) {
            double temp = matrixA[row1][k];
            matrixA[row1][k] = matrixA[row2][k];
            matrixA[row2][k] = temp;
        }

        double temp = matrixB[row1];
        matrixB[row1] = matrixB[row2];
        matrixB[row2] = temp;
    }

    private boolean isZeroRow(double[][] matrixA, int n, int row) {

        for (int col = 0; col < n; ++col) {
            if (abs(matrixA[row][col]) > ZERO) {
                return false;
            }
        }
        return true;
    }
}
