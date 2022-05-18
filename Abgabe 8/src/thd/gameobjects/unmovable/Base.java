package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;


/**
 * The Base.
 */
public class Base extends GameObject {

    private double blocksize;

    /** Instantiates the clouds.
     * @param gameView GameView for GUI uses
     * @param gamePlayManager Gameplay flow managing */
    public Base(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        position.x = 700;
        position.y = 350;
        blocksize = 2;
        width = 59 * blocksize;
        height = 46 * blocksize;
    }


    /**
     * Fügt das Spielobject in GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("Base - Choplifter.png",position.x, position.y, blocksize,rotation);
    }
}
