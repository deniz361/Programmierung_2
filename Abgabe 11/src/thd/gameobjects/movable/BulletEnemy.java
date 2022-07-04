package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.WrongInput;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameview.GameView;

import java.awt.*;


/** Die Kugeln von den Gegnern. Die Kugeln vom Gegner und von Chopper müssen getrennt sein, weil die Bullets vom
 * Chopper beim Abschießen die Hit-box vom Chopper berühren.
 */
public class BulletEnemy extends CollidableGameObject implements AutoMovable {


    private boolean flyFromLeftToRight;
    private double positionChopperX;
    private double positionChopperY;
    private double speedX;
    private double speedY;

    /**
     * Mindestanforderung, das jedes GameObject haben muss.
     *
     * @param gameView        to interact with game view.
     * @param gamePlayManager to control the game flow.
     * @param spawnPositionX wo die Kugel spawnen soll.
     * @param spawnPositionY wo die Kugel spawnen soll.
     */
    public BulletEnemy(GameView gameView, GamePlayManager gamePlayManager, double spawnPositionX, double spawnPositionY, double speedX, double speedY) {
        super(gameView, gamePlayManager);
        position.x = spawnPositionX;
        position.y = spawnPositionY;
        height = 2;
        width = 5;

        speedInPixel = 3;
        this.speedX = speedX;
        this.speedY = speedY;


        hitBoxHeight = height;
        hitBoxWidth = width;
        flyFromLeftToRight = false;


        //AutoAim
        positionChopperX = gamePlayManager.positionChopper().x;
        positionChopperY = gamePlayManager.positionChopper().y;
    }

    /**
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {
        /*
        if (other.getClass() == Chopper.class) {
            gamePlayManager.chopperHasBeenHit();
        }

         */
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
            position.right(speedX);
            position.up(speedY);
        } else {
            position.left(speedX);
            position.up(speedY);
        }
    }


}
