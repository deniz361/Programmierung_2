package thd.game.managers;

import thd.game.utilities.TooManyGameObjectsException;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.Cloud;
import thd.gameview.GameView;
import thd.gameobjects.base.*;

import java.util.ArrayList;
import java.util.LinkedList;

class GameObjectManager {

    private final LinkedList<GameObject> gameObjects;
    private final ArrayList<GameObject> toAdd;
    private final ArrayList<GameObject> toRemove;


    protected GameObjectManager(GameView gameView, GamePlayManager gamePlayManager) {

        toAdd = new ArrayList<>(120);
        toRemove = new ArrayList<>(120);
        gameObjects = new LinkedList<>();
        gameObjects.add(new Jet(gameView, gamePlayManager));
        gameObjects.add(new Tank(gameView, gamePlayManager));
        gameObjects.add(new Cloud(gameView, gamePlayManager));
        gameObjects.add(new Chopper(gameView, gamePlayManager));
    }

    void updateGameObjects() throws TooManyGameObjectsException{
        modifyGameObjectsList();
        for (GameObject gameObject : gameObjects) {
            gameObject.updateStatus();
            gameObject.updatePosition();
            gameObject.addToCanvas();
        }
    }

    void addGameObject(GameObject gameObject) {
        toAdd.add(gameObject);
    }

    void removeGameObject(GameObject gameObject) {
        toRemove.add(gameObject);
    }

    private void modifyGameObjectsList() throws TooManyGameObjectsException{
        gameObjects.addAll(toAdd);
        gameObjects.removeAll(toRemove);
        toAdd.clear();
        toRemove.clear();

        if (gameObjects.size() > 300) {
            throw new TooManyGameObjectsException("Too many game Objects");
        }
    }

    LinkedList<GameObject> getGameObjects() {
        return gameObjects;
    }
}
