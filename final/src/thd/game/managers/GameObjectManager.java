package thd.game.managers;

import thd.game.utilities.Sorter;
import thd.game.utilities.TooManyGameObjectsException;
import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.*;
import thd.gameview.GameView;
import thd.gameobjects.base.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Manages all the Game Objects.
 */
public class GameObjectManager {

    private LinkedList<GameObject> gameObjects;
    private final LinkedList<GameObject> backgroundObjects;
    private final ArrayList<GameObject> toAdd;
    private final ArrayList<GameObject> toRemove;
    private final ArrayList<GameObject> toAddBackground;

    Chopper chopper;
    Overlay overlay;
    Background background;

    protected GameObjectManager(GameView gameView, GamePlayManager gamePlayManager) {
        toAdd = new ArrayList<>(120);
        toAddBackground = new ArrayList<>(120);
        toRemove = new ArrayList<>(120);
        gameObjects = new LinkedList<>();
        backgroundObjects = new LinkedList<>();

        chopper = new Chopper(gameView, gamePlayManager, this);
        background = new Background(gameView, gamePlayManager);
        overlay = new Overlay(gameView, gamePlayManager);

        backgroundObjects.add(background);
        backgroundObjects.add(new Cloud(gameView, gamePlayManager));

        /*
        backgroundObjects.add(new LandingPlace(gameView, gamePlayManager));
        backgroundObjects.add(new Base(gameView, gamePlayManager));

         */


        gameObjects.add(chopper);
        gameObjects.add(overlay);
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
        //es ist das Problem aufgetreten, dass wenn man einmal Leute abgeliefert hat, dass man nicht mehr neue
        // aufsammeln konnte. Deswegen dieses Konstrukt:
        for (CollidableGameObject g: collidables) {
            if (g instanceof LandingPlace) {
                if (!(chopper.collidesWith(g))) {
                    chopper.chopperIsOnLandingPlace = false;
                }
            }
        }


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

    private void modifyGameObjectsList() {
        if (toAdd.size() != 0) {
            gameObjects.sort(new Sorter());
        }

        gameObjects.removeAll(toRemove);
        gameObjects.addAll(toAdd);
        backgroundObjects.addAll(toAddBackground);
        toAdd.clear();
        toRemove.clear();

        if (gameObjects.size() > 300) {
            throw new TooManyGameObjectsException("Too many game Objects");
        }
    }

    /**
     * Moves the background
     *
     * @param shiftX shift in x
     */
    void moveWorld(double shiftX) {
        for (GameObject o : backgroundObjects) {
            o.worldHasMoved(shiftX);
        }

        for (GameObject foreground : gameObjects) {
            if (!(foreground instanceof Chopper)) {
                //if ((foreground instanceof Tank) || foreground instanceof House || foreground instanceof Jet || foreground instanceof People
                //|| foreground instanceof Base || foreground instanceof LandingPlace || foreground instanceof Bullet){
                    foreground.worldHasMoved(shiftX);
                //}
            }
            if (foreground instanceof Jet) {
                for (Position p: ((Jet) foreground).bezierPoints) {
                    if (p != null) {
                        p.x -= shiftX;
                    }
                }
            }
        }
    }


    /**
     * Returns all GameObjects.
     *
     * @return all game objects
     */
    public LinkedList<GameObject> getGameObjects() {
        return gameObjects;
    }



    /*
    public LinkedList<GameObject> getBackgroundObjects() {
        return backgroundObjects;
    }

     */
}
