package thd.gameobjects.base;

import thd.game.managers.GamePlayManager;
import thd.gameview.GameView;

/**Jedes Gameobject erbt von dieser Klasse.*/
public class GameObject {

    protected final GameView gameView;
    protected final Position position;
    protected double speedInPixel;
    protected double size;
    protected double rotation;
    protected double width;
    protected double height;
    protected GamePlayManager gamePlayManager;

    /**
     * Mindestanforderung, das jedes GameObject haben muss.
     *
     * @param gameView gibt gameview weier.
     */
    public GameObject(GameView gameView, GamePlayManager gamePlayManager) {
        this.gameView = gameView;
        position = new Position();
        this.gamePlayManager = gamePlayManager;
    }

    /**Aktualisiert die Position des Spielobjects.*/
    public void updatePosition() {

    }

    /**Fügt das Spielobject in GameView hinzu.*/
    public void addToCanvas() {
    }

    /** Statusupdate. */
    public void updateStatus() {

    }

    public void changeDirectionTo(String direction) {

    }

    /**Gibt die Position zurück.
     * @return gibt die Position zurück.*/
    public Position getPosition() {
        return position;
    }

}
