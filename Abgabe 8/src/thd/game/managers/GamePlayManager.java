package thd.game.managers;

import thd.game.level.Level;
import thd.game.level.Level1;
import thd.game.level.Level2;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameobjects.movable.Chopper;
import thd.gameobjects.movable.Jet;
import thd.gameobjects.movable.Tank;
import thd.gameview.GameView;

import java.util.ArrayList;


/**
 * Manages the flow of the game.
 */
public class GamePlayManager {

    private final GameView gameView;
    private GameObjectManager gameObjectManager;
    private final ArrayList<GameObject> createdTanks;
    final LevelManager levelManager;
    Level currentLevel;

    GamePlayManager(GameView gameView) {
        this.gameView = gameView;
        createdTanks = new ArrayList<>(100);
        levelManager = new LevelManager(Level.Difficulty.STANDARD);
        currentLevel = levelManager.levels.getFirst();
    }


    /**
     * Steuert den Spielverlauf.
     */
    void updateGamePlay() {
        if (!gameView.timerIsActive("level", this)) {
            gameView.activateTimer("level", this, 2000);
            currentLevel = levelManager.nextLevel();
            setGameObjectManager(gameObjectManager);
            //spawnTanks();
        }
    }

    void initializeLevel() {
        for (GameObject o : gameObjectManager.getGameObjects()) {
            if (!(o instanceof Chopper)) {
                destroy(o);
            }
        }

        if (currentLevel instanceof Level1) {
            gameObjectManager.chopper.resetPosition();
            gameObjectManager.background.setBackgroundImage("background.png");
            gameObjectManager.addGameObject(new Tank(gameView, this));
            gameObjectManager.addGameObject(new Jet(gameView, this));
        } if (currentLevel instanceof Level2) {

            gameObjectManager.chopper.resetPosition();
            gameObjectManager.background.setBackgroundImage("background_level2.png");


            gameObjectManager.addGameObject(new Tank(gameView, this));
            gameObjectManager.addGameObject(new Jet(gameView, this));
        }


    }



    /**
     * Spawns game objects.
     *
     * @param gameObject game object to spawn
     */
    public void spawn(GameObject gameObject) {
        gameObjectManager.addGameObject(gameObject);
    }

    /**
     * Destroys game objects.
     *
     * @param gameObject game object to destroy
     */
    public void destroy(GameObject gameObject) {
        gameObjectManager.removeGameObject(gameObject);
    }


    /**
     * Moves the world to left.
     *
     * @param pixels how fast it moves
     */
    public void moveWorldToLeft(double pixels) {
        gameObjectManager.moveWorld(pixels, 0);
    }

    /**
     * Moves the world to right.
     *
     * @param pixels how fast it moves
     */
    public void moveWorldToRight(double pixels) {
        gameObjectManager.moveWorld(-pixels, 0);
    }


    private void spawnTanks() {
        if (!gameView.timerIsActive("spawn", this)) {
            gameView.activateTimer("spawn", this, 10000);
            GameObject o = new Tank(gameView, this);
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


    /**
     * Um den GameObjectManager als Instanzvariable hinzuzufügen.
     *
     * @param gameObjectManager der GameObjectManager
     */
    void setGameObjectManager(GameObjectManager gameObjectManager) {
        this.gameObjectManager = gameObjectManager;
        initializeLevel();
    }
}