package thd.game.managers;

import thd.gameview.GameView;

import java.awt.*;


/**
 * Managed das Spiel.
 */
public class GameLoopManager {
    private final GameView gameView;
    private GameObjectManager gameObjectManager;
    private int id;


    /**
     * Erstellt GameView und die extras.
     */
    public GameLoopManager() {
        this.gameView = new GameView();
        gameObjectManager = new GameObjectManager(gameView);
        this.gameView.setWindowTitle("Choplifter");
        this.gameView.setStatusText("Java Programmierung SS 2022");
        this.gameView.setWindowIcon("choplifter icon.png");


        this.gameView.setBackgroundColor(Color.WHITE);
        //this.id = gameView.playSound("weSuckenDick.wav", true);
    }

    /**
     * Startet die Spielumgebung.
     */
    public void startGame() {


        while (true) { // Der "Game-Loop"
            gameObjectManager.updateGameObjects();
            gameView.printCanvas();   // Es werden maximal 120 Bilder pro Sekunde angezeigt.

        }

    }

}
