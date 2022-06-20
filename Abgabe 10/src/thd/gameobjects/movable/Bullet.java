package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.WrongInput;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameview.GameView;

import java.awt.*;


/**
 * Die Klasse zu dem Objekt Bullet.
 */
public class Bullet extends CollidableGameObject implements AutoMovable {

    private boolean flyFromLeftToRight;
    private Chopper chopper;


    /**
     * Initializes the Bullet.
     *
     * @param gameView        gamView for GUI uses
     * @param gamePlayManager gameplay flow managing
     * @param chopper to get the position of the chopper
     */
    Bullet(GameView gameView, GamePlayManager gamePlayManager, Chopper chopper) {
        super(gameView, gamePlayManager);
        this.chopper = chopper;
        height = 2;
        width = 5;
        speedInPixel = 3;
        position.x = chopper.getPosition().x + 10;
        position.y = chopper.getPosition().y + 26;
        flyFromLeftToRight = true;

        hitBoxOffsetX = 0;
        hitBoxOffsetY = 0;
        hitBoxHeight = height;
        hitBoxWidth = width;

    }


    @Override
    public void updateStatus() {
        if (position.x > GameView.WIDTH || position.x + width < 0 || position.y + height < 0 || position.y > GameView.HEIGHT) {
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
            throw new WrongInput("Wrong Input! Chose between 'left' or 'right'.");
        }
    }


    /**
     * Bewegt das Objekt.
     */
    @Override
    public void updatePosition() {
        if (flyFromLeftToRight) {
            position.right((speedInPixel * (chopper.getRotation() / 10)));
            position.down(speedInPixel);
        } else {
            position.left(-(speedInPixel * (chopper.getRotation() / 10)));
            position.down(speedInPixel);
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

    /**
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {
        if (other.getClass() == Jet.class || other.getClass() == Tank.class) {
            gamePlayManager.destroy(this);
        }
    }
}
