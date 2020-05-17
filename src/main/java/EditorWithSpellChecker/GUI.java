/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorWithSpellChecker;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Timer;
import java.util.stream.Stream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author Kerem
 */
public class GUI extends javax.swing.JFrame   {

    
    @Override
    public synchronized void addWindowListener(WindowListener l) {
        super.addWindowListener(l); 
    };
    
    private boolean isSavedOnce; //the flag to check if the current text that is in the
    //JTextArea is saved or not
    private String currentFilePath;

    /**
     * Creates new form TextEditorGui
     */
    public GUI() {
        initComponents();
        isSavedOnce = false;
        currentFilePath = System.getProperty("user.home") + "/Desktop";

    }

    public boolean isSavedOnce() {
        return isSavedOnce;
    }

    public JTextArea getjTextArea() {
        return jTextArea;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea = new javax.swing.JTextArea();
        jButtonLoad = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonNew = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTextArea.setColumns(20);
        jTextArea.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        jButtonLoad.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jButtonLoad.setText("Load File");
        jButtonLoad.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        jButtonLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadActionPerformed(evt);
            }
        });

        jButtonSave.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jButtonSave.setText("Save File");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonNew.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jButtonNew.setText("New File");
        jButtonNew.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        jButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSave)
                        .addGap(29, 29, 29)
                        .addComponent(jButtonLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jButtonNew, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 994, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNew, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        System.out.println(currentFilePath);
        fileChooser.setCurrentDirectory(new File(currentFilePath));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String selectedFilePath = selectedFile.getAbsolutePath();
            StringBuilder contentBuilder = new StringBuilder();

            try {
                Stream<String> stream = Files.lines(Paths.get(selectedFilePath),
                        StandardCharsets.UTF_8);
                stream.forEach(s -> contentBuilder.append(s).append("\n"));
                jTextArea.setText(contentBuilder.toString());
                isSavedOnce = true;
                currentFilePath = selectedFilePath;
                System.out.println(currentFilePath);
                JOptionPane.showMessageDialog(this, "File loaded succesfully");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Please select a .txt file!");
            }

        }
    }//GEN-LAST:event_jButtonLoadActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        
        saveCurrentText(true); //We want to perform a "Save as" operation, so send true
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewActionPerformed
        //JOptionPane.showConfirmDialog(this, "Would you like to save the current file"
        //      + "before opening a new one?", "Saving", 0);
        int selection = JOptionPane.showConfirmDialog(null, "Would you like to save before"
                + " opening a new file?");
        
        switch (selection) {
            case 0:
                if(isSavedOnce) 
                    saveCurrentText(false); //the behaviour is not "Save as" so send false
                else 
                    saveCurrentText(true); //user has to "Save As" at least once
                break;
            case 1:
                break;
            default:
                return;
        }

        jTextArea.setText("");
        isSavedOnce = false;
        currentFilePath = System.getProperty("user.home") + "/Desktop";
        JOptionPane.showMessageDialog(this, "New file created.");
    }//GEN-LAST:event_jButtonNewActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int selection = JOptionPane.showConfirmDialog(null, "Would you like to save before"
                + " exiting?");
        
        boolean close;

        switch (selection) {
            case 0:
                if (isSavedOnce())
                    close = saveCurrentText(false); //the behaviour is not "Save as" so send false
                else
                    close = saveCurrentText(true); //user has to "Save As" at least once
                
                if (close)
                    System.exit(0);
                break;
            case 1:
                System.exit(0);
            case 2:
                
        }
    }//GEN-LAST:event_formWindowClosing

    public boolean saveCurrentText(boolean isSaveAs) {

        String textToSave = jTextArea.getText();
        if (isSaveAs) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(currentFilePath));
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String selectedFilePath = selectedFile.getAbsolutePath();
                if (!selectedFilePath.contains(".txt")) {
                    selectedFilePath += ".txt";
                }
                if (Main.saveFile(textToSave, selectedFilePath)) {
                    JOptionPane.showMessageDialog(this, "Successfully saved!");
                    isSavedOnce = true;
                    currentFilePath = selectedFilePath;
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "Error saving file!");
                    return false;
                }

            }
        } else {
            if (Main.saveFile(textToSave, currentFilePath)) {
                JOptionPane.showMessageDialog(this, "Successfully saved!");
                isSavedOnce = false; //this time the new file is opened so the currently
                                     //open file is changed
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Error saving file!");
                return false;
            }
        }
        
        return false;
    }

    //when the user clicks on the close button of the whole frame
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLoad;
    private javax.swing.JButton jButtonNew;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea;
    // End of variables declaration//GEN-END:variables



}
