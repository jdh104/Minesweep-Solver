
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

public class Cell extends JTextField {
    
    public static final long serialVersionUID = 1004L;
    private boolean resolved, safe;
    private Short value;
    
    public Cell() {
        super();
        this.resolved = false;
        this.safe = false;
        this.value = null;
        this.setBackground(Color.LIGHT_GRAY);
        this.setHorizontalAlignment(CENTER);
        this.setText("?");

        this.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {
                Cell.this.selectAll();
            }

            @Override public void focusLost(FocusEvent e) {
                try {
                    Cell.this.setValue(Short.parseShort(getText()));
                } catch (Exception ex) {
                    Cell.this.setText("?");
                    Cell.this.setValue(null);
                }
                CellMatrix.getInstance().autoValidateAndResolve();
            }
        });
    }

    public Short getValue() throws UnknownAnswerException {
        if (this.value == null) {
            throw new UnknownAnswerException();
        } return this.value;
    }

    public boolean isResolved() {
        return this.resolved;
    }

    public boolean isSafe() {
        return this.safe;
    }

    public void setResolvedFlag(boolean newFlag) {
        this.resolved = newFlag;
    }

    public void setSafetyFlag(boolean newFlag) {
        this.safe = newFlag;
        if (this.safe) {
            super.setBackground(Color.GREEN);
        } else {
            super.setBackground(Color.LIGHT_GRAY);
        }
    }

    public Cell setValue(Short newValue) throws ImpossibleBoardException {
        if (newValue == null || (newValue >= 0 && newValue <= 9)) {
            this.value = newValue;
            if (this.value == null) {
                this.setText("?");
                this.setResolvedFlag(false);
                this.setSafetyFlag(false);
            } else {
                this.setText(newValue.toString());
                this.setResolvedFlag(true);
                this.setSafetyFlag(true);
            } return this;
        } else {
            throw new ImpossibleBoardException(this);
        }
    }

    public boolean isBombCell() throws UnknownAnswerException {
        if (value == null) {
            throw new UnknownAnswerException();
        } return false;
    }

    public boolean isEmptyCell() throws UnknownAnswerException {
        return !this.isBombCell();
    }

    public boolean isUnknownCell() {
        return (this.value == null);
    }
}