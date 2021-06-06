package org.kpi.mmsa.mvc.view;

import org.kpi.mmsa.core.Solver;
import org.kpi.mmsa.model.Result;

import javax.swing.*;

public class Controller {
    private Model model;
    private View view;
    private Solver solver;


    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        this.solver = new Solver();
        initView();
    }

    public void initView() {
        JTextField[][] textFields = view.getTextFields();
        double[][] a = model.getA();
        double[] b = model.getB();
        int n = model.getN();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                textFields[i][j].setText(String.valueOf(a[i][j]));
            }
        }
        for (int i = 0; i < n; i++) {
            textFields[i][n].setText(String.valueOf(b[i]));
        }
    }
    public void initController() {
        view.getSolveButton().addActionListener(e -> solveEquation());
    }

    private void solveEquation() {
        JTextField[][] textFields = view.getTextFields();
        double[][] a = model.getA();
        double[] b = model.getB();
        int n = model.getN();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = Double.parseDouble(textFields[i][j].getText());
            }
        }
        for (int i = 0; i < n; i++) {
            b[i] = Double.parseDouble(textFields[i][n].getText());
        }


        Result result = solver.solve(this.model);
//        String result = getResult(this.model);
        view.getTextArea().setText(result.getLog());
    }

    private String getResult(Model model) {
        StringBuilder sb = new StringBuilder("\n\t======================================\n");
        for (int i = 0; i < model.getN(); i++) {
            sb.append(String.format("\n\tX[%d] = %5.2f;\n", i+1, model.getX()[i]));
        }
        return sb.toString();
    }
}
