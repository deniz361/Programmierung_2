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
        position.x = 100;
        position.y = 100;
    }


    /** Adds the clouds to the canvas. */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("Cloud.png",position.x, position.y,2,0);
        gameView.addImageToCanvas("Cloud.png",position.x + 200,position.y -50,2,0);
        gameView.addImageToCanvas("Cloud.png",position.x + 300,position.y,2,0);
        gameView.addImageToCanvas("Cloud.png",position.x + 500,position.y - 50,2,0);
        gameView.addImageToCanvas("Cloud.png",position.x + 600,position.y,2,0);

    }
}
