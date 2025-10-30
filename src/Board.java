import java.util.Random;

// The Board class manages the Minesweeper grid.
public class Board {
    private final int rows, cols, totalMines;
    private final Cell[][] grid; // 2D array of Cell objects

    // Constructor: creates a board of given size with given number of mines.
    public Board(int rows, int cols, int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        this.grid = new Cell[rows][cols];
        initializeBoard();
    }

    // Initializes the board by creating cells.
    private void initializeBoard() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = new Cell();

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
            for (int j = -1; j <= 1; j++)
                if (isInBounds(r + i, c + j) && grid[r + i][c + j].hasMine())
                    count++;
        return count;
    }

    private boolean isInBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    // Reveals a cell — returns true if it’s a mine (game over), otherwise false.
    public boolean revealCell(int r, int c) {
        if (!isInBounds(r, c) || grid[r][c].isRevealed() || grid[r][c].isFlagged())
            return false;

        grid[r][c].reveal();

        if (grid[r][c].hasMine()) return true;

        if (grid[r][c].getAdjacentMines() == 0)
            for (int i = -1; i <= 1; i++)
                for (int j = -1; j <= 1; j++)
                    if (i != 0 || j != 0)
                        revealCell(r + i, c + j);

        return false;
    }

    // NEW METHOD: allows flag toggling on unrevealed cells
    public void toggleFlag(int r, int c) {
        if (isInBounds(r, c) && !grid[r][c].isRevealed()) {
            grid[r][c].toggleFlag();
        }
    }

    public boolean isCleared() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (!grid[r][c].hasMine() && !grid[r][c].isRevealed())
                    return false;
        return true;
    }

    public boolean isValidCell(int r, int c) {
        return isInBounds(r, c);
    }

    public int getRevealedCount() {
        int count = 0;
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (grid[r][c].isRevealed() && !grid[r][c].hasMine())
                    count++;
        return count;
    }

    // UPDATED to show bombs (B) when revealAll == true (e.g., game over)
    public void printBoard(boolean revealAll) {
        System.out.print("   ");
        for (int c = 0; c < cols; c++) System.out.print(c + " ");
        System.out.println();

        for (int r = 0; r < rows; r++) {
            System.out.printf("%2d | ", r);
            if (r < 10) System.out.print(" ");
            for (int c = 0; c < cols; c++) {
                Cell cell = grid[r][c];
                if (revealAll && cell.hasMine()) System.out.print("B ");
                else System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    // Displays the board with last moves colored
    public void printBoardWithLastMove(int[] playerLastMove, int[] cpuLastMove) {
        System.out.print("   ");
        for (int c = 0; c < cols; c++) System.out.print(c + " ");
        System.out.println();

        for (int r = 0; r < rows; r++) {
            System.out.print(r + " ");
            if (r < 10) System.out.print(" ");
            for (int c = 0; c < cols; c++) {
                Cell cell = grid[r][c];
                String display = cell.toString();

                if (playerLastMove != null && r == playerLastMove[0] && c == playerLastMove[1])
                    display = GameController.BLUE + display + GameController.RESET;
                else if (cpuLastMove != null && r == cpuLastMove[0] && c == cpuLastMove[1])
                    display = GameController.RED + display + GameController.RESET;

                System.out.print(display + " ");
            }
            System.out.println();
        }
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
}