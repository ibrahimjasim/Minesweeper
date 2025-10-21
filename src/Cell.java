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

    //Returns true if this cell contains a mine.
    public boolean hasMine() {
        return hasMine;
    }

    //Sets whether this cell contains a mine.
    public void setHasMine() {
        this.hasMine = hasMine;
    }

    //Returns true if the cell is revealed.
    public boolean isRevealed() {
        return revealed;
    }

    //Marks the cell as revealed
    public void reveal() {
        this.revealed = true;
    }
    // Returns true if the cell is flagged.
    public boolean isFlagged() {
        return flagged;
    }
    //Toggles the flagged state (flag <-> unflag).
    public void toggleFlag() {
        this.flagged = !this.flagged;
    }

    //Returns how many mines are adjacent to this cell.
    public int getAdjacentMines() {
        return adjacentMines;
    }

    //Sets the number of adjacent mines.
    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    //
    //Returns a character representation of the cell.
    // - F = flagged
    //- . = hidden
    //- * = mine
    //- number = number of adjacent mines
    //

    @Override
    public String toString(){
        if(flagged) return "F";
        if(revealed) return ".";
        if(hasMine) return "*";
        return (adjacentMines == 0) ? " " :  Integer.toString(adjacentMines);
    }

}

