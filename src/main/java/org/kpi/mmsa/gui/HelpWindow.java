package org.kpi.mmsa.gui;

import javax.swing.*;
import java.awt.*;

public class HelpWindow extends JFrame {
    JTextArea textArea = new JTextArea( "  The Gaussian Elimination program is a software for " +
            "solving the systems of linear equations in a form A*X=B. " +
            "Please, select the correct number of unknown variables and press \"Solve\" button. " +
            "Or simply upload a *.csv file with your system of linear equations in correct structure. " +
            "Example:\n" +
            "4,\n" +
            "1, 3, -2, -7\n" +
            "-1, -2, 1, 2\n" +
            "-2, -1, 3, 1\n" +
            "-3, -2, 3, 3\n" +
            "-3, 2, -2, 3\n" +
            "  Where the number 4 in first line is a number of variable" +
            " and the last row is a free members matrix (matrix B)");

    public HelpWindow() {

        textArea.setBounds(5, 5, 390, 390);
        textArea.setFont(new Font(textArea.getName(), Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setVisible(true);

        this.add(textArea);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(420, 420);
        this.setLayout(null);
        this.setVisible(true);
    }
}
