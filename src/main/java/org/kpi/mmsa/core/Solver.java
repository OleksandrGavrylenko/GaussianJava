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
        printMatrices(matrix);
        System.out.println("\n\tThe result of the Gaussian Elimination is:");
        printResult(matrix);

    }

    private int forwardElimination(Matrix matrix) {
        int nextRow = 0;
//    changed to matrix->n-1 for not to show last step
        for (int column = 0; column < matrix.getN()-1; column++) {
            int maxRow = column;
            double maxValue = matrix.getA()[maxRow][column];

            for (int row = nextRow; row < matrix.getN(); row++) {
                if (abs(matrix.getA()[row][column]) > maxValue) {
                    maxValue = matrix.getA()[row][column];
                    maxRow = row;
                }
            }

            if (abs(matrix.getA()[column][maxRow]) < ZERO) {
                if (isZeroRow(matrix, maxRow)) {
                    return column;
                }
            } else {
                System.out.println("\n\n\tStep " + (column + 1));

                if(maxRow != column) {
                    swapRow(matrix, nextRow, maxRow);
                }

                for (int i = column+1; i < matrix.getN(); i++) {
                    double divider = matrix.getA()[column][column];
                    if (abs(divider) > ZERO) {
                        double f = matrix.getA()[i][column] / divider;

                        for (int j = column+1; j < matrix.getN(); j++) {
                            matrix.getA()[i][j] -= matrix.getA()[column][j] * f;
                        }

                        matrix.getB()[i] -= matrix.getB()[column] * f;
                    }

                    matrix.getA()[i][column] = 0;
                }
                printMatrices(matrix);
                nextRow++;
            }
        }
        return -1;
    }

    int backSubstitution(Matrix matrix) {
        for (int i = matrix.getN() - 1; i >= 0; i--) {
            if (isZeroRow(matrix, i)) {
                return i;
            }
            matrix.getX()[i] = matrix.getB()[i];
            for (int j=i+1; j<matrix.getN(); j++) {
                matrix.getX()[i] -= matrix.getA()[i][j] * matrix.getX()[j];
            }
            matrix.getX()[i] = matrix.getX()[i] / matrix.getA()[i][i];
        }
        return -1;
    }

    boolean isSingular(int singularFlag, Matrix matrix) {
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

    private void swapRow(Matrix matrix, int row1, int row2) {
        System.out.printf("\tSwapped rows %d and %d\n", row1+1, row2+1);

        for (int k = 0; k < matrix.getN(); ++k) {
            double temp = matrix.getA()[row1][k];
            matrix.getA()[row1][k] = matrix.getA()[row2][k];
            matrix.getA()[row2][k] = temp;
        }

        double temp = matrix.getB()[row1];
        matrix.getB()[row1] = matrix.getB()[row2];
        matrix.getB()[row2] = temp;
    }

    private boolean isZeroRow(Matrix matrix, int row) {
        for (int col = 0; col < matrix.getN(); ++col) {
            if (abs(matrix.getA()[row][col]) > ZERO) {
                return false;
            }
        }
        return true;
    }
}
