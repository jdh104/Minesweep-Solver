
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainClass {
    private static final String AUTHOR = "Jonah Haney";
    private static final String VERSION = "2018.10.9";
    public static final int DEFAULT_LENGTH = 7;
    public static final int DEFAULT_WIDTH = 7;
    private static Backend backend;
    
    public static String getAuthor() {
        return AUTHOR;
    }

    public static Backend getBackend() {
        return backend;
    }

    public static SolverFrame getRootFrame() {
        return backend.getRootFrame();
    }

    public static String getVersion() {
        return VERSION;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("Failed to apply Nimbus Look-and-Feel");
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                backend = new Backend();
                backend.getRootFrame().setVisible(true);
            }
        });
    }
}