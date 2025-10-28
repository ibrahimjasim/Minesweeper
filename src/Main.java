public class Main {

    public static void main(String[] args) {  // <-- Must be public
        IO.println("====== Welcome to Minesweeper ======");
        IO.println("Created by: Ibrahim, Pinar, and Micke");
        IO.println("Press Enter to start...");
        IO.readString("");

        GameController controller = new GameController();
        controller.startGame();

        IO.println("====== Thanks for playing! ======");
    }
}