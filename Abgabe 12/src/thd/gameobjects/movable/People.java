package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.unmovable.Base;
import thd.gameview.GameView;

import java.util.Random;


/**
 * Die Menschen, die gerettet werden sollen.
 */
public class People extends CollidableGameObject implements AutoMovable {

    //private String imageFile;
    private WalkingAnimation walkingAnimation;
    private WalkingAnimationLeft walkingAnimationLeft;
    private boolean facingLeft;
    private boolean changeDirection;

    private Random random;
    private long duration;
    private double distanceToChopper;
    public boolean runToBase;

    /**
     * Erstellt das GameObject.
     *
     * @param gameView        Das Fenster wo das Spielobjekt angezeigt wird.
     * @param gamePlayManager Bestimmt den Spielablauf.
     * @param positionY Y Koordinate des Spawnpunkts.
     * @param positionX X Koordinate des Spawnpunkts.
     */
    public People(GameView gameView, GamePlayManager gamePlayManager, double positionX, double positionY) { // maybe House house in die Signatur
        super(gameView, gamePlayManager);
        position.x = positionX;
        position.y = positionY;
        speedInPixel = 2;
        size = 1;
        width = 7 * size + 3;
        height = 27 * size;
        hitBoxOffsetX = 10;
        hitBoxOffsetY = 5;
        hitBoxHeight = height;
        hitBoxWidth = width;
        //imageFile = "Human standard.png";
        walkingAnimation = WalkingAnimation.STANDARD;
        walkingAnimationLeft = WalkingAnimationLeft.STANDARD_LEFT;
        facingLeft = false;
        changeDirection = false;
        random = new Random();
        runToBase = false;

    }

    /**
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {
        if (other.getClass() == BulletEnemy.class) {
            gamePlayManager.addLostPeople(this);
            gamePlayManager.adjustScore(-250.0);
            gamePlayManager.destroy(this);
        }

        if (other.getClass() == Chopper.class) {
            gamePlayManager.pickUpPeople(this);
        }

        if (other.getClass() == Base.class) {
            gamePlayManager.storePeopleInBase(this);
        }
    }

    /**
     * Fügt das Spielobjekt in GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        if (facingLeft) {
            gameView.addImageToCanvas(walkingAnimationLeft.imageFile, position.x, position.y, size, rotation);
        } else {
            gameView.addImageToCanvas(walkingAnimation.imageFile, position.x, position.y, size, rotation);
        }


    }

    @Override
    public void updateStatus() {
        if (facingLeft) {
            walkingAnimationLeft();
        } else {
            walkingAnimation();
        }

    }

    @Override
    public void updatePosition() {
        if (runToBase) {
            walkingRight(0.5);
        } else {
            distanceToChopper = distanceToChopper();

            if (distanceToChopper < 250 && distanceToChopper > -250 && gamePlayManager.chopperLanded()) {
                if (distanceToChopper > 0) {
                    walkingRight(0.5);
                } else {
                    walkingLeft(0.5);
                }
            } else {
                randomChangeOfDirection();
            }
        }
    }

    private double distanceToChopper() {
        return gamePlayManager.positionChopper().x - position.x;
    }

    private void randomChangeOfDirection() {
        if (!gameView.timerIsActive("walkingPeople", this)) {
            duration = random.nextInt(3000) + 1000;
            gameView.activateTimer("walkingPeople", this, duration);
            changeDirection = !changeDirection;
        } else {
            if (changeDirection) {
                walkingLeft(0);
            } else {
                walkingRight(0);
            }
        }

    }

    private void walkingLeft(double additionalSpeed) {
        if (!gameView.alarmIsSet("walkingLeftPeople", this)) {
            gameView.setAlarm("walkingLeftPeople", this, 50);
        } else if (gameView.alarm("walkingLeftPeople", this)) {
            position.left(speedInPixel + additionalSpeed);
            facingLeft = true;
        }

    }

    private void walkingRight(double additionalSpeed) {
        if (!gameView.alarmIsSet("walkingRightPeople", this)) {
            gameView.setAlarm("walkingRightPeople", this, 50);
        } else if (gameView.alarm("walkingRightPeople", this)) {
            position.right(speedInPixel + additionalSpeed);
            facingLeft = false;
        }
    }

    private void walkingAnimation() {
        if (!gameView.alarmIsSet("walkingAnimation", this)) {
            gameView.setAlarm("walkingAnimation", this, 100);
        } else if (gameView.alarm("walkingAnimation", this)) {
            switch (walkingAnimation) {
                case STANDARD:
                    walkingAnimation = WalkingAnimation.WALKING1;
                    break;
                case WALKING1:
                    walkingAnimation = WalkingAnimation.WALKING2;
                    break;
                case WALKING2:
                    walkingAnimation = WalkingAnimation.WALKING3;
                    break;
                case WALKING3:
                    walkingAnimation = WalkingAnimation.STANDARD;
                    break;
                default:
            }
        }
    }

    private void walkingAnimationLeft() {
        if (!gameView.alarmIsSet("walkingAnimationLeft", this)) {
            gameView.setAlarm("walkingAnimationLeft", this, 100);
        } else if (gameView.alarm("walkingAnimationLeft", this)) {
            switch (walkingAnimationLeft) {
                case STANDARD_LEFT:
                    walkingAnimationLeft = WalkingAnimationLeft.WALKING1_LEFT;
                    break;
                case WALKING1_LEFT:
                    walkingAnimationLeft = WalkingAnimationLeft.WALKING2_LEFT;
                    break;
                case WALKING2_LEFT:
                    walkingAnimationLeft = WalkingAnimationLeft.WALKING3_LEFT;
                    break;
                case WALKING3_LEFT:
                    walkingAnimationLeft = WalkingAnimationLeft.STANDARD_LEFT;
                    break;
                default:
            }
        }
    }



    private enum WalkingAnimationLeft {
        STANDARD_LEFT("Human standard left.png"), WALKING1_LEFT("human walking1 left.png"),
        WALKING2_LEFT("human walking 2 left.png"), WALKING3_LEFT("human walking1 left.png");

        private String imageFile;

        WalkingAnimationLeft(String imageFile) {
            this.imageFile = imageFile;
        }
    }

    private enum WalkingAnimation {
        STANDARD("Human standard.png"), WALKING1("human walking1.png"),
        WALKING2("human walking 2.png"), WALKING3("human walking1.png");

        private String imageFile;

        WalkingAnimation(String imageFile) {
            this.imageFile = imageFile;
        }
    }
}
