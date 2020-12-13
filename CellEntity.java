import java.util.Objects;

public class CellEntity {
    public static final int UN_DEFINITIVE_NUMBER = -1;

    private int number;
    private int row;
    private int col;
    private boolean definitive;

    public CellEntity(int number, int row, int col) {
        this.number = number;
        this.row = row;
        this.col = col;
        this.definitive = false;
    }

    public CellEntity(int row, int col) {
        this(UN_DEFINITIVE_NUMBER, row, col);
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return this.col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return this.row;
    }

    public boolean isDefinitive(){
        return definitive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellEntity that = (CellEntity) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}