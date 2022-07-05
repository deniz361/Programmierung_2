package thd.game.managers;

import thd.game.level.Level;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Der FileManager.
 */
public final class FileManager {
    private static final Path PATH = Path.of(System.getProperty("user.home"));
    private static final Path TEXT_FILE = PATH.resolve("wichtelgame.txt");


    static void writeDifficultyToDisc(Level.Difficulty difficulty) {
        try {
            Files.writeString(TEXT_FILE, difficulty.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Die Datei wichtelgame.txt konnte nicht gefunden werden.");
        }
    }

    static Level.Difficulty readDifficultyFromDisc() {
        try {
            String difficulty = Files.readString(TEXT_FILE, StandardCharsets.UTF_8);
            switch (difficulty) {
                case "EASY":
                    return Level.Difficulty.EASY;
                case "STANDARD":
                    return Level.Difficulty.STANDARD;
                default:
                    break;
            }
        } catch (IOException e) {
            System.err.println("Die Datei wichtelgame.txt konnte nicht gefunden werden.");
        }
        return Level.Difficulty.STANDARD;
    }

}
