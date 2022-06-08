package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.unmovable.House;
import thd.gameview.GameView;

import java.awt.*;

public class People extends CollidableGameObject {
    /**
     * Crates a new GameObject.
     *
     * @param gameView        Window to show the GameObject on.
     * @param gamePlayManager Controls the gameplay.
     */
    public People(GameView gameView, GamePlayManager gamePlayManager) { // maybe House house in die Signatur
        super(gameView, gamePlayManager);
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
        gameView.addRectangleToCanvas(position.x, position.y, width, height,2,false, Color.BLACK);
    }
}
