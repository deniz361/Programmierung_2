package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameview.GameView;

import java.awt.*;
import java.util.LinkedList;


/**
 * Pictures the jet.
 */
public class Jet extends CollidableGameObject implements AutoMovable {

    //Bezierkurve bzw movement
    private double resultX;
    private double resultY;
    private int counter;
    private boolean calculatedBezierPoints;
    private boolean finished;
    private Position mid;
    private Position bottom;
    private boolean wayUp;
    private boolean flyStraightForCoupleSeconds;


    //Animation
    private JetAnimation jetAnimation;

    //dynamic bezier
    private boolean flyStraight;
    private boolean inCurve;
    private boolean facingRight;
    private boolean facingLeft;

    /**
     * Diese Variable wird in GameObjectManager gebraucht, damit sich die
     * Bezier Punkte auch bewegen, wenn sich die Welt bewegt.
     */
    public Position[] bezierPoints;
    private int bezierPointCounter;


    //shoot
    private double shotsPerSecond;
    private LinkedList<GameObject> createdBullets;
    private double distanceToChopper;

    /**
     * Instantiates a new Jet.
     *
     * @param gameView        GameView for GUI uses
     * @param gamePlayManager Gameplay flow managing
     * @param positionX Startposition.
     * @param positionY Startposition.
     */
    public Jet(GameView gameView, GamePlayManager gamePlayManager, int positionX, int positionY) {
        super(gameView, gamePlayManager);
        counter = 0;


        position.x = positionX; //400;
        position.y = positionY; //200;

        speedInPixel = 0.5;

        size = 0.5;
        height = 37 * size;
        width = 134 * size;


        //dynamic bezier
        flyStraight = true;
        inCurve = false;
        facingLeft = false;
        facingRight = true;

        bezierPoints = new Position[10];


        //shoot
        shotsPerSecond = 1;
        createdBullets = new LinkedList<>();
        distanceToChopper = 0;


        //hit box
        hitBoxOffsetX = 0;
        hitBoxOffsetY = 0;
        hitBoxHeight = height;
        hitBoxWidth = width;

        //Animation
        jetAnimation = JetAnimation.JET_ANIMATION_RIGHT1;
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

    /**
     * Adds the jet to the canvas.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(jetAnimation.imageFile, position.x, position.y, size, 0);



        /*
if (calculatedBezierPoints) {
            gameView.addOvalToCanvas(bezierPoints[0].x, bezierPoints[0].y, 10, 10, 2, false, Color.GREEN);
            gameView.addOvalToCanvas(bezierPoints[1].x, bezierPoints[1].y, 10, 10, 2, false, Color.GREEN);
            gameView.addOvalToCanvas(bezierPoints[2].x, bezierPoints[2].y, 10, 10, 2, false, Color.GREEN);
        }

         */


    }

    @Override
    public void updateStatus() {
        if (inCurve) {
            if (facingRight) {
                jetAnimationRight();
            } else if (facingLeft) {
                jetAnimationLeft();
            }
        }
        if (flyStraight || wayUp) {
            distanceToChopper = gamePlayManager.positionChopper().x - position.x;

            if (facingRight && distanceToChopper >= 0 && distanceToChopper < 960) {
                shoot();
            } else if (facingLeft && distanceToChopper < 0 && distanceToChopper > -960) {
                shoot();
            }
        }
    }


    /**
     * Moves the jet.
     */
    @Override
    public void updatePosition() {
        if (flyStraight) {
            flyStraight();
        } else if (inCurve) {
            //problem wenn eins positiv und das andere negativ...
            inCurve();

            if (finished) {
                finished();
            }
        } else if (wayUp) {
            wayUp();
        }
    }

    private void flyStraight() {
        speedInPixel = 1;
        if (!gameView.alarmIsSet("whenShouldTheJetStartWithTheCurve", this)) {
            gameView.setAlarm("whenShouldTheJetStartWithTheCurve", this, 1000);
        } else if (gameView.alarm("whenShouldTheJetStartWithTheCurve", this)) {
            flyStraight = false;
            inCurve = true;
            wayUp = false;

            calculatedBezierPoints = false;
        }
        if (facingRight) {
            position.x += speedInPixel;
        } else if (facingLeft) {
            position.x -= speedInPixel;
        }
    }

    private void wayUp() {
        speedInPixel = 1.2;
        if (position.y >= 100) {
            if (facingLeft) {
                position.x -= speedInPixel;
                position.y -= 0.3; //0.05
            } else if (facingRight) {
                position.x += speedInPixel;
                position.y -= 0.3; // 0.05
            }
        } else {
            wayUp = false;
            flyStraight = true;

        }
    }

    private void inCurve() {
        speedInPixel = 0.5;
        if (facingRight && !calculatedBezierPoints) {
            chooseBezierPoints();
            calculateBezierCurve(mid, bottom);
        } else if (facingLeft && !calculatedBezierPoints) {
            chooseBezierPoints();
            calculateBezierCurve(mid, bottom);
        }

        followTargetPostion(bezierPoints[counter]);
    }

    private void finished() {
        inCurve = false;
        facingRight = !facingRight;
        facingLeft = !facingLeft;
        finished = false;
        wayUp = true;

        counter = 0;
        bezierPointCounter = 0;

        if (jetAnimation == JetAnimation.JET_ANIMATION_LEFT7) {
            jetAnimation = JetAnimation.JET_ANIMATION_RIGHT1;
        } else if (jetAnimation == JetAnimation.JET_ANIMATION_RIGHT7) {
            jetAnimation = JetAnimation.JET_ANIMATION_LEFT1;
        }
    }

    private void chooseBezierPoints() {
        if (facingRight) {
            mid = new Position(position.x + 300, position.y + 100);
            bottom = new Position(position.x, position.y + 200);
        } else if (facingLeft) {
            mid = new Position(position.x - 300, position.y + 100);
            bottom = new Position(position.x, position.y + 200);
        }
    }


    private void followTargetPostion(Position targetPosition) {

        if (counter == bezierPointCounter) {
            finished = true;
        } else {

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

            if (distance <= 2) {
                counter++;
            }
        }


    }

    private void shoot() {
        if (!gameView.timerIsActive("shootJet", this)) {
            gameView.activateTimer("shootJet", this, (long) (1000 / shotsPerSecond));
            GameObject o = new BulletEnemy(gameView, gamePlayManager, this.position.x + 20, this.position.y + 15, 3, 0.2);
            createdBullets.add(o);
            gamePlayManager.spawn(o);

            if (facingRight) {
                o.changeDirectionTo("right");

            } else if (facingLeft) {
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
     * Calculates the BezierCurve and adds the essential Points into the array "bezierPoints"
     *
     * @param p1 The second Postion to create the Bézier curve
     * @param p2 The third Postion to create the Bézier curve
     */
    private void calculateBezierCurve(Position p1, Position p2) {
        bezierPointCounter = 0;
        for (double i = 0.25; i <= 0.75; i += 0.25) {
            resultX = (position.x - 2 * p1.x + p2.x) * Math.pow(i, 2) + (-2 * position.x + 2 * p1.x) * i + position.x;
            resultY = (position.y - 2 * p1.y + p2.y) * Math.pow(i, 2) + (-2 * position.y + 2 * p1.y) * i + position.y;

            bezierPoints[bezierPointCounter] = new Position(resultX, resultY);
            bezierPointCounter++;
        }
        calculatedBezierPoints = true;
    }


    private void jetAnimationRight() {
        if (!gameView.alarmIsSet("jetAnimationRight", this)) {
            gameView.setAlarm("jetAnimationRight", this, 600);
        } else if (gameView.alarm("jetAnimationRight", this)) {
            switch (jetAnimation) {
                case JET_ANIMATION_RIGHT1:
                    animationRight1();
                    break;
                case JET_ANIMATION_RIGHT2:
                    animationRight2();
                    break;
                case JET_ANIMATION_RIGHT3:
                    animationRight3();
                    break;
                case JET_ANIMATION_RIGHT4:
                    jetAnimation = JetAnimation.JET_ANIMATION_RIGHT5;
                    hitBoxWidth = 73 * size;
                    hitBoxHeight = 68 * size;
                    break;
                case JET_ANIMATION_RIGHT5:
                    jetAnimation = JetAnimation.JET_ANIMATION_RIGHT6;
                    hitBoxWidth = 108 * size;
                    hitBoxHeight = 53 * size;
                    break;
                case JET_ANIMATION_RIGHT6:
                    jetAnimation = JetAnimation.JET_ANIMATION_RIGHT7;
                    hitBoxWidth = 136 * size;
                    hitBoxHeight = 48 * size;
                    break;
                case JET_ANIMATION_RIGHT7:
                    jetAnimation = JetAnimation.JET_ANIMATION_LEFT1;
                    hitBoxWidth = 134 * size;
                    hitBoxHeight = 37 * size;
                    break;
                default:
            }
        }
    }



    private void jetAnimationLeft() {
        if (!gameView.alarmIsSet("jetAnimationLeft", this)) {
            gameView.setAlarm("jetAnimationLeft", this, 600);
        } else if (gameView.alarm("jetAnimationLeft", this)) {
            switch (jetAnimation) {
                case JET_ANIMATION_LEFT1:
                    animationLeft1();
                    break;
                case JET_ANIMATION_LEFT2:
                    animationLeft2();
                    break;
                case JET_ANIMATION_LEFT3:
                    animationLeft3();
                    break;
                case JET_ANIMATION_LEFT4:
                    jetAnimation = JetAnimation.JET_ANIMATION_LEFT5;
                    hitBoxWidth = 73 * size;
                    hitBoxHeight = 68 * size;
                    break;
                case JET_ANIMATION_LEFT5:
                    jetAnimation = JetAnimation.JET_ANIMATION_LEFT6;
                    hitBoxWidth = 108 * size;
                    hitBoxHeight = 53 * size;
                    break;
                case JET_ANIMATION_LEFT6:
                    jetAnimation = JetAnimation.JET_ANIMATION_LEFT7;
                    hitBoxWidth = 136 * size;
                    hitBoxHeight = 48 * size;
                    break;
                case JET_ANIMATION_LEFT7:
                    jetAnimation = JetAnimation.JET_ANIMATION_RIGHT1;
                    hitBoxWidth = 134 * size;
                    hitBoxHeight = 37 * size;
                    break;
                default:
            }
        }
    }
    //clean code xdddd
    private void animationRight1() {
        jetAnimation = JetAnimation.JET_ANIMATION_RIGHT2;
        hitBoxWidth = 127 * size;
        hitBoxHeight = 61 * size;
    }
    private void animationRight2() {
        jetAnimation = JetAnimation.JET_ANIMATION_RIGHT3;
        hitBoxWidth = 105 * size;
        hitBoxHeight = 61 * size;
    }
    private void animationRight3() {
        jetAnimation = JetAnimation.JET_ANIMATION_RIGHT4;
        hitBoxWidth = 89 * size;
        hitBoxHeight = 77 * size;
    }
    private void animationLeft1() {
        jetAnimation = JetAnimation.JET_ANIMATION_LEFT2;
        hitBoxWidth = 127 * size;
        hitBoxHeight = 61 * size;
    }
    private void animationLeft2() {
        jetAnimation = JetAnimation.JET_ANIMATION_LEFT3;
        hitBoxWidth = 105 * size;
        hitBoxHeight = 61 * size;
    }
    private void animationLeft3() {
        jetAnimation = JetAnimation.JET_ANIMATION_LEFT4;
        hitBoxWidth = 89 * size;
        hitBoxHeight = 77 * size;
    }

    private enum JetAnimation {
        JET_ANIMATION_RIGHT1("Jet_right_1.png"), JET_ANIMATION_RIGHT2("Jet_right_2.png"),
        JET_ANIMATION_RIGHT3("Jet_right_3.png"), JET_ANIMATION_RIGHT4("Jet_right_4.png"),
        JET_ANIMATION_RIGHT5("Jet_right_5.png"), JET_ANIMATION_RIGHT6("Jet_right_6.png"),
        JET_ANIMATION_RIGHT7("Jet_right_7.png"),

        JET_ANIMATION_LEFT1("Jet_left_1.png"), JET_ANIMATION_LEFT2("Jet_left_2.png"),
        JET_ANIMATION_LEFT3("Jet_left_3.png"), JET_ANIMATION_LEFT4("Jet_left_4.png"),
        JET_ANIMATION_LEFT5("Jet_left_5.png"), JET_ANIMATION_LEFT6("Jet_left_6.png"),
        JET_ANIMATION_LEFT7("Jet_left_7.png");

        final String imageFile;

        JetAnimation(String imageFile) {
            this.imageFile = imageFile;
        }
    }
}
