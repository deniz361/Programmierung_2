package thd.gameobjects.movable;


import thd.game.managers.GamePlayManager;
import thd.game.managers.GameObjectManager;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

import java.awt.*;
import java.util.ArrayList;

/**
 * The "main" Object, the player can control the Chopper.
 */
public class Chopper extends CollidableGameObject implements AutoMovable {


    private boolean right;
    private final ArrayList<GameObject> createdBullets;
    private String imageFile;
    private final String emptyImage;
    private final String imageLeft;
    private final String imageRight;
    private double shotsPerSecond;
    private double health;
    private double gas;
    private double blocksize;
    private GameObjectManager gameObjectManager;

    private enum Status {STANDARD, DAMAGED, EXPLODING, EXPLODED}

    private Status status;

    /**
     * return true if the chopper is destroyed.
     */
    public boolean exploded;

    /**
     * Initializes the Chopper.
     *
     * @param gameView          gamView for GUI uses
     * @param gamePlayManager   gameplay flow managing
     * @param gameObjectManager to interact with other GameObjects
     */
    public Chopper(GameView gameView, GamePlayManager gamePlayManager, GameObjectManager gameObjectManager) {
        super(gameView, gamePlayManager);
        this.gameObjectManager = gameObjectManager;
        position.x = GameView.WIDTH / 2.0;
        position.y = GameView.HEIGHT / 2.0;
        speedInPixel = 2;
        rotation = 0;
        size = 0;
        health = 100.0;
        gas = 100.0;
        imageFile = "Chopper_links.png";
        emptyImage = "empty.png";
        imageLeft = "Chopper_links.png";
        imageRight = "Chopper_rechts.png";
        createdBullets = new ArrayList<>(100);
        right = false;
        shotsPerSecond = 3;

        //hit box
        blocksize = 1.5;
        height = 21 * blocksize;
        width = 47.5 * blocksize;

        hitBoxOffsetX = 7;
        hitBoxOffsetY = 4;
        hitBoxHeight = height;
        hitBoxWidth = width;

        status = Status.STANDARD;
        exploded = false;
    }


    /**
     * Diese Methoden werden initialisiert, um den Code in der Klasse InputManager
     * lesbarer zu machen. Nun wird nur noch z.B. chopper.left() in InputManager
     * aufgerufen.
     */
    public void left() {
        /*
        if (position.x < - 6) {
            position.right();
        } else {
            position.left();
        }
         */
        if (position.x > GameView.WIDTH / 2d - 350) {
            position.left(speedInPixel);
        } else {
            gamePlayManager.moveWorldToRight(speedInPixel);
        }
    }

    /**
     * Siehe left().
     */
    public void right() {

        if (position.x < GameView.WIDTH - 70 - width) {
            position.right(speedInPixel);
        } else {
            gamePlayManager.moveWorldToLeft(speedInPixel);
        }
        /*
        if (position.x + width > GameView.WIDTH) {
            position.left();
        } else {
            position.x += speedInPixel;
        }

         */
    }

    /**
     * Siehe left().
     */
    public void up() {


        if (position.y < -5) {
            position.down();
        } else {
            position.y -= speedInPixel;
        }
    }

    /**
     * Siehe left().
     */
    public void down() {


        if (position.y >= 510) {
            imageFile = emptyImage;
            status = Status.EXPLODED;
        } else {
            position.y += speedInPixel;
        }
    }

    /**
     * Schießt.
     */
    public void shoot() {
        if (!gameView.timerIsActive("shoot", this)) {
            gameView.activateTimer("shoot", this, (long) (1000 / shotsPerSecond));
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

    /**
     * Erhöht die Geschwindigkeit.
     */
    public void faster() {
        speedInPixel += 0.5;
    }

    /**
     * Verringert die Geschwindigkeit.
     */
    public void slower() {
        speedInPixel -= 0.5;
    }

    /**
     * Der Helikopter schaut nach rechts.
     */
    public void changeDirectionToRight() {
        if (!imageFile.equals(emptyImage)) {
            imageFile = imageRight;
            right = true;
        }
    }

    /**
     * Der Helikopter schaut nach links.
     */
    public void changeDirectionToLeft() {
        if (!imageFile.equals(emptyImage)) {
            imageFile = imageLeft;
            right = false;
        }
    }


    /**
     * Fügt den Chopper zu GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        gameView.addTextToCanvas("Airspeed: " + speedInPixel, 0, 0, 18, Color.WHITE, 0);

        gameView.addImageToCanvas(imageFile, position.x, position.y, blocksize, 0);
    }


    /**
     * updated the Position.
     */
    @Override
    public void updatePosition() {


    }

    @Override
    public void updateStatus() {


        switch (status) {
            case EXPLODED:
                imageFile = "Chopper exploded.png";
                exploded = true;
                break;
            case DAMAGED:
                break;
            case EXPLODING:
                break;
            case STANDARD:
                break;
            default:

        }
    }

    /**
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {

    }
}
