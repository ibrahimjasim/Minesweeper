import java.util.Random;

public class Board {
    private final int rows, cols, totalMines;
    private final Cell[][] grid;

    public Board(int rows, int cols, int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        this.grid = new Cell[rows][cols];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = new Cell();

        placeMines();
        calculateAdjacents();
    }

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

    private void calculateAdjacents() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (!grid[r][c].hasMine())
                    grid[r][c].setAdjacentMines(countAdjacentMines(r, c));
    }

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

    public boolean isCellRevealed(int r, int c) {
        return isInBounds(r, c) && grid[r][c].isRevealed();
    }

    // FIXED: spacing and visual alignment
    public void printBoard(boolean revealAll) {
        System.out.print("     ");
        for (int c = 0; c < cols; c++) System.out.printf("%2d ", c);
        System.out.println();

        for (int r = 0; r < rows; r++) {
            System.out.printf("%3d | ", r);
            for (int c = 0; c < cols; c++) {
                Cell cell = grid[r][c];
                if (revealAll && cell.hasMine())
                    System.out.print("B  ");
                else
                    System.out.print(cell + "  ");
            }
            System.out.println();
        }
        System.out.println(); // extra line for clean bottom spacing
    }

    // FIXED: alignment and color consistency
    public void printBoardWithLastMove(int[] playerLastMove, int[] cpuLastMove) {
        System.out.print("     ");
        for (int c = 0; c < cols; c++) System.out.printf("%2d ", c);
        System.out.println();

        for (int r = 0; r < rows; r++) {
            System.out.printf("%3d | ", r);
            for (int c = 0; c < cols; c++) {
                Cell cell = grid[r][c];
                String display = cell.toString();

                if (playerLastMove != null && r == playerLastMove[0] && c == playerLastMove[1])
                    display = GameController.BLUE + display + GameController.RESET;
                else if (cpuLastMove != null && r == cpuLastMove[0] && c == cpuLastMove[1])
                    display = GameController.RED + display + GameController.RESET;

                System.out.print(display + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getRows() { return rows; }
    public int getCols() { return cols; }
// Added winning statement
    public boolean allMinesCorrectlyFlagged() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = grid[r][c];
                if (cell.hasMine() && !cell.isFlagged()) return false; // a mine not flagged
                if (!cell.hasMine() && cell.isFlagged()) return false; // a safe cell incorrectly flagged
            }
        }
        return true;
    }
}