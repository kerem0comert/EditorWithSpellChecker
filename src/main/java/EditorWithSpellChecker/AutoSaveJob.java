package EditorWithSpellChecker;

public class AutoSaveJob extends java.util.Timer {
    
    private Main gui;
    
    public AutoSaveJob(Main gui){
        this.gui = gui;
    }
    
    public void start(){
        schedule(new AutoSaveThread(gui), 0, 120000);
    }

    private class AutoSaveThread extends java.util.TimerTask {

        private final Main gui;

        AutoSaveThread(Main gui) {
            this.gui = gui;
        }

        @Override
        public void run() {
            if (gui.isSavedOnce) {
                System.out.println("Saving...");
                gui.saveFile();
            }

        }
    }
}


