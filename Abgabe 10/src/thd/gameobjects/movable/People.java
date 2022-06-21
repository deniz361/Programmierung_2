package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameview.GameView;


/**
 * Die Menschen, die gerettet werden sollen.
 */
public class People extends CollidableGameObject implements AutoMovable {

    //private String imageFile;
    private WalkingAnimation walkingAnimation;

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
        speedInPixel = 1;
        size = 1;
        width = 7 * size + 3;
        height = 27 * size;
        hitBoxOffsetX = 10;
        hitBoxOffsetY = 5;
        hitBoxHeight = height;
        hitBoxWidth = width;
        //imageFile = "Human standard.png";
        walkingAnimation = WalkingAnimation.STANDARD;
    }

    /**
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {
        if (other.getClass() == BulletEnemy.class) {
            gamePlayManager.destroy(this);
        }
    }

    /**
     * Fügt das Spielobject in GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(walkingAnimation.imageFile, position.x, position.y, size, rotation);

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
                    walkingAnimation = WalkingAnimation.STANDARD;
                    break;
                default:
            }
        }
    }

    @Override
    public void updateStatus() {
        walkingAnimation();
    }

    @Override
    public void updatePosition() {
        if (!gameView.alarmIsSet("updatePosition", this)) {
            gameView.setAlarm("updatePosition", this, 50);
        } else if (gameView.alarm("updatePosition", this)) {
            position.right(speedInPixel);
        }

    }

    private enum WalkingAnimation {
        STANDARD("Human standard.png"), WALKING1("human walking1.png"),
        WALKING2("human walking 2.png");

        private String imageFile;

        WalkingAnimation(String imageFile) {
            this.imageFile = imageFile;
        }
    }
}
