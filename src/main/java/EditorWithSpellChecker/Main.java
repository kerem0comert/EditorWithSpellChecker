/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorWithSpellChecker;

import com.inet.jortho.FileUserDictionary;
import com.inet.jortho.SpellChecker;
import com.inet.jortho.SpellCheckerOptions;
import javax.swing.JPopupMenu;



public class Main {

    /**
     * @param args the command line arguments
     */
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
        
       
    }
    
}
