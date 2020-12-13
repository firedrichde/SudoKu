import java.util.HashSet;
import java.util.Set;

public class NumberUtil {
    public static final Set<Integer> DOMAIN = new HashSet<>();
    static {
        for (int i = 0; i <= NumberRecorder.MAX_NUMBER; i++) {
            DOMAIN.add(i);
        }
    }
    public static Set<Integer> complement(Set<Integer> set){
        Set<Integer> result = new HashSet<>();
        for (Integer number:
             set) {
            if (!DOMAIN.contains(number)){
                result.add(number);
            }
        }
        return result;
    }
}
