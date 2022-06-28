package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameview.GameView;


/**
 * The Base.
 */
public class Base extends CollidableGameObject {

    /**
     * Instantiates the clouds.
     *
     * @param gameView        GameView for GUI uses
     * @param gamePlayManager Gameplay flow managing
     */
    public Base(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        position.x = 700;
        position.y = 350;
        size = 2;
        width = 59 * size;
        height = 46 * size;

        //hitbox
        hitBoxOffsetX = width - 13 * size;
        hitBoxOffsetY = height - 10 * size - 7;
        hitBoxHeight = 13 * size;
        hitBoxWidth = 10 * size;
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
        gameView.addImageToCanvas("Base - Choplifter.png", position.x, position.y, size, rotation);
    }
}
