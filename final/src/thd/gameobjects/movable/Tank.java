package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

import java.util.ArrayList;
import java.util.Random;


/**
 * Pictures the Tank.
 */
public class Tank extends CollidableGameObject implements AutoMovable {


    private final String tankLeft;
    private final String tankRight;
    private final Random random;
    private final double yStart;
    private final double yGoal;
    private final double xStart;
    private boolean flyFromLeftToRight;

    /** don't move the Tank while it's driving into the scene. */
    private double positionXNotDynamic;


    // shoot
    private double shotsPerSecond;
    private ArrayList<GameObject> createdBullets;

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
        size = 3;
        width = 13 * size;
        height = 7 * size;
        xStart = random.nextInt(751) + 100;
        yStart = GameView.HEIGHT + height + 15;  //gameview height ist 540
        yGoal = random.nextInt(100) + 400;
        position.x = xStart;
        positionXNotDynamic = xStart;
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

        //shoot
        shotsPerSecond = 1;
        createdBullets = new ArrayList<>();

    }

    /**
     * Adds the Tank to the canvas.
     */
    @Override
    public void addToCanvas() {
        if (flyFromLeftToRight) {
            gameView.addBlockImageToCanvas(tankRight, position.x, position.y, size, 0);
        } else {
            gameView.addBlockImageToCanvas(tankLeft, position.x, position.y, size, 0);
        }
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
                shoot();
            } else {
                position.left(speedInPixel);
                shoot();
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
        positionXNotDynamic += speedInPixel;
    }

    private void leftCurve() {
        parabel();
        positionXNotDynamic -= speedInPixel;
    }

    private void parabel() {
        position.y = -0.005 * Math.pow(positionXNotDynamic - xStart, 2) + yStart;
    }


    /** shoots.*/
    private void shoot() {
        if (!gameView.timerIsActive("shootTank", this)) {
            gameView.activateTimer("shootTank", this, (long) (1000 / shotsPerSecond));
            GameObject o = new BulletEnemy(gameView, gamePlayManager, this.position.x, this.position.y, 3, 3);
            createdBullets.add(o);
            gamePlayManager.spawn(o);

            if (flyFromLeftToRight) {
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