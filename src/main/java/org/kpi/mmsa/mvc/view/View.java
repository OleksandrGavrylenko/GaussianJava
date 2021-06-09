package org.kpi.mmsa.mvc.view;

import org.kpi.mmsa.gui.DoubleField;
import org.kpi.mmsa.gui.HelpWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class View {
    private int n;
    private JFrame frame;
    private JPanel mainPanel;
    private JMenuItem openItem;
    private JMenuItem aboutItem;
    private JLabel[][] labels;
    private DoubleField[][] textFields;
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
        this.textFields = createDoubleFields(this.n);
        addLabelTextRows(labels, textFields, equationPanel);

        equationPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("System of Equations"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel buttonPanel = new JPanel();
        solveButton = new JButton("Solve");
        solveButton.setActionCommand("Solve equation");
        buttonPanel.add(solveButton);

        JPanel upperPane = new JPanel(new BorderLayout());
        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.add(buttonPanel, BorderLayout.WEST);
        upperPane.add(variablesPanel, BorderLayout.NORTH);
        upperPane.add(equationPanel,
                BorderLayout.WEST);
        upperPane.add(jPanel, BorderLayout.CENTER);

        textArea = createTextArea();
        JScrollPane editorScrollPane = new JScrollPane(textArea);
        editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(250, 145));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));

        this.centralPane = new JPanel(new GridLayout(1,0));
        result.setFont(new Font(result.getName(), Font.PLAIN, 16));
        centralPane.add(result);
        this.centralPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Result of Gaussian Elimination"),
                BorderFactory.createEmptyBorder(5,5,5,5)));



        JPanel buttomPane = new JPanel(new GridLayout(1,0));
        buttomPane.add(editorScrollPane);
        buttomPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Program log"),
                BorderFactory.createEmptyBorder(5,5,5,5)));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                centralPane,
                buttomPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setResizeWeight(0.5);

        mainPanel.add(upperPane, BorderLayout.PAGE_START);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        createAndShowGUI();
    }

    public void init(Model model) {
        JTextField[][] textFields = this.getTextFields();
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

    public void refreshView(int n) {
        this.n = n;
        this.labels = createLabels(this.n);
        this.textFields = createDoubleFields(this.n);
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

    private DoubleField[][] createDoubleFields(int n) {
        int rows = n;
        int columns = n+1;
        DoubleField [][] textFields = new DoubleField[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                textFields[i][j] = new DoubleField(5);
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
        frame = new JFrame("Gaussian Elimination");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        openItem.setFocusPainted(true);
        fileMenu.add(openItem);
        menuBar.add(fileMenu);

        JMenu helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About");
        aboutItem.setFocusPainted(true);
        helpMenu.add(aboutItem);
        menuBar.add(helpMenu);

        frame.add(mainPanel);

        frame.setVisible(true);
    }

    public void showHelpWindow() {
        new HelpWindow();
    }

    public File readFile() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int sd = fc.showOpenDialog(this.getFrame());
        if (sd == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        }
        return null;
    }

    public JFrame getFrame() {
        return frame;
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

    public JLabel getResult() {
        return result;
    }

    public JComboBox getVariablesComboBox() {
        return variablesComboBox;
    }

    public JMenuItem getOpenItem() {
        return openItem;
    }

    public JMenuItem getAboutItem() {
        return aboutItem;
    }
}
