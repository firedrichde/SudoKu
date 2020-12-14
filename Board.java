import java.io.InputStream;
import java.util.*;

/**
 * Board
 */
public class Board extends AbstractBoard {
    public static final int S_SIDE_LENGTH = 9;
    public static final int S_ROWS = 9;
    public static final int S_COLS = 9;
    public static final int S_REGIONS = 9;
    public static final int S_COL_REGIONS = 3;
    public static final int S_ROW_REGIONS = 3;
//    public static final String EMPTY_SYMBOL = ".";

    private boolean solved;
    private List<CellEntity> mCells;
    private int mCurrentIndex;
    private List<Region> mRegions;
    private Map<Integer, List<CellEntity>> mRowGroupMap;
    private Map<Integer, List<CellEntity>> mColGroupMap;
    private Map<Integer, List<CellEntityLink>> mLinkGroupMap;
    private Map<Integer, List<CellEntityXWing>> mXwingGroupMap;
    private ArrayList<CellEntityLinkPair> mCellPairs;
    private List<String> mInitData;
//    private NumberRecorder numberRecorder;

    public Board(InputStream inputStream) {
        super(S_SIDE_LENGTH);

    }

    public Board() {
        super(S_SIDE_LENGTH);
        this.mCurrentIndex = -1;
//        this.mInitData = initData;
        solved = false;
        mCells = new ArrayList<>();
        mLinkGroupMap = new HashMap<>();
        mXwingGroupMap = new HashMap<>();
        mCellPairs = new ArrayList<>();
//        numberRecorder = new NumberRecorder();
        mRegions = new ArrayList<>();
        for (int i = 0; i < S_REGIONS; i++) {
            mRegions.add(new Region(i));
        }
        mRowGroupMap = new HashMap<>();
        for (int i = 0; i < S_ROWS; i++) {
            mRowGroupMap.put(i, new ArrayList<>());
        }
        mColGroupMap = new HashMap<>();
        for (int i = 0; i < S_COLS; i++) {
            mColGroupMap.put(i, new ArrayList<>());
        }
    }


//    @Deprecated
//    private void scanRegion(int regionId, int number, Set<Integer> regions) {
//
//        List<Integer> rowAvailable = new ArrayList<>();
//        List<Integer> colAvailable = new ArrayList<>();
//        for (int i = 0; i < Region.S_COLS; i++) {
//            int rowIndex = getRowFromRegion(regionId, i);
//            if (!rowGroupContains(number, rowIndex)) {
//                rowAvailable.add(i);
//            }
//        }
//        for (int j = 0; j < Region.S_COLS; j++) {
//            int colIndex = getColFromRegion(regionId, j);
//            if (!colGroupContains(colIndex, number)) {
//                colAvailable.add(j);
//            }
//        }
//        for (Integer row :
//                rowAvailable) {
//            for (Integer col :
//                    colAvailable) {
//            }
//        }
//
//    }


    private Set<Integer> regionsWithNumber(int number) {
        Set<Integer> result = new HashSet<>();
        for (Region region :
                mRegions) {
            if (region.containsNumber(number)) {
                result.add(region.getId());
            }
        }
        return result;
    }

    @Deprecated
    private boolean validate() {
        return false;
    }

    @Deprecated
    private boolean validateRegion(int id, int number) {
        Region region = mRegions.get(id);
        return !region.containsNumber(number);
    }

    private boolean validateRow(int row, int number) {
        List<CellEntity> rowGroup = mRowGroupMap.get(row);
        for (CellEntity cell :
                rowGroup) {
            if (cell.getNumber() == number) {
                return false;
            }
        }
        return true;
    }


    @Deprecated
    private boolean validateCol(int col, int number) {
        List<CellEntity> colGroup = mColGroupMap.get(col);
        for (CellEntity cell :
                colGroup) {
            if (cell.getNumber() == number) {
                return false;
            }
        }
        return true;
    }

//    /**
//     * parse the initial data from outer Environment
//     *
//     * @return false if the initial data is against the SudoKu rules,otherwise return true
//     */
//    private boolean parse() {
//        for (int i = 0; i < mInitData.size(); i++) {
//            int number;
//            int row = i % S_COLS;
//            int col = i - row * S_COLS;
//            int regionId = (row / Region.S_ROWS) * S_ROW_REGIONS + (col / Region.S_COLS);
//            if (mInitData.get(i).equals(EMPTY_SYMBOL)) {
//                number = CellEntity.UN_DEFINITIVE_NUMBER;
//            } else {
//                number = Integer.parseInt(mInitData.get(i));
//                boolean valid = validateRow(row, number) || validateCol(col, number) || validateRegion(regionId, number);
//                if (!valid) {
//                    return false;
//                }
//                numberRecorder.increment(number);
//            }
//            addCell(row, col, number);
//        }
//        return true;
//    }

    private CellEntity getCell(int index) {
        return mCells.get(index);
    }

//    private CellEntity getCell(int row, int col) {
//        Region targetRegion = getRegion(row, col);
//        int region_row = row % Region.ROWS;
//        int region_col = col % Region.COLS;
//        return targetRegion.getCell(region_row, region_col);
//    }


//    private Region getRegion(int row, int col) {
//        int id = (row / Region.S_ROWS) * S_COL_REGIONS + col / Region.S_COLS;
//        return mRegions.get(id);
//    }
//
//    public int getRowFromRegion(int regionId, int row) {
//        return row + (regionId / S_COL_REGIONS) * Region.S_ROWS;
//    }
//
//    public int getColFromRegion(int regionId, int col) {
//        return col + (regionId % S_COL_REGIONS) * Region.S_COLS;
//    }
//
//    public int getRegionRow(int row) {
//        return row % Region.S_ROWS;
//    }
//
//    public int getRegionCol(int col) {
//        return col % Region.S_COLS;
//    }
//
//    public boolean rowGroupContains(int number, int row) {
//        return mRowGroupMap.get(row).contains(number);
//    }
//
//    public boolean colGroupContains(int number, int col) {
//        return mColGroupMap.get(col).contains(number);
//    }

//    public boolean assignNumber(int row, int col, int number) {
//        Region region = getRegion(row, col);
//        if (validateRegion(region.getId(), number) && validateRow(row, number) && validateCol(col, number)) {
//            CellEntity cellEntity = region.getCell(getRegionRow(row), getRegionCol(col));
//            if (cellEntity.isDefinitive()) {
//                return false;
//            } else {
//                return true;
//            }
//        }
//        return true;
//    }

    private int getColIndexFromIndex(int index) {
        return index % S_COLS;
    }

    private int getRowIndexFromIndex(int index) {
        return index / S_COLS;
    }

    private int getRegionIdFromIndex(int index) {
        int rowIndex = getRowIndexFromIndex(index);
        int colIndex = getColIndexFromIndex(index);
        return (rowIndex / Region.S_ROWS) * S_COL_REGIONS + (colIndex / Region.S_COLS);
    }

    private boolean validate(int number, int rowIndex, int colIndex, int regionId) {
        if (number == S_UN_DEFINITIVE_NUMBER) {
            return true;
        }
        Set<Integer> rowNumbers = getNumbersAtRow(rowIndex);
        if (!rowNumbers.isEmpty() && rowNumbers.contains(number)) {
            return false;
        }
        Set<Integer> colNumbers = getNumbersAtCol(colIndex);
        if (!colNumbers.isEmpty() && colNumbers.contains(number)) {
            return false;
        }
        Set<Integer> regionNumbers = getNumbersAtChildBoard(regionId);
        if (!regionNumbers.isEmpty() && regionNumbers.contains(number)) {
            return false;
        }
        List<CellEntityLink> links = getCellEntityLinks(number);
        if (links != null && !links.isEmpty()) {
            for (CellEntityLink link :
                    links) {
                if (link.getCellEntityLinkType() == CellEntityLinkType.HORIZONTAL && link.getRow() == rowIndex) {
                    return false;
                }
                if (link.getCellEntityLinkType() == CellEntityLinkType.VERTICAL && link.getCol() == colIndex) {
                    return false;
                }
            }
        }
        return true;
    }

    public void addCell(CellEntity cellEntity) {
        int number = cellEntity.getNumber();
        updateRowGroups(cellEntity);
        updateColGroups(cellEntity);
        updateRegionGroup(cellEntity);
    }

    public void updateRowGroups(CellEntity cellEntity) {
        mRowGroupMap.get(cellEntity.getRow()).add(cellEntity);
    }

    public void updateColGroups(CellEntity cellEntity) {
        mColGroupMap.get(cellEntity.getCol()).add(cellEntity);
    }

    public void updateRegionGroup(CellEntity cellEntity) {
        mRegions.get(cellEntity.getRegionId()).add(cellEntity);
    }

    @Override
    public Set<Integer> getNumbersAtRow(int rowIndex) {
        Set<Integer> result = new HashSet<>();
        for (CellEntity cell :
                mRowGroupMap.get(rowIndex)) {
            result.add(cell.getNumber());
        }
        return result;
    }

    @Override
    public Set<Integer> getNumbersAtCol(int colIndex) {
        Set<Integer> result = new HashSet<>();
        for (CellEntity cell :
                mColGroupMap.get(colIndex)) {
            result.add(cell.getNumber());
        }
        return result;
    }

    @Override
    public Set<Integer> getNumbersAtChildBoard(int childId) {
        Set<Integer> result = new HashSet<>();
        for (CellEntity cell : mRegions.get(childId).getCells()) {
            result.add(cell.getNumber());
        }
        return result;
    }

    public int scanRows(){
        int changes = 0;
        List<CellEntity> cells;
        Set<Integer> numbers;
        for (int i = 0; i < S_ROWS; i++) {
            cells = mRowGroupMap.get(i);
            numbers = getNumbersAtRow(i);
            if (numbers!=null && !numbers.isEmpty()){
                for (CellEntity cell:
                     cells) {
                    if (cell.getType()!=CellEntityType.SINGLE && cell.getCandidate().size()==1){
                        assignNumber(cell,cell.getCandidate().getNumber());
                        changes+=1;
                    }
                }
            }
        }
        return changes;
    }

    public int scanCols(){
        int changes = 0;
        List<CellEntity> cells;
        Set<Integer> numbers;
        for (int i = 0; i < S_COLS; i++) {
            cells = mColGroupMap.get(i);
            numbers = getNumbersAtCol(i);
            if (numbers!=null && !numbers.isEmpty()){
                for (CellEntity cell:
                        cells) {
                    if (cell.getType()!=CellEntityType.SINGLE && cell.getCandidate().size()==1){
                        assignNumber(cell,cell.getCandidate().getNumber());
                        changes +=1;
                    }
                }
            }
        }
        return changes;
    }

    @Override
    public void scanNumber(int number) {
//        Set<Integer> regions_fill = getRegionIdWithNumber(number);
//        Set<Integer> regions_empty = getRegionIdWithOutNumber(number);
        for (int i = 0; i < S_REGIONS; i++) {
            scanNumberAtRegion(number, i);
        }
        int colChanges = -1;
        int rowChanges = -1;
        while(colChanges!=0 && rowChanges!=0){
            colChanges = scanCols();
            rowChanges = scanRows();
        }
    }

    @Override
    public void scanNumberAtRegion(int number, int regionId) {
        Region region = mRegions.get(regionId);
        List<CellEntity> candidates = new ArrayList<>();
        CellEntity cellEntity;
        for (int i = 0; i < region.size(); i++) {
            cellEntity = region.getCell(i);
            if (cellEntity.isDefinitive()) {
                continue;
            } else {
                boolean valid = isCandidateForNumber(cellEntity, number);
                if (valid) {
                    candidates.add(cellEntity);
//                    assignCandidate(cellEntity,number);
                } else {
                    removeCandidate(cellEntity, number);
                }
            }
        }
        if (candidates.size() == 1) {
            cellEntity = candidates.get(0);
            int index = cellEntity.getRow() * S_COLS + cellEntity.getCol();
            assignNumber(index, number);
        } else if (candidates.size() == 2) {
            //add Cell Entity Link
            CellEntityLink link = new CellEntityLink(candidates.get(0), candidates.get(1));
            addLink(number, link);
        } else {
        }
    }

    public void assignCandidate(CellEntity cellEntity, int number) {
        cellEntity.addCandidate(number);
    }

    public void removeCandidate(CellEntity cellEntity, int number) {
        cellEntity.getCandidate().remove(number);
    }

    public void assignNumber(int index, int number) {
        CellEntity cellEntity = mCells.get(index);
        assignNumber(cellEntity,number);
    }

    public void assignNumber(CellEntity cellEntity,int number){
        cellEntity.setNumber(number);
        updateAfterAssign(cellEntity,number);
    }

    public void updateAfterAssign(CellEntity cellEntity,int number){
        for (CellEntityLinkPair pair:
             getCellPairs()) {
            if (pair.belong(cellEntity)){
                pair.assignNumber(cellEntity,number);
            }
        }
    }

    public void updateLinks(int number){
        List<CellEntityLink> links = mLinkGroupMap.get(number);
        if (links!=null && !links.isEmpty()){
            Set<CellEntityLink> invalidSet = new HashSet<>();
            for (int i = 0; i < links.size(); i++) {
                if (!links.get(i).isValid()){
                    invalidSet.add(links.get(i));
                }
            }
            for (CellEntityLink link:
                 invalidSet) {
                links.remove(link);
            }
        }
    }

    public void updatePairs(){
        Set<CellEntityLinkPair> invalidSet = new HashSet<>();
        for (CellEntityLinkPair pair:
             mCellPairs) {
            if (!pair.isValid()){
                invalidSet.add(pair);
            }
        }
        for (CellEntityLinkPair pair:
             invalidSet) {
            mCellPairs.remove(pair);
        }
    }


    public boolean isCandidateForNumber(CellEntity cellEntity, int number) {
        boolean valid = validate(number, cellEntity.getRow(), cellEntity.getCol(), cellEntity.getRegionId());
        return valid;
    }

    @Override
    public boolean add(int number) {
        int index = mCurrentIndex + 1;
        int rowIndex = getRowIndexFromIndex(index);
        int colIndex = getColIndexFromIndex(index);
        int regionId = getRegionIdFromIndex(index);
        if (validate(number, rowIndex, colIndex, regionId)) {
            CellEntity newCell = new CellEntity(number,rowIndex, colIndex, regionId);
            mCells.add(newCell);
            addCell(newCell);
            mCurrentIndex += 1;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<Integer> getRegionIdWithNumber(int number) {
        return null;
    }

    @Override
    public Set<Integer> getRegionIdWithOutNumber(int number) {
        return null;
    }

    public List<CellEntityLink> getCellEntityLinks(int number) {
        if (mLinkGroupMap.containsKey(number)) {
            updateLinks(number);
            return mLinkGroupMap.get(number);
        } else {
            return null;
        }
    }

    public List<CellEntityLinkPair> getCellPairs(){
        updatePairs();
        return mCellPairs;
    }

    public void addLink(int number, CellEntityLink link) {
        if (mLinkGroupMap.containsKey(number)) {
            mLinkGroupMap.get(number).add(link);
        } else {
            List<CellEntityLink> linkList = new ArrayList<>();
            linkList.add(link);
            mLinkGroupMap.put(number, linkList);
        }
    }

    public void print(){
        for (int i = 0; i < mCells.size() ; i++) {
            CellEntity cellEntity = mCells.get(i);
            if (cellEntity.getType()==CellEntityType.SINGLE ){
                System.out.print(cellEntity);
            }else {
                System.out.print(S_UN_DEFINITIVE_NUMBER_SYMBOL);
            }
            if((i+1)%S_COLS==0){
                System.out.println();
            }
        }
    }
}