package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameview.GameView;


/**
 * The class Turret.
 */
public class Turret extends CollidableGameObject {
    /**
     * Crates a new GameObject.
     *
     * @param gameView        Window to show the GameObject on.
     * @param gamePlayManager Controls the gameplay.
     * @param positionX spawn position.
     * @param positionY spawn position.
     */
    public Turret(GameView gameView, GamePlayManager gamePlayManager, double positionX, double positionY) {
        super(gameView, gamePlayManager);
    }

    /**
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {

    }

    /**
     * Fügt das Spielobject in GameView hinzu.
     */
    @Override
    public void addToCanvas() {

    }
}
