package thd.gameobjects.base;

import thd.gameview.GameView;

/**Jedes Gameobject erbt von dieser Klasse.*/
public class GameObject {

    /**Gameview.*/
    public GameView gameView;
    /**Die Position.*/
    public Position position;
    /**Geschwindigkeit.*/
    public double speedInPixel;
    /**Größe des Spielobjekts.*/
    private double size;
    /**Die Rotation des Spielobjekts.*/
    private double rotation;
    /**Die Breite des Spielobjekts.*/
    public double width;
    /**Die Höhe des Spielobjekts.*/
    public double height;

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
    protected void addToCanvas() {

    }

    /**Gibt die Position zurück.
     * @return gibt die Position zurück.*/
    public Position getPosition() {
        return position;
    }

}
