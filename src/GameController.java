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
            IO.println("****** Minesweeper menu ******");
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
        IO.println("******Select difficulty******");
        IO.println("1. Easy (8x8, 8 mines)");
        IO.println("2. Medium (10x10, 15 mines)");
        IO.println("3. Hard (12x12, 25 mines)");

        int diffChoice = IO.readInt("Choose difficulty: ");
        int rows = 10;
        int cols = 10;
        int mines = 10;

        //Added diffChoice instead of choice
        switch (diffChoice) {
            case 1 -> {
                rows = 8;
                cols = 8;
                mines = 8;
            }
            case 2 -> {
                rows = 10;
                cols = 10;
                mines = 15;
            }
            case 3 -> {
                rows = 12;
                cols = 12;
                mines = 25;
            }
            default -> IO.println("Invalid choice, defaulting to Medium.");
        }

        this.board = new Board(rows, cols, mines);
        this.isRunning = true;

//        Game Mode
        IO.println(" Select Game Mode:");
        IO.println("1. Single Player ");
        IO.println("2. Player vs CPU");

        int mode = IO.readInt("Choose mode: ");

        switch (mode) {
            case 1 -> startSinglePlayer();
            case 2 -> startPlayerVsCpu();
            default -> {
                IO.println("Invalid choice, starting Single Player as default.");
                startSinglePlayer();
            }
        }
    }

//        Single Player
        private void startSinglePlayer() {
            this.player = new Player ("Player 1");

            IO.println(String.format("Starting Single Player Game: %s", player.getName()));
            board.printBoard(false);

            while (isRunning) {
                playerTurn();
                if (checkGameOver()) break;
            }
            endGame();
        }

//        Player Vs CPU
        private void startPlayerVsCpu() {
            this.player = new Player ("player 1");
            this.cpu = new CpuUser("CPU");

            IO.println(String.format("Starting Player vs CPU" ));
            board.printBoard(false);

            while (isRunning) {
                playerTurn();
                if (checkGameOver()) break;

                cpuTurn();
                if (checkGameOver()) break;
            }
            endGame();
        }


//Check if code works with r and c from board.java

    private void playerTurn() {
        IO.println(String.format("%s's turn!",player.getName()));
        int row = IO.readInt("Choose Row: ");
        int col = IO.readInt("Choose Column: ");

        if (board.revealCell(row, col)) {
            IO.println("BOOM, the bomb exploded!");
            isRunning = false;
        } else {
            IO.println("SAFE SQUARE!");
        }
        board.printBoard(false);
    }
//makeMove
    private void cpuTurn() {
        IO.println("CPU is playing..");
        int[] move =cpu.makeMove(board);

        IO.println(String.format("CPU choosed row %d and colum %d", move[0],move[1]));
        if (board.revealCell(move[0], move[1])) {
            IO.println("CPU got BOMBED!");
            isRunning = false;
        } else {
            IO.println("CPU hits a safe square!");
        }
    }

    private boolean checkGameOver() {
       if (board.allSafeCellsRevealed()) {
           IO.println("All the safe squares are found, You Win! ");
           isRunning = false;
           return true;
       }
       return !isRunning;
    }
    private void endGame() {
        IO.println("Game Over!");
        highscore.saveScore(player.getName(),
        board.getRevealedCount());
    }

 }
