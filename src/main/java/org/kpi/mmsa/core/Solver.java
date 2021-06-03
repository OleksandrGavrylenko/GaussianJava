package org.kpi.mmsa.core;

import org.kpi.mmsa.model.Matrix;

import static java.lang.Math.abs;
import static org.kpi.mmsa.util.PrintUtils.printMatrices;
import static org.kpi.mmsa.util.PrintUtils.printResult;

public class Solver {
    private final static double ZERO = 10e-7;

    public void solve(Matrix matrix) {
        if (matrix == null) {
            System.out.println("\tMatrix is not initialized.\n");
            return;
        }

        int singularFlag = forwardElimination(matrix);

        if(isSingular(singularFlag, matrix)) {
            return;
        }

        singularFlag = backSubstitution(matrix);
        if (isSingular(singularFlag, matrix)) {
            return;
        }

        System.out.println("\n\tThe Gaussian forward stroke:");
        printMatrices(matrix.getA(), matrix.getB(), matrix.getN());
        System.out.println("\n\tThe result of the Gaussian Elimination is:");
        printResult(matrix);

    }

    private int forwardElimination(Matrix matrix) {
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
                System.out.println("\n\n\tStep " + (column + 1));

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
                printMatrices(matrixA, matrixB, n);
                nextRow++;
            }
        }
        matrix.setA(matrixA);
        matrix.setB(matrixB);
        return -1;
    }

    int backSubstitution(Matrix matrix) {
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

    boolean isSingular(int singularFlag, final Matrix matrix) {
        if (singularFlag == -1) {
            return false;
        }
        System.out.println("\tThe Matrix is Singular.\n");

        if (matrix.getB()[singularFlag] > ZERO){
            System.out.println("\tInconsistent System.");
        } else {
            System.out.println("\tMay have infinitely many solutions.");
        }
        return true;
    }

    private void swapRow(double[][] matrixA, double[] matrixB, int n, int row1, int row2) {
        System.out.printf("\tSwapped rows %d and %d\n", row1+1, row2+1);

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
