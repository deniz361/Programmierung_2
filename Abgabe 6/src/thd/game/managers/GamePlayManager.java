package thd.game.managers;

import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.Tank;
import thd.gameview.GameView;

import java.util.ArrayList;
import java.util.Random;


/** Manages the flow of the game. */
public class GamePlayManager {

    private final GameView gameView;
    private GameObjectManager gameObjectManager;
    private final ArrayList<GameObject> createdTanks;
    private final Random random;

    GamePlayManager(GameView gameView) {
        this.gameView = gameView;
        createdTanks = new ArrayList<>(100);
        random = new Random();
    }


    /** Steuert den Spielverlauf.*/
    void updateGamePlay() {
        spawnAndDestroy();
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




    private void spawnAndDestroy() {
        if (!gameView.timerIsActive("spawn", this)) {
            gameView.activateTimer("spawn", this, 1000);
            GameObject o = new Tank(gameView,this);
            createdTanks.add(o);
            spawn(o);
        }

        if (!gameView.timerIsActive("destroy", this)) {
            gameView.activateTimer("destroy", this, 1500);
            GameObject o = createdTanks.get(random.nextInt(createdTanks.size()));
            createdTanks.remove(o);
            destroy(o);
        }
    }
    /** Um den GameObjectManager als Instanzvariable hinzuzufügen.
     * @param gameObjectManager der GameObjectManager
     */
    void setGameObjectManager(GameObjectManager gameObjectManager) {
        this.gameObjectManager = gameObjectManager;
    }


}
