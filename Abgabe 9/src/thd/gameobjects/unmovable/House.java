package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.movable.Bullet;
import thd.gameview.GameView;


public class House extends CollidableGameObject {

    private String image;
    boolean broken;
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
    }

    public boolean isBroken() {
        return broken;
    }
}
