import java.util.Objects;
import java.util.Set;

public class CellEntity {
    public static final int UN_DEFINITIVE_NUMBER = -1;

    private int mNumber;
    private int mRow;
    private int mCol;
    private int mRegionId;
    // the number is definitive
    private boolean mDefinitive;
    private Candidate mCandidate;
    private CellEntityType mType;

    public CellEntity() {

    }

    public CellEntity(int number, int row, int col, int regionId) {
        this.mNumber = number;
        this.mRow = row;
        this.mCol = col;
        this.mRegionId = regionId;
        if (number != UN_DEFINITIVE_NUMBER) {
            this.mDefinitive = true;
            this.mType = CellEntityType.SINGLE;
        } else {
            this.mDefinitive = false;
            mCandidate = new Candidate();
            mCandidate.fillAllNumber();
            this.mType = CellEntityType.CANDIDATES;
        }
    }

    public CellEntity(int row, int col, int regionId) {
        this(UN_DEFINITIVE_NUMBER, row, col, regionId);
    }

    public void setNumber(int number) {
        this.mNumber = number;
        if (this.mNumber != UN_DEFINITIVE_NUMBER) {
            mDefinitive = true;
            mCandidate = null;
            mType = CellEntityType.SINGLE;
        }
    }

    public int getNumber() {
        return this.mNumber;
    }

    public void setCol(int col) {
        this.mCol = col;
    }

    public int getCol() {
        return this.mCol;
    }

    public void setRow(int row) {
        this.mRow = row;
    }

    public int getRow() {
        return this.mRow;
    }

    public boolean isDefinitive() {
        return mDefinitive;
    }

    public void setRegionId(int regionId) {
        mRegionId = regionId;
    }

    public int getRegionId() {
        return mRegionId;
    }

    public void addCandidate(int number) {
        mCandidate.add(number);
    }

    public void removeCandidate(int number){
        if (mType!=CellEntityType.SINGLE){
            mCandidate.remove(number);
        }
//        if (mCandidate==null || mCandidate.size()==1){
//            mType = CellEntityType.SINGLE;
//            mDefinitive = true;
//        }
    }

    public boolean onlyOneCandidate(){
        return mCandidate!=null && mCandidate.size()==1;
    }

    public Candidate getCandidate() {
        return mCandidate;
    }

    public CellEntityType getType() {
        return mType;
    }

    public void setType(CellEntityType type) {
        mType = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellEntity that = (CellEntity) o;
        return that.getRow() == getRow() && that.getCol() == getCol();
    }

    @Override
    public int hashCode() {
        return Objects.hash(mNumber);
    }

    @Override
    public String toString() {
        return String.valueOf(mNumber);
    }
}