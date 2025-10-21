//Represents a single square (cell) on the Minesweeper board.
public class Cell {
    private boolean hasMine; // True if this cell contains a mine
    private boolean revealed; // True if the player has revealed this cell
    private boolean flagged;  // True if the player marked this cell as a suspected mine
    private int adjacentMines;  // Number of mines in the 8 surrounding cells


    //Constructor: Initializes an empty cell with default values.
    public Cell() {
        this.hasMine = false;
        this.revealed = false;
        this.flagged = false;
        this.adjacentMines = 0;
    }
}

