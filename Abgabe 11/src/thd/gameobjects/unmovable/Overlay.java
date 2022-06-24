package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameview.GameView;

import java.awt.*;

/**
 * Das Overlay des Spiels.
 */
public class Overlay extends CollidableGameObject {

    private String text;
    /**
     * Damit vom GamePlayManager auf diesen counter zugegriffen werden kann.
     */
    public int gameOverCounter;
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
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {

    }

    /**
     * Um am Anfang die Nachricht anzeigen zu lassen.
     *
     * @param message       Die Nachricht.
     * @param secondsToShow Wie lange die Nachricht angezeigt werden soll.
     */
    public void showMessage(String message, int secondsToShow) {
        text = message;
        gameView.activateTimer(text, this, secondsToShow * 1000L);
    }


    /**
     * Zeigt den vorläufigen counter an.
     * @param counter counter.
     * @param secondsToShow wie lange der counter angezeigt werden soll.
     */
    public void showCounter(int counter, int secondsToShow) {
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
