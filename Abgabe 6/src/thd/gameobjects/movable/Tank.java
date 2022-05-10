package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameview.GameView;

import java.util.Random;


/**
 * Pictures the Tank.
 */
public class Tank extends CollidableGameObject implements AutoMovable {


    private final String tankLeft;
    private final String tankRight;
    private double blockSize;
    private final Random random;
    private final double yStart;
    private final double yGoal;
    private final double xStart;
    private boolean flyFromLeftToRight;

    // Hit boxen
    /*
    private double hitBoxOffsetX;
    private double hitBoxOffsetY;
    private double hitBoxWidth;
    private double hitBoxHeight;

     */


    /**
     * Instantiates a new Tank.
     *
     * @param gameView        GameView for GUI uses
     * @param gamePlayManager gameplay flow managing
     */
    public Tank(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        random = new Random();
        blockSize = 3;
        width = 13 * blockSize;
        height = 7 * blockSize;
        xStart = random.nextInt(100, 851);
        yStart = GameView.HEIGHT + height + 15;  //gameview height ist 540
        yGoal = random.nextDouble(350, 450);
        position.x = xStart;
        position.y = yStart;
        tankLeft =
                "     oOoOO\n" +
                        "LLLLLOOooOO\n" +
                        "     ooOOo\n" +
                        " oOOoOOooOOo\n" +
                        "oLLLLLLLLLLLo\n" +
                        "LWLWLWLWLWLWL\n" +
                        " LLLLLLLLLLL";

        tankRight =
                "   OOoOo\n" +
                        "  OOooOOLLLLL\n" +
                        "   oOOoo\n" +
                        " oOOooOOoOOo\n" +
                        "oLLLLLLLLLLLo\n" +
                        "LWLWLWLWLWLWL\n" +
                        " LLLLLLLLLLL";

        // Neue Farben hinzufügen:.
        // Zeile 1449 GameView

        //hit boxen
        hitBoxOffsetX = 0;
        hitBoxOffsetY = 0;
        hitBoxHeight = height;
        hitBoxWidth = width;

    }

    /**
     * Adds the Tank to the canvas.
     */
    @Override
    public void addToCanvas() {
        if (flyFromLeftToRight) {
            gameView.addBlockImageToCanvas(tankRight, position.x, position.y, blockSize, 0);
        } else {
            gameView.addBlockImageToCanvas(tankLeft, position.x, position.y, blockSize, 0);
        }
        //gameView.addImageToCanvas("tank_left.png", position.x,position.y,0.03,0);
    }

    @Override
    public void updateStatus() {

    }


    /**
     * Moves the Object.
     */
    @Override
    public void updatePosition() {
        if (position.y >= yGoal) {
            goIntoScene();
            speedInPixel = 0.25;
        } else {
            speedInPixel = 0.5;
            if ((position.x + width) > GameView.WIDTH) {  //gameview width is 960
                flyFromLeftToRight = false;
            } else if ((position.x) < 0) {
                flyFromLeftToRight = true;
            }


            if (flyFromLeftToRight) {
                position.right(speedInPixel);
            } else {
                position.left(speedInPixel);
            }
        }


    }


    private void goIntoScene() {
        if (xStart > 475) {
            leftCurve();
            flyFromLeftToRight = false;
        } else {
            rightCurve();
            flyFromLeftToRight = true;
        }
    }

    private void rightCurve() {
        parabel();
        position.right(speedInPixel);
    }

    private void leftCurve() {
        parabel();
        position.left(speedInPixel);
    }

    private void parabel() {
        position.y = -0.005 * Math.pow(position.x - xStart, 2) + yStart;
    }

    /**
     * Maybe for debugging.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Tank: ");
        return stringBuilder.toString() + position;
    }

    /**
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {
        if (other.getClass() == Bullet.class) {
            gamePlayManager.destroy(this);
        }
    }

}