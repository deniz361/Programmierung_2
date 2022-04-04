package thd.gameobjects.movable;

import thd.gameobjects.base.Position;
import thd.gameview.GameView;

import java.awt.*;


/** Die Klasse zu dem Objekt Bullet. */
public class Bullet {


    private final Position position;
    private double speedInPixel;
    private final GameView gameView;
    private double height;
    private double width;
    private boolean flyFromLeftToRight;


    /**
     *
     * @param gameView Damit Gameview weitergegeben werden kann
     * @param speed Gibt an wie schnell sich das Objekt bewegt
     * @param width Breite des Objekts
     * @param height Höhe des Objekts
     */
    public Bullet(GameView gameView, double speed, double width, double height) {
        this.gameView = gameView;
        this.speedInPixel = speed;
        this.height = height;
        this.width = width;
        this.position = new Position(100,500);
        this.flyFromLeftToRight = true;

    }


    /**
     * Bewegt das Objekt.
     */
    public void updatePosition() {

        if ((position.x + width / 2) > GameView.WIDTH) {
            flyFromLeftToRight = false;
        } else if ((position.x - width / 2) < 0) {
            flyFromLeftToRight = true;
        }

        if (flyFromLeftToRight) {
            position.right(3);
        } else {
            position.left(3);
        }
    }


    /**
     * fügt die Grafik zu dem Objekt in GameView ein.
     */
    public void addToCanvas() {
        gameView.addOvalToCanvas(position.x, position.y, width, height, 5, false, Color.darkGray);
    }
}
