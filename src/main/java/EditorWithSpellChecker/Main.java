/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorWithSpellChecker;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import com.inet.jortho.SpellCheckerOptions;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;




public class Main {
    
    public static Timer timer;
    

    public static void main(String[] args) {
        GUI gui = new GUI();
        SpellChecker.setUserDictionaryProvider(new FileUserDictionary());

        SpellChecker.registerDictionaries(Main.class.getResource("/dictionary"), "en");
        SpellChecker.register(gui.getjTextArea());

        SpellCheckerOptions sco=new SpellCheckerOptions();
        sco.setCaseSensitive(true);
        sco.setSuggestionsLimitMenu(15);
        JPopupMenu popup = SpellChecker.createCheckerPopup(sco);
        gui.getjTextArea().setComponentPopupMenu(popup); 
        gui.setVisible(true);
        timer = new Timer();
        timer.schedule(new AutoSaveThread(gui), 0, 2000); //TODO MAKE THIS 120 SECSS
                           
    }
    

    public static boolean saveFile(String textToSave, String filePath){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false));
            writer.append(textToSave);
            writer.close();    
            return true;
        }
        catch (Exception e) {
            return false;
        }   
    }
    
}
