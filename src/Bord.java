//The Board class manages the Minesweeper grid.
public class Bord {
    private final int rows;
    private final int cols;
    private final int totalMines;
    private final Cell[][] grid; // 2D array of Cell objects

    //Constructor: creates a board of given size with given number of mines.
    public Bord(int rows, int cols,  int totalMines) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = totalMines;
        grid = new Cell[rows][cols];

        initializeBoard();
    }

    // Initializes the board by creating cells, placing mines, and calculating numbers.
    private void initializeBoard() {
        // Create empty cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell();
            }
        }


    }




}
