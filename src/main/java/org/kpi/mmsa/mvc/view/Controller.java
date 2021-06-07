package org.kpi.mmsa.mvc.view;

import org.kpi.mmsa.core.Solver;
import org.kpi.mmsa.model.Result;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
        view.getVariablesComboBox().addActionListener(e -> updateView(e));
        view.getOpenItem().addActionListener(e -> readFile());
    }

    private void readFile() {
        JFileChooser fc=new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int sd=fc.showOpenDialog(view.getFrame());
        if(sd==JFileChooser.APPROVE_OPTION){
            File f=fc.getSelectedFile();
            String filepath=f.getPath();
            Scanner scanner;
            int n = 0;
            double[][] a;
            double[] b;
            try{
                scanner = new Scanner(new BufferedReader(new FileReader(filepath)));
                if (scanner.hasNextLine()){
                    String[] line = scanner.nextLine().trim().split(",");
                    n = Integer.parseInt(line[0]);
                }
                a = new double[n][n];
                b = new double[n];
                if (scanner.hasNextLine()){
                    for (int i = 0; i < n; i++) {
                        String[] line = scanner.nextLine().trim().split(",");
                        for (int j=0; j<n; j++) {
                            a[i][j] = Double.parseDouble(line[j]);
                        }
                    }
                }
                if (scanner.hasNextLine()){
                    String[] line = scanner.nextLine().trim().split(",");
                    for (int i = 0; i < n; i++) {
                        b[i] = Double.parseDouble(line[i]);
                    }
                }
                model.setN(n);
                model.setA(a);
                model.setB(b);
                model.setX(new double[n]);
                this.view.refreshView(n);
                initView();

            }catch (Exception ex) {
                ex.printStackTrace();
            }
        }
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
