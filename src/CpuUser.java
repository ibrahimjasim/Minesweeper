import java.util.Random;

// Represents a CPU-controlled player in the Minesweeper game
public class CpuUser {
    private String name;
    private Random rand;

}

    public String getName() {
        return name;
    }

    // The CPU randomly selects a cell that has not been revealed or flagged yet//
    public int[] makeMove(Bord board) {
        int rows = board.getRows();
        int cols = board.getCols();
        int r,c;



