/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EditorWithSpellChecker;

/**
 *
 * @author Kerem
 */
public class SpellCheckerThread implements Runnable{
    private String name;
    
    public SpellCheckerThread(String name) {
        this.name = name;
        
    }
    
   
    @Override
    public void run() {
        System.out.println("EditorWithSpellChecker.SpellCheckerThread.run()");
        System.err.println(this.name);
    }
    
}
