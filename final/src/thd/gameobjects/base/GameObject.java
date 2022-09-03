package thd.gameobjects.base;

import thd.game.managers.GamePlayManager;
import thd.gameview.GameView;

/**Jedes Gameobject erbt von dieser Klasse.*/
public abstract class GameObject implements Comparable<GameObject> {

    protected final GameView gameView;
    protected final Position position;
    protected final GamePlayManager gamePlayManager;
    protected double speedInPixel;
    protected double size;
    protected double rotation;
    protected double width;
    protected double height;
    private double gameWidth;

    /**
     * Sortierung der gameobjects
     */
    public int positionInSort;

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
        gameWidth = (1840 * 2) - 100;    //größe des Hintergrunds ist 1840, der Hintergrund ist 2-mal Nebeneinander. -100, damit er nicht komplett zum Rand geht
    }


    /**
     * Verschiebung der Spielwelt.
     *
     * @param shiftX Verschiebung in X-Richtung
     */
    public void worldHasMoved(double shiftX) {
        position.x -= shiftX;
    }

    /**
     * Um herauszufinden, ob ein GameObject noch im Spielfenster ist.
     * @return gibt "true" zurück, wenn das GameObject nicht mehr im Spiel ist
     */
    public boolean outOfGame() {
        return position.x > GameView.WIDTH || position.x < 0 || position.y < 0 || position.y > GameView.HEIGHT;
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
