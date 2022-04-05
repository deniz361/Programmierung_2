package thd.gameobjects.unmovable;

import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameview.GameView;

public class Flag extends GameObject {

    public Flag(GameView gameView) {
        super(gameView);
        this.gameView = gameView;
        position = new Position(200,200);
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("AmericanFlag.png", position.x, position.y, 0.1,0);
    }
}
