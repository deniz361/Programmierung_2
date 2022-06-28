package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.movable.Chopper;
import thd.gameview.GameView;


/** The landing place for the Chopper. */
public class LandingPlace extends CollidableGameObject {

    /** Wenn der Chopper auf dem Landeplatz landet, steigen die Passagiere aus. */
    private boolean landed;

    /**
     * Mindestanforderung, die jedes GameObject haben muss.
     * @param gameView        to interact with game view.
     * @param gamePlayManager to control the game flow.
     */
    public LandingPlace(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        landed = false;
        position.x = 620;
        position.y = 430;
        size = 3.5;

        //hitbot
        hitBoxOffsetX = 0;
        hitBoxOffsetY = 0;
        hitBoxHeight = 9 * size;
        hitBoxWidth = 59 * size;
    }

    /**
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {
        if (other.getClass() == Chopper.class && gamePlayManager.pickedUpPeopleSize() > 0) {
            gamePlayManager.unloadPeople();
        }
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("Landeplatz.png",position.x, position.y, size,0);
    }
}
