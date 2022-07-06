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
import thd.gameobjects.unmovable.*;
import thd.gameview.GameView;
import thd.screens.EndScreen;
import thd.screens.StartScreen;

import java.util.LinkedList;


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
    private final LinkedList<GameObject> savedPeople;
    private final LinkedList<GameObject> lostPeople;


    private double landingplacePositionXWhenHit;
    private double distanceToBase;
    private boolean chopperHit;


    private LevelManager levelManager;
    private Level currentLevel;
    private boolean first;

    private boolean nextLevel;

    GamePlayManager(GameView gameView) {
        this.gameView = gameView;
        gameOver = false;
        createdTanks = new LinkedList<>();
        pickedUpPeople = new LinkedList<>();
        unloadedPeople = new LinkedList<>();
        savedPeople = new LinkedList<>();
        lostPeople = new LinkedList<>();
        levelManager = new LevelManager(Level.Difficulty.STANDARD);
        currentLevel = levelManager.levels.getFirst();
        first = true;
        nextLevel = true;
    }


    /**
     * Steuert den Spielverlauf.
     */
    void updateGamePlay() {
        if (first) {
            initializeGame();
            first = false;
        } else {
            if (gameOver()) {
                initializeGame();
            } else {
                /*
                if (!gameView.alarmIsSet("level", this)) {
                    gameView.setAlarm("level", this, 3000);
                } else if (gameView.alarm("level", this)) {

                 */
                if (returnSavedPeopleSize() >= 7 && nextLevel) { //eventuell nextLevel anpassen
                    try {
                        nextLevel = false;
                        currentLevel = levelManager.nextLevel();
                        initializeLevel();



                    } catch (NoMoreLevelsAvailableException ignore) {
                        //gameOver = true;
                        gameView.showEndScreen("Score: " + returnScore());
                        initializeGame();

                    }
                }
            }
        }
        if (chopperHit) {
            if (!gameView.alarmIsSet("reset", this)) {
                gameView.setAlarm("reset", this, 3000);
            } else if (gameView.alarm("reset", this)) {
                gameObjectManager.chopper.reset();
                moveWorldToLeft(distanceToBase);
                chopperHit = false;
            }


        }


    }

    private void initializeLevel() {
        for (GameObject o : gameObjectManager.getGameObjects()) {
            if (!(o instanceof Chopper) && !(o instanceof Overlay)) {
                destroy(o);
            }
        }

        if (currentLevel instanceof Level1) {
            gameObjectManager.overlay.showMessage("Level 1", 1);
            gameObjectManager.chopper.reset();
            gameObjectManager.background.setBackgroundImage("background.png");
            //House
            gameObjectManager.addGameObject(new House(gameView, this, -800, 350));
            gameObjectManager.addGameObject(new House(gameView, this, -2100, 350));
            gameObjectManager.addGameObject(new House(gameView, this, -3800, 350));


            //Artillery
            gameObjectManager.addGameObject(new Artillery(gameView, this, -100, 420));
            gameObjectManager.addGameObject(new Artillery(gameView, this, -300, 420));
            gameObjectManager.addGameObject(new Artillery(gameView, this, -1500, 420));
            gameObjectManager.addGameObject(new Artillery(gameView, this, -2800, 420));
            gameObjectManager.addGameObject(new Artillery(gameView, this, -3100, 420));
            gameObjectManager.addGameObject(new Artillery(gameView, this, -3000, 420));

            //Tanks
            //gameObjectManager.addGameObject(new Tank(gameView, this));

            //Jet
            gameObjectManager.addGameObject(new Jet(gameView, this, 300, 100));

            //Base
            gameObjectManager.addGameObject(new LandingPlace(gameView, this));
            gameObjectManager.addGameObject(new Base(gameView, this));


            //gameObjectManager.overlay.showMessage("      Level 1 \n At the mountains", 2);

        }
        if (currentLevel instanceof Level2) {
            gameObjectManager.overlay.showMessage("Level 2", 1);

            gameObjectManager.chopper.reset();
            gameObjectManager.background.setBackgroundImage("background_level2.png");


            gameObjectManager.addGameObject(new Tank(gameView, this));

            //Base
            gameObjectManager.addGameObject(new LandingPlace(gameView, this));
            gameObjectManager.addGameObject(new Base(gameView, this));
        }
    }

    private void initializeGame() {
        Level.Difficulty difficulty = FileManager.readDifficultyFromDisc();
        difficulty = StartScreen.showStartScreen(gameView, difficulty);
        FileManager.writeDifficultyToDisc(difficulty);
        levelManager = new LevelManager(difficulty);
        levelManager.resetLevelCounter();
        currentLevel = levelManager.levels.getFirst();

        //gameOver = false;
        //first = true;
        createdTanks.clear();
        savedPeople.clear();
        unloadedPeople.clear();
        pickedUpPeople.clear();
        lostPeople.clear();
        initializeLevel();

    }

    private boolean gameOver() {
        if (returnHealthChopper() == 0) {
            EndScreen.showEndScreen(gameView, gameObjectManager.chopper.score);
            gameObjectManager.chopper.health = 3;
            return true;
        } else {
            return gameOver;
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


    /**
     * Was passieren soll, wenn der Chopper von einer Kugel getroffen wird.
     */
    public void chopperHasBeenHit() {
        for (GameObject o : gameObjectManager.getGameObjects()) {
            if (o instanceof Chopper) {
                Chopper chopper = (Chopper) o;
                chopper.decreaseHealth();
                chopper.chopperHit();
                chopperHit = true;


            }
            if (o instanceof LandingPlace) {
                LandingPlace landingPlace = (LandingPlace) o;
                landingplacePositionXWhenHit = landingPlace.getPosition().x;
                distanceToBase = (landingplacePositionXWhenHit - 620);


            }
        }
    }

    /**
     * Chopper sammelt die Leute ein.
     *
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
        for (int i = 0; i < pickedUpPeople.size(); i++) {
            if (!gameView.timerIsActive("unloadPeople", this) && gameObjectManager.chopper.chopperIsOnLandingPlace) {
                gameView.activateTimer("unloadPeople", this, 600);
                People o = new People(gameView, this, gameObjectManager.chopper.getPosition().x + 60, gameObjectManager.chopper.getPosition().y + 13);
                unloadedPeople.add(o);
                spawn(o);
                o.runToBase = true;
                pickedUpPeople.remove(i);
            }
        }
    }


    /**
     * Wenn eine Person in die Base geht.
     *
     * @param g die bestimmte Person.
     */
    public void storePeopleInBase(GameObject g) {
        savedPeople.add(g);
        destroy(g);
        adjustScore(250.0);
    }

    /**
     * Wie groß die Liste "pickedUpPeopleSize" ist auszugeben, ohne die Liste public zu machen.
     *
     * @return die größe der Liste
     */
    public int returnPickedUpPeopleSize() {
        return pickedUpPeople.size();
    }

    /**
     * Verändere score.
     *
     * @param score Um wie viel der score verändert werden soll
     */
    public void adjustScore(double score) {
        gameObjectManager.chopper.score += score;
    }

    /**
     * Gibt die Position des Choppers zurück, wichtig, um zu berechnen, wo die Gegner hin schießen müssen.
     *
     * @return gibt die Position des Choppers zurück.
     */
    public Position positionChopper() {
        for (GameObject o : gameObjectManager.getGameObjects()) {
            if (o instanceof Chopper) {
                return o.getPosition();
            }
        }
        return null;
    }


    /**
     * Um die geretteten Menschen im Overlay anzeigen zu lassen.
     *
     * @return gibt die geretteten Menschen zurück.
     */
    public int returnSavedPeopleSize() {
        return savedPeople.size();
    }

    /**
     * Wenn ein Objekt "People" mit dem Objekt "BulletEnemy" kollidiert, wird es in diese Liste hinzugefügt. Diese
     * Liste wurde hauptsächlich für das Overlay erstellt.
     *
     * @param people welches Objekt zur Liste hinzugefügt werden soll.
     */
    public void addLostPeople(People people) {
        lostPeople.add(people);
    }

    /**
     * Um bei Overlay diese Information anzeigen zu lassen.
     * @return gibt lostpeople.size zurück.
     */
    public int returnLostPeopleSize() {
        return lostPeople.size();
    }


    /**
     * Um bei Overlay diese Information anzeigen zu lassen.
     * @return gibt health des Choppers zurück
     */
    public double returnHealthChopper() {
        return gameObjectManager.chopper.health;
    }


    /**
     * Um in der Klasse People auf "ChopperLanded" zuzugreifen.
     *
     * @return true wenn der Chopper gelandet ist, false wenn nicht.
     */
    public boolean chopperLanded() {
        return gameObjectManager.chopper.chopperLanded();
    }

    /**
     * Um bei Overlay diese Information anzeigen zu lassen.
     * @return gibt den score zurück
     */
    public double returnScore() {
        return gameObjectManager.chopper.score;
    }


    /**
     * Um den GameObjectManager als Instanzvariable hinzuzufügen.
     *
     * @param gameObjectManager der GameObjectManager
     */
    void setGameObjectManager(GameObjectManager gameObjectManager) {
        this.gameObjectManager = gameObjectManager;
    }
}