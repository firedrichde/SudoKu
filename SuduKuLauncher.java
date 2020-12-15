import java.util.Arrays;

public class SuduKuLauncher {


    public static void main(String[] args) {
        String[] data = new String[81];
        String[] line1 = new String[]{".",".",".","4",".",".",".",".","."};
        String[] line2 = new String[]{".","4","2",".","1",".","7","8","."};
        String[] line3 = new String[]{"1","9","7","6",".","3",".","5","."};
        String[] line4 = new String[]{".","7",".",".",".",".",".",".","8"};
        String[] line5 = new String[]{"8",".",".","3",".",".",".","2","."};
        String[] line6 = new String[]{".",".","6",".",".","1",".","4","3"};
        String[] line7 = new String[]{".",".","9",".","6","4","8",".","."};
        String[] line8 = new String[]{".",".",".",".","9",".",".",".","1"};
        String[] line9 = new String[]{"6",".",".",".",".",".","9",".","4"};
        int index=0;
        for (int i = 0; i < line1.length; i++) {
            data[index] = line1[i];
            index+=1;
        }
        for (int i = 0; i < line2.length; i++) {
            data[index] = line2[i];
            index+=1;
        }
        for (int i = 0; i < line3.length; i++) {
            data[index] = line3[i];
            index+=1;
        }
        for (int i = 0; i < line4.length; i++) {
            data[index] = line4[i];
            index+=1;
        }
        for (int i = 0; i < line5.length; i++) {
            data[index] = line5[i];
            index+=1;
        }
        for (int i = 0; i < line6.length; i++) {
            data[index] = line6[i];
            index+=1;
        }
        for (int i = 0; i < line7.length; i++) {
            data[index] = line7[i];
            index+=1;
        }
        for (int i = 0; i < line8.length; i++) {
            data[index] = line8[i];
            index+=1;
        }
        for (int i = 0; i < line9.length; i++) {
            data[index] = line9[i];
            index+=1;
        }
        assert index==data.length;
        Board board = new Board();
        board.layout(Arrays.asList(data));
        board.scan();
        board.print();
    }
}
