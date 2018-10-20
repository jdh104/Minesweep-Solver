
import javax.swing.SwingUtilities;

public class Backend{

    private SolverFrame rootFrame;

    public Backend() {
        super();
        new CellMatrix();
        this.rootFrame = new SolverFrame();
        this.rootFrame.setDefaultCloseOperation(SolverFrame.EXIT_ON_CLOSE);
    }

    public SolverFrame getRootFrame() {
        return this.rootFrame;
    }

    public void setRootFrameVisibility(boolean bool) {
        this.rootFrame.setVisible(bool);
    }
}