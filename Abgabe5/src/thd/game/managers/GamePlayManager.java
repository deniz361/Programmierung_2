package thd.game.managers;

import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

public class GamePlayManager {

    GameView gameView;
    GameObjectManager gameObjectManager;

    GamePlayManager(GameView gameView) {
        this.gameView = gameView;
    }

    void updateGamePlay() {

    }

    void spawn(GameObject gameObject) {
        gameObjectManager.addGameObject(gameObject);
    }

    void destroy(GameObject gameObject) {
        gameObjectManager.removeGameObject(gameObject);
    }

    public void setGameObjectManager(GameObjectManager gameObjectManager) {
        this.gameObjectManager = gameObjectManager;
    }
}
