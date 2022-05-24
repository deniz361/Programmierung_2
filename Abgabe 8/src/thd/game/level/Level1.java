package thd.game.level;

public class Level1 extends Level {


    public Level1(Difficulty difficulty, String name, int number, String backgroundImage, int numberOfEnemies) {
        super(difficulty);
        this.name = name;
        this.number = number;
        this.backgroundImage = backgroundImage;
        this.numberOfEnemies = numberOfEnemies;
    }
}
