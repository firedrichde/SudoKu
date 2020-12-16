import java.util.List;
import java.util.Set;

abstract public class AbstractBoard {
    public static final String S_UN_DEFINITIVE_NUMBER_SYMBOL = ".";
    public static final int S_UN_DEFINITIVE_NUMBER = -1;
    private final int mSideLength;
    private NumberRecorder mNumberRecorder;

    public AbstractBoard(int sideLength) {
        mSideLength = sideLength;
        mNumberRecorder = new NumberRecorder();
    }

    public void layout(List<String> data) {
        for (int i = 0; i < data.size(); i++) {
            int number;
            if (S_UN_DEFINITIVE_NUMBER_SYMBOL.equals(data.get(i))) {
                number = S_UN_DEFINITIVE_NUMBER;
            } else {
                number = Integer.parseInt(data.get(i));
                mNumberRecorder.increment(number);
            }
            boolean valid = add(number);
            if (!valid) {
                throw new IllegalArgumentException(
                        String.format("input data is against the rule, index=%d,number=%d", i, number));
            }
        }
    }

    public void scan() {
        preHandle();
        List<Integer> order = mNumberRecorder.sortByCount();
        for (int i = 0; i < order.size(); i++) {
            scanNumber(order.get(i));
        }
    }

    abstract void preHandle();

    abstract public void scanNumber(int number);

    abstract public void scanNumberAtRegion(int number, int regionId);

    abstract public boolean add(int number);

    abstract public Set<Integer> getNumbersAtRow(int rowIndex);

    abstract public Set<Integer> getNumbersAtCol(int colIndex);

    abstract public Set<Integer> getNumbersAtChildBoard(int childId);

    @Deprecated
    abstract public Set<Integer> getRegionIdWithNumber(int number);

    @Deprecated
    abstract public Set<Integer> getRegionIdWithOutNumber(int number);
}
