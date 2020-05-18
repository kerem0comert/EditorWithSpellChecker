package EditorWithSpellChecker;

import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

public class Main extends javax.swing.JFrame {

    @Override
    public synchronized void addWindowListener(WindowListener l) {
        super.addWindowListener(l);
    }
    
    public boolean isSavedOnce; //the flag to check if the current text that is in the
    //JTextArea is saved or not
    public String currentFilePath;
    public String backupFilePath;
    public String backupFileListPath;
    public boolean isTextChaged;
    public Map<String, LinkedList<String>> backupFileList;

    public Main() {
        initComponents();
        isSavedOnce = false;
        isTextChaged = false;
        currentFilePath = System.getProperty("user.home") + "\\Desktop";
        backupFilePath = System.getProperty("user.dir") + "\\backup\\";
        backupFileListPath = System.getProperty("user.dir") + "\\backup.map";
        try {
            backupFileList = (Map<String, LinkedList<String>>)(new ObjectInputStream(new FileInputStream(backupFileListPath))).readObject();
        } catch (Exception ex) {
            backupFileList = new HashMap<>();
        }
        (new File(backupFilePath)).mkdirs();
        setLocationRelativeTo(null);
        
    }
    
    public JTextArea getjTextArea() {
        return jTextAreaMain;
    }

    public void backup() throws IOException {
        File source = new File(currentFilePath);
        File target = new File(backupFilePath + source.getName() + ".backup-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyymmddhhmmss")));
        
        LinkedList<String> fileList = null;
        
        for (String s : backupFileList.keySet())
            if (s.equals(currentFilePath)) {
                fileList = backupFileList.get(s);
                break;
            }
        
        if (fileList == null)
        {
            fileList = new LinkedList<>();
            backupFileList.put(currentFilePath, fileList);
        }

        Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        fileList.add(target.toPath().toString());

        
        (new ObjectOutputStream(new FileOutputStream(backupFileListPath))).writeObject(backupFileList);
    }
    
    public boolean saveFile() {
        try {
            backup();
        } catch (IOException ex) {
        }
        
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFilePath, false))) {
                writer.append(jTextAreaMain.getText());
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String loadFile(String selectedFilePath) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get(selectedFilePath), StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s).append("\n"));

        return contentBuilder.toString();
    }
    
    public void loadBackupFile(String fileName) {
        LinkedList<String> fileList = null;

        for (String s : backupFileList.keySet()) {
            if (s.equals(currentFilePath)) {
                fileList = backupFileList.get(s);
                break;
            }
        }
        
        String fullFilePath = null;
        
        for (String s : fileList) {
            if (s.contains(fileName)) {
                fullFilePath = s;
            }
        }
        
        try {
            jTextAreaMain.setText(loadFile(fullFilePath));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error while loading file!");
        }
    }

    public boolean saveCurrentText(boolean isSaveAs) {

        if (isSaveAs) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(currentFilePath));
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String selectedFilePath = selectedFile.getAbsolutePath();
                currentFilePath = selectedFilePath;
                if (!currentFilePath.contains(".txt")) {
                    currentFilePath += ".txt";
                }
                if (saveFile()) {
                    JOptionPane.showMessageDialog(this, "Successfully saved!");
                    isTextChaged = false;
                    isSavedOnce = true;
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "Error saving file!");
                    return false;
                }

            }
        } else {
            if (saveFile()) {
                JOptionPane.showMessageDialog(this, "Successfully saved!");
                isTextChaged = false;
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Error saving file!");
                return false;
            }
        }

        return false;
    }

    public void enableButtons() {
        jTextAreaMain.setEnabled(true);
        jButtonSave.setEnabled(true);
        jButtonSaveAs.setEnabled(true);
        jButtonShowFileBackups.setEnabled(true);
    }

    public static void main(String[] args) {
        Main gui = new Main();

        Thread spellCheckerThread = new Thread(new SpellCheckerThread(gui));
        spellCheckerThread.start();

        AutoSaveJob autoSaveJob = new AutoSaveJob(gui);
        autoSaveJob.start();
        
        gui.setVisible(true);

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
        jTextAreaMain = new javax.swing.JTextArea();
        jButtonLoad = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonNew = new javax.swing.JButton();
        jButtonSaveAs = new javax.swing.JButton();
        jButtonShowFileBackups = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("METUSoft Word");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jTextAreaMain.setColumns(20);
        jTextAreaMain.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextAreaMain.setRows(5);
        jTextAreaMain.setText("Use one of the above buttons first.");
        jTextAreaMain.setEnabled(false);
        jTextAreaMain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextAreaMainKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTextAreaMain);

        jButtonLoad.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jButtonLoad.setText("Load");
        jButtonLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadActionPerformed(evt);
            }
        });

        jButtonSave.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jButtonSave.setText("Save");
        jButtonSave.setEnabled(false);
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonNew.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jButtonNew.setText("New");
        jButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewActionPerformed(evt);
            }
        });

        jButtonSaveAs.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jButtonSaveAs.setText("Save As");
        jButtonSaveAs.setEnabled(false);
        jButtonSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveAsActionPerformed(evt);
            }
        });

        jButtonShowFileBackups.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        jButtonShowFileBackups.setText("File Backups");
        jButtonShowFileBackups.setEnabled(false);
        jButtonShowFileBackups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowFileBackupsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 994, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonNew, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonSaveAs, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonShowFileBackups, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonShowFileBackups, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNew, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSaveAs, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadActionPerformed

        if (isTextChaged) {
            int selection = JOptionPane.showConfirmDialog(null, "Would you like to save before loading a file?");

            switch (selection) {
                case 0:
                    saveCurrentText(!isSavedOnce);
                    break;
                case 1:
                    break;
                case 2:
                    return;
            }
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(currentFilePath));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String selectedFilePath = selectedFile.getAbsolutePath();
            StringBuilder contentBuilder = new StringBuilder();

            try {
                jTextAreaMain.setText(loadFile(selectedFilePath));
                isTextChaged = false;
                isSavedOnce = true;
                currentFilePath = selectedFilePath;
                JOptionPane.showMessageDialog(this, "File loaded succesfully");
                enableButtons();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Please select a .txt file!");
            }

        }
    }//GEN-LAST:event_jButtonLoadActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        saveCurrentText(!isSavedOnce);
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewActionPerformed

        if (isTextChaged) {
            int selection = JOptionPane.showConfirmDialog(null, "Would you like to save before opening a new file?");

            switch (selection) {
                case 0:
                    saveCurrentText(!isSavedOnce);
                    break;
                case 1:
                    break;

            }
        }

        jTextAreaMain.setText("");
        isSavedOnce = false;
        isTextChaged = false;
        currentFilePath = System.getProperty("user.home") + "/Desktop";
        enableButtons();
        JOptionPane.showMessageDialog(this, "New file created.");
    }//GEN-LAST:event_jButtonNewActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (isTextChaged) {
            int selection = JOptionPane.showConfirmDialog(null, "Would you like to save before exit?");

            boolean close;

            switch (selection) {
                case 0:
                    close = saveCurrentText(!isSavedOnce);
                    if (close) {
                        System.exit(0);
                    }
                    break;
                case 1:
                    System.exit(0);
                case 2:
            }
        } else
            System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void jTextAreaMainKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextAreaMainKeyTyped
        isTextChaged = true;
    }//GEN-LAST:event_jTextAreaMainKeyTyped

    private void jButtonSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveAsActionPerformed
        saveCurrentText(true);
    }//GEN-LAST:event_jButtonSaveAsActionPerformed

    private void jButtonShowFileBackupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowFileBackupsActionPerformed
        

        LinkedList<String> fileList = null;

        for (String s : backupFileList.keySet()) {
            if (s.equals(currentFilePath)) {
                fileList = backupFileList.get(s);
                break;
            }
        }

        if (fileList == null || fileList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "There is no backup file yet!");
            return;
        }

        DefaultListModel<String> fileListOnlyNames = new DefaultListModel<>();
        for (String s : fileList) {
            fileListOnlyNames.addElement((new File(s)).getName());
        }
        
        FileBackupDialog dialog = new FileBackupDialog(this, true);
        dialog.jList1.setModel(fileListOnlyNames);
        dialog.setVisible(true);
    }//GEN-LAST:event_jButtonShowFileBackupsActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLoad;
    private javax.swing.JButton jButtonNew;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSaveAs;
    private javax.swing.JButton jButtonShowFileBackups;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaMain;
    // End of variables declaration//GEN-END:variables

}
