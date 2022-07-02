package thd.gameobjects.movable;


import thd.game.managers.*;
import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.unmovable.Base;
import thd.gameobjects.unmovable.House;
import thd.gameobjects.unmovable.LandingPlace;
import thd.gameview.GameView;

import java.awt.*;
import java.util.ArrayList;

/**
 * The "main" Object, the player can control the Chopper.
 */
public class Chopper extends CollidableGameObject {


    private final GameObjectManager gameObjectManager;
    private ArrayList<GameObject> createdBullets;
    private final String emptyImage;

    /**
     * Um in "Inputmanager" darauf zugreifen zu können.
     */
    public final double shotsPerSecond;
    private boolean shooting;
    public double health;
    private double gas;

    /** Display the score. */
    public double score;

    /**
     * Ob der Chopper auf der Landefläche ist oder nicht. Essenziell wichtig für das Aufsammeln und Ausladen der Personen.
     */
    public boolean chopperIsOnLandingPlace;
    private House house;

    //Animation
    boolean facingLeft;
    boolean facingRight;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingUp;
    private boolean movingDown;
    public Status status;

    /**
     * Ob der Chopper nach unten schießt oder nicht.
     */
    public boolean shootDown;

    /**
     * Ob der Chopper von der Schwerkraft heruntergezogen wird oder nicht.
     */
    public boolean flyDown;
    private boolean landed;

    /**
     * return true if the chopper has been destroyed.
     */
    public boolean exploded;
    public boolean chopperHit;
    private House.FireAnimation fireAnimation;
    private BasicAnimation basicAnimation;


    public enum Status {STANDARD, DAMAGED, EXPLODING, EXPLODED}

    private enum BasicAnimation {
        //links
        STANDARD("Chopper_links.png"), ANIMATION1("Chopper_links animation1.png"), ANIMATION2("Chopper_links animation2.png"),
        ANIMATION3("Chopper_links animation3.png"), ANIMATION4("Chopper_links animation4.png"), ANIMATION5("Chopper_links animation5.png"),
        ANIMATION6("Chopper_links animation4.png"), ANIMATION7("Chopper_links animation3.png"), ANIMATION8("Chopper_links animation2.png"),
        ANIMATION9("Chopper_links animation1.png"),

        //rechts
        STANDARD_RIGHT("Chopper_rechts.png"), ANIMATION1_RIGHT("Chopper_rechts animation1.png"),
        ANIMATION2_RIGHT("Chopper_rechts animation2.png"), ANIMATION3_RIGHT("Chopper_rechts animation3.png"),
        ANIMATION4_RIGHT("Chopper_rechts animation4.png"), ANIMATION5_RIGHT("Chopper_rechts animation5.png"),
        ANIMATION6_RIGHT("Chopper_rechts animation4.png"), ANIMATION7_RIGHT("Chopper_rechts animation3.png"),
        ANIMATION8_RIGHT("Chopper_rechts animation2.png"), ANIMATION9_RIGHT("Chopper_rechts animation1.png"),

        //when the chopper hits the ground
        EXPLODED("Chopper exploded.png");

        private final String animationFile;

        BasicAnimation(String animationFile) {
            this.animationFile = animationFile;
        }
    }


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

        size = 1.5;
        health = 100.0;
        flyDown = false;
        landed = true;
        gas = 100.0;
        emptyImage = "empty.png";
        createdBullets = new ArrayList<>(100);
        shotsPerSecond = 10;
        shooting = true;
        shootDown = false;
        chopperIsOnLandingPlace = false;



        //hit box
        height = 21 * size - 10;
        width = 47.5 * size - 5;


        hitBoxOffsetX = 7;
        hitBoxOffsetY = 10;
        hitBoxHeight = height;
        hitBoxWidth = width;


        //health
        health = 3;

        //Animation
        status = Status.STANDARD;
        exploded = false;
        facingLeft = true;
        facingRight = false;
        movingLeft = false;
        movingDown = false;
        movingUp = false;
        movingRight = false;
        basicAnimation = BasicAnimation.STANDARD;
        fireAnimation = House.FireAnimation.FIRE1;


    }


    /**
     * Diese Methoden werden initialisiert, um den Code in der Klasse InputManager
     * lesbarer zu machen. Nun wird nur noch z.B. chopper.left() in InputManager
     * aufgerufen.
     */
    public void left() {
        rotationDown();

        for (GameObject o : gameObjectManager.getGameObjects()) {
            if ((o instanceof Base)) {
                if (o.getPosition().x > 5000 && position.x <= 432.0) {
                    return;
                }
            }
        }

        if (position.x > GameView.WIDTH / 2d - 50) {
            if (!gameView.timerIsActive("faster", this) && speedInPixel < 2) {
                gameView.activateTimer("faster", this, 200);
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
        rotationUp();

        for (GameObject o : gameObjectManager.getGameObjects()) {
            if ((o instanceof Base)) {
                if (o.getPosition().x < 630 && position.x > 678.0) {
                    return;
                }
            }
        }

        /*
        if (gameObjectManager.getBackgroundObjects().get(3).getPosition().x < 630 && position.x > 678.0) {
            return;
        }

         */
        if (position.x < GameView.WIDTH / 2d + 200) {
            if (!gameView.timerIsActive("faster", this) && speedInPixel < 2) {
                gameView.activateTimer("faster", this, 200);
            }
            position.right(speedInPixel);
        } else {
            gamePlayManager.moveWorldToLeft(speedInPixel);
        }
    }

    /**
     * Siehe left().
     */
    public void up() {
        if (position.y < -5) {
            return;
        } else {
            if (!gameView.timerIsActive("faster", this) && speedInPixel < 2) {
                gameView.activateTimer("faster", this, 200);
            }
            position.up(speedInPixel);
        }
    }

    /**
     * Siehe left().
     */
    public void down() {
        if (position.y >= 510) {
            status = Status.EXPLODED;
        } else {
            if (!gameView.timerIsActive("slower", this) && speedInPixel > 1) {
                gameView.activateTimer("slower", this, 200);
            }
            position.down(speedInPixel / 2);
        }
    }

    /**
     * Schießt.
     */
    public void shoot() {
        if (shooting && !chopperLanded()) {
            if (!gameView.timerIsActive("shoot", this)) {
                gameView.activateTimer("shoot", this, (long) (1000 / shotsPerSecond));
                GameObject o = new Bullet(gameView, gamePlayManager, this);
                createdBullets.add(o);
                gamePlayManager.spawn(o);

                if (facingLeft) {
                    o.changeDirectionTo("left");

                } else if (facingRight) {
                    o.changeDirectionTo("right");
                }
            }

            for (GameObject o : createdBullets) {
                if (o.outOfGame()) {
                    gamePlayManager.destroy(o);
                }
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


    private void rotationUp() {
        if (rotation <= 20) {
            rotation += 1;
            hitBoxOffsetY = 5;
        }

    }

    private void rotationDown() {
        if (rotation >= -20) {
            rotation -= 1;
            hitBoxOffsetY = 15;
        }
    }

    /**
     * Der Helikopter schaut nach rechts.
     */
    public void changeDirectionOfTheChopperToRight() {
        if (facingLeft) {
            basicAnimation = BasicAnimation.STANDARD_RIGHT;
        }

        facingLeft = false;
        facingRight = true;
        hitBoxOffsetX = 1;
    }

    /**
     * Der Helikopter schaut nach links.
     */
    public void changeDirectionOfTheChopperToLeft() {
        if (facingRight) {
            basicAnimation = BasicAnimation.STANDARD;
        }

        facingLeft = true;
        facingRight = false;
        hitBoxOffsetX = 7;
    }


    /**
     * When the chopper gets hit by an enemy bullet his health decreases by 1.
     */
    public void decreaseHealth() {
        if (!gameView.timerIsActive("decreaseHealth", this)) {
            gameView.activateTimer("decreaseHealth", this, 1500);
            health -= 1;
        }
    }

    /*
    public void increaseScore(double score) {
        this.score += score;
    }
     */


    /**
     * setzt alles Wichtige vom Chopper zurück.
     */
    public void reset() {
        position.x = 620;
        position.y = 400;
        hitBoxOffsetX = 7;
        hitBoxOffsetY = 10;
        rotation = 0;
        status = Status.STANDARD;
        basicAnimation = BasicAnimation.STANDARD;
        exploded = false;
        chopperHit = false;
    }


    /**
     * Fügt den Chopper zu GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(basicAnimation.animationFile, position.x, position.y, size, rotation);
    }


    /**
     * Wenn der Chopper in der Luft ist und er sich nicht bewegt, soll er langsam herunterfliegen
     */
    private void chopperFlyDown() {
        if (position.y <= 400) {
            flyDown = true;
        } else if (position.y > 400) {
            flyDown = false;
        }

        if (flyDown) {
            position.down(0.5);
        }
    }


    @Override
    public void updateStatus() {
        flyDownAnimationReset();
        chopperFlyDown();
        basicAnimation();
        damageAnimation();
        crashLanding();

        if (chopperHit && !chopperLanded() && facingLeft) {
            position.down(1.3);
            position.left(1);
        } else if(chopperHit && !chopperLanded() && facingRight) {
            position.down(1.3);
            position.right(1);
        }

    }

    /**
     * If a game object collides with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {
        if (other.getClass() == LandingPlace.class && gamePlayManager.getPickedUpPeopleSize() > 0) {
            chopperIsOnLandingPlace = true;
        }
    }


    //Animations

    /**
     * Wenn der chopper gelandet ist, soll er nur noch hochfliegen können. Siehe Input Manager
     *
     * @return true, wenn der Chopper gelandet ist. False, wenn er gerade fliegt.
     */
    public boolean chopperLanded() {
        if (position.y >= 400) {
            landed = true;
        } else if (position.y <= 400) {
            landed = false;
        }

        return landed;
    }

    private void crashLanding() {
        if ((rotation >= 10 || rotation <= -10) && chopperLanded()) {
            status = Status.EXPLODED;
            rotation = 3;
        }
    }



    public void chopperHit() {
        status = Status.EXPLODED;
        chopperHit = true;
    }




    private void flyDownAnimationReset() {
        if (flyDown) {
            if (rotation < 0) {
                rotation += 0.2;
            } else if (rotation > 0) {
                rotation -= 0.2;
            }
        }
    }

    /**
     * Damit sich die Propeller drehen
     * Der Alarm gibt an, wie schnell sich der Propeller dreht
     */
    private void basicAnimation() {
        if (!exploded) {
            if (!gameView.alarmIsSet("basicAnimation", this)) {
                gameView.setAlarm("basicAnimation", this, 50);
            } else if (gameView.alarm("basicAnimation", this)) {
                if (facingLeft) {
                    basicAnimationLeft();
                } else if (facingRight) {
                    basicAnimationRight();
                }
            }
        }
    }


    private void basicAnimationLeft() {
        switch (basicAnimation) {
            case STANDARD:
                basicAnimation = BasicAnimation.ANIMATION1;
                break;
            case ANIMATION1:
                basicAnimation = BasicAnimation.ANIMATION2;
                break;
            case ANIMATION2:
                basicAnimation = BasicAnimation.ANIMATION3;
                break;
            case ANIMATION3:
                basicAnimation = BasicAnimation.ANIMATION4;
                break;
            case ANIMATION4:
                basicAnimation = BasicAnimation.ANIMATION5;
                break;
            case ANIMATION5:
                basicAnimation = BasicAnimation.ANIMATION6;
                break;
            case ANIMATION6:
                basicAnimation = BasicAnimation.ANIMATION7;
                break;
            case ANIMATION7:
                basicAnimation = BasicAnimation.ANIMATION8;
                break;
            case ANIMATION8:
                basicAnimation = BasicAnimation.ANIMATION9;
                break;
            case ANIMATION9:
                basicAnimation = BasicAnimation.STANDARD;
                break;
            default:
        }
    }

    private void basicAnimationRight() {
        switch (basicAnimation) {
            case STANDARD_RIGHT:
                basicAnimation = BasicAnimation.ANIMATION1_RIGHT;
                break;
            case ANIMATION1_RIGHT:
                basicAnimation = BasicAnimation.ANIMATION2_RIGHT;
                break;
            case ANIMATION2_RIGHT:
                basicAnimation = BasicAnimation.ANIMATION3_RIGHT;
                break;
            case ANIMATION3_RIGHT:
                basicAnimation = BasicAnimation.ANIMATION4_RIGHT;
                break;
            case ANIMATION4_RIGHT:
                basicAnimation = BasicAnimation.ANIMATION5_RIGHT;
                break;
            case ANIMATION5_RIGHT:
                basicAnimation = BasicAnimation.ANIMATION6_RIGHT;
                break;
            case ANIMATION6_RIGHT:
                basicAnimation = BasicAnimation.ANIMATION7_RIGHT;
                break;
            case ANIMATION7_RIGHT:
                basicAnimation = BasicAnimation.ANIMATION8_RIGHT;
                break;
            case ANIMATION8_RIGHT:
                basicAnimation = BasicAnimation.ANIMATION9_RIGHT;
                break;
            case ANIMATION9_RIGHT:
                basicAnimation = BasicAnimation.STANDARD_RIGHT;
                break;
            default:
        }
    }

    private void damageAnimation() {
        switch (status) {
            case EXPLODED:
                basicAnimation = BasicAnimation.EXPLODED;
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
     * Damit die Klasse "Bullet" die Rotation des Choppers sehen kann.
     *
     * @return gibt die Rotation zurück
     */
    double rotation() {
        return this.rotation;
    }


}
