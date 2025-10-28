import java.util.Random;

// The Board class manages the Minesweeper grid.
public class Board {
    private final int rows;
    private final int cols;
    private final int totalMines;
    private final Cell[][] grid; // 2D array of Cell objects

    // Constructor: creates a board of given size with given number of mines.
    public Board(int rows, int cols, int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        grid = new Cell[rows][cols];
        initializeBoard();
    }
    // Initializes the board by creating cells,
    private void initializeBoard() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                grid[i][j] = new Cell();

        placeMines();
        calculateAdjacents();
    }
    // Randomly places mines on the board.
    private void placeMines() {
        Random rand = new Random();
        int placed = 0;
        while (placed < totalMines) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            // Only place a mine if there isn't one already
            if (!grid[r][c].hasMine()) {
                grid[r][c].setMine(true);
                placed++;
            }
        }
    }

    // Calculates how many mines surround each non-mine cell.
    private void calculateAdjacents() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (!grid[r][c].hasMine())
                    grid[r][c].setAdjacentMines(countAdjacentMines(r, c));
    }
    // Counts the number of mines around a specific cell.
    private int countAdjacentMines(int r, int c) {
        int count = 0;
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {
                int nr = r + i, nc = c + j;
                if (isInBounds(nr, nc) && grid[nr][nc].hasMine())
                    count++;
            }
        return count;
    }

    private boolean isInBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    // Reveals a cell — returns false if it’s a mine (game over), otherwise true.
    public boolean revealCell(int r, int c) {
        if (!isInBounds(r, c) || grid[r][c].isRevealed() || grid[r][c].isFlagged())
            return true; // Ignore invalid or flagged cells

        grid[r][c].reveal();

        // If the player hits a mine, game ends
        if (grid[r][c].hasMine())
            return false;

        // If no adjacent mines, recursively reveal neighbors
        if (grid[r][c].getAdjacentMines() == 0) {
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 1; j++)
                    if (i != 0 || j != 0)
                        revealCell(r + i, c + j);
        }

        return true;
    }

    // Checks if all non-mine cells are revealed
    public boolean allSafeCellsRevealed() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (!grid[r][c].hasMine() && !grid[r][c].isRevealed())
                    return false;
        return true;
    }

    public int getRevealedCount() {
        int count = 0;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (grid[r][c].isRevealed() && !grid[r][c].hasMine())
                    count++;
        return count;
    }

    public void printBoard(boolean revealAll) {
        System.out.print("   ");
        for (int c = 0; c < cols; c++) System.out.print(c + " ");
        System.out.println();

        for (int r = 0; r < rows; r++) {
            System.out.print(r + " ");
            if (r < 10) System.out.print(" "); // spacing
            for (int c = 0; c < cols; c++) {
                if (revealAll && grid[r][c].hasMine())
                    System.out.print("* ");
                else
                    System.out.print(grid[r][c] + " ");
            }
            System.out.println();
        }
    }
    // Allows toggling a flag on a specific cell.
    public void toggleFlag(int r, int c) {
        if (isInBounds(r, c) && !grid[r][c].isRevealed())
            grid[r][c].toggleFlag();
    }
    // Getter methods
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean isRevealed(int r, int c) { return isInBounds(r, c) && grid[r][c].isRevealed(); }
    public boolean isFlagged(int r, int c) { return isInBounds(r, c) && grid[r][c].isFlagged(); }
}