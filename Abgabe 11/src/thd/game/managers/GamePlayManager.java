package thd.game.managers;

import thd.game.level.Level;
import thd.game.level.Level1;
import thd.game.level.Level2;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameobjects.movable.Chopper;
import thd.gameobjects.movable.Jet;
import thd.gameobjects.movable.People;
import thd.gameobjects.movable.Tank;
import thd.gameobjects.unmovable.Base;
import thd.gameobjects.unmovable.House;
import thd.gameobjects.unmovable.LandingPlace;
import thd.gameview.GameView;
import java.util.LinkedList;

import static thd.game.managers.FileManager.writeDifficultyToDisc;


/**
 * Manages the flow of the game.
 */
public class GamePlayManager {

    private final GameView gameView;
    /**
     * Um zu bestimmen, wann das Spiel vorbei ist.
     */
    boolean gameOver;
    private GameObjectManager gameObjectManager;
    private final LinkedList<GameObject> createdTanks;
    private final LinkedList<GameObject> pickedUpPeople;
    private final LinkedList<GameObject> unloadedPeople;



    private LevelManager levelManager;
    private Level currentLevel;
    private int counter;

    GamePlayManager(GameView gameView) {
        this.gameView = gameView;
        gameOver = false;
        createdTanks = new LinkedList<>();
        pickedUpPeople = new LinkedList<>();
        unloadedPeople = new LinkedList<>();
        levelManager = new LevelManager(Level.Difficulty.STANDARD);
        currentLevel = levelManager.levels.getFirst();
    }


    /**
     * Steuert den Spielverlauf.
     */
    void updateGamePlay() {
        if (gameOver()) {
            initializeGame();
        }


    }

    private void initializeLevel() {
        for (GameObject o : gameObjectManager.getGameObjects()) {
            if (!(o instanceof Chopper)) {
                destroy(o);
            }
        }

        if (currentLevel instanceof Level1) {
            gameObjectManager.chopper.reset();
            gameObjectManager.background.setBackgroundImage("background.png");
            gameObjectManager.addGameObject(new House(gameView, this, 100, 350));
            gameObjectManager.addGameObject(new Tank(gameView, this));
            gameObjectManager.addGameObject(new Jet(gameView, this));

            gameObjectManager.addGameObject(new LandingPlace(gameView, this));
            gameObjectManager.addGameObject(new Base(gameView, this));


            //gameObjectManager.overlay.showMessage("      Level 1 \n At the mountains", 2);

        }
        if (currentLevel instanceof Level2) {

            gameObjectManager.chopper.reset();
            gameObjectManager.background.setBackgroundImage("background_level2.png");


            gameObjectManager.addGameObject(new Tank(gameView, this));
            gameObjectManager.addGameObject(new Jet(gameView, this));
        }
    }

    private void initializeGame() {
        Level.Difficulty difficulty = FileManager.readDifficultyFromDisc();
        writeDifficultyToDisc(difficulty);

        levelManager = new LevelManager(difficulty);

        initializeLevel();
        createdTanks.clear();
    }

    private boolean gameOver() {
        return false;
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
        gameObjectManager.moveWorld(pixels);
    }

    /**
     * Moves the world to right.
     *
     * @param pixels how fast it moves
     */
    public void moveWorldToRight(double pixels) {
        gameObjectManager.moveWorld(-pixels);
    }


    private void spawnTanks() {
        if (!gameView.alarmIsSet("spawn", this)) {
            gameView.setAlarm("spawn", this, 5000);
        } else if (gameView.alarm("spawn", this)) {
            GameObject o = new Tank(gameView, this);
            createdTanks.add(o);
            spawn(o);
        }
    }




    /** Was passieren soll, wenn der Chopper von einer Kugel getroffen wird. */
    public void chopperHasBeenHit() {
        for (GameObject o : gameObjectManager.getGameObjects()) {
            if (o instanceof Chopper) {
                Chopper chopper = (Chopper) o;
                chopper.decreaseHealth();
            }
        }

    }

    /**
     * Chopper sammelt die Leute ein.
     * @param g das bestimmte Objekt
     */
    public void pickUpPeople(GameObject g) {
        if (pickedUpPeople.size() <= 7 && !gameObjectManager.chopper.chopperIsOnLandingPlace && gameObjectManager.chopper.chopperLanded()) {
            pickedUpPeople.add(g);
            destroy(g);
        }
    }

    /**
     * Man kann nicht mehr die selben Objekte aus "pickedUpPeople" hinzufügen, da man die position schlecht verändern kann.
     * --> einfache Lösung: neue Objekte mit Position des Choppers hinzufügen
     */
    public void unloadPeople() {
            for (GameObject g: pickedUpPeople) {
                if (!gameView.timerIsActive("unloadPeople", this) && gameObjectManager.chopper.chopperIsOnLandingPlace) {
                    gameView.activateTimer("unloadPeople", this, 600);
                    pickedUpPeople.remove(g);
                    GameObject o = new People(gameView, this, gameObjectManager.chopper.getPosition().x + 60, gameObjectManager.chopper.getPosition().y + 13);
                    unloadedPeople.add(o);
                    spawn(o);
                }
            }
    }


    /**
     * Wenn eine Person in die Base geht.
     * @param g die bestimmte Person.
     */
    public void storePeopleInBase(GameObject g) {
            destroy(g);
            //erhöhe score
    }

    /**
     * Wie groß die Liste "pickedUpPeopleSize" ist auszugeben, ohne die Liste public zu machen.
     * @return die größe der Liste
     */
    public int pickedUpPeopleSize() {
        return pickedUpPeople.size();
    }

    /** Gibt die Position des Choppers zurück, wichtig, um zu berechnen, wo die Gegner hin schießen müssen.
     * @return gibt die Position des Choppers zurück. */
    public Position positionChopper() {
        for (GameObject o : gameObjectManager.getGameObjects()) {
            if (o instanceof Chopper) {
                return o.getPosition();
            }
        }
        return null;
    }

    /**
     * Um in der Klasse People auf "ChopperLanded" zuzugreifen.
     * @return true wenn der Chopper gelandet ist, false wenn nicht.
     */
    public boolean chopperLanded() {
        return gameObjectManager.chopper.chopperLanded();
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