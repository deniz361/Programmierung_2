package thd.game.level;


/**
 * Level 2.
 */
public class Level2 extends Level {
    private Difficulty difficulty;

    /**
     * All changes for level 2.
     * @param difficulty either easy, standard or hard
     */
    public Level2(Difficulty difficulty) {
        super(difficulty);
        this.difficulty = difficulty;
        name = "Level 1";
        number = 0;
        backgroundImage = "background_level2.png";
        numberOfEnemies();
        shotsPerSecond();
    }

    private void numberOfEnemies() {
        switch (difficulty) {
            case EASY: numberOfEnemies = 10;
                break;
            case STANDARD: numberOfEnemies = 20;
                break;
            case HARD: numberOfEnemies = 30;
                break;
            default:

        }
    }

    private void shotsPerSecond() {
        switch (difficulty) {
            case EASY: shotsPerSecond = 1;
                break;
            case STANDARD: shotsPerSecond = 1.2;
                break;
            case HARD: shotsPerSecond = 1.5;
                break;
            default:

        }
    }
}
