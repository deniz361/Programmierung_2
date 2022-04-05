package thd.gameobjects.movable;

import thd.gameobjects.base.Position;
import thd.gameview.GameView;


/**
 * Die Klasse zu dem Objekt Tank.
 */
public class Tank {

    private final Position position;
    private double speedInPixel;
    private final GameView gameView;
    private String tank;


    /**
     * Erstellt das Objekt "Tank".
     * @param gameView Damit Gameview weitergegeben werden kann
     * @param speed Die Geschwindigkeit vom Tank
     */
    public Tank(GameView gameView, double speed) {
        this.gameView = gameView;
        position = new Position(500, GameView.HEIGHT / 2.0);
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
     * @param size Die Größe des Spielobjekts
     * @param rotation Um wie viel sich das Objekt drehen soll
     */
    public void addToCanvas(int size, int rotation) {
        gameView.addBlockImageToCanvas(tank, position.x, position.y, size, rotation);
    }


    /**
     * Bewegt das Objekt.
     */
    public void updatePosition() {
        position.left(speedInPixel);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Tank: ");
        //stringBuilder.append(position);

        return stringBuilder.toString() + position;
        //stringBuilder.append((int) Math.round(position.x)).append(",").append((int) Math.round(position.y)).append(")");
    }


}
