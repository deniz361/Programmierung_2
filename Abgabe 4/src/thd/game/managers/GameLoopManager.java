package thd.game.managers;

import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.Tank;
import thd.gameview.GameView;

import java.awt.*;
import java.util.ArrayList;


/**
 * Managed das Spiel.
 */
public class GameLoopManager {
    private final GameView gameView;
    private GameObjectManager gameObjectManager;
    private Color sky;
    private InputManager inputManager;
    private ArrayList<GameObject> createdGameObjects;
    private Tank tank;

    /**
     * Erstellt GameView und die extras.
     */
    public GameLoopManager() {
        gameView = new GameView();
        gameObjectManager = new GameObjectManager(gameView);
        inputManager = new InputManager(gameView, gameObjectManager.chopper);
        gameView.setWindowTitle("Choplifter");
        gameView.setStatusText("Java Programmierung SS 2022");
        gameView.setWindowIcon("choplifter icon.png");

        tank = new Tank(gameView, 0.5);

        sky = new Color(141, 191, 224);
        gameView.setBackgroundColor(sky);
        //this.id = gameView.playSound("weSuckenDick.wav", true);

        createdGameObjects = new ArrayList<>(120);
    }

    /**
     * Startet die Spielumgebung.
     */
    public void startGame() {
        while (true) { // Der "Game-Loop"
            updateGamePlay();
            inputManager.updateUserInputs();
            gameObjectManager.updateGameObjects();
            gameView.printCanvas();   // Es werden maximal 120 Bilder pro Sekunde angezeigt.
        }
    }

    private void updateGamePlay() {
        while (gameView.getGameTimeInMilliseconds() / 1000 == 5) {
            createdGameObjects.add(tank);
            gameObjectManager.addGameObject(tank);
            /*
            gameObjectManager.addGameObject(gameObjectManager.gameObjects.get(1));
            createdGameObjects.addAll(gameObjectManager.toAdd);
             */
        }

        if (gameView.getGameTimeInMilliseconds() / 1000 == 7) {
            for (GameObject o : createdGameObjects) {
                gameObjectManager.removeGameObject(o);
            }

        }
    }

}
