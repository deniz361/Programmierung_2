package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;


/** Pictures the Tank.*/
public class Tank extends GameObject {


    private String tank;
    private boolean flyFromLeftToRight;



    /**
     * Instantiates a new Tank.
     * @param gameView GameView for GUI uses
     * @param gamePlayManager gameplay flow managing
     */
    public Tank(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        width = 20;
        position.x = 500;
        position.y = GameView.HEIGHT / 2.0;
        speedInPixel = 0.5;
        this.tank = "     oOoOO\n" +
                    "LLLLLOOooOO\n" +
                    "     ooOOo\n" +
                    " oOOoOOooOOo\n" +
                    "oLLLLLLLLLLLo\n" +
                    "LWLWLWLWLWLWL\n" +
                    " LLLLLLLLLLL";

        // Neue Farben hinzufügen:.
        // Zeile 1449 GameView
    }

    /** Adds the Tank to the canvas. */
    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(tank, position.x, position.y, 2.5, 0);
        //gameView.addImageToCanvas("tank_left.png", position.x,position.y,0.03,0);
    }


    /** Moves the Object. */
    @Override
    public void updatePosition() {
        if ((position.x + width / 2) > GameView.WIDTH) {
            flyFromLeftToRight = false;
        } else if ((position.x - width / 2) < 0) {
            flyFromLeftToRight = true;
        }

        if (flyFromLeftToRight) {
            position.right(speedInPixel);
        } else {
            position.left(speedInPixel);
        }
    }


    /** Maybe for debugging. */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Tank: ");
        return stringBuilder.toString() + position;
    }


}
