package org.kpi.mmsa.mvc;

public class App {
    public static void main(String[] args) {
        int n = 2;
        Model m = new Model(n);
        View v = new View(n);
        Controller c = new Controller(m, v);
        c.initController();
    }
}
