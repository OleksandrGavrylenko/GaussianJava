package org.kpi.mmsa.mvc.view;

import javax.swing.*;
import java.awt.*;

public class View {
    private int n;
    private JPanel mainPanel;
    private JLabel[][] labels;
    private JTextField[][] textFields;
    private JButton solveButton;
    private JTextArea textArea;
    private JComboBox variablesComboBox;
    private JPanel equationPanel;
    private JPanel centralPane;
    private JLabel result = new JLabel();

    public View(int n) {
        this.n = n;

        mainPanel = new JPanel(new BorderLayout());

        JPanel variablesPanel = new JPanel();
        JLabel label = new JLabel("Select the number of variables");
        label.setBounds(20, 30, 180, 20);
        variablesPanel.add(label);

        String[] variables ={"0", "1","2","3","4","5", "6", "7"};
        variablesComboBox = new JComboBox(variables);
        variablesComboBox.setSelectedIndex(n);
        variablesComboBox.setBounds(200, 30, 40, 20);
        variablesPanel.add(variablesComboBox);

        this.equationPanel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        this.equationPanel.setLayout(gridbag);

        this.labels = createLabels(this.n);
        this.textFields = createTextFields(this.n);
        addLabelTextRows(labels, textFields, equationPanel);

        equationPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("System of Equations"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel buttonPanel = new JPanel();
        solveButton = new JButton("Solve");
        solveButton.setActionCommand("Solve equation");
        buttonPanel.add(solveButton);


        //Put everything together.
//        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
//                editorScrollPane,
//                paneScrollPane);
        JPanel upperPane = new JPanel(new BorderLayout());
        upperPane.add(variablesPanel, BorderLayout.NORTH);
        upperPane.add(equationPanel,
                BorderLayout.WEST);
        upperPane.add(buttonPanel,
                BorderLayout.EAST);

        textArea = createTextArea();
        JScrollPane editorScrollPane = new JScrollPane(textArea);
        editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(250, 145));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));

        this.centralPane = new JPanel(new GridLayout(1,0));
        centralPane.add(result);
        this.centralPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Result of Gaussian Elimination"),
                BorderFactory.createEmptyBorder(5,5,5,5)));



        JPanel buttomPane = new JPanel(new GridLayout(1,0));
        buttomPane.add(editorScrollPane);
//        buttomPane.add(splitPane);
        buttomPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Styled Text"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                centralPane,
                buttomPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.5);

        mainPanel.add(upperPane, BorderLayout.PAGE_START);
        mainPanel.add(splitPane, BorderLayout.CENTER);
//        mainPanel.add(buttomPane, BorderLayout.PAGE_END);

        createAndShowGUI();
    }

    public void refreshView(int n) {
        this.n = n;
        this.labels = createLabels(this.n);
        this.textFields = createTextFields(this.n);
        equationPanel.removeAll();
        addLabelTextRows(labels, textFields, this.equationPanel);
        equationPanel.revalidate();
        equationPanel.repaint();
        getTextArea().setText("");

    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

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

    public JButton getSolveButton() {
        return solveButton;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JComboBox getVariablesComboBox() {
        return variablesComboBox;
    }
}
