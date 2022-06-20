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


    /** Der verschiedenen Schwierigkeitsgräte. */
    public enum Difficulty {EASY, STANDARD, HARD}


    /**
     * Der Konstruktor.
     * @param difficulty der Schwierigkeitsgrat, der übergeben wird.
     */
    Level(Difficulty difficulty) {

    }

}
