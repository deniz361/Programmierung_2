package thd.game.level;

/** Level 1. */
public class Level1 extends Level {

    private Difficulty difficulty;

    /**
     * Level 1.
     * @param difficulty easy, standard oder hard
     */
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
            case EASY: numberOfEnemies = 10;
                break;
            case STANDARD: numberOfEnemies = 20;
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
            default:

        }
    }
}
