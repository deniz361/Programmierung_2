package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidableGameObject;
import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

import java.awt.*;

public class Overlay extends CollidableGameObject {

    private String text;
    private boolean onlyOneTime;
    private String message;
    private int secondsToShow;

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
        onlyOneTime = true;
    }

    /**
     * If a game object is collided with something, it is able to react to the collision.
     *
     * @param other The other GameObject that is involved in the collision.
     */
    @Override
    public void reactToCollision(CollidableGameObject other) {

    }

    public void showMessage(String message, int secondsToShow) {
        this.message = message;
        this.secondsToShow = secondsToShow;
        if (onlyOneTime) {
            if (!gameView.alarmIsSet("overlay", this)) {
                gameView.setAlarm("overlay", this, secondsToShow);
                text = message;
            } else if (gameView.alarm("overlay", this)) {
                text = " ";
                onlyOneTime = false;
            }
        }
    }


    /**
     * Fügt das Spielobject in GameView hinzu.
     */
    @Override
    public void addToCanvas() {
        showMessage(message, secondsToShow);
        gameView.addTextToCanvas(text, 150, 200, size, Color.BLACK, rotation);
    }
}
