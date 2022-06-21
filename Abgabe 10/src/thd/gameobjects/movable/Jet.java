package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.base.Position;
import thd.gameview.GameView;


/** Pictures the jet. */
public class Jet extends CollidableGameObject implements AutoMovable {


    //Jet design
    private String jet;
    private String leerzeichen;
    private String leerzeichen2;
    private String leerzeichen3;
    private String leerzeichen4;
    private String leerzeichen5;
    private String leerzeichen6;
    private String leerzeichen7;

    //WIDTH = 960;
    //HEIGHT = 540;

    //Bezierkurve bzw movement
    private double t1;
    private double t2;
    private double t3;
    private Position p1;
    private Position p2;
    private double resultX;
    private double resultY;
    private Position bezierPoint1;
    private Position bezierPoint2;
    private Position bezierPoint3;
    private int counter;


    /** Instantiates a new Jet.
     * @param gameView GameView for GUI uses
     * @param gamePlayManager Gameplay flow managing
     */
    public Jet(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        counter = 0;


        position.x = 400;
        position.y = 200;

        speedInPixel = 0.5;
        leerzeichen = " ".repeat(27);
        leerzeichen2 = " ".repeat(26);
        leerzeichen3 = " ".repeat(25);
        leerzeichen4 = " ".repeat(11);
        leerzeichen5 = " ".repeat(9);
        leerzeichen6 = " ".repeat(7);
        leerzeichen7 = " ".repeat(14);

        jet = leerzeichen + "LL  \n"
                + leerzeichen2 + "LCL\n"
                + leerzeichen3 + "LccL\n"
                + leerzeichen4 + "LLLLLLL      LCCCL\n"
                + leerzeichen5 + "LLWWWWWWWL" + "    LccCCL\n"
                + leerzeichen6 + "LLWWWWWWLLLLLLLLCCCCCL\n" +
                "    LLLCCLLLLLLCCCCCCCCCCCCCCLL\n" +
                " LLLCCCCCCCCCLLLLLLLLLLLLLLCCCL\n" +
                "LCCCCCCCCCCCLWWWWWWWWWWWWWLLLL\n" +
                "LLLLLLLLLLLLLLRRRRRRRRLLLL\n"
                + leerzeichen7 + "LLLLLLLL";

        size = 2;
        height = 11 * size;
        width = 31 * size;
        //Bezier
        p1 = new Position(100, 300);
        p2 = new Position(400, 400);

        t1 = 0.25;
        t2 = 0.5;
        t3 = 0.75;

        calculateBezierCurve(t1);
        bezierPoint1 = new Position(resultX, resultY);
        calculateBezierCurve(t2);
        bezierPoint2 = new Position(resultX, resultY);
        calculateBezierCurve(t3);
        bezierPoint3 = new Position(resultX, resultY);


        //hit box
        hitBoxOffsetX = 0;
        hitBoxOffsetY = 0;
        hitBoxHeight = height;
        hitBoxWidth = width;
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

    /** Adds the jet to the canvas. */
    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(jet, position.x, position.y, size, 0);

        /*
        gameView.addOvalToCanvas(bezierPoint1.x, bezierPoint1.y, 10, 10, 2, false, Color.GREEN);
        gameView.addOvalToCanvas(bezierPoint2.x, bezierPoint2.y, 10, 10, 2, false, Color.GREEN);
        gameView.addOvalToCanvas(bezierPoint3.x, bezierPoint3.y, 10, 10, 2, false, Color.GREEN);
         */

    }

    @Override
    public void updateStatus() {

    }


    /** Moves the jet.*/
    @Override
    public void updatePosition() {
        switch (counter) {
            case 0:
                followTargetPostion(bezierPoint1);
                break;
            case 1:
                followTargetPostion(bezierPoint2);
                break;
            case 2:
                followTargetPostion(bezierPoint3);
                break;
            default:
                position.right(speedInPixel);

        }
    }


    private void followTargetPostion(Position targetPosition) {

        double distance = position.distance(targetPosition);

        if (position.x < targetPosition.x) {
            position.right((targetPosition.x - position.x) / distance * speedInPixel);
        } else if (position.x > targetPosition.x) {
            position.left(-((targetPosition.x - position.x) / distance * speedInPixel));
        }
        if (position.y < targetPosition.y) {
            position.down((targetPosition.y - position.y) / distance * speedInPixel);
        } else if (position.y > targetPosition.y) {
            position.up((targetPosition.y - position.y) / distance * speedInPixel);
        }

        if (distance <= 5) {
            counter++;
        }


    }

    /** Calculates a specific Bezier point.
     *  It should return a 2 Dimensional tuple
     * @param t the point on the Bézier curve that you want to calculate
     */
    private void calculateBezierCurve(double t) {
        resultX = (position.x - 2 * p1.x + p2.x) * Math.pow(t, 2) + (-2 * position.x + 2 * p1.x) * t + position.x;
        resultY = (position.y - 2 * p1.y + p2.y) * Math.pow(t, 2) + (-2 * position.y + 2 * p1.y) * t + position.y;
    }
}
