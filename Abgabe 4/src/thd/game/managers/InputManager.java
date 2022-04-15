package thd.game.managers;

import thd.gameobjects.movable.Chopper;
import thd.gameview.GameView;

import java.awt.*;
import java.awt.event.KeyEvent;

import static thd.gameobjects.movable.Chopper.DIAGONAL_MOVEMENT_ALLOWED;

public class InputManager {

    private GameView gameView;
    private Chopper chopper;


    public InputManager(GameView gameView, Chopper chopper) {
        this.gameView = gameView;
        this.chopper = chopper;
    }

    public void updateUserInputs() {
        Integer[] pressedKeys = gameView.getKeyCodesOfCurrentlyPressedKeys();
        if (!DIAGONAL_MOVEMENT_ALLOWED) {
            for (int keyCode : pressedKeys) {
                if (keyCode == KeyEvent.VK_A) {
                    chopper.left();
                    return;
                } else if (keyCode == KeyEvent.VK_S) {
                    chopper.down();
                    return;
                } else if (keyCode == KeyEvent.VK_D) {
                    chopper.right();
                    return;
                } else if (keyCode == KeyEvent.VK_W) {
                    chopper.up();
                    return;
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    chopper.shoot();
                }
            }
        } else {
            for (int keyCode : pressedKeys) {
                if (keyCode == KeyEvent.VK_A) {
                    chopper.left();
                } else if (keyCode == KeyEvent.VK_S) {
                    chopper.down();
                } else if (keyCode == KeyEvent.VK_D) {
                    chopper.right();
                } else if (keyCode == KeyEvent.VK_W) {
                    chopper.up();
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    chopper.shoot();
                }
            }
        }
    }

}
