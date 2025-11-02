public class GameController {
    private Player player;
    private CpuUser cpu;
    private Board board;
    private final HighScore highscore;
    private boolean isRunning;

    private int[] playerLastMove = null;
    private int[] cpuLastMove = null;

    private String playerName = null;
    private String cpuName = null;

    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String RED = "\u001B[31m";

    public GameController() {
        this.highscore = new HighScore("highscores.txt");
    }

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
        if (playerName == null) playerName = IO.readString("Enter your name: ");
        player = new Player(playerName);
        System.out.println();
        IO.println("Hello " + playerName + "! Let's play Minesweeper!");
        System.out.println();

        IO.println("Select difficulty:");
        IO.println("1. Easy (9x9, 8 mines)");
        IO.println("2. Medium (18x18, 15 mines)");
        IO.println("3. Hard (27x27, 25 mines)");
        System.out.println();

        int diffChoice = IO.readInt("Choose difficulty: ");
        System.out.println();

        int rows, cols, mines;
        switch (diffChoice) {
            case 1 -> { rows = 10; cols = 10; mines = 8; }
            case 2 -> { rows = 19; cols = 19; mines = 15; }
            case 3 -> { rows = 28; cols = 28; mines = 25; }
            default -> { IO.println("Invalid choice! Defaulting to Easy."); rows = 10; cols = 10; mines = 8; }
        }

        System.out.println();
        IO.println("Creating board of size " + rows + "x" + cols + " with " + mines + " mines.");
        System.out.println();

        board = new Board(rows, cols, mines);

        IO.println("Select Game Mode:");
        IO.println("1. Single Player");
        IO.println("2. Player vs CPU");
        System.out.println();
        int mode = IO.readInt("Choose mode: ");
        System.out.println();

        if (mode == 2) {
            if (cpuName == null) {
                cpuName = IO.readString("Enter CPU name (or press Enter for 'CPU'): ");
                if (cpuName.isEmpty()) cpuName = "CPU";
            }
            cpu = new CpuUser(cpuName);
        } else {
            cpu = null;
        }

        playerLastMove = null;
        cpuLastMove = null;
        board.printBoard(false);
    }

    private void playerTurn() {
        IO.println("\n" + BLUE + player.getName() + "'s turn!" + RESET);
        IO.println("💡 Tip: 'R row col' = reveal, 'F row col' = flag\n");

        String input = IO.readString("Enter your move (R/F row col): ").trim().toUpperCase();
        String[] parts = input.split("\\s+");

        if (parts.length != 3) { IO.println("Invalid input! Use 'R row col' or 'F row col'."); return; }

        String action = parts[0];
        int row, col;
        try { row = Integer.parseInt(parts[1]); col = Integer.parseInt(parts[2]); }
        catch (NumberFormatException e) { IO.println("Row and column must be numbers!"); return; }

        if (!board.isValidCell(row, col)) { IO.println("Invalid coordinates! Try again."); return; }

        if (action.equals("F")) {
            board.toggleFlag(row, col);
            IO.println(BLUE + "Flag toggled at (" + row + ", " + col + ")" + RESET);
            board.printBoardWithLastMove(playerLastMove, cpuLastMove);
            return;
        }

        if (action.equals("R")) {
            if (board.isCellRevealed(row, col)) {
                IO.println(BLUE + "You already revealed this cell! Try a different one." + RESET);
                return;
            }

            boolean hitMine = board.revealCell(row, col);
            playerLastMove = new int[]{row, col};

            if (hitMine) {
                IO.println(BLUE + "Boom! You hit a mine!" + RESET);
                isRunning = false;
                board.printBoard(true);
            } else {
                player.addScore(); // +1 per safe revealed cell
                IO.println(BLUE + "Safe move!" + RESET);
                board.printBoardWithLastMove(playerLastMove, cpuLastMove);
            }
            return;
        }

        IO.println("Invalid action! Use 'R' or 'F'.");
    }

    private void cpuTurn() {
        IO.println("\n" + RED + cpu.getName() + "'s turn!" + RESET);
        int[] move = cpu.makeMove(board);
        cpuLastMove = move;

        IO.println(RED + cpu.getName() + " chooses (" + move[0] + ", " + move[1] + ")" + RESET);

        boolean hitMine = board.revealCell(move[0], move[1]);
        if (hitMine) {
            IO.println(RED + cpu.getName() + " hit a mine!" + RESET);
            isRunning = false;
            board.printBoard(true);
        } else {
            cpu.addScore(); // +1 safe revealed cell for cpu
            IO.println(RED + cpu.getName() + " made a safe move!" + RESET);
            board.printBoardWithLastMove(playerLastMove, cpuLastMove);
        }
    }

    private boolean checkGameOver() {
        if (board.isCleared() || board.allMinesCorrectlyFlagged()) {
            IO.println("All safe squares revealed! You win!");
            isRunning = false;
            return true;
        }
        return !isRunning;
    }

    private void endGame() {
        String border = "=".repeat(50);
        System.out.println("\n" + border);

        String centerText = "======== GAME OVER ========";
        int padding = (50 - centerText.length()) / 2;
        System.out.println(" ".repeat(padding) + centerText + "\n");

        board.printBoard(true);

        int playerScore = board.getRevealedCount();
        System.out.println("\n" + " ".repeat(20) + player.getName() + "'s score: " + playerScore);
        highscore.saveScore(player.getName(), playerScore);

        if (cpu != null) {
            int cpuScore = (int)(Math.random() * ((double)(board.getRows() * board.getCols()) / 2));
            System.out.println(" ".repeat(20) + cpu.getName() + "'s score: " + cpuScore);
            highscore.saveScore(cpu.getName(), cpuScore);

            if (cpuScore > playerScore) System.out.println(" ".repeat(20) + cpu.getName() + " wins!");
            else if (cpuScore < playerScore) System.out.println(" ".repeat(20) + player.getName() + " wins!");
            else System.out.println(" ".repeat(20) + "It's a tie!");
        }

        highscore.displayScores();
        System.out.println(border);
    }
}