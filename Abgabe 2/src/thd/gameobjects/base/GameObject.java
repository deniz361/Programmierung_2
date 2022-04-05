package thd.gameobjects.base;

import thd.gameview.GameView;

public class GameObject {

    public GameView gameView;
    public Position position;
    public double speedInPixel;
    public double size;
    public double rotation;
    public double width;
    public double height;

    public GameObject(GameView gameView) {
        this.gameView = gameView;
        position = new Position();
    }

    public void updatePosition() {

    }

    public void addToCanvas() {

    }

    public Position getPosition() {
        return position;
    }

}
