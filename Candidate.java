import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Candidate {
    private Set<Integer> numbers;

    public Candidate() {
        numbers = new HashSet<>();
    }

    public void fillAllNumber(){
        for (int i = 1; i <=NumberRecorder.MAX_NUMBER ; i++) {
            numbers.add(i);
        }
    }

    public void add(int num) {
        numbers.add(num);
    }

    public int size() {
        return numbers.size();
    }

    public void remove(int num) {
        numbers.remove(num);
    }

    public boolean isDefinitive(){
        return size()==1;
    }

    public int getNumber(){
        return  (int)numbers.toArray()[0];
    }

}
