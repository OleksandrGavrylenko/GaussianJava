package org.kpi.mmsa.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class EquationPanel extends JPanel implements ActionListener {

    private int n = 2;
    private String newline = "\n";
    protected static final String textFieldString = "JTextField";
    protected static final String passwordFieldString = "JPasswordField";
    protected static final String ftfString = "JFormattedTextField";
    protected static final String buttonString = "JButton";

    protected JLabel actionLabel;
    private JButton solveButton;
    private JTextArea textArea;


    public EquationPanel() {
        super(new BorderLayout());

        //Create a label to put messages during an action event.
//        actionLabel = new JLabel("Type text in a field and press Enter.");
//        actionLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

        //Lay out the text controls and the labels.
        JPanel equationPanel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        equationPanel.setLayout(gridbag);

        JLabel[][] labels = createLabels(n);
        JTextField[][] textFields = createTextFields(n);
        addLabelTextRows(labels, textFields, gridbag, equationPanel);

        equationPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Text Fields"),
                        BorderFactory.createEmptyBorder(5,5,5,5)));

        JPanel buttonPanel = new JPanel();
        solveButton = new JButton("Solve");
        solveButton.setActionCommand("Solve equation");
        solveButton.addActionListener(this);
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

        add(leftPane, BorderLayout.PAGE_START);
        add(rightPane, BorderLayout.CENTER);

    }

    private JEditorPane createEditorPane() {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        java.net.URL helpURL = EquationPanel.class.getResource(
                "TextSamplerDemoHelp.html");
        if (helpURL != null) {
            try {
                editorPane.setPage(helpURL);
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + helpURL);
            }
        } else {
            System.err.println("Couldn't find file: TextSampleDemoHelp.html");
        }

        return editorPane;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println(actionCommand);

    }

    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TextSamplerDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);

        //Add content to the window.
        frame.add(new EquationPanel());

        //Display the window.
//        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }


}
