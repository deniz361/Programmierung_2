package thd.gameobjects.unmovable;


import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

/**
 * Pictures the clouds.
 */
public class Cloud extends GameObject {

    /**
     * Instantiates the clouds.
     *
     * @param gameView        GameView for GUI uses
     * @param gamePlayManager Gameplay flow managing
     */
    public Cloud(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        position.x = 100;
        position.y = 100;
    }


    /**
     * Adds the clouds to the canvas.
     */
    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("Cloud.png", position.x, position.y, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x + 200, position.y - 50, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x + 300, position.y, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x + 500, position.y - 50, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x + 600, position.y, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x + 800, position.y - 100, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x + 880, position.y + 60, 2, 0);


        // linke Seite
        gameView.addImageToCanvas("Cloud.png", position.x - 200, position.y, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 380, position.y - 70, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 500, position.y, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 700, position.y, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 800, position.y - 50, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 1000, position.y + 50, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 1200, position.y - 60, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 1400, position.y, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 1500, position.y - 80, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 1700, position.y + 60, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 1800, position.y + 20, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 1920, position.y - 20, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 2200, position.y, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 2400, position.y - 50, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 2600, position.y + 20, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 2800, position.y - 20, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 2900, position.y + 30, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 3100, position.y - 60, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 3200, position.y + 20, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 3400, position.y, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 3660, position.y + 10, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 3800, position.y - 40, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 4000, position.y + 50, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 4150, position.y - 10, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 4250, position.y, 2, 0);
        gameView.addImageToCanvas("Cloud.png", position.x - 4400, position.y + 50, 2, 0);

    }
}
