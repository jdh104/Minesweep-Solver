

public class ImpossibleBoardException extends RuntimeException {
    
    private static final long serialVersionUID = 1003L;
    private Cell conflictCell;
    
    public ImpossibleBoardException(Cell conflictCell) {
        super();
        this.conflictCell = conflictCell;
    }

    public Cell getConflictCell() {
        return conflictCell;
    }
}