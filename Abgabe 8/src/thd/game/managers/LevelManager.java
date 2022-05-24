package thd.game.managers;

import thd.game.level.Level;
import thd.game.level.Level.*;
import thd.game.level.Level1;

import java.security.DigestInputStream;
import java.util.LinkedList;

public class LevelManager {

    public static void main(String[] args) {
        LevelManager levelManager = new LevelManager(Difficulty.EASY);
        levelManager.hasNextLevel();
    }

    private final LinkedList<Level> levels;
    LevelManager(Difficulty difficulty) {
        levels = new LinkedList<>();
        levels.add(new Level1(difficulty,"level 1",1,"",10));
    }

    void hasNextLevel() {
        System.out.println();
    }
}
