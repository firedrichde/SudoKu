public class CellEntityXWing {
    private int mNumber;
    private CellEntityLink mLeft;
    private CellEntityLink mRight;

    public CellEntityXWing(CellEntityLink left, CellEntityLink right,int number) {
        this.mLeft = left;
        this.mRight = right;
        this.mNumber = number;
    }

    public CellEntityLink getLeft() {
        return mLeft;
    }

    public CellEntityLink getRight() {
        return mRight;
    }

    public static boolean isCellEntityXWing(CellEntityLink x, CellEntityLink y) {
        if (x == y || x.equals(y)) {
            return false;
        }
        if (x.getNumber() != y.getNumber()) {
            return false;
        } else {
            CellEntityLinkType xType = x.getCellEntityLinkType();
            CellEntityLinkType yType = y.getCellEntityLinkType();
            int x_left_row = x.getLeft().getRow();
            int x_left_col = x.getLeft().getCol();
            int x_right_row = x.getRight().getRow();
            int x_right_col = x.getRight().getCol();
            int y_left_row = y.getLeft().getRow();
            int y_left_col = y.getLeft().getCol();
            int y_right_row = y.getRight().getRow();
            int y_right_col = y.getRight().getCol();
            if ((xType == CellEntityLinkType.HORIZONTAL && yType == CellEntityLinkType.VERTICAL)
                    || (xType == CellEntityLinkType.VERTICAL && yType == CellEntityLinkType.HORIZONTAL)) {
                return false;
            } else {
                if (xType == CellEntityLinkType.HORIZONTAL) {
                    if (yType == CellEntityLinkType.HORIZONTAL) {
                        if (x_left_col == y_left_col || x_right_col == y_left_col || x_left_col == y_right_col || x_right_col == y_right_col) {
                            return true;
                        }
                    } else {
                        if ((x_left_col == y_left_col && x_right_col == y_right_col) || (x_left_col == y_right_col && x_right_col == y_left_col)) {
                            return true;
                        }
                    }
                } else if (xType == CellEntityLinkType.VERTICAL) {
                    if (yType == CellEntityLinkType.VERTICAL) {
                        if (x_left_row == y_left_row || x_right_row == y_left_row || x_left_row == y_right_row || x_right_row == y_right_row) {
                            return true;
                        }
                    }else{
                        if ((x_left_row == y_left_row && x_right_row == y_right_row) || (x_left_row == y_right_row && x_right_row == y_left_row)) {
                            return true;
                        }
                    }
                } else {
                    if ((x_left_col==y_left_col && x_right_col==y_right_col) || (x_left_col==y_right_col && x_right_col==y_left_col)
                    || (x_left_row==y_left_row && x_right_row==y_right_row) || (x_left_row==y_right_row && x_right_row==y_left_row)){
                        return true;
                    }
                }
                return false;
            }
        }
    }
}
