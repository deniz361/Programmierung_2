package thd.gameobjects.base;

import thd.gameview.GameView;

import java.awt.*;

/**Jedes Gameobject erbt von dieser Klasse.*/
public class GameObject {

    protected final GameView gameView;
    protected final Position position;
    protected double speedInPixel;
    protected double size;
    protected double rotation;
    protected double width;
    protected double height;

    /**Mindestanforderung, das jedes GameObject haben muss.
     * @param gameView gibt gameview weier.*/
    public GameObject(GameView gameView) {
        this.gameView = gameView;
        position = new Position();
        size = 0;
        rotation = 0;
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

    /**Gibt die Position zurück.
     * @return gibt die Position zurück.*/
    public Position getPosition() {
        return position;
    }

}
