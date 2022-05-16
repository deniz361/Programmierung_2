package thd.gameobjects.base;

import thd.game.managers.GamePlayManager;
import thd.gameview.GameView;

/**Jedes Gameobject erbt von dieser Klasse.*/
public abstract class GameObject {

    protected final GameView gameView;
    protected final Position position;
    protected final GamePlayManager gamePlayManager;
    protected double speedInPixel;
    protected double size;
    protected double rotation;
    protected double width;
    protected double height;

    /**
     * Mindestanforderung, das jedes GameObject haben muss.
     *
     * @param gameView to interact with game view.
     * @param gamePlayManager to control the game flow.
     */
    public GameObject(GameView gameView, GamePlayManager gamePlayManager) {
        this.gameView = gameView;
        position = new Position();
        this.gamePlayManager = gamePlayManager;
    }


    /**
     * Verschiebung der Spielwelt
     * @param shiftX Verschiebung in X-Richtung
     * @param shiftY Verschiebung in Y-Richtung
     */
    public void worldHasMoved(double shiftX, double shiftY) {
        position.x -= shiftX;
        position.y -= shiftY;
    }



    /**Fügt das Spielobject in GameView hinzu.*/
    public abstract void addToCanvas();

    /**
     * Statusupdate.
     */
    public void updateStatus() {

    }


    /**
     * Changes the variable flyFromLeftToRight.
     *
     * @param direction input left or right
     */
    public void changeDirectionTo(String direction) {

    }

    /**Gibt die Position zurück.
     * @return gibt die Position zurück.*/
    public Position getPosition() {
        return position;
    }

}
