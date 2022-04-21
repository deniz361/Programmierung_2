package thd.game.managers;

import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.Cloud;
import thd.gameview.GameView;
import thd.gameobjects.base.*;

import java.util.ArrayList;
import java.util.LinkedList;

class GameObjectManager {

    protected LinkedList<GameObject> gameObjects;
    protected ArrayList<GameObject> toAdd;
    protected ArrayList<GameObject> toRemove;
    protected Chopper chopper;


    protected GameObjectManager(GameView gameView) {

        toAdd = new ArrayList<>(120);
        toRemove = new ArrayList<>(120);
        gameObjects = new LinkedList<>();
        gameObjects.add(new Jet(gameView, 0.5));
        gameObjects.add(new Tank(gameView, 0.5));
        //gameObjects.add(new Bullet(gameView, 1, 100, 50));
        gameObjects.add(new Cloud(gameView));
        gameObjects.add(new CringeObject(gameView));



        chopper = new Chopper(gameView);

    }

    protected void updateGameObjects() {
        modifyGameObjectsList();
        for (GameObject gameObject : gameObjects) {
            gameObject.updateStatus();
            gameObject.updatePosition();
            gameObject.addToCanvas();
        }

        chopper.updateStatus();
        chopper.addToCanvas();
        chopper.updatePosition();
    }

    protected void addGameObject(GameObject gameObject) {
        toAdd.add(gameObject);
    }

    protected void removeGameObject(GameObject gameObject) {
        toRemove.add(gameObject);
    }

    private void modifyGameObjectsList() {
        gameObjects.addAll(toAdd);
        gameObjects.removeAll(toRemove);
        toAdd.clear();
        toRemove.clear();
    }
}
