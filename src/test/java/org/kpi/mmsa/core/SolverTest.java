package org.kpi.mmsa.core;

import org.junit.Assert;
import org.junit.Test;
import org.kpi.mmsa.mvc.Model;


public class SolverTest {

    @Test
    public void solveExample1() {
        Solver solver = new Solver();

        Model matrixToSolve = createInitMatrix1();
        solver.solve(matrixToSolve);

        double[] correctResult = new double[] {-3.25, -0.15, -3.15, 0.80};
        double[] actualResult = matrixToSolve.getX();
        Assert.assertEquals(correctResult[0], actualResult[0], 0.01);
        Assert.assertEquals(correctResult[1], actualResult[1], 0.01);
        Assert.assertEquals(correctResult[2], actualResult[2], 0.01);
        Assert.assertEquals(correctResult[3], actualResult[3], 0.01);
    }

    @Test
    public void solveExample2() {
        Solver solver = new Solver();

        Model matrixToSolve = createInitMatrix2();
        solver.solve(matrixToSolve);

        double[] correctResult = new double[] {-2.17, -11.89, -1.32};
        double[] actualResult = matrixToSolve.getX();
        Assert.assertEquals(correctResult[0], actualResult[0], 0.01);
        Assert.assertEquals(correctResult[1], actualResult[1], 0.01);
        Assert.assertEquals(correctResult[2], actualResult[2], 0.01);

    }

    @Test
    public void solveExample3() {
        Solver solver = new Solver();

        Model matrixToSolve = createInitMatrix3();
        solver.solve(matrixToSolve);

        double[] correctResult = new double[] {2, 4.71, 6};
        double[] actualResult = matrixToSolve.getX();
//        Assert.assertEquals(correctResult[0], actualResult[0], 0.01);
//        Assert.assertEquals(correctResult[1], actualResult[1], 0.01);
//        Assert.assertEquals(correctResult[2], actualResult[2], 0.01);
    }

    @Test
    public void solveExample4() {
        Solver solver = new Solver();

        Model matrixToSolve = createInitMatrix4();
        solver.solve(matrixToSolve);

        double[] correctResult = new double[] {3, 2.25, 1.5, 0.0};
        double[] actualResult = matrixToSolve.getX();
//        Assert.assertEquals(correctResult[0], actualResult[0], 0.01);
//        Assert.assertEquals(correctResult[1], actualResult[1], 0.01);
//        Assert.assertEquals(correctResult[2], actualResult[2], 0.01);
//        Assert.assertEquals(correctResult[3], actualResult[3], 0.01);
    }

    @Test
    public void backSubstitution() {

    }

    private Model createInitMatrix1() {
        Model matrix = new Model(4);

//        matrix.setN(4);
        matrix.setA(new double[][]{{1, 3, -2, -7}, {-1, -2, 1, 2}, {-2, -1, 3, 1}, {-3, -2, 3, 3}});
        matrix.setB(new double[]{-3, 2, -2, 3});
        matrix.setX(new double[]{0, 0, 0, 0});

        return matrix;
    }

    private Model createInitMatrix2() {
        Model matrix = new Model(3);

//        matrix.setN(3);
        matrix.setA(new double[][]{{7.5, 2.4, -15}, {6.15, -4.3, 5.1}, {11.5, -4.7, 12}});
        matrix.setB(new double[]{-25, 31, 15});
        matrix.setX(new double[]{0, 0, 0});

        return matrix;
    }

    private Model createInitMatrix3() {
        Model matrix = new Model(3);

//        matrix.setN(3);
        matrix.setA(new double[][]{{7, -2, -1}, {6, -4, -5}, {1, 2, 4}});
        matrix.setB(new double[]{2, 3, 5});
        matrix.setX(new double[]{0, 0, 0});

        return matrix;
    }

    private Model createInitMatrix4() {
        Model matrix = new Model(4);

//        matrix.setN(4);
        matrix.setA(new double[][]{{2, 3, -1, 1}, {8, 12, -9, 8}, {4, 6, 3, -2}, {2, 3, 9, -7}});
        matrix.setB(new double[]{1, 3, 3, 3});
        matrix.setX(new double[]{0, 0, 0, 0});

        return matrix;
    }

    @Test
    public void notSingular() {
        Solver solver = new Solver();

        Model matrix = createSingularMatrix(2);
        int singularFlag = -1;
        boolean singular = solver.isSingular(singularFlag, matrix);

        Assert.assertFalse(singular);
    }

    @Test
    public void singular() {
        Solver solver = new Solver();

        Model matrix = createSingularMatrix(2);
        int singularFlag = 2;
        boolean singular = solver.isSingular(singularFlag, matrix);

        Assert.assertTrue(singular);
    }

    private Model createSingularMatrix(int zeroRow) {
        Model matrix = new Model(5);
        double[] matrixB = new double[] {1,2,3,4,5};
        matrixB[zeroRow] = 0.0;

        matrix.setB(matrixB);
        matrix.setB(matrixB);

        return matrix;
    }
}