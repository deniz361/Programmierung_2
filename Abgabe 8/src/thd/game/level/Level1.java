package thd.game.level;

public class Level1 extends Level {

    Difficulty difficulty;

    public Level1(Difficulty difficulty) {
        super(difficulty);
        this.difficulty = difficulty;
        name = "Level 1";
        number = 0;
        backgroundImage = "background.png";
        numberOfEnemies();
        shotsPerSecond();
    }

    private void numberOfEnemies() {
        switch (difficulty) {
            case EASY -> numberOfEnemies = 10;
            case STANDARD -> numberOfEnemies = 20;
            case HARD -> numberOfEnemies = 30;
        }
    }

    private void shotsPerSecond() {
        switch (difficulty) {
            case EASY -> shotsPerSecond = 1;
            case STANDARD -> shotsPerSecond = 1.2;
            case HARD -> shotsPerSecond = 1.5;
        }
    }
}