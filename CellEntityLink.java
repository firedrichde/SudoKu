public class CellEntityLink {

    private int number;
    private CellEntity left;
    private CellEntity right;
    private CellEntityLinkType cellEntityLinkType;

    public CellEntityLink(CellEntity left, CellEntity right) {
        this.left = left;
        this.right = right;
        this.number = left.getNumber();
        if (left.getRow()==right.getRow()){
            cellEntityLinkType = CellEntityLinkType.HORIZONTAL;
        }else if(left.getCol()==right.getCol()){
            cellEntityLinkType = CellEntityLinkType.VERTICAL;
        }else {
            cellEntityLinkType = CellEntityLinkType.DECLINING;
        }
    }

    public int getNumber() {
        return number;
    }

    public CellEntity getLeft() {
        return left;
    }

    public CellEntity getRight() {
        return right;
    }

    public CellEntityLinkType getCellEntityLinkType() {
        return cellEntityLinkType;
    }

    public int getCol(){
        return left.getCol();
    }
    public int getRow(){
        return left.getRow();
    }
}
