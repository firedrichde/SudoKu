public class CellEntityLink {

    private int mNumber;
    private CellEntity mLeft;
    private CellEntity mRight;
    private CellEntityLinkType mCellEntityLinkType;
    private boolean mValid;

    public CellEntityLink(CellEntity left, CellEntity right) {
        this.mNumber = left.getNumber();
        if (left.getRow()==right.getRow()){
            mCellEntityLinkType = CellEntityLinkType.HORIZONTAL;
            if (left.getCol()<=right.getCol()){
                this.mLeft = left;
                this.mRight = right;
            }else {
                this.mLeft = right;
                this.mRight = left;
            }
        }else if(left.getCol()==right.getCol()){
            mCellEntityLinkType = CellEntityLinkType.VERTICAL;
            if (left.getRow()<=right.getRow()){
                this.mLeft = left;
                this.mRight = right;
            }else {
                this.mLeft =right;
                this.mRight = left;
            }
        }else {
            mCellEntityLinkType = CellEntityLinkType.DECLINING;
            if (left.getCol()<=right.getCol()){
                this.mLeft = left;
                this.mRight = right;
            }else {
                this.mLeft = right;
                this.mRight = left;
            }
        }
        mValid = true;
    }

    public int getNumber() {
        return mNumber;
    }

    public CellEntity getLeft() {
        return mLeft;
    }

    public CellEntity getRight() {
        return mRight;
    }

    public CellEntityLinkType getCellEntityLinkType() {
        return mCellEntityLinkType;
    }

    public int getCol(){
        return mLeft.getCol();
    }
    public int getRow(){
        return mLeft.getRow();
    }

    public void assignNumber(CellEntity cellEntity){
        CellEntity anotherCell = cellEntity.equals(mLeft)? mRight:mLeft;
        anotherCell.removeCandidate(this.mNumber);
        invalidate();
    }

    public void invalidate(){
        mValid =  false;
    }

    public boolean isValid() {
        return mValid;
    }

    public boolean belong(CellEntity cellEntity){
        if(mLeft.equals(cellEntity) || mRight.equals(cellEntity)){
            return true;
        }else {
            return false;
        }
    }
}
