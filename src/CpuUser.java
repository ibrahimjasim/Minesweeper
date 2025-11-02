import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents a CPU-controlled player in the Minesweeper game
public class CpuUser {
    private final String name;
    private final Random rand;

    public CpuUser(String name) {
        this.name = name;
        this.rand = new Random();
    }

    public String getName() {
        return name;
    }

    // CPU selects a random unrevealed cell
    public int[] makeMove(Board board) {
        int rows = board.getRows();
        int cols = board.getCols();
        List<int[]> available = new ArrayList<>();

        for (int r=0;r<rows;r++) {
            for (int c=0;c<cols;c++) {
                if (board.isValidCell(r, c) && !board.isCleared()) {
                    available.add(new int[]{r, c});
                }
            }
        }

        if (available.isEmpty()) {
            return new int[]{rand.nextInt(rows), rand.nextInt(cols)};
        }

        return available.get(rand.nextInt(available.size()));
    }

    public void addScore() {

    }
}