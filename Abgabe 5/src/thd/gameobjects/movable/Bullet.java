package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

import java.awt.*;


/**
 * Die Klasse zu dem Objekt Bullet.
 */
public class Bullet extends GameObject {

    private boolean flyFromLeftToRight;


    /**
     * Initializes the Bullet.
     *
     * @param gameView        gamView for GUI uses
     * @param gamePlayManager gameplay flow managing
     */
    public Bullet(GameView gameView, GamePlayManager gamePlayManager, Chopper chopper) {
        super(gameView, gamePlayManager);
        height = 2;
        width = 5;
        speedInPixel = 3;
        position.x = chopper.getPosition().x + 10;
        position.y = chopper.getPosition().y + 26;
        flyFromLeftToRight = true;

    }


    @Override
    public void updateStatus() {
        if (position.x > GameView.WIDTH || position.y > GameView.HEIGHT) {
            gamePlayManager.destroy(this);
        }
    }

    @Override
    public void changeDirectionTo(String direction) {
        if (direction.equals("left")) {
            flyFromLeftToRight = false;
        } else if (direction.equals("right")) {
            flyFromLeftToRight = true;
            position.x += 30;
        } else {
            //exception!!!
        }
    }


    /**
     * Bewegt das Objekt.
     */
    @Override
    public void updatePosition() {
        if (flyFromLeftToRight) {
            position.right(speedInPixel);
        } else {
            position.left(speedInPixel);
        }
    }


    /**
     * fügt die Grafik zu dem Objekt in GameView ein.
     */
    @Override
    public void addToCanvas() {
        //gameView.addOvalToCanvas(position.x, position.y, width, height, 5, false, Color.darkGray);
        gameView.addRectangleToCanvas(position.x, position.y, width, height, 0, true, Color.BLACK);
    }
}
