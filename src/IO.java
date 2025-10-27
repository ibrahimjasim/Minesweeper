import java.util.Scanner;

public class IO {

    private static final Scanner scanner = new Scanner(System.in);

    public static void println(String s) {
        System.out.println();
    }
    public static void println() {
        System.out.println();
    }
    public static String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
    public static int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }
}
