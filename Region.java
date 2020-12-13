import java.util.ArrayList;
import java.util.List;

/**
 * Region
 */
public class Region {
    public static final int CELL_SIZE = 9;
    public static final int ROWS = 3;
    public static final int COLS = 3;
    private int id;
    private List<CellEntity> cells;

    public Region(int id) {
        this.id = id;
        this.cells = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cells.add(new CellEntity(i, j));
            }
        }
    }

    public List<CellEntity> getCellsAtRowIndex(int row) {
        List<CellEntity> result = new ArrayList<>();
        for (int i = 0; i < COLS; i++) {
            int index = row * COLS + i;
            result.add(cells.get(index));
        }
        return result;
    }

    
}