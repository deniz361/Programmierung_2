package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.AutoMovable;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.unmovable.House;
import thd.gameview.GameView;

import java.awt.*;

public class People extends CollidableGameObject implements AutoMovable {

    private String imageFile;

    /**
     * Crates a new GameObject.
     *
     * @param gameView        Window to show the GameObject on.
     * @param gamePlayManager Controls the gameplay.
     */
    public People(GameView gameView, GamePlayManager gamePlayManager, double positionX, double positionY) { // maybe House house in die Signatur
        super(gameView, gamePlayManager);
        position.x = positionX;
        position.y = positionY;
        size = 1;
        width = 7 * size;
        height = 27 * size;
        hitBoxHeight = height;
        hitBoxWidth = width;
        imageFile = "Human standard.png";
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
        //gameView.addImageToCanvas();

    }

    @Override
    public void updatePosition() {
    }
}
