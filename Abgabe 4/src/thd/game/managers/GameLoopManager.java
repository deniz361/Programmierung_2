package thd.game.managers;

import thd.gameview.GameView;

import java.awt.*;


/**
 * Managed das Spiel.
 */
public class GameLoopManager {
    private final GameView gameView;
    private GameObjectManager gameObjectManager;
    private Color sky;
    private InputManager inputManager;

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

        sky = new Color(141, 191, 224);
        gameView.setBackgroundColor(sky);
        //this.id = gameView.playSound("weSuckenDick.wav", true);
    }

    /**
     * Startet die Spielumgebung.
     */
    public void startGame() {


        while (true) { // Der "Game-Loop"
            inputManager.updateUserInputs();
            gameObjectManager.updateGameObjects();
            gameView.printCanvas();   // Es werden maximal 120 Bilder pro Sekunde angezeigt.

        }

    }

}
