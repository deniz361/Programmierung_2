package thd.screens;

import thd.gameview.GameView;

import java.awt.*;

/**
 * Der EndScreen.
 */
public class EndScreen {

    /**
     * Um den EndScreen anzeigen zu lassen.
     * @param gameView GameView
     * @param score der Schwierigkeitsgrad.
     */
    public static void showEndScreen(GameView gameView, int score) {
        gameView.showEndScreen("Game Over");
        gameView.addTextToCanvas(score + "", GameView.WIDTH / 2d, GameView.HEIGHT / 2d - 80, 13, Color.WHITE, 0);
    }
}
