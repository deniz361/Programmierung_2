package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;


/** The Background. */
public class Background extends GameObject {

    private String backgroundImage;


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
        backgroundImage = "background.png";
    }

    /**
     * Fügt das Spielobject in GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(backgroundImage, position.x, position.y, 1, 0);
        gameView.addImageToCanvas(backgroundImage, position.x - 1840, position.y, 1, 0);
    }


    /** Changes the background image.
     * @param backgroundImage the new background image */
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
