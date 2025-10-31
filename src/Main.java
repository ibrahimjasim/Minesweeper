public class Main {
    static void main() {
        IO.println("====== Welcome to Minesweeper ======");
        IO.println("Created by: Ibrahim, Pinar and Micke");
        System.out.println();
        IO.waitForEnter("Press ENTER to start...");
        System.out.println();

        GameController controller = new GameController();
        controller.startGame();

        IO.waitForEnter("Press ENTER to exit...");
    }
}
