import java.io.*;
import java.util.*;

public class HighScore {
    private final String fileName;

    public HighScore(String fileName) { this.fileName = fileName; }

    public void saveScore(String playerName, int score) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(playerName + ";" + score + "\n");
        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }

    public void displayScores() {
        List<ScoreEntry> scores = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) { System.out.println("No scores yet."); return; }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) scores.add(new ScoreEntry(parts[0], Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            System.out.println("Error reading scores: " + e.getMessage());
        }

        scores.sort((a,b) -> Integer.compare(b.score, a.score));
        System.out.println();
        System.out.println(" ====== HIGHSCORES ======");
        int rank = 1;
        for (ScoreEntry entry : scores)
            System.out.printf("%2d. %-15s %5d%n", rank++, entry.name, entry.score);
        System.out.println("================================");
    }

    private static class ScoreEntry {
        String name; int score;
        ScoreEntry(String name, int score) { this.name = name; this.score = score; }
    }
}