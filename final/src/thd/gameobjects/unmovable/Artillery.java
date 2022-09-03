package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.Bullet;
import thd.gameobjects.movable.BulletEnemy;
import thd.gameview.GameView;

import java.util.LinkedList;


/**
 * The class Turret.
 */
public class Artillery extends CollidableGameObject {

    private DifferentDirections differentDirections;
    private double distanceToChopper;
    private double shotsPerSecond;

    private LinkedList<GameObject> createdBullets;
    private boolean broken;
    private boolean exploding;
    private boolean burning;

    /**
     * Crates a new GameObject.
     *
     * @param gameView        Window to show the GameObject on.
     * @param gamePlayManager Controls the gameplay.
     * @param positionX       spawn position.
     * @param positionY       spawn position.
     */
    public Artillery(GameView gameView, GamePlayManager gamePlayManager, double positionX, double positionY) {
        super(gameView, gamePlayManager);
        width = 83;
        height = 86;
        size = 0.4;
        shotsPerSecond = 1.5;
        exploding = false;
        burning = false;
        broken = false;

        createdBullets = new LinkedList<>();

        position.x = positionX;
        position.y = positionY;

        //hitbox

        hitBoxHeight = height * size;
        hitBoxWidth = width * size;

        differentDirections = DifferentDirections.RIGHT;

        positionInSort = 95;
    }


    @Override
    public void updateStatus() {
        if (broken) {
            if (exploding) {
                if (!gameView.alarmIsSet("explosionAnimation", this)) {
                    gameView.setAlarm("explosionAnimation", this, 2000);
                    differentDirections = DifferentDirections.BROKEN;
                    position.y += 15;
                } else if (gameView.alarm("explosionAnimation", this)) {
                    exploding = false;
                }
            }
        } else {
            changeDirection();
            shoot();
        }

    }

    /**
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {
        if (other.getClass() == Bullet.class) {
            broken = true;
            exploding = true;
            gamePlayManager.adjustScore(150.0);
        }

    }

    private void shoot() {
        if (distanceToChopper > -540 && distanceToChopper < 500) {
            if (!gameView.timerIsActive("shootArtillery", this)) {
                gameView.activateTimer("shootArtillery", this, (long) (1000 / shotsPerSecond));
                if (differentDirections == DifferentDirections.RIGHT) {
                    GameObject o = new BulletEnemy(gameView, gamePlayManager, this.position.x + 20, this.position.y + 5, -3.5, 0.75);
                    createdBullets.add(o);
                    gamePlayManager.spawn(o);
                } else if (differentDirections == DifferentDirections.RIGHT_MID) {
                    GameObject o = new BulletEnemy(gameView, gamePlayManager, this.position.x + 20, this.position.y + 5, -3.5, 1.75);
                    createdBullets.add(o);
                    gamePlayManager.spawn(o);
                } else if (differentDirections == DifferentDirections.FRONT) {
                    GameObject o = new BulletEnemy(gameView, gamePlayManager, this.position.x + 20, this.position.y + 5, 0, 3.5);
                    createdBullets.add(o);
                    gamePlayManager.spawn(o);
                } else if (differentDirections == DifferentDirections.LEFT_MID) {
                    GameObject o = new BulletEnemy(gameView, gamePlayManager, this.position.x + 20, this.position.y + 5, 3.5, 1.75);
                    createdBullets.add(o);
                    gamePlayManager.spawn(o);
                } else if (differentDirections == DifferentDirections.LEFT) {
                    GameObject o = new BulletEnemy(gameView, gamePlayManager, this.position.x + 20, this.position.y + 5, 3.5, 0.75);
                    createdBullets.add(o);
                    gamePlayManager.spawn(o);
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
     * Fügt das Spielobject in GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(differentDirections.imagefile, position.x, position.y, size, rotation);
    }


    private void changeDirection() {
        distanceToChopper = position.x - gamePlayManager.positionChopper().x;

        if (distanceToChopper > 400) {
            differentDirections = DifferentDirections.LEFT;
        } else if (distanceToChopper < 450 && distanceToChopper > 100) {
            differentDirections = DifferentDirections.LEFT_MID;
        } else if (distanceToChopper < 100 && distanceToChopper > -100) {
            differentDirections = DifferentDirections.FRONT;
        } else if (distanceToChopper < -100 && distanceToChopper > -450) {
            differentDirections = DifferentDirections.RIGHT_MID;
        } else if (distanceToChopper < -450) {
            differentDirections = DifferentDirections.RIGHT;
        }
    }


    private enum DifferentDirections {

        RIGHT("artillerie2_rechts.png"), RIGHT_MID("artillerie2_rechts_mitte.png"),
        FRONT("artillerie2_vorne.png"), LEFT_MID("artillerie2 _ links_rechts.png"),
        LEFT("artillerie2_links.png"), BROKEN("artillerie2_broken.png");

        private String imagefile;

        DifferentDirections(String imagefile) {
            this.imagefile = imagefile;
        }
    }

    @Override
    public int compareTo(GameObject o) {
        return Integer.compare(positionInSort, o.positionInSort);
    }
}
