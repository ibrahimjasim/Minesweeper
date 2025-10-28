public class Cell {
    private boolean hasMine;
    private boolean revealed;
    private boolean flagged;
    private int adjacentMines;

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

    @Override
    public String toString() {
        if(flagged) return "F";
        if(!revealed) return "."; // Hidden cell
        if(hasMine) return "B"; //Revealed mine
        return (adjacentMines == 0) ? " " : Integer.toString(adjacentMines);
    }
}