package org.kpi.mmsa.mvc.view;

import org.kpi.mmsa.gui.EquationPanel;

import javax.swing.*;
import java.awt.*;

public class View {
    private int n;
    private JPanel mainPanel;
    private JLabel[][] labels;
    private JTextField[][] textFields;
    private JButton solveButton;
    private JTextArea textArea;

    public View(int n) {
        this.n = n;

        mainPanel = new JPanel();

        JPanel equationPanel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        equationPanel.setLayout(gridbag);

        labels = createLabels(n);
        textFields = createTextFields(n);
        addLabelTextRows(labels, textFields, gridbag, equationPanel);

        equationPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Text Fields"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel buttonPanel = new JPanel();
        solveButton = new JButton("Solve");
        solveButton.setActionCommand("Solve equation");
        buttonPanel.add(solveButton);


        //Put everything together.
//        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
//                editorScrollPane,
//                paneScrollPane);
        JPanel leftPane = new JPanel(new BorderLayout());
        leftPane.add(equationPanel,
                BorderLayout.WEST);
        leftPane.add(buttonPanel,
                BorderLayout.EAST);

        textArea = createTextArea();
        JScrollPane editorScrollPane = new JScrollPane(textArea);
        editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(250, 145));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));


//        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
//                editorScrollPane,
//                paneScrollPane);
//        splitPane.setOneTouchExpandable(true);
//        splitPane.setResizeWeight(0.5);

        JPanel rightPane = new JPanel(new GridLayout(1,0));
        rightPane.add(editorScrollPane);
//        rightPane.add(splitPane);
        rightPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Styled Text"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        mainPanel.add(leftPane, BorderLayout.PAGE_START);
        mainPanel.add(rightPane, BorderLayout.CENTER);

        createAndShowGUI();
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.append("Test result");

        return textArea;
    }

    private JTextField[][] createTextFields(int n) {
        int rows = n;
        int columns = n+1;
        JTextField [][] textFields = new JTextField[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                textFields[i][j] = new JTextField("0", 5);
            }

        }
        return textFields;
    }

    private JLabel[][] createLabels(int n) {
        JLabel [][] labels = new JLabel[n][n];
        String text;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (j == n-1) {
                    text = "X" + (j + 1) + " = ";
                } else {
                    text = "X" + (j + 1) + " + ";
                }
                labels[i][j] = new JLabel(text);
            }
        }
        return labels;
    }


    private void addLabelTextRows(JLabel[][] labels,
                                  JTextField[][] textFields,
                                  GridBagLayout gridbag,
                                  Container container) {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        int numLabels = labels.length;

        for (int i = 0; i < numLabels; i++) {
            for (int j = 0; j < numLabels; j++) {
                c.fill = GridBagConstraints.NONE;
                c.gridx = GridBagConstraints.RELATIVE;
                c.gridy = i;
                container.add(textFields[i][j], c);
                container.add(labels[i][j], c);
            }
            container.add(textFields[i][numLabels], c);
        }
    }

    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Gaussian Elimination");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        //Add content to the window.
        frame.add(mainPanel);

        //Display the window.
//        frame.pack();
        frame.setVisible(true);
    }

    public JTextField[][] getTextFields() {
        return textFields;
    }

    public void setTextFields(JTextField[][] textFields) {
        this.textFields = textFields;
    }

    public JButton getSolveButton() {
        return solveButton;
    }

    public void setSolveButton(JButton solveButton) {
        this.solveButton = solveButton;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }
}
