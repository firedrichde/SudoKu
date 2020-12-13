import java.io.InputStream;
import java.util.*;

/**
 * Board
 */
public class Board {
    public static final int ROWS = 9;
    public static final int COLS = 9;
    public static final int REGIONS = 9;
    public static final int COL_REGIONS = 3;
    public static final int ROW_REGIONS = 3;
    public static final String EMPTY_SYMBOL = ".";

    private boolean solved;
    private List<Region> regions;
    private Map<Integer, List<CellEntity>> rowGroupMap;
    private Map<Integer, List<CellEntity>> colGroupMap;
    private List<String> initData;
    private NumberRecorder numberRecorder;

    public Board(InputStream inputStream) {

    }

    public Board(List<String> initData) {
        this.initData = initData;
        solved = false;
        numberRecorder = new NumberRecorder();
        regions = new ArrayList<>();
        for (int i = 0; i < REGIONS; i++) {
            regions.add(new Region(i));
        }
        rowGroupMap = new HashMap<>();
        for (int i = 0; i < ROWS; i++) {
            rowGroupMap.put(i, new ArrayList<>());
        }
        colGroupMap = new HashMap<>();
        for (int i = 0; i < COLS; i++) {
            colGroupMap.put(i, new ArrayList<>());
        }
        parse();
    }

    public void scanAll(){
        List<Integer> order = numberRecorder.sortByCount();
        for (int i = 0; i < order.size(); i++) {
            int scanNumber = order.get(i);
            scan(scanNumber);
        }
    }

    private void scan(int number){
        Set<Integer> regions_with_number = regionsWithNumber(number);
        if (regions_with_number.size()==0 || regions_with_number.size()==NumberRecorder.MAX_NUMBER){
            return;
        }
        Set<Integer> regions_without_number = NumberUtil.complement(regions_with_number);

    }

    private Set<Integer> regionsWithNumber(int number){
        Set<Integer> result = new HashSet<>();
        for (Region region:
             regions) {
            if (region.containsNumber(number)){
                result.add(region.getId());
            }
        }
        return result;
    }

    private boolean validate() {
        return false;
    }

    private boolean validateRegion(int id, int number) {
        Region region = regions.get(id);
        return !region.containsNumber(number);
    }

    private boolean validateRow(int row, int number) {
        List<CellEntity> rowGroup = rowGroupMap.get(row);
        for (CellEntity cell :
                rowGroup) {
            if (cell.getNumber() == number) {
                return false;
            }
        }
        return true;
    }


    private boolean validateCol(int col, int number) {
        List<CellEntity> colGroup = colGroupMap.get(col);
        for (CellEntity cell :
                colGroup) {
            if (cell.getNumber() == number) {
                return false;
            }
        }
        return true;
    }

    /**
     * parse the initial data from outer Environment
     *
     * @return false if the initial data is against the SudoKu rules,otherwise return true
     */
    private boolean parse() {
        for (int i = 0; i < initData.size(); i++) {
            int number;
            int row = i % COLS;
            int col = i - row * COLS;
            int regionId = (row / Region.ROWS) * ROW_REGIONS + (col / Region.COLS);
            if (initData.get(i).equals(EMPTY_SYMBOL)) {
                number = CellEntity.UN_DEFINITIVE_NUMBER;
            } else {
                number = Integer.parseInt(initData.get(i));
                boolean valid = validateRow(row, number) || validateCol(col, number) || validateRegion(regionId, number);
                if (!valid) {
                    return false;
                }
                numberRecorder.increment(number);
            }
            addCell(row,col,number);
        }
        return true;
    }

    private CellEntity getCell(int row, int col) {
        Region targetRegion = getRegion(row, col);
        int region_row = row % Region.ROWS;
        int region_col = col % Region.COLS;
        return targetRegion.getCell(region_row, region_col);
    }

    private void addCell(int row, int col,int number) {
        CellEntity cellEntity = new CellEntity(row,col,number);
        Region targetRegion = getRegion(row, col);
//        int region_row = row % Region.ROWS;
//        int region_col = col % Region.COLS;
        targetRegion.add(cellEntity);
        rowGroupMap.get(row).add(cellEntity);
        colGroupMap.get(col).add(cellEntity);
    }

    private Region getRegion(int row, int col) {
        int id = (row / Region.ROWS) * COL_REGIONS + col / Region.COLS;
        return regions.get(id);
    }

}