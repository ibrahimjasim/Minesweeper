public class GameController {
    private Board board;
    private Player player;
    private CpuUser cpu;
    private boolean isRunning;
    private HighScore highscore;

    public GameController() {
        this.board= new Board(10,10,10);
        this.player = new Player ("Player 1");
        this.cpu = new CpuUser("CPU");
        this.isRunning = true;
        this.highscore = new HighScore();
    }

    public void startGame() {
        IO.println("Welcome to Minesweeper!");
        board.printBoard(false);

        while (isRunning) {
            playerTurn();
            if (checkGameOver()) break;

            cpuTurn();
            if (checkGameOver()) break;
        }

        endGame();
    }
//Check if code works with r and c

    private void playerTurn() {
        IO.println(String.format("Players turn!" , player.getName()));
        int row = IO.readInt ("Choose Row: ");
        int col = IO.readInt ("Choose Colon: ");

        if (board.revealCell(row, col)) {
            IO.println("BOOM, the bomb exploded!");
            isRunning=false;
        } else {
            IO.println("SAFE SQUARE!");
        }
        board.printBoard(false);
    }

    private void cpuTurn() {
        IO.println("CPU is playing..");
        int[] move =cpu.makeMove(board);

        IO.println(String.format("CPU choosed row and colon", move[0],move[1]));
        if (board.revealCell(move[0], move [1])) {
            IO.println("CPU got BOMBED!");
            isRunning = false;
        } else {
            IO.println("CPU hits a safe square!");
        }
    }

    private boolean checkGameOver() {
       if (board.allSafeCellsRevealed()) {
           IO.println("All the safe squares are founden, You Win! ");
           isRunning = false;
           return true;
       }
       return !isRunning;
    }
    private void endGame() {
        IO.println("Game Over!");
        highScore.saveScore(player.getName(),
        board.getRevealedCount());
    }

 }
