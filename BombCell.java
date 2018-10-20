public class BombCell extends Cell {
    public BombCell() {
        super();
        this.setValue(null);
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