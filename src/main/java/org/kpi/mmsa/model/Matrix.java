package org.kpi.mmsa.model;

public class Matrix {
    private int n;
    private double A[][];
    private double B[];
    private double X[];

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double[][] getA() {
        return A;
    }

    public void setA(double[][] a) {
        A = a;
    }

    public double[] getB() {
        return B;
    }

    public void setB(double[] b) {
        B = b;
    }

    public double[] getX() {
        return X;
    }

    public void setX(double[] x) {
        X = x;
    }
}
