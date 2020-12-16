public class CellEntityLinkPair {
    private CellEntityLink mLeft;
    private CellEntityLink mRight;
    private CellEntity mLeftCell;
    private CellEntity mRightCell;
    private int[] mNumberPair;
    private boolean mValid;

    public CellEntityLinkPair(CellEntityLink left, CellEntityLink right) {
        mLeft = left;
        mRight = right;
        mNumberPair = new int[2];
        mNumberPair[0] = mLeft.getNumber();
        mNumberPair[1] = mRight.getNumber();
        mLeftCell = mLeft.getLeft();
        mRightCell = mLeft.getRight();
        mValid = true;
    }

    public boolean belong(CellEntity cellEntity) {
        return mLeftCell.equals(cellEntity) || mRightCell.equals(cellEntity);
    }

    public void assignNumber(CellEntity cellEntity,int number){
        CellEntity anotherCell;
        if (cellEntity.equals(mLeftCell)){
            anotherCell = mRightCell;
        }else {
            anotherCell = mLeftCell;
        }
        cellEntity.setNumber(number);
        if(mNumberPair[0]==number){
            anotherCell.setNumber(mNumberPair[1]);
        }else {
            anotherCell.setNumber(mNumberPair[0]);
        }
        invalidate();
    }

    public void invalidate(){
        mValid = false;
        mLeft.invalidate();
        mRight.invalidate();
    }

    public boolean isValid() {
        return mValid;
    }

    public int[] getNumberPair() {
        return mNumberPair;
    }
}
