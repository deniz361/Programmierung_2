package thd.game.managers;

import thd.game.utilities.TooManyGameObjectsException;
import thd.gameobjects.base.GameObject;
import thd.gameobjects.movable.Chopper;
import thd.gameview.GameView;

import java.awt.*;


/**
 * Managed das Spiel.
 */
public class GameLoopManager {
    private final GameView gameView;
    private final GameObjectManager gameObjectManager;
    private final GamePlayManager gamePlayManager;
    private InputManager inputManager;

    /**
     * Erstellt GameView und die extras.
     */
    public GameLoopManager() {
        gameView = new GameView();
        gamePlayManager = new GamePlayManager(gameView);
        gameObjectManager = new GameObjectManager(gameView, gamePlayManager);
        gamePlayManager.setGameObjectManager(gameObjectManager);
        for (GameObject o : gameObjectManager.getGameObjects()) {
            if (o instanceof Chopper) {
                inputManager = new InputManager(gameView, (Chopper) o);
            }
        }
        //inputManager = new InputManager(gameView, (Chopper) gameObjectManager.getGameObjects().get(1));
        gameView.setWindowTitle("Choplifter");
        gameView.setStatusText("Deniz Adigüzel - Java Programmierung SS 2022");
        gameView.setWindowIcon("choplifter icon.png");


        Color sky = new Color(141, 191, 224);
        gameView.setBackgroundColor(sky);
        //int id = gameView.playSound("getToTheChoppa.wav", true);

    }

    /**
     * Startet die Spielumgebung.
     */
    public void startGame() {
        while (!gamePlayManager.gameOver) { // Der "Game-Loop"
            gamePlayManager.updateGamePlay();
            inputManager.updateUserInputs();
            gameObjectManager.updateGameObjects();
            gameView.printCanvas();   // Es werden maximal 120 Bilder pro Sekunde angezeigt.
        }
    }

}
