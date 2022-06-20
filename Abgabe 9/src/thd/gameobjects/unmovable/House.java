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
    private boolean burning;
    private boolean exploded;
    private final int maxPeople;
    private int counter;

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
        burning = false;
        exploded = false;
        fireAnimation = FireAnimation.FIRE1;
        people = new People(gameView, gamePlayManager, position.x + width / 2d - 10, position.y + height / 2d + 10);
        maxPeople = 7;
        counter = 0;
    }

    @Override
    public void updateStatus() {
        if (broken) {
            if (!gameView.alarmIsSet("spawnPeople", this)) {
                gameView.setAlarm("spawnPeople", this, 15000);
                burning = true;
            } else if (gameView.alarm("spawnPeople", this)) {
                burning = false;
            }
            fireAnimation();
            spawnPeople();
        }


    }

    private void fireAnimation() {
        if (burning) {
            if (!gameView.alarmIsSet("fireAnimation", this)) {
                gameView.setAlarm("fireAnimation", this, 100);
            } else if (gameView.alarm("fireAnimation", this)) {
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

        if (burning) {
            gameView.addImageToCanvas(fireAnimation.file, position.x + width / 2d - 38,position.y + height / 2d - 22,2,rotation);
        }
    }

    private void spawnPeople() {
        if (counter < maxPeople) {
            if (!gameView.alarmIsSet("spawn", this)) {
                gameView.setAlarm("spawn", this, 100);
            } else if (gameView.alarm("spawn", this)) {
                gamePlayManager.spawnUnmovable(people);
                counter++;
            }
        }

        /*
        if (counter < maxPeople) {

            if (!gameView.alarmIsSet("spawnPeople", this)) {
                gameView.setAlarm("spawnPeople", this, 100);
            } else if (gameView.alarm("spawnPeople", this)) {
                gamePlayManager.spawn(people);
                System.out.println("spawn people");
                counter++;
            }
        }

         */
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
