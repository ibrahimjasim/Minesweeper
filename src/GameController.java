public class GameController {
    private Player player;
    private CpuUser cpu;
    private Board board;
    private final HighScore highscore;
    private boolean isRunning;

    private int[] playerLastMove = null;
    private int[] cpuLastMove = null;

    private String playerName = null; // remember across rounds
    private String cpuName = null;    // remember CPU name across rounds

    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String RED = "\u001B[31m";

    public GameController() { this.highscore = new HighScore("highscores.txt"); }

    public void startGame() {
        boolean playAgain = true;

        while (playAgain) {
            setupNewGame();

            isRunning = true;
            while (isRunning) {
                playerTurn();
                if (!isRunning || checkGameOver()) break;

                if (cpu != null) {
                    cpuTurn();
                    if (!isRunning || checkGameOver()) break;
                }
            }

            endGame();

            String response = IO.readString("Do you want to play another round? (Y/N): ");
            playAgain = response.equalsIgnoreCase("Y");
        }
        System.out.println();
        IO.println("Thanks for playing! Goodbye.");
        System.out.println();
    }

    private void setupNewGame() {
        if (playerName == null) {
            playerName = IO.readString("Enter your name: ");
        }
        this.player = new Player(playerName);
        System.out.println();
        IO.println("Hello " + playerName + "! Let's play Minesweeper!");
        System.out.println();

        IO.println("Select difficulty:");
        IO.println("1. Easy (8x8, 8 mines)");
        IO.println("2. Medium (10x10, 15 mines)");
        IO.println("3. Hard (12x12, 25 mines)");

        int diffChoice = IO.readInt("Choose difficulty: ");
        System.out.println();
        int rows=10, cols=10, mines=15;

//        switch(diffChoice) {
//            case 1 -> { rows=8; cols=8; mines=8; }
//            case 2 -> { rows=10; cols=10; mines=15; }
//            case 3 -> { rows=12; cols=12; mines=25; }
//        }

        this.board = new Board(rows, cols, mines);

        System.out.println();
        IO.println("Select Game Mode:");
        IO.println("1. Single Player");
        IO.println("2. Player vs CPU");
        System.out.println();
        int mode = IO.readInt("Choose mode: ");
        System.out.println();
        if (mode == 2) {
            if (cpuName == null) {
                cpuName = IO.readString("Enter CPU name (or press Enter for 'CPU'): ");
                System.out.println();
                if (cpuName.isEmpty()) cpuName = "CPU";
            }
            this.cpu = new CpuUser(cpuName);
        } else {
            cpu = null;
        }

        playerLastMove = null;
        cpuLastMove = null;

        board.printBoard(false);
    }

    private void playerTurn() {
        IO.println("\n" + BLUE + player.getName() + "'s turn!" + RESET);
        int row, col;
        while (true) {
            row = IO.readInt("Enter row: ");
            col = IO.readInt("Enter column: ");
            if (board.isValidCell(row, col)) break;
            IO.println("Invalid coordinates! Try again.");
        }

        boolean hitMine = board.revealCell(row, col);
        playerLastMove = new int[]{row, col};

        if (hitMine) {
            IO.println(BLUE + "Boom! You hit a mine!" + RESET);
            System.out.println();
            isRunning = false;
            board.printBoard(true);
        } else {
            IO.println(BLUE + "Safe move!" + RESET);
            System.out.println();
            board.printBoardWithLastMove(playerLastMove, cpuLastMove);
        }
    }

    private void cpuTurn() {
        IO.println("\n" + RED + cpu.getName() + "'s turn!" + RESET);
        int[] move = cpu.makeMove(board);
        cpuLastMove = move;

        IO.println(RED + cpu.getName() + " chooses (" + move[0] + ", " + move[1] + ")" + RESET);

        boolean hitMine = board.revealCell(move[0], move[1]);
        if (hitMine) {
            IO.println(RED + cpu.getName() + " hit a mine!" + RESET);
            System.out.println();
            isRunning = false;
            board.printBoard(true);
        } else {
            IO.println(RED + cpu.getName() + " made a safe move!" + RESET);
            System.out.println();
            board.printBoardWithLastMove(playerLastMove, cpuLastMove);
        }
    }

    private boolean checkGameOver() {
        if (board.isCleared()) {
            IO.println("All safe squares revealed! You win!");
            isRunning = false;
            return true;
        }
        return !isRunning;
    }

    private void endGame() {
        IO.println("\n====== GAME OVER ======");
        System.out.println();
        board.printBoard(true);

        int playerScore = board.getRevealedCount();
        IO.println(player.getName() + "'s score: " + playerScore);
        System.out.println();
        highscore.saveScore(player.getName(), playerScore);

        if (cpu != null) {
            int cpuScore = (int)(Math.random()*((double) (board.getRows() * board.getCols()) /2));
            IO.println(cpu.getName() + "'s score: " + cpuScore);
            highscore.saveScore(cpu.getName(), cpuScore);

            if (cpuScore>playerScore) IO.println(cpu.getName() + " wins!");
            else if (cpuScore<playerScore) IO.println(player.getName() + " wins!");
            else IO.println("It's a tie!");
        }

        highscore.displayScores();
        IO.println("=========================");
    }
}