package thd.game.managers;

import thd.game.level.Level;
import thd.game.level.Level1;
import thd.game.level.Level2;
import thd.game.utilities.NoMoreLevelsAvailableException;

import java.util.LinkedList;


/**
 * Manages the different Levels.
 */
public class LevelManager {
    LinkedList<Level> levels;

    private int currentLevel;

    /** Der Konstruktor.
     * @param difficulty Der Schwierigkeitsgrat, der das Spiel haben soll
     */
    public LevelManager(Level.Difficulty difficulty) {
        levels = new LinkedList<>();
        levels.add(new Level1(difficulty));
        levels.add(new Level2(difficulty));
        currentLevel = 1;

    }


    /**
     * Checks, if there is another level.
     * @return gibt true zurück, wenn es ein weiteres Level gibt. Gibt false zurück,
     * wenn es kein neues Level gibt
     */
    private boolean hasNextLevel() {
        return levels.size() > currentLevel;


    }

    Level nextLevel() {
        if (hasNextLevel()) {
            currentLevel++;
            return levels.get(currentLevel - 1);
        } else {
            throw new NoMoreLevelsAvailableException("There are no more Levels");
        }
    }

    void resetLevelCounter() {
        currentLevel = 1;
    }


}
