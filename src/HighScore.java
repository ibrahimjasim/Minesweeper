import java.io.*;
import java.util.*;

public class HighScore {
    private final String fileName;
    private static final int MAX_ENTRIES = 10;

    public HighScore(String fileName) {
        this.fileName = fileName;
    }

    public void saveScore(String playerName, int score) {
        List<ScoreEntry> scores = loadScores();


        scores.add(new ScoreEntry(playerName, score));


        scores.sort((a, b) -> Integer.compare(b.score, a.score));


        if (scores.size() > MAX_ENTRIES)
            scores = scores.subList(0, MAX_ENTRIES);


        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            for (ScoreEntry entry : scores)
                writer.println(entry.name + ";" + entry.score);
        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }

    public void displayScores() {
        List<ScoreEntry> scores = loadScores();
        if (scores.isEmpty()) {
            System.out.println("No scores yet.");
            return;
        }

        System.out.println();
        System.out.println(" ====== TOP 10 HIGHSCORES ======");
        int rank = 1;
        for (ScoreEntry entry : scores) {
            System.out.printf("%2d. %-15s %5d%n", rank++, entry.name, entry.score);
        }
        System.out.println("================================");
    }

    private List<ScoreEntry> loadScores() {
        List<ScoreEntry> scores = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) return scores;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    try {
                        scores.add(new ScoreEntry(parts[0], Integer.parseInt(parts[1])));
                    } catch (NumberFormatException ignored) { }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading scores: " + e.getMessage());
        }

        scores.sort((a, b) -> Integer.compare(b.score, a.score));
        if (scores.size() > MAX_ENTRIES)
            scores = scores.subList(0, MAX_ENTRIES);
        return scores;
    }

    private static class ScoreEntry {
        String name;
        int score;
        ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}