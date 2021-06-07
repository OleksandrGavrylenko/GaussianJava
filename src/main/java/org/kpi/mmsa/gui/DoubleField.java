package org.kpi.mmsa.gui;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class DoubleField extends JTextField {
    public DoubleField() {
        super();
    }

    public DoubleField(int cols) {
        super(cols);
    }

    @Override
    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }

    static class UpperCaseDocument extends PlainDocument {
        @Override
        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {

            if (str == null) {
                return;
            }

            boolean ok = true;

            if (!str.equals(".")) {
                try {
                    Double.parseDouble(str);
                } catch (NumberFormatException exc) {
                    ok = false;
                }
            }

            if (ok) {
                super.insertString(offs, str, a);
            }
        }
    }
}
