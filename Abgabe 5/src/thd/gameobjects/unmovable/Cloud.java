package thd.gameobjects.unmovable;


import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

/** Pictures the clouds. */
public class Cloud extends GameObject {

    /** Instantiates the clouds.
     * @param gameView GameView for GUI uses
     * @param gamePlayManager Gameplay flow managing */
    public Cloud(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);

    }


    /** Adds the clouds to the canvas. */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("Cloud.png",100,100,2,0);
        gameView.addImageToCanvas("Cloud.png",300,50,2,0);
        gameView.addImageToCanvas("Cloud.png",400,100,2,0);
        gameView.addImageToCanvas("Cloud.png",600,50,2,0);
        gameView.addImageToCanvas("Cloud.png",700,100,2,0);

    }
}
