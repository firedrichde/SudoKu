import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberRecorder {
    public static final int MAX_NUMBER = 9;
    private Map<Integer, Integer> numberCountMap;

    public NumberRecorder() {
        numberCountMap = new HashMap<>();
        for (int i = 1; i <= MAX_NUMBER; i++) {
            numberCountMap.put(i, 0);
        }
    }

    public void increment(int number) {
        int count = numberCountMap.get(number);
        numberCountMap.put(number, count + 1);
    }

    public List<Integer> sortByCount() {
        int[] tmpArray = new int[]{1,2,3,4,5,6,7,8,9};
        for (int i = 0; i < tmpArray.length; i++) {
            int max = numberCountMap.get(tmpArray[i]);
            int index = i;
            for (int j = i; j <tmpArray.length; j++) {
                if (numberCountMap.get(tmpArray[j])>max){
                    max = numberCountMap.get(tmpArray[j]);
                    index = j;
                }
            }
            int tmp = tmpArray[i];
            tmpArray[i] = tmpArray[index];
            tmpArray[index] = tmp;
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < tmpArray.length; i++) {
            result.add(tmpArray[i]);
        }
        return result;
    }
}
