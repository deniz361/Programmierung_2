package thd.gameobjects.movable;


import thd.game.managers.*;
import thd.game.managers.GamePlayManager;
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


    private final GameObjectManager gameObjectManager;
    private boolean right;
    private ArrayList<GameObject> createdBullets;
    private String imageFile;
    private final String emptyImage;
    private final String imageLeft;
    private boolean chopperMovesLeft;
    private boolean chopperMovesRight;
    private final String imageRight;
    private final double shotsPerSecond;
    private double health;
    private double gas;


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
        position.x = 620;
        position.y = 400;
        speedInPixel = 2;
        rotation = 0;
        size = 1.5;
        health = 100.0;
        gas = 100.0;
        imageFile = "Chopper_links.png";
        emptyImage = "empty.png";
        imageLeft = "Chopper_links.png";
        chopperMovesLeft = false;
        imageRight = "Chopper_rechts.png";
        chopperMovesRight = false;
        createdBullets = new ArrayList<>(100);
        right = false;
        shotsPerSecond = 3;

        //hit box
        height = 21 * size;
        width = 47.5 * size;


        hitBoxOffsetX = 7;
        hitBoxOffsetY = 4;
        hitBoxHeight = height;
        hitBoxWidth = width;

        status = Status.STANDARD;
        exploded = false;

        //health
        health = 3;
    }


    /**
     * Diese Methoden werden initialisiert, um den Code in der Klasse InputManager
     * lesbarer zu machen. Nun wird nur noch z.B. chopper.left() in InputManager
     * aufgerufen.
     */
    public void left() {
        if (gameObjectManager.getBackgroundObjects().get(3).getPosition().x > 3300 && position.x <= 132.0) {
            return;
        }
        if (position.x > GameView.WIDTH / 2d - 350) {
            if (!gameView.timerIsActive("faster", this) && speedInPixel < 2) {
                gameView.activateTimer("faster", this, 200);
                faster();
            }
            position.left(speedInPixel);
        } else {
            gamePlayManager.moveWorldToRight(speedInPixel);
        }
    }

    /**
     * Siehe left().
     */
    public void right() {
        if (gameObjectManager.getBackgroundObjects().get(3).getPosition().x < 630 && position.x > 810) {
            return;
        }
        if (position.x < GameView.WIDTH - 70 - width) {
            if (!gameView.timerIsActive("faster", this) && speedInPixel < 2) {
                gameView.activateTimer("faster", this, 200);
                faster();
            }
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
            if (!gameView.timerIsActive("faster", this) && speedInPixel < 2) {
                gameView.activateTimer("faster", this, 200);
                faster();
            }
            position.up(speedInPixel);
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
            if (!gameView.timerIsActive("slower", this) && speedInPixel > 1) {
                gameView.activateTimer("slower", this, 200);
                slower();
            }
            position.down(speedInPixel);
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

        for (GameObject o : createdBullets) {
            if (o.outOfGame()) {
                gamePlayManager.destroy(o);
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
            hitBoxOffsetX = 1;
        }
    }

    /**
     * Der Helikopter schaut nach links.
     */
    public void changeDirectionToLeft() {
        if (!imageFile.equals(emptyImage)) {
            imageFile = imageLeft;
            right = false;
            hitBoxOffsetX = 7;
        }
    }
    /** When the chopper gets hit by an enemy bullet his health decreases by 1. */
    public void decreaseHealth() {
        health -= 1;
    }


    /**
     * setzt alles Wichtige vom Chopper zurück.
     */
    public void reset() {
        position.x = 620;
        position.y = 400;
        status = Status.STANDARD;
        imageFile = "Chopper_links.png";
        exploded = false;
    }


    /**
     * Fügt den Chopper zu GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        gameView.addTextToCanvas("Airspeed: " + speedInPixel, 0, 0, 18, Color.WHITE, 0);

        gameView.addImageToCanvas(imageFile, position.x, position.y, size, 0);
    }

    /**
     * updates the position.
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
     * If a game object collides with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {
    }
}
