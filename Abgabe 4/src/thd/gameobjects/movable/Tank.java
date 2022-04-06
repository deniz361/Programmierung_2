package thd.gameobjects.movable;

import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameview.GameView;


/**
 * Die Klasse zu dem Objekt Tank.
 */
public class Tank extends GameObject {


    private String tank;
    private boolean flyFromLeftToRight;



    /**
     * Erstellt das Objekt "Tank".
     * @param gameView Damit Gameview weitergegeben werden kann
     * @param speed Die Geschwindigkeit vom Tank
     */
    public Tank(GameView gameView, double speed) {
        super(gameView);
        width = 20;
        position.x = 500;
        position.y = GameView.HEIGHT / 2.0;
        speedInPixel = speed;
        this.tank = "     oOoOO\n" +
                    "LLLLLOOooOO\n" +
                    "     ooOOo\n" +
                    " oOOoOOooOOo\n" +
                    "oLLLLLLLLLLLo\n" +
                    "LWLWLWLWLWLWL\n" +
                    " LLLLLLLLLLL";

    }

    /**
     *
     *  Neue Farben hinzufügen:.
     *  Zeile 1449 GameView
     */
    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(tank, position.x, position.y, 5, 0);
    }


    /**
     * Bewegt das Objekt.
     */
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Tank: ");
        //stringBuilder.append(position);

        return stringBuilder.toString() + position;
        //stringBuilder.append((int) Math.round(position.x)).append(",").append((int) Math.round(position.y)).append(")");
    }


}
