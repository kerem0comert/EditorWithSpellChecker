package EditorWithSpellChecker;

public class AutoSaveJob extends java.util.Timer {
    
    public AutoSaveJob(GUI gui){
        schedule(new AutoSaveThread(gui), 0, 120000);
    }

    private class AutoSaveThread extends java.util.TimerTask {

        private GUI gui;

        AutoSaveThread(GUI gui) {
            this.gui = gui;
        }

        public void run() {
            if (gui.isSavedOnce()) {
                System.out.println("Saving...");
                Main.saveFile(gui.getjTextArea().getText(), gui.getCurrentFilePath());
            }

        }
    }
}


