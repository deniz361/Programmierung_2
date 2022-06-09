package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.movable.Bullet;
import thd.gameobjects.movable.People;
import thd.gameview.GameView;


public class House extends CollidableGameObject {

    boolean broken;
    private String image;
    private FireAnimation fireAnimation;
    private People people;

    /**
     * Mindestanforderung, das jedes GameObject haben muss.
     *
     * @param gameView        to interact with game view.
     * @param gamePlayManager to control the game flow.
     */
    public House(GameView gameView, GamePlayManager gamePlayManager, double positionX, double positionY) {
        super(gameView, gamePlayManager);
        position.x = positionX;
        position.y = positionY;
        width = 133;
        height = 77;
        hitBoxHeight = height;
        hitBoxWidth = width;
        size = 0.75;
        image = "house.png";
        broken = false;
        fireAnimation = FireAnimation.FIRE1;
        people = new People(gameView, gamePlayManager, position.x, position.y);
    }

    @Override
    public void updateStatus() {
        if (broken) {
            fireAnimation();
            spawnPeople();
        }
    }

    private void fireAnimation() {
        if (!gameView.alarmIsSet("basicAnimation", this)) {
            gameView.setAlarm("basicAnimation", this, 100);
        } else if (gameView.alarm("basicAnimation", this)) {
            switch (fireAnimation) {
                case FIRE1:
                    fireAnimation = FireAnimation.FIRE2;
                    break;
                case FIRE2:
                    fireAnimation = FireAnimation.FIRE3;
                    break;
                case FIRE3:
                    fireAnimation = FireAnimation.FIRE1;
                    break;
                default:

            }
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
            image = "house_broken.png";
            broken = true;
        }
    }

    /**
     * Fügt das Spielobject in GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(image, position.x, position.y, size, rotation);

        if (broken) {
            gameView.addImageToCanvas(fireAnimation.file, position.x + width / 2d - 40, position.y + height / 2d - 22, 2, rotation);
        }
    }

    private void spawnPeople() {
        gamePlayManager.spawn(people);
    }

    public boolean isBroken() {
        return broken;
    }

    private enum FireAnimation {
        FIRE1("Fire 1.png"), FIRE2("Fire 2.png"), FIRE3("Fire 3.png");

        private final String file;

        FireAnimation(String file) {
            this.file = file;
        }
    }
}
