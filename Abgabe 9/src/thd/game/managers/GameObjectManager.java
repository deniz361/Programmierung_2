package thd.game.managers;

import thd.game.utilities.TooManyGameObjectsException;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;
import thd.gameview.GameView;
import thd.gameobjects.base.*;

import java.util.ArrayList;
import java.util.LinkedList;

/** Manages all the Game Objects. */
public class GameObjectManager {

    private LinkedList<GameObject> gameObjects;
    private final LinkedList<GameObject> backgroundObjects;
    private final ArrayList<GameObject> toAdd;
    private final ArrayList<GameObject> toRemove;
    private final ArrayList<GameObject> toAddBackground;

    Chopper chopper;
    Background background;

    protected GameObjectManager(GameView gameView, GamePlayManager gamePlayManager) {
        toAdd = new ArrayList<>(120);
        toAddBackground = new ArrayList<>(120);
        toRemove = new ArrayList<>(120);
        gameObjects = new LinkedList<>();
        backgroundObjects = new LinkedList<>();

        chopper = new Chopper(gameView, gamePlayManager, this);
        background = new Background(gameView, gamePlayManager);

        backgroundObjects.add(background);
        backgroundObjects.add(new Cloud(gameView, gamePlayManager));
        backgroundObjects.add(new LandingPlace(gameView,gamePlayManager));
        backgroundObjects.add(new Base(gameView, gamePlayManager));
        gameObjects.add(chopper);
        }

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

    void chopperToFront() {
        for(GameObject g : gameObjects) {
            if (g instanceof Chopper) {
                removeGameObject(g);
            }
        }
    }


    void addGameObject(GameObject gameObject) {
        toAdd.add(gameObject);
    }

    void addUnmovableGameObject(GameObject gameObject) {
        toAddBackground.add(gameObject);
    }

    void removeGameObject(GameObject gameObject) {
        toRemove.add(gameObject);
    }

    private void modifyGameObjectsList() {
        //chopperToFront();
        gameObjects.addAll(toAdd);
        backgroundObjects.addAll(toAddBackground);
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
     */
    void moveWorld(double shiftX) {
        for (GameObject o : backgroundObjects) {
            o.worldHasMoved(shiftX);
        }

        for (GameObject foreground : gameObjects) {
            if (!(foreground instanceof Chopper)) {
                if ((foreground instanceof Tank && !((Tank) foreground).doNotDisturb) || foreground instanceof House) {
                    foreground.worldHasMoved(shiftX);

                }
            }
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
