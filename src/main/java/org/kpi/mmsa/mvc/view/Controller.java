package org.kpi.mmsa.mvc.view;

import org.kpi.mmsa.core.Solver;
import org.kpi.mmsa.model.Result;

import javax.swing.*;
import java.awt.event.ActionEvent;

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
        view.getVariablesComboBox().addActionListener(e -> updateView(e));
    }

    private void updateView(ActionEvent e) {
        int selectedIndex = view.getVariablesComboBox().getSelectedIndex();
        this.view.refreshView(selectedIndex);
        this.model = new Model(selectedIndex);
        initView();
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
        view.getTextArea().setText(result.getLog());
    }
}
