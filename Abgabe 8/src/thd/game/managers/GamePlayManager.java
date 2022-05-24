package thd.game.managers;

import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameobjects.movable.Chopper;
import thd.gameobjects.movable.Tank;
import thd.gameview.GameView;

import java.util.ArrayList;
import java.util.Random;


/** Manages the flow of the game. */
public class GamePlayManager {

    private final GameView gameView;
    private GameObjectManager gameObjectManager;
    private final ArrayList<GameObject> createdTanks;

    GamePlayManager(GameView gameView) {
        this.gameView = gameView;
        createdTanks = new ArrayList<>(100);
    }


    /** Steuert den Spielverlauf.*/
    void updateGamePlay() {
        //spawnTanks();
    }


    /** Spawns game objects.
     * @param gameObject game object to spawn */
    public void spawn(GameObject gameObject) {
        gameObjectManager.addGameObject(gameObject);
    }

    /** Destroys game objects.
     * @param gameObject game object to destroy */
    public void destroy(GameObject gameObject) {
        gameObjectManager.removeGameObject(gameObject);
    }


    /**
     * Moves the world to left.
     * @param pixels how fast it moves
     */
    public void moveWorldToLeft(double pixels) {
        gameObjectManager.moveWorld(pixels, 0);
    }

    /**
     * Moves the world to right.
     * @param pixels how fast it moves
     */
    public void moveWorldToRight(double pixels) {
        gameObjectManager.moveWorld(-pixels, 0);
    }



    private void spawnTanks() {
        if (!gameView.timerIsActive("spawn", this)) {
            gameView.activateTimer("spawn", this, 10000);
            GameObject o = new Tank(gameView,this);
            createdTanks.add(o);
            spawn(o);
        }
    }

    public void chopperHasBeenHit() {
        Chopper chopper = (Chopper) gameObjectManager.getGameObjects().get(0);
        chopper.decreaseHealth();
    }

    public Position getPositonChopper() {
        Chopper chopper = (Chopper) gameObjectManager.getGameObjects().get(0);
        return chopper.getPosition();
    }


    /** Um den GameObjectManager als Instanzvariable hinzuzufügen.
     * @param gameObjectManager der GameObjectManager
     */
    void setGameObjectManager(GameObjectManager gameObjectManager) {
        this.gameObjectManager = gameObjectManager;
    }


}
