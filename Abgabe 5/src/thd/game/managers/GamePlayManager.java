package thd.game.managers;

import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

public class GamePlayManager {

    private GameView gameView;
    private GameObjectManager gameObjectManager;

    GamePlayManager(GameView gameView) {
        this.gameView = gameView;
    }


    /** Steuert den Spielverlauf.*/
    void updateGamePlay() {

    }


    // Diese beiden Methoden delegieren den Aufruf an die passenden Methoden im GameObjectManager

    /** Erzeugt Spielobjekte */
    void spawn(GameObject gameObject) {
        gameObjectManager.addGameObject(gameObject);
    }

    /** Löscht Spielobjecte */
    void destroy(GameObject gameObject) {
        gameObjectManager.addGameObject(gameObject);
    }



    /** Um den GameObjectManager als Instanzvariable hinzuzufügen.
     * @param gameObjectManager der GameObjectManager
     */
    public void setGameObjectManager(GameObjectManager gameObjectManager) {
        this.gameObjectManager = gameObjectManager;
    }


}
