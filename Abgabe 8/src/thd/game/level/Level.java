package thd.game.level;

public abstract class Level {

    protected String name;
    protected int number;

    //Schwierigkeitsgrad
    protected int numberOfEnemies;
    protected int speedOfEnemies;
    protected double shotsPerSecond;

    //Design
    protected String backgroundImage;

    public enum Difficulty {EASY, STANDARD, HARD}

    Level(Difficulty difficulty) {

    }

}
