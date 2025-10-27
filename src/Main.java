public class Main {

    static void main(String[] args) {
        IO.println("====== Welcome to the Minesweeper ======");
        IO.println("Created by: Ibrahim, Pinar and Micke");
        IO.println("Press Enter to start...");
        IO.readString("");

        GameController controller = new GameController();
        controller.startGame();

        IO.println("====== Thanks for playing! ======");
    }
}