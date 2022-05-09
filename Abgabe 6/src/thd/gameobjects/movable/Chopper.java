package thd.gameobjects.movable;


import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

import java.awt.*;
import java.util.ArrayList;


/** The "main" Object, the player can control the Chopper.*/
public class Chopper extends GameObject implements AutoMovable {

    private boolean shooting;
    private boolean right;
    private final ArrayList<GameObject> createdBullets;
    private String imageFile;
    private final String emptyImage;
    private final String imageLeft;
    private final String imageRight;
    private double shotsPerSecond;
    private double health;
    private double gas;


    /** Initializes the Chopper.
     * @param gameView gamView for GUI uses
     * @param gamePlayManager gameplay flow managing
     */
    public Chopper(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        position.x = GameView.WIDTH / 2.0;
        position.y = GameView.HEIGHT / 2.0;
        speedInPixel = 2;
        rotation = 0;
        height = 0;
        size = 0;
        health = 100.0;
        gas = 100.0;
        imageFile = "Chopper_links.png";
        emptyImage = "empty.png";
        imageLeft = "Chopper_links.png";
        imageRight = "Chopper_rechts.png";
        createdBullets = new ArrayList<>(100);
        shooting = false;
        right = false;
        shotsPerSecond = 3;
    }

    /**
     * Diese Methoden werden initialisiert, um den Code in der Klasse InputManager
     * lesbarer zu machen. Nun wird nur noch z.B. chopper.left() in InputManager
     * aufgerufen.
     */
    public void left() {
        position.x -= speedInPixel;
    }

    /** Siehe left(). */
    public void right() {
        position.x += speedInPixel;
    }

    /** Siehe left(). */
    public void up() {
        position.y -= speedInPixel;
    }

    /** Siehe left(). */
    public void down() {
        position.y += speedInPixel;
    }

    /** Schießt. */
    public void shoot() {
        if (!gameView.timerIsActive("shoot", this)) {
            gameView.activateTimer("shoot", this, (long) (1000 / shotsPerSecond));
            shooting = true;
            GameObject o = new Bullet(gameView, gamePlayManager, this);
            createdBullets.add(o);
            gamePlayManager.spawn(o);

            if (right) {
                o.changeDirectionTo("right");

            } else {
                o.changeDirectionTo("left");
            }
        }
    }

    /** Erhöht die Geschwindigkeit. */
    public void faster() {
        speedInPixel += 0.5;
    }

    /** Verringert die Geschwindigkeit. */
    public void slower() {
        speedInPixel -= 0.5;
    }

    /** Der Helikopter schaut nach rechts. */
    public void changeDirectionToRight() {
        if (!imageFile.equals(emptyImage)) {
            imageFile = imageRight;
            right = true;
        }
    }

    /** Der Helikopter schaut nach links. */
    public void changeDirectionToLeft() {
        if (!imageFile.equals(emptyImage)) {
            imageFile = imageLeft;
            right = false;
        }
    }


    /** Fügt den Chopper zu GameView hinzu. */
    @Override
    public void addToCanvas() {
        gameView.addTextToCanvas("Airspeed: " + speedInPixel,0,0, 18, Color.WHITE, 0);

        gameView.addImageToCanvas(imageFile, position.x, position.y, 1.5, 0);
    }


    @Override
    public void updatePosition() {
        shooting = false;
    }

    @Override
    public void updateStatus() {
        if (position.y >= 510) {
            imageFile = emptyImage;
        }
    }


}
