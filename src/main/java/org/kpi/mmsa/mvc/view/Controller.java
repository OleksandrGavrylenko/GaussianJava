package org.kpi.mmsa.mvc.view;

import org.kpi.mmsa.core.Solver;
import org.kpi.mmsa.model.Result;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

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
        view.getVariablesComboBox().addActionListener(e -> updateView());
        view.getOpenItem().addActionListener(e -> readFile());
        view.getAboutItem().addActionListener(e -> showAbout());
    }

    private void showAbout() {
        view.showHelpWindow();
    }

    private void readFile() {
        File file = view.readFile();
        if (file != null) {
            String filepath = file.getPath();
            Scanner scanner;
            int n = 0;
            double[][] a;
            double[] b;
            StringBuilder sb = new StringBuilder();
            try {
                scanner = new Scanner(new BufferedReader(new FileReader(filepath)));
                if (scanner.hasNextLine()) {
                    try {
                        String[] line = scanner.nextLine().trim().split(",");
                        n = Integer.parseInt(line[0]);
                    } catch (NumberFormatException e) {
                        sb.append("Invalid symbol at line first line\n.");
                    }
                }
                a = new double[n][n];
                b = new double[n];
                if (scanner.hasNextLine()) {
                    for (int i = 0; i < n; i++) {
                        String[] line = scanner.nextLine().trim().split(",");
                        for (int j = 0; j < n; j++) {
                            try {
                                a[i][j] = Double.parseDouble(line[j]);
                            } catch (NumberFormatException e) {
                                sb.append("Invalid symbol at line " + (i + 2) + ", column " + (j + 1) + ".\n");
                            }
                        }
                    }
                }
                if (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().trim().split(",");
                    for (int i = 0; i < n; i++) {
                        try {
                            b[i] = Double.parseDouble(line[i]);
                        } catch (NumberFormatException e) {
                            sb.append("Invalid symbol at line " + (n + 2) + ", column " + (i + 1) + ".\n");
                        }
                    }
                }

                if (sb.length() > 0) {
                    view.getTextArea().setText(sb.toString());
                } else {
                    model.setN(n);
                    model.setA(a);
                    model.setB(b);
                    model.setX(new double[n]);
                    this.view.getResult().setText("");
                    this.view.refreshView(n);
                    initView();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateView() {
        int selectedIndex = view.getVariablesComboBox().getSelectedIndex();
        this.view.refreshView(selectedIndex);
        this.model = new Model(selectedIndex);
        initView();
    }

    private void solveEquation() {
        JTextField[][] textFields = view.getTextFields();
        int n = model.getN();
        double[][] a = new double[n][n];
        double[] b = new double[n];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                try {
                    a[i][j] = Double.parseDouble(textFields[i][j].getText());
                } catch (NumberFormatException e) {
                    sb.append("Invalid symbol at line ").append(i + 1).append(", column ").append(j + 1).append(".\n");
                }
            }
        }
        for (int i = 0; i < n; i++) {
            try {
                b[i] = Double.parseDouble(textFields[i][n].getText());
            } catch (NumberFormatException e) {
                sb.append("Invalid symbol at result column, line number ").append(i + 1).append("./n");
            }
        }

        if (sb.length() > 0) {
            view.getTextArea().setText(sb.toString());
        } else {
            model.setA(a);
            model.setB(b);
            model.setX(new double[n]);
            Result result = solver.solve(this.model);

            if (result.getModel() != null) {
                view.getResult().setText(prettyPrint(result.getModel()));
            }
            view.getTextArea().setText(result.getLog());
        }
    }

    private String prettyPrint(Model model) {
        double[] x = model.getX();
        String result = "";
        for (int i = 0; i < model.getN(); i++) {
            result += String.format("\n\tX[%d] = %5.2f; \n", (i + 1), x[i]);
        }
        return result;
    }
}
