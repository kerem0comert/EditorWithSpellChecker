/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorWithSpellChecker;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class SpellCheckExampleUi {
    private JFrame frame;
    private JTextArea textArea;

    public SpellCheckExampleUi() {
        frame = new JFrame("Jortho example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame = new JFrame("JOrtho Example");
        frame.setSize(new Dimension(800, 500));
        frame.setLocationRelativeTo(null);
        frame.add(getContentPanel());
    }

    public Component getContentPanel() {
        textArea = new JTextArea();
        return new JScrollPane(textArea);
    }

    public JTextComponent getTextComponent() {
        return textArea;
    }

    public void showUI() {
        frame.setVisible(true);
    }
}