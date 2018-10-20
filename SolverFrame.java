
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SolverFrame extends JFrame {
    private static final long serialVersionUID = 1001L;
    private static SolverFrame instance;
    private JPanel contentPane;
    private JPanel topPanel, middlePanel, leftExtendPanel, cellMatrixPanel, rightExtendPanel, bottomPanel, controlPanel;
    private JButton topExtendButton, leftExtendButton, rightExtendButton, bottomExtendButton;

    public static SolverFrame getInstance() {
        return instance;
    }

    public SolverFrame() {
        super("Minesweep solver " + MainClass.getVersion() + " by " + MainClass.getAuthor());
        SolverFrame.instance = this;
        
        contentPane = (JPanel) this.getContentPane();
        
        topPanel = new JPanel();
        middlePanel = new JPanel();
        leftExtendPanel = new JPanel();
        cellMatrixPanel = new JPanel();
        rightExtendPanel = new JPanel();
        bottomPanel = new JPanel();
        controlPanel = new JPanel();

        topExtendButton = new JButton("+");
        leftExtendButton = new JButton("+");
        rightExtendButton = new JButton("+");
        bottomExtendButton = new JButton("+");

        topExtendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                CellMatrix.getInstance().addRowToTop();
                refreshCellMatrix();
            }
        });
        leftExtendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                CellMatrix.getInstance().addColumnToLeft();
                refreshCellMatrix();
            }
        });
        rightExtendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                CellMatrix.getInstance().addColumnToRight();
                refreshCellMatrix();
            }
        });
        bottomExtendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent a) {
                CellMatrix.getInstance().addRowToBottom();
                refreshCellMatrix();
            }
        });

        int cml = CellMatrix.getInstance().getLength();
        int cmw = CellMatrix.getInstance().getWidth();

        cellMatrixPanel.setLayout(new GridLayout(cml, cmw));
        cellMatrixPanel.setSize(249, 249);
        for (int i=0; i<cml; i++) {
            for (int j=0; j<cmw; j++) {
                cellMatrixPanel.add(CellMatrix.getInstance().getCell(i,j));
            }
        }

        topPanel.setLayout(new GridLayout(1,1));
        topPanel.add(topExtendButton);

        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        leftExtendPanel.setLayout(new GridLayout(1,1));
        rightExtendPanel.setLayout(new GridLayout(1,1));

        leftExtendPanel.add(leftExtendButton);
        rightExtendPanel.add(rightExtendButton);

        middlePanel.add(leftExtendPanel);
        middlePanel.add(cellMatrixPanel);
        middlePanel.add(rightExtendPanel);

        bottomPanel.setLayout(new GridLayout(1,1));
        bottomPanel.add(bottomExtendButton);

        topPanel.setLayout(new GridLayout(1,1));
        topPanel.add(topExtendButton);

        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(topPanel);
        contentPane.add(middlePanel);
        contentPane.add(bottomPanel);
        contentPane.add(controlPanel);

        this.setLocationRelativeTo(null);
        this.setSize(300, 500);
    }
    
    public void refreshCellMatrix() {
        int cml = CellMatrix.getInstance().getLength();
        int cmw = CellMatrix.getInstance().getWidth();
        cellMatrixPanel.setLayout(new GridLayout(cml, cmw));

        for (int i=0; i<cml; i++) {
            for (int j=0; j<cmw; j++) {
                cellMatrixPanel.add(CellMatrix.getInstance().getCell(i,j));
            }
        }

        cellMatrixPanel.validate();
        this.validate();
        this.repaint();
    }

}