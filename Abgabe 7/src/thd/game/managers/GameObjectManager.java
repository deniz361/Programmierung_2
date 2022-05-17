package thd.game.managers;

import thd.game.utilities.TooManyGameObjectsException;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.Base;
import thd.gameobjects.unmovable.Cloud;
import thd.gameview.GameView;
import thd.gameobjects.base.*;

import java.util.ArrayList;
import java.util.LinkedList;

/** Manages all the Game Objects. */
public class GameObjectManager {

    private final LinkedList<GameObject> gameObjects;
    private final LinkedList<GameObject> backgroundObjects;
    private final ArrayList<GameObject> toAdd;
    private final ArrayList<GameObject> toRemove;


    protected GameObjectManager(GameView gameView, GamePlayManager gamePlayManager) {
        toAdd = new ArrayList<>(120);
        toRemove = new ArrayList<>(120);
        gameObjects = new LinkedList<>();
        backgroundObjects = new LinkedList<>();


        backgroundObjects.add(new Cloud(gameView, gamePlayManager));
        backgroundObjects.add(new Base(gameView, gamePlayManager));
        gameObjects.add(new Jet(gameView, gamePlayManager));
        gameObjects.add(new Tank(gameView, gamePlayManager));
        gameObjects.add(new Chopper(gameView, gamePlayManager, this));}

    void updateGameObjects() {
        for (GameObject backgroundObject : backgroundObjects) {
            backgroundObject.addToCanvas();
        }

        modifyGameObjectsList();
        ArrayList<CollidableGameObject> collidables = new ArrayList<>(gameObjects.size());
        for (GameObject gameObject : gameObjects) {
            gameObject.updateStatus();
            if (gameObject instanceof AutoMovable) {
                ((AutoMovable) gameObject).updatePosition();
            }
            gameObject.addToCanvas();
            if (gameObject instanceof CollidableGameObject) {
                collidables.add((CollidableGameObject) gameObject);
            }
        }
        detectCollisionsAndNotifyGameObjects(collidables);
    }

    private void detectCollisionsAndNotifyGameObjects(ArrayList<CollidableGameObject> collidables) {
        for (int index = 0; index < collidables.size(); index++) {
            for (int other = index + 1; other < collidables.size(); other++) {
                if (collidables.get(index).collidesWith(collidables.get(other))) {
                    collidables.get(index).reactToCollision(collidables.get(other));
                    collidables.get(other).reactToCollision(collidables.get(index));
                }
            }
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

    /**
     * Moves the background
     * @param shiftX shift in x
     * @param shiftY shift in y
     */
    void moveWorld(double shiftX, double shiftY) {
        for (GameObject background : backgroundObjects) {
            background.worldHasMoved(shiftX, shiftY);
        }
    }


    /** Returns all GameObjects.
     * @return all game objects */
    LinkedList<GameObject> getGameObjects() {
        return gameObjects;
    }

    /** Returns all background GameObjects.
     * @return all background game objects */
    public LinkedList<GameObject> getBackgroundObjects() {
        return backgroundObjects;
    }
}
