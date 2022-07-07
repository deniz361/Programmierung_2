package thd.screens;

import thd.game.level.Level;
import thd.gameview.GameView;

/**
 * Der StarScreen.
 */
public class StartScreen {


    /**
     * Um den StarScreen anzeigen zu lassen.
     * @param gameView GameView
     * @param difficulty der Schwierigkeitsgrad.
     * @return Die Methode gibt den vom Spieler ausgewählten Schwierigkeitsgrad zurück.
     */
    public static Level.Difficulty showStartScreen(GameView gameView, Level.Difficulty difficulty) {
        String title = " CHOPLIFTER";
        String description =
                " Rette Kriegsgefangene mit dem Hubschrauber und setze sie \n" +
                " in der Basis wieder ab. \n\n" +
                " 'WASD' um den Hubschrauber zu steuern, 'SPACE' um zu \n" +
                " schießen, 'F' um nach unten zu schießen und 'Q' oder \n" +
                " 'E' um die Richtung des Hubschraubers zu wechseln.";


        boolean easy;
        if (difficulty == Level.Difficulty.EASY) {
            easy = true;
        } else {
            easy = false;
        }



        boolean difficultyIsSetToEasy = gameView.showSimpleStartScreen(title, description, easy);

        //return difficulty;


        if (difficultyIsSetToEasy) {
            return Level.Difficulty.EASY;
        } else {
            return Level.Difficulty.STANDARD;
        }


    }
}
