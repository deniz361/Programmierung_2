package thd.gameobjects.movable;

import thd.game.managers.InputManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

import java.awt.*;

/** Der Spieler steuert den Chopper.*/
public class Chopper extends GameObject {

    private boolean shooting;
    private String imageFile;
    private double health;
    private double gas;


    /** Initialisierung von Chopper.*/
    public Chopper(GameView gameView) {
        super(gameView);
        //shooting = false;
        position.x = GameView.WIDTH / 2.0;
        position.y = GameView.HEIGHT / 2.0;
        speedInPixel = 1;
        imageFile = "Chopper.png";
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

    /** Erhöht die Geschwindigkeit */
    public void faster() {
        speedInPixel += 0.05;
    }

    /** Verringert die Geschwindigkeit */
    public void slower() {
        speedInPixel -= 0.05;
    }

    public void changeDirectionToRight() {

    }


    /**
     * Fügt den Chopper zu GameView hinzu
     */
    @Override
    public void addToCanvas() {

        System.out.println(getPosition());
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
        if (position.y >= 475) {
            imageFile = "Empty.png";
        }
    }


}
