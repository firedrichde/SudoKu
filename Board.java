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
    private int step;
//    private NumberRecorder numberRecorder;

    public Board(InputStream inputStream) {
        super(S_SIDE_LENGTH);

    }

    public Board() {
        super(S_SIDE_LENGTH);
        this.step = -1;
        this.mCurrentIndex = -1;
        solved = false;
        mCells = new ArrayList<>();
        mLinkGroupMap = new HashMap<>();
        mXwingGroupMap = new HashMap<>();
        mCellPairs = new ArrayList<>();
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

    private CellEntity getCell(int index) {
        return mCells.get(index);
    }


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
        if (cellEntity.getNumber() != CellEntity.UN_DEFINITIVE_NUMBER) {
            removeCandidateAtRegion(cellEntity.getRegionId(), number);
            removeCandidateAtRow(cellEntity.getRow(), number);
            removeCandidateAtCol(cellEntity.getCol(), number);
        }
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
            if (cell.isDefinitive()) {
                result.add(cell.getNumber());
            }
        }
        return result;
    }

    @Override
    public Set<Integer> getNumbersAtCol(int colIndex) {
        Set<Integer> result = new HashSet<>();
        for (CellEntity cell :
                mColGroupMap.get(colIndex)) {
            if (cell.isDefinitive()) {
                result.add(cell.getNumber());
            }
        }
        return result;
    }

    @Override
    public Set<Integer> getNumbersAtChildBoard(int childId) {
        Set<Integer> result = new HashSet<>();
        for (CellEntity cell : mRegions.get(childId).getCells()) {
            if (cell.isDefinitive()) {
                result.add(cell.getNumber());
            }
        }
        return result;
    }

    private boolean regionContainsNumber(int regionId, int number) {
        Region region = mRegions.get(regionId);
        return region != null && region.containsNumber(number);
    }

    public int scanRows() {
        int changes = 0;
        List<CellEntity> cells;
        Set<Integer> numbers;
        for (int i = 0; i < S_ROWS; i++) {
            cells = mRowGroupMap.get(i);
            numbers = getNumbersAtRow(i);
            if (numbers != null && !numbers.isEmpty()) {
                for (CellEntity cell :
                        cells) {
                    if (cell.getType() != CellEntityType.SINGLE && cell.onlyOneCandidate()) {
                        assignNumber(cell, cell.getCandidate().getNumber());
                        changes += 1;
                    }
                }
            }
        }
        return changes;
    }

    public int scanCols() {
        int changes = 0;
        List<CellEntity> cells;
        Set<Integer> numbers;
        for (int i = 0; i < S_COLS; i++) {
            cells = mColGroupMap.get(i);
            numbers = getNumbersAtCol(i);
            if (numbers != null && !numbers.isEmpty()) {
                for (CellEntity cell :
                        cells) {
                    if (cell.getType() != CellEntityType.SINGLE && cell.onlyOneCandidate()) {
                        assignNumber(cell, cell.getCandidate().getNumber());
                        changes += 1;
                    }
                }
            }
        }
        return changes;
    }

    /**
     * remove candidate for each empty cell from numbers of filled cells
     */
    @Override
    void preHandle() {
        for (CellEntity cell:
             mCells) {
            if (cell.isDefinitive()){
                int number = cell.getNumber();
                removeCandidateAtCol(cell.getCol(),number);
                removeCandidateAtRow(cell.getRow(),number);
                removeCandidateAtRegion(cell.getRegionId(),number);
            }
        }
    }

    @Override
    public void scanNumber(int number) {
        for (int i = 0; i < S_REGIONS; i++) {
            if (!regionContainsNumber(i, number)) {
                scanNumberAtRegion(number, i);
            }
        }
        int colChanges = -1;
        int rowChanges = -1;
        while (colChanges != 0 && rowChanges != 0) {
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
            System.out.printf("cell %d (%d,%d)%n", number, cellEntity.getRow(), cellEntity.getCol());
            assignNumber(index, number);
            print();
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
        assignNumber(cellEntity, number);
    }

    public void assignNumber(CellEntity cellEntity, int number) {
        cellEntity.setNumber(number);
        updateAfterAssign(cellEntity, number);
    }

    public void updateAfterAssign(CellEntity cellEntity, int number) {
        for (CellEntityLinkPair pair :
                getCellPairs()) {
            if (pair.belong(cellEntity)) {
                pair.assignNumber(cellEntity, number);
            }
        }
        // if assign a number to the specify cell,we should remove candidate(number)  for each related cell
        removeCandidateAtCol(cellEntity.getCol(), number);
        removeCandidateAtRow(cellEntity.getRow(), number);
        removeCandidateAtRegion(cellEntity.getRegionId(), number);
    }

    public void removeCandidateAtRow(int row, int number) {
        List<CellEntity> cells = mRowGroupMap.get(row);
        for (CellEntity cell :
                cells) {
            if (!cell.isDefinitive()) {
                cell.removeCandidate(number);
            }
        }
    }

    public void removeCandidateAtCol(int col, int number) {
        List<CellEntity> cells = mColGroupMap.get(col);
        for (CellEntity cell :
                cells) {
            if (!cell.isDefinitive()) {
                cell.removeCandidate(number);
            }
        }
    }

    public void removeCandidateAtRegion(int regionId, int number) {
        List<CellEntity> cells = mRegions.get(regionId).getCells();
        for (CellEntity cell :
                cells) {
            if (!cell.isDefinitive()) {
                cell.removeCandidate(number);
            }
        }
    }

    public void updateLinks(int number) {
        List<CellEntityLink> links = mLinkGroupMap.get(number);
        if (links != null && !links.isEmpty()) {
            Set<CellEntityLink> invalidSet = new HashSet<>();
            for (CellEntityLink cellEntityLink : links) {
                if (!cellEntityLink.isValid()) {
                    invalidSet.add(cellEntityLink);
                }
            }
            for (CellEntityLink link :
                    invalidSet) {
                links.remove(link);
            }
        }
    }

    public void updatePairs() {
        Set<CellEntityLinkPair> invalidSet = new HashSet<>();
        for (CellEntityLinkPair pair :
                mCellPairs) {
            if (!pair.isValid()) {
                invalidSet.add(pair);
            }
        }
        for (CellEntityLinkPair pair :
                invalidSet) {
            mCellPairs.remove(pair);
        }
    }


    public boolean isCandidateForNumber(CellEntity cellEntity, int number) {
        return validate(number, cellEntity.getRow(), cellEntity.getCol(), cellEntity.getRegionId());
    }

    @Override
    public boolean add(int number) {
        int index = mCurrentIndex + 1;
        int rowIndex = getRowIndexFromIndex(index);
        int colIndex = getColIndexFromIndex(index);
        int regionId = getRegionIdFromIndex(index);
        if (validate(number, rowIndex, colIndex, regionId)) {
            CellEntity newCell = new CellEntity(number, rowIndex, colIndex, regionId);
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

    public List<CellEntityLinkPair> getCellPairs() {
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

    public void print() {
        step += 1;
        System.out.println("Step: " + step);
        for (int i = 0; i < mCells.size(); i++) {
            CellEntity cellEntity = mCells.get(i);
            if (cellEntity.getType() == CellEntityType.SINGLE) {
                System.out.print(cellEntity);
            } else {
                System.out.print(S_UN_DEFINITIVE_NUMBER_SYMBOL);
            }
            System.out.print(" ");
            if ((i + 1) % S_COLS == 0) {
                System.out.println();
            }
        }
    }
}