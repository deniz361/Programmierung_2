package thd.gameobjects.movable;


import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

import java.awt.*;


/** The "main" Object, the player can control the Chopper.*/
public class Chopper extends GameObject {

    private boolean shooting;
    private String imageFile;
    private final String emptyImage;
    private final String imageLeft;
    private final String imageRight;
    private double health;
    private double gas;


    /** Initializes the Chopper.
     * @param gameView gamView for GUI uses
     * @param gamePlayManager gameplay flow managing
     */
    public Chopper(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        shooting = false;
        position.x = GameView.WIDTH / 2.0;
        position.y = GameView.HEIGHT / 2.0;
        speedInPixel = 5;
        rotation = 0;
        height = 0;
        size = 0;
        health = 100.0;
        gas = 100.0;
        imageFile = "Chopper_links.png";
        emptyImage = "empty.png";
        imageLeft = "Chopper_links.png";
        imageRight = "Chopper_rechts.png";
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
        shooting = true;
    }

    /** Erhöht die Geschwindigkeit. */
    public void faster() {
        speedInPixel += 0.05;
    }

    /** Verringert die Geschwindigkeit. */
    public void slower() {
        speedInPixel -= 0.05;
    }

    /** Der Helikopter schaut nach rechts. */
    public void changeDirectionToRight() {
        if (!imageFile.equals(emptyImage)) {
            imageFile = imageRight;
        }
    }

    /** Der Helikopter schaut nach links. */
    public void changeDirectionToLeft() {
        if (!imageFile.equals(emptyImage)) {
            imageFile = imageLeft;
        }
    }


    /** Fügt den Chopper zu GameView hinzu. */
    @Override
    public void addToCanvas() {

        //System.out.println(getPosition());
        gameView.addTextToCanvas("Airspeed: " + speedInPixel,0,0, 18, Color.WHITE, 0);

        if (shooting) {
            gameView.addTextToCanvas("O", position.x, position.y, 50, Color.BLACK, 0);
        } else {
            gameView.addImageToCanvas(imageFile, position.x, position.y, 1.5, 0);

        }
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
