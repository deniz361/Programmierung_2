package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.movable.Bullet;
import thd.gameobjects.movable.People;
import thd.gameview.GameView;

/**
 * Das Haus, in dem sich die Menschen befinden, die gerettet werden müssen.
 */
public class House extends CollidableGameObject {

    private boolean broken;
    private String image;
    private FireAnimation fireAnimation;
    private People people;
    private boolean burning;
    private boolean exploded;
    private final int maxPeople;
    private int counter;
    private boolean once;
    private boolean onceFire;


    /**
     * Mindestanforderung, das jedes GameObject haben muss.
     *
     * @param gameView        GameView.
     * @param gamePlayManager GamePlayManager.
     * @param positionX X Koordinate, wo das Haus sein soll.
     * @param positionY Y Koordinate, wo das Haus sein soll.
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
        people = new People(gameView, gamePlayManager, this.position.x, this.position.y + height / 2d + 10);
        maxPeople = 7;
        counter = 0;
        once = true;
        onceFire = true;
    }

    @Override
    public void updateStatus() {
        if (broken) {
            if (onceFire) {
                if (!gameView.alarmIsSet("spawnPeople", this)) {
                    gameView.setAlarm("spawnPeople", this, 30000);
                    burning = true;
                } else if (gameView.alarm("spawnPeople", this)) {
                    burning = false;
                    onceFire = false;
                }
            }
            fireAnimation();
            spawnPeople();
        }
    }

    private void spawnPeople() {
        if (counter < maxPeople) {
            if (!once) {
                if (!gameView.alarmIsSet("spawnDelay", this)) {
                    gameView.setAlarm("spawnDelay", this, 1500);
                } else if (gameView.alarm("spawnDelay", this)) {
                    gamePlayManager.spawn(new People(gameView, gamePlayManager, this.position.x + 50, this.position.y + height / 2d + 15));
                    counter++;
                }
            }
            if (once) {
                if (!gameView.alarmIsSet("delayAfterHouseHasBeenShot", this)) {
                    gameView.setAlarm("delayAfterHouseHasBeenShot", this, 100);
                } else if (gameView.alarm("delayAfterHouseHasBeenShot", this)) {
                    gamePlayManager.spawn(new People(gameView, gamePlayManager, this.position.x + 50, this.position.y + height / 2d + 15));
                    once = false;
                    counter++;
                }
            }
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



    public enum FireAnimation {
        FIRE1("Fire 1.png"), FIRE2("Fire 2.png"), FIRE3("Fire 3.png");

        public final String file;

        FireAnimation(String file) {
            this.file = file;
        }
    }

    public void fireAnimation() {
        //if (burning) {
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
        //}
    }

    /**
     * Um von außen abzufragen, ob das Haus schon zerstört wurde oder nicht.
     * @return gibt True zurück, wenn das Haus zerstört wurde.
     */
    public boolean isBroken() {
        return broken;
    }
}
