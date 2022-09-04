package thd.game.level;


/**
 * Die Oberklasse der Level.
 */
public abstract class Level {

    protected String name;

    protected int number;

    //Schwierigkeitsgrad
    protected int numberOfEnemies;
    //protected int speedOfEnemies;
    protected double shotsPerSecond;

    //Design
    protected String backgroundImage;

    protected Difficulty difficulty;


    /** Der verschiedenen Schwierigkeitsgräte. */
    public enum Difficulty {EASY, STANDARD}


    Level() {

    }

}
