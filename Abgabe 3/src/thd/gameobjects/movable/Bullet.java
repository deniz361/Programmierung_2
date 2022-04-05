package thd.gameobjects.movable;

import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameview.GameView;

import java.awt.*;


/** Die Klasse zu dem Objekt Bullet. */
public class Bullet extends GameObject {


    //private final Position position;
    //private double speedInPixel;
    //private final GameView gameView;
    //private double height;
    //private double width;
    private boolean flyFromLeftToRight;


    /** Der Konstruktor.
     *  @param gameView Damit Gameview weitergegeben werden kann
     * @param speedInPixel Gibt an wie schnell sich das Objekt bewegt
     * @param width Breite des Objekts
     * @param height Höhe des Objekts*/
    public Bullet(GameView gameView, double speedInPixel, double width, double height) {
        super(gameView);
        this.gameView = gameView;
        this.height = height;
        this.width = width;
        this.speedInPixel = speedInPixel;

        position = new Position(100,500);
        flyFromLeftToRight = true;
    }


    /** Bewegt das Objekt.*/
    @Override
    public void updatePosition() {
        if ((position.x + width / 2) > GameView.WIDTH) {
            flyFromLeftToRight = false;
        } else if ((position.x - width / 2) < 0) {
            flyFromLeftToRight = true;
        }

        if (flyFromLeftToRight) {
            position.right(speedInPixel);
        } else {
            position.left(speedInPixel);
        }
    }


    /**fügt die Grafik zu dem Objekt in GameView ein.*/
    @Override
    public void addToCanvas() {
        gameView.addOvalToCanvas(position.x, position.y, width, height, 5, false, Color.darkGray);
    }
}
