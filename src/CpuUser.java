import java.util.Random;

// Represents a CPU-controlled player in the Minesweeper game
public class CpuUser {
    private final String name;
    private final Random rand;

    public CpuUser(String name) {
        this.name = name;
        this.rand = new Random();
    }

    public String getName() { return name; }

    // Randomly selects an unrevealed and unflagged cell
    public int[] makeMove(Board board) {
        int rows = board.getRows();
        int cols = board.getCols();
        int r, c;

        do {
            r = rand.nextInt(rows);
            c = rand.nextInt(cols);
        } while (board.isRevealed(r, c) || board.isFlagged(r, c));

        return new int[]{r, c};
    }
}