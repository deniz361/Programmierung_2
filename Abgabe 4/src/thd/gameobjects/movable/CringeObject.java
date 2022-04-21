package thd.gameobjects.movable;

import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

import java.awt.*;
import java.util.Random;

public class CringeObject extends GameObject {

    private Random random;
    private double randomNumber;
    public CringeObject(GameView gameView) {
        super(gameView);
        speedInPixel = 2;
        size = 10;
        random = new Random();
    }

    @Override
    public void addToCanvas() {
        gameView.addOvalToCanvas(300, 200, size, size, 3, false, Color.BLACK);
    }

    @Override
    public void updatePosition() {

        randomNumber = random.nextInt(100);

        if(randomNumber < 25) {
            position.left();
        } else if (randomNumber >= 25 && randomNumber < 75) {
            position.right();
        } else {
            position.down();
        }
    }
}
