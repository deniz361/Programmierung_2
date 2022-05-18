package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

public class Background extends GameObject {
    /**
     * Mindestanforderung, die jedes GameObject haben muss.
     *
     * @param gameView        to interact with game view.
     * @param gamePlayManager to control the game flow.
     */
    public Background(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        position.x = -800;
        position.y = 0;
    }

    /**
     * Fügt das Spielobject in GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("background.png",position.x,position.y,1,0);
        gameView.addImageToCanvas("background.png",position.x - 1840,position.y,1,0);
    }
}
