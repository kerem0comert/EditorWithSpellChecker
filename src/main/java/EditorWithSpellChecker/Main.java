/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorWithSpellChecker;



public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("test");
        Thread spellCheckerThread = new Thread(new SpellCheckerThread("kerem"));
        Thread guiThread = new Thread(new GuiThread());
        spellCheckerThread.start();
        
       
    }
    
}
