public class GameController {
    private Board board;
    private Player player;
    private CpuUser cpu;
    private boolean isRunning;
    private HighScore highscore;

    public GameController() {
        this.highscore = new HighScore();
    }

    public void startGame() {
        boolean exit = false;

        while (!exit) {
            IO.println("******Minesweeper menu******");
            IO.println("1. Start New Game");
            IO.println("2. Show Highscore");
            IO.println("3. Exit");

            int choice = IO.readInt("Choose an option: ");

            switch (choice) {
                case 1 -> startNewGame();
                case 2 -> highscore.showScores();
                case 3 -> {
                    IO.println("Exiting game...Goodbye!");
                    exit = true;
                }
                default -> IO.println("Invalid choice, please try again!");
            }
        }
    }

    private void startNewGame() {
        IO.println("Starting new game...");

        this.board = new Board(10,10,10);
    }

//    public GameController() {
//        this.board= new Board(10,10,10);
//        this.player = new Player ("Player 1");
//        this.cpu = new CpuUser("CPU");
//        this.isRunning = true;
//        this.highscore = new HighScore();
//    }

//    public void startGame() {
//        IO.println("Welcome to Minesweeper!");
//        board.printBoard(false);
//
//        while (isRunning) {
//            playerTurn();
//            if (checkGameOver()) break;
//
//            cpuTurn();
//            if (checkGameOver()) break;
//        }
//
//        endGame();
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
