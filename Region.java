import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Region
 */
public class Region {
    public static final int S_SIDE_LENGTH = 3;
    public static final int S_ROWS = 3;
    public static final int S_COLS = 3;
    private final int mId;
    private List<CellEntity> mCells;
    private Set<Integer> mSolvedNumberSet;

    public Region(int id) {
        this.mId = id;
        this.mCells = new ArrayList<>();
        this.mSolvedNumberSet = new HashSet<>();
    }

    public  int getId(){
        return mId;
    }

    public List<CellEntity> getCells(){
        return mCells;
    }

    @Deprecated
    public List<CellEntity> getCellsAtRowIndex(int row) {
        List<CellEntity> result = new ArrayList<>();
        for (int i = 0; i < S_COLS; i++) {
            int index = row * S_COLS + i;
            result.add(mCells.get(index));
        }
        return result;
    }

    @Deprecated
    public List<CellEntity> getCellAtColIndex(int col) {
        List<CellEntity> result = new ArrayList<>();
        for (int i = 0; i < S_ROWS; i++) {
            int index = i * S_COLS + col;
            result.add(mCells.get(index));
        }
        return result;
    }

    public CellEntity getCell(int index) {
        return mCells.get(index);
    }

//    @Deprecated
//    public boolean assignNumber(int row, int col, int number) {
//        if (containsNumber(number)) {
//            return false;
//        } else {
//            CellEntity cellEntity = getCell(row, col);
//            cellEntity.setNumber(number);
//            mSolvedNumberSet.add(number);
//            return true;
//        }
//    }
    public void add(CellEntity cellEntity){
        mCells.add(cellEntity);
        mSolvedNumberSet.add(cellEntity.getNumber());
    }

    @Deprecated
    public boolean isDefinitive(int index){
        return mCells.get(index).isDefinitive();
    }

    public boolean containsNumber(int number) {
        for (CellEntity cell:
             mCells) {
            if (cell.getNumber() == number){
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public Set<Integer> getCandidates(){
        return NumberUtil.complement(mSolvedNumberSet);
    }

    public int size(){
        return S_SIDE_LENGTH*S_SIDE_LENGTH;
    }
}