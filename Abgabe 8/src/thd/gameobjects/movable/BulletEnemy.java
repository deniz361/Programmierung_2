package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.WrongInput;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

import java.awt.*;

public class BulletEnemy extends CollidableGameObject implements AutoMovable {


    private boolean flyFromLeftToRight;
    private double positionChopperX;
    private double positionChopperY;

    /**
     * Mindestanforderung, das jedes GameObject haben muss.
     *
     * @param gameView        to interact with game view.
     * @param gamePlayManager to control the game flow.
     */
    public BulletEnemy(GameView gameView, GamePlayManager gamePlayManager, double spawnPositionX, double spawnPositionY) {
        super(gameView, gamePlayManager);
        position.x = spawnPositionX;
        position.y = spawnPositionY;
        height = 2;
        width = 5;
        speedInPixel = 3;
        hitBoxOffsetX = 0;
        hitBoxOffsetY = 0;
        hitBoxHeight = height;
        hitBoxWidth = width;
        flyFromLeftToRight = false;


        //Operation AutoAim
        positionChopperX = gamePlayManager.getPositonChopper().x;
        positionChopperY = gamePlayManager.getPositonChopper().y;
    }

    /**
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {
        if (other.getClass() == Chopper.class) {
            gamePlayManager.chopperHasBeenHit();
        }
    }

    /**
     * Fügt das Spielobject in GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        gameView.addRectangleToCanvas(position.x, position.y, width, height, 0, true, Color.BLACK);
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
     * updates the position.
     */
    @Override
    public void updatePosition() {
        if (flyFromLeftToRight) {
            position.right(speedInPixel);
            position.up(speedInPixel);
        } else {
            position.left(speedInPixel);
            position.up(speedInPixel);
        }
    }

    //Operation autoaim



}
