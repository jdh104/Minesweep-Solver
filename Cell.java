
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

public class Cell extends JTextField {
    
    public static final long serialVersionUID = 1004L;
    private boolean safe;
    private Short value;
    
    public Cell() {
        super();
        this.setBackground(Color.LIGHT_GRAY);
        this.value = null;
        this.safe = false;
        this.setHorizontalAlignment(HORIZONTAL);
        this.setText("?");

        this.addFocusListener(new FocusListener() {
            @Override public void focusGained(FocusEvent e) {
                selectAll();
            }

            @Override public void focusLost(FocusEvent e) {
                try {
                    setValue(Short.parseShort(getText()));
                } catch (Exception ex) {
                    setText("?");
                    setValue(null);
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

    public boolean isSafe() {
        return this.safe;
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
                this.setSafetyFlag(false);
            } else {
                this.setText(newValue.toString());
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
        return (value == null);
    }
}