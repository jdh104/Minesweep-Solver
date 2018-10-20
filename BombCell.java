
import java.awt.Color;

public class BombCell extends Cell {

    public static final long serialVersionUID = 1005L;

    public BombCell() {
        super();
        this.setValue(null);
        this.setText("X");
        this.setBackground(Color.RED);
    }

    @Override public Short getValue() throws UnknownAnswerException {
        throw new UnknownAnswerException();
    }

    @Override public boolean isBombCell() throws UnknownAnswerException {
        return true;
    }

    @Override public boolean isEmptyCell() throws UnknownAnswerException {
        return false;
    }

    @Override public boolean isUnknownCell() {
        return false;
    }
}