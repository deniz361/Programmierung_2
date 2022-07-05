package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

import java.awt.*;

/**
 * Das Overlay des Spiels.
 */
public class Overlay extends GameObject {

    private String text;
    /**
     * Damit vom GamePlayManager auf diesen counter zugegriffen werden kann.
     */
    private int gameOverCounter;
    private boolean executeThisOneTime;

    /**
     * Mindestanforderung, das jedes GameObject haben muss.
     *
     * @param gameView        to interact with game view.
     * @param gamePlayManager to control the game flow.
     */
    public Overlay(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 35;
        text = "This is a test";
        executeThisOneTime = true;
        gameOverCounter = 0;
    }


    /**
     * Um am Anfang die Nachricht anzeigen zu lassen.
     *
     * @param message       Die Nachricht.
     * @param secondsToShow Wie lange die Nachricht angezeigt werden soll.
     */
    private void showMessage(String message, int secondsToShow) {
        text = message;
        gameView.activateTimer(text, this, secondsToShow * 1000L);
    }


    /**
     * Zeigt den vorläufigen counter an.
     * @param counter counter.
     * @param secondsToShow wie lange der counter angezeigt werden soll.
     */
    private void showCounter(int counter, int secondsToShow) {
        if (executeThisOneTime) {
            gameOverCounter = counter;
            executeThisOneTime = false;
        }
        if (!gameView.alarmIsSet("gameOverCounter", this)) {
            gameView.setAlarm("gameOverCounter", this, secondsToShow * 1000L);
        } else if (gameView.alarm("gameOverCounter", this)) {
            gameOverCounter--;
        }


    }


    /**
     * Fügt das Spielobject in GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        //gameView.addRectangleToCanvas(0,0,GameView.WIDTH, 80,0, true, Color.BLACK);
        gameView.addTextToCanvas("SCORE:" + gamePlayManager.getScore(), 780, 0, 15, Color.BLACK, 0);
        gameView.addTextToCanvas(" LOAD:" + gamePlayManager.returnPickedUpPeopleSize() + "/7", 780, 20, 15, Color.BLACK, 0);
        gameView.addTextToCanvas(" SAFE:" + gamePlayManager.returnSavedPeopleSize(), 780, 40, 15, Color.BLACK, 0);
        gameView.addTextToCanvas(" LOST:" + gamePlayManager.returnLostPeopleSize(), 780, 60, 15, Color.BLACK, 0);
        gameView.addTextToCanvas("HEALTH:" + gamePlayManager.returnHealthChopper(), 0,0,15, Color.BLACK, 0);


        if (gameView.timerIsActive(text, this)) {
            final int size = 30;
            final double xCoordinate = GameView.WIDTH / 2.0 - size * 7;
            final double yCoordinate = GameView.HEIGHT / 2.0 - size / 2.0;
            gameView.addTextToCanvas(text, xCoordinate, yCoordinate, size, Color.BLACK, rotation);

        }


        /*
        if (gameOverCounter >= 0) {
            gameView.addTextToCanvas("The Game ends in: " + gameOverCounter, 500, 50, 20, Color.BLACK, rotation);
        }

         */
    }
}
