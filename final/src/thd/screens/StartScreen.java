package thd.screens;

import thd.game.level.Level;
import thd.gameview.GameView;

/**
 * Der StarScreen.
 */
public class StartScreen {


    /**
     * Um den StarScreen anzeigen zu lassen.
     *
     * @param gameView   GameView
     * @param difficulty der Schwierigkeitsgrad.
     * @return Die Methode gibt den vom Spieler ausgewählten Schwierigkeitsgrad zurück.
     */
    public static Level.Difficulty showStartScreen(GameView gameView, Level.Difficulty difficulty) {
        String title = " CHOPLIFTER";
        String description =
                """
                        Rette Kriegsgefangene mit dem Hubschrauber und setze sie\s
                        in der Basis wieder ab.\s

                        'WASD' um den Hubschrauber zu steuern, 'SPACE' um zu\s
                        schießen, 'F' um nach unten zu schießen und 'Q' oder\s
                        'E' um die Richtung des Hubschraubers zu wechseln.""".indent(1);


        boolean easy;
        easy = difficulty == Level.Difficulty.EASY;
        boolean difficultyIsSetToEasy = gameView.showSimpleStartScreen(title, description, easy);

        if (difficultyIsSetToEasy) {
            return Level.Difficulty.EASY;
        } else {
            return Level.Difficulty.STANDARD;
        }
    }
}
