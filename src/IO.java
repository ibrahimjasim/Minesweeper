import java.util.Scanner;

// Utility class for console input/output
public class IO {
    private static final Scanner scanner = new Scanner(System.in);

    // Prints a line of text
    public static void println(String text) {
        System.out.println(text);
    }

    // Prints text without a newline
    public static void print(String text) {
        System.out.print(text);
    }
    // Reads an integer from the console with validation
    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid number. Please enter a valid integer.");
            }
        }
    }
    // Reads a string from the console (trims extra whitespace)
    public static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}