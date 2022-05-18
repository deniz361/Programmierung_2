package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;


/** The landing place for the Chopper */
public class LandingPlace extends GameObject {

    /** Wenn der Chopper auf dem Landeplatz landet, steigen die Passagiere aus */
    public boolean landed;

    /**
     * Mindestanforderung, ddie jedes GameObject haben muss.
     * @param gameView        to interact with game view.
     * @param gamePlayManager to control the game flow.
     */
    public LandingPlace(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        landed = false;
    }

    @Override
    public void addToCanvas() {

    }
}
