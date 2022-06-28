package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;


/** The landing place for the Chopper. */
public class LandingPlace extends GameObject {

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
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("Landeplatz.png",position.x, position.y, size,0);
    }
}
