// Represents a single square (cell) on the Minesweeper board.
public class Cell {
    private boolean hasMine;      // True if this cell contains a mine
    private boolean revealed;     // True if the player has revealed this cell
    private boolean flagged;      // True if the player marked this cell as a suspected mine
    private int adjacentMines;    // Number of mines in the 8 surrounding cells

    // Constructor: Initializes an empty cell with default values.
    public Cell() {
        this.hasMine = false;
        this.revealed = false;
        this.flagged = false;
        this.adjacentMines = 0;
    }

    public boolean hasMine() { return hasMine; }
    public void setMine(boolean hasMine) { this.hasMine = hasMine; }

    public boolean isRevealed() { return revealed; }
    public void reveal() { this.revealed = true; }

    public boolean isFlagged() { return flagged; }
    public void toggleFlag() { this.flagged = !this.flagged; }

    public int getAdjacentMines() { return adjacentMines; }
    public void setAdjacentMines(int adjacentMines) { this.adjacentMines = adjacentMines; }

    // Character representation of the cell
    @Override
    public String toString() {
        if (flagged) return "F";
        if (!revealed) return ".";          // Hidden cell
        if (hasMine) return "*";            // Revealed mine
        return (adjacentMines == 0) ? " " : Integer.toString(adjacentMines);
    }
}