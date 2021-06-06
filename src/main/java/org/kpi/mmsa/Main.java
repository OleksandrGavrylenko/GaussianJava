package org.kpi.mmsa;


import javax.swing.*;

import static org.kpi.mmsa.gui.EquationPanel.createAndShowGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}
