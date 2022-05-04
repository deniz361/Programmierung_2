package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameview.GameView;

import java.awt.*;


/** Pictures the jet. */
public class Jet extends GameObject {


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

        jet = leerzeichen + "LL  \n" + leerzeichen2 + "LCL\n" + leerzeichen3 + "LccL\n" + leerzeichen4 + "LLLLLLL      LCCCL\n" + leerzeichen5 + "LLWWWWWWWL" +
                "    LccCCL\n" + leerzeichen6 + "LLWWWWWWLLLLLLLLCCCCCL\n    LLLCCLLLLLLCCCCCCCCCCCCCCLL\n LLLCCCCCCCCCLLLLLLLLLLLLLLCCCL\nLCCCCCCCCCCCLWWWWWWWWWWWWWLLLL\n" +
                "LLLLLLLLLLLLLLRRRRRRRRLLLL\n" + leerzeichen7 + "LLLLLLLL";


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


        /*
        p1 = new Position(position.x - 200, position.y + 100);
        p2 = new Position(position.x, position.y + 200);



         */
    }

    /** Adds the jet to the canvas. */
    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(jet, position.x, position.y, 1.5, 0);

        /*
        gameView.addOvalToCanvas(bezierPoint1.x, bezierPoint1.y, 10, 10, 2, false, Color.GREEN);
        gameView.addOvalToCanvas(bezierPoint2.x, bezierPoint2.y, 10, 10, 2, false, Color.GREEN);
        gameView.addOvalToCanvas(bezierPoint3.x, bezierPoint3.y, 10, 10, 2, false, Color.GREEN);
         */

    }


    /** Moves the jet.*/
    @Override
    public void updatePosition() {

        /*
        timeElapsed = Math.round(((System.currentTimeMillis() - time) / 1000.0));

        if (!doTheKurve) {
            position.left(speedInPixel);
        }

        if (timeElapsed >= 2) {
            p1 = new Position(position.x - 300, position.y + 100);
            p2 = new Position(position.x, position.y + 200);
            doTheKurve = true;

        }

        while (doTheKurve) {

         */
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
        //}











        /*

        position.y = position.y + Math.round(Math.sin(Math.toRadians(position.x * 2)));
        position.x = position.x + Math.round(Math.cos(Math.toRadians(position.y * 2)));


        System.out.println("Position y: " + position.y);

        //if (position.x <= 320)

         */


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
