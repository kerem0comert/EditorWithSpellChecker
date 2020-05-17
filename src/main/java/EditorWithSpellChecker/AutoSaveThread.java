/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorWithSpellChecker;

import java.util.TimerTask;

/**
 *
 * @author Kerem
 */
public class AutoSaveThread extends TimerTask{
    private String filePath, textToSave;
    private GUI gui;
    
    AutoSaveThread(GUI gui){
       this.gui = gui;
    }
    public void run() {
        if(gui.isSavedOnce()){
            System.out.println("Saving...");
           Main.saveFile(gui.getjTextArea().getText(), gui.getCurrentFilePath());
        }

    }
}
