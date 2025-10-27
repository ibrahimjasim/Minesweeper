import java.io.*;
import java.util.*;

public class HighScore {

    private static final String FILE_NAME = "highscores.txt";

//    Save points to file
    public void saveScore(String playerName, int score) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(playerName + ";" + score + "\n");
            System.out.println("Score saved for" + playerName + ": " + score);

        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }

    }

//    Shows highscore
    public void showScores() {
        List<ScoreEntry> scores = loadScores();

        if (scores.isEmpty()) {
            System.out.println("No highscore yet - play a game first!");
            return;
        }

//        Sorts highest first
        scores.sort ((a, b) -> Integer.compare(b.score, a.score));

        System.out.println("******HIGHSCORES******");
        int rank = 1;

        for (ScoreEntry entry : scores) {
            System.out.println("%2d. %-15s %5d\n",rank++, entry.name, entry.score);
        }
        System.out.println("======================");
    }

//    Reads in all scores from file

    private List<ScoreEntry> loadScores() {
        List<ScoreEntry> scores = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return scores;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) !=null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    try{
                        String name = parts[0];
                        int score = Integer.parseInt(parts[1]);
                        scores.add(new ScoreEntry(name, score));
                    } catch (NumberFormatException e) {

                    }
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR reading highscore file: " + e.getMessage());
        }
        return scores;
    }
//    Intern class for scorepost
    private static class ScoreEntry {
        String name;
        int score;

        ScoreEntry(String name, int score) {
            this.name = name;
            this.score = score;
        }
}

}
