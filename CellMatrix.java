
import java.util.ArrayList;

public class CellMatrix {
    
    private static CellMatrix instance;
    private ArrayList<ArrayList<Cell>> matrix;
    private int length, width;

    public static CellMatrix getInstance() {
        return CellMatrix.instance;
    }

    public CellMatrix() {
        super();
        CellMatrix.instance = this;
        this.length = MainClass.DEFAULT_LENGTH;
        this.width = MainClass.DEFAULT_WIDTH;
        this.matrix = buildEmptyMatrix(length, width);
    }

    public CellMatrix(int length, int width) {
        super();
        CellMatrix.instance = this;
        this.length = length;
        this.width = width;
        this.matrix = buildEmptyMatrix(length, width);
    }

    public CellMatrix addColumnToLeft() {
        this.width++;
        for (int i=0; i<this.length; i++) {
            this.matrix.get(i).add(0, new Cell());
        } return this;
    }

    public CellMatrix addColumnToRight() {
        return this.resizeMatrixTo(this.length, this.width + 1);
    }

    public CellMatrix addRowToBottom() {
        return this.resizeMatrixTo(this.length + 1, this.width);
    }

    public CellMatrix addRowToTop() {
        this.length++;
        this.matrix.add(0, new ArrayList<>());
        for (int i=0; i<this.width; i++) {
            this.matrix.get(0).add(new Cell());
        } return this;
    }

    public CellMatrix autoResolve(ImpossibleBoardException ibe) {
        try {
            ibe.getConflictCell().setValue(null);
            return this.autoValidateAndResolve();
        } catch (ImpossibleBoardException ibe2) {
            return this.autoResolve(ibe2);
        }
    }

    public CellMatrix autoValidateAndResolve() throws ImpossibleBoardException {
        for (int i=0; i<this.length; i++) {
            for (int j=0; j<this.width; j++) {
                try {
                    this.autoValidateCellAt(i, j);
                } catch (ImpossibleBoardException ibe) {
                    autoResolve(ibe);
                    return this;
                }
            }
        } return this;
    }

    public void autoValidateCellAt(int x, int y) throws ImpossibleBoardException {
        Cell subj = this.matrix.get(x).get(y);
        Short v;
        try {
            v = subj.getValue();
        } catch (UnknownAnswerException uae) {
            return;
        }

        if (v == null) {
            return;
        } else {
            short b = this.countBombCellsSurroundingCellAt(x, y);
            short u = this.countUnknownCellsSurroundingCellAt(x, y);

            if (v > b + u) {
                throw new ImpossibleBoardException(subj);
            } else if (v == b + u) {
                this.revealBombsAroundCellAt(x, y);
            } else if (b == v && u > 0) {
                this.revealSafeCellsAroundCellAt(x, y);
            }
        }
    }

    private static ArrayList<ArrayList<Cell>> buildEmptyMatrix(int l, int w) {
        ArrayList<ArrayList<Cell>> tmp = new ArrayList<>();
        for (int i=0; i<l; i++) {
            tmp.add(new ArrayList<>());
            for (int j=0; j<w; j++)
                tmp.get(i).add(new Cell());
        } return tmp;
    }

    public short countBombCellsSurroundingCellAt(int x, int y) {
        short count = 0;
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                try {
                    if (this.hasBombCellAt(x-1+i, y-1+j)) count++;
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    continue;
                }
            }
        } return count;
    }

    public short countEmptyCellsSurroundingCellAt(int x, int y) {
        short count = 0;
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                try {
                    if (this.hasEmptyCellAt(x-1+i, y-1+j)) count++;
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    continue;
                }
            }
        } return count;
    }

    public short countUnknownCellsSurroundingCellAt(int x, int y) {
        short count = 0;
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                try {
                    if (this.hasUnknownCellAt(x-1+i, y-1+j)) count++;
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    continue;
                }
            }
        } return count;
    }

    public Cell getCell(int x, int y) throws IndexOutOfBoundsException {
        return this.matrix.get(x).get(y);
    }

    public int getLength() {
        return this.length;
    }

    public int getWidth() {
        return this.width;
    }

    public Boolean hasBombCellAt(int x, int y) {
        try {
            return this.matrix.get(x).get(y).isBombCell();
        } catch (UnknownAnswerException uae) {
            return false;
        }
    }

    public Boolean hasEmptyCellAt(int x, int y) {
        try {
            return !this.matrix.get(x).get(y).isBombCell();
        } catch (UnknownAnswerException uae) {
            return false;
        }
    }

    public Boolean hasUnknownCellAt(int x, int y) {
        return this.matrix.get(x).get(y).isUnknownCell();
    }

    private CellMatrix resizeMatrixTo(int l, int w) {
        ArrayList<ArrayList<Cell>> tmp = new ArrayList<>();
        for (int i=0; i<l; i++) {
            try {
                // add existing cells to preserve data
                tmp.add(this.matrix.get(i));
            } catch (IndexOutOfBoundsException ioobe) {
                // extend matrix if new length > old length
                tmp.add(new ArrayList<>());
                for (int j=0; j<w; j++) {
                    tmp.get(i).add(new Cell());
                } continue;
            }
            // append new cells if new width > old width
            for (int j=this.width-1; j<w; j++) {
                tmp.get(i).add(new Cell());
            }
        }

        this.matrix = tmp;
        this.length = l;
        this.width = w;

        return this;
    }

    private void revealBombsAroundCellAt(int x, int y) {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (i != 1 && j != 1 && x-1+i >= 0 && x-1+i < this.width && y-1+j >= 0 && y-1+j < this.length) {
                    if (this.matrix.get(x-1+i).get(y-1+j).isUnknownCell()) {
                        this.matrix.get(x-1+i).set(y-1+j, new BombCell());
                    }
                }
            }
        }
    }

    private void revealSafeCellsAroundCellAt(int x, int y) {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (i != 1 && j != 1 && x-1+i >= 0 && x-1+i < this.width && y-1+j >= 0 && y-1+j < this.length) {
                    if (this.matrix.get(x-1+i).get(y-1+j).isUnknownCell()) {
                        this.matrix.get(x-1+i).get(y-1+j).setSafetyFlag(true);
                    }
                }
            }
        }
    }

    public CellMatrix setDimensions(int newLength, int newWidth) {
        return this.resizeMatrixTo(newLength, newWidth);
    }

    public CellMatrix setLength(int newLength) {
        return this.resizeMatrixTo(newLength, this.width);
    }

    public CellMatrix setWidth(int newWidth) {
        return this.resizeMatrixTo(this.length, newWidth);
    }
}