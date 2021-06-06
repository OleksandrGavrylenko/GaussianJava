package org.kpi.mmsa.mvc.view;

public class Model {
    private int n;
    private double A[][];
    private double B[];
    private double X[];

    public Model(int n) {
        this.n = n;
        A = new double[this.n][this.n];
        B = new double[this.n];
        X = new double[this.n];
    }

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
