import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Region
 */
public class Region {
    public static final int CELL_SIZE = 9;
    public static final int ROWS = 3;
    public static final int COLS = 3;
    private int id;
    private List<CellEntity> cells;
    private Set<Integer> solvedNumberSet;

    public Region(int id) {
        this.id = id;
        this.cells = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cells.add(new CellEntity(i, j));
            }
        }
        this.solvedNumberSet = new HashSet<>();
    }

    public  int getId(){
        return id;
    }

    public List<CellEntity> getCellsAtRowIndex(int row) {
        List<CellEntity> result = new ArrayList<>();
        for (int i = 0; i < COLS; i++) {
            int index = row * COLS + i;
            result.add(cells.get(index));
        }
        return result;
    }

    public List<CellEntity> getCellAtColIndex(int col) {
        List<CellEntity> result = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            int index = i * COLS + col;
            result.add(cells.get(index));
        }
        return result;
    }

    public CellEntity getCell(int row, int col) {
        int index = row * COLS + col;
        return cells.get(index);
    }

    public boolean assignNumber(int row, int col, int number) {
        if (containsNumber(number)) {
            return false;
        } else {
            CellEntity cellEntity = getCell(row, col);
            cellEntity.setNumber(number);
            solvedNumberSet.add(number);
            return true;
        }
    }
    public void add(CellEntity cellEntity){
        cells.add(cellEntity);
        solvedNumberSet.add(cellEntity.getNumber());
    }

    public boolean containsNumber(int number) {
        return solvedNumberSet.contains(number);
    }
}