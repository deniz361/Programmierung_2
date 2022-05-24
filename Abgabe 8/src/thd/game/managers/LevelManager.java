package thd.game.managers;

import thd.game.level.Level;
import thd.game.level.Level.*;
import thd.game.level.Level1;
import thd.game.level.Level2;
import thd.game.utilities.NoMoreLevelAvailableException;

import java.util.LinkedList;


/**
 * Manages the different Levels
 */
public class LevelManager {
    protected final LinkedList<Level> levels;
    protected int currentLevel;

    LevelManager(Difficulty difficulty) {
        levels = new LinkedList<>();
        levels.add(new Level1(difficulty));
        levels.add(new Level2(difficulty));
        currentLevel = 0;

    }


    /**
     * Checks, if there is another level
     */
    boolean hasNextLevel() {
        if (levels.size() - 1 < currentLevel) {
            resetLevelCounter();
        }
        return true;

    }

    Level nextLevel() {
        if (hasNextLevel()) {
            currentLevel++;
            return levels.get(currentLevel - 1);
        } else {
            throw new NoMoreLevelAvailableException("There are no more Levels");
        }
    }

    void resetLevelCounter() {
        currentLevel = 0;
    }


}
