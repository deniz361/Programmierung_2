package thd.game.managers;

import thd.gameobjects.movable.Chopper;
import thd.gameview.GameView;

import java.awt.event.KeyEvent;


class InputManager {

    /**
     * Die Klassen variable, um diagonales Movement zu erlauben
     */
    public static final boolean DIAGONAL_MOVEMENT_ALLOWED = false;
    private GameView gameView;
    private Chopper chopper;


    InputManager(GameView gameView, Chopper chopper) {
        this.gameView = gameView;
        this.chopper = chopper;
    }

    void updateUserInputs() {
        Integer[] pressedKeys = gameView.getKeyCodesOfCurrentlyPressedKeys();
        if (!DIAGONAL_MOVEMENT_ALLOWED) {
            for (int keyCode : pressedKeys) {
                if (keyCode == KeyEvent.VK_A) {
                    chopper.left();
                    chopper.changeDirectionToLeft();
                    return;
                } else if (keyCode == KeyEvent.VK_S) {
                    chopper.down();
                    return;
                } else if (keyCode == KeyEvent.VK_D) {
                    chopper.right();
                    chopper.changeDirectionToRight();
                    return;
                } else if (keyCode == KeyEvent.VK_W) {
                    chopper.up();
                    return;
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    chopper.shoot();
                } else if (keyCode == KeyEvent.VK_E) {
                    chopper.faster();
                    break;
                } else if (keyCode == KeyEvent.VK_Q) {
                    chopper.slower();
                    break;
                }
            }
        } else {
            for (int keyCode : pressedKeys) {
                if (keyCode == KeyEvent.VK_A) {
                    chopper.left();
                    chopper.changeDirectionToLeft();
                } else if (keyCode == KeyEvent.VK_S) {
                    chopper.down();
                } else if (keyCode == KeyEvent.VK_D) {
                    chopper.right();
                    chopper.changeDirectionToRight();
                } else if (keyCode == KeyEvent.VK_W) {
                    chopper.up();
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    chopper.shoot();
                } else if (keyCode == KeyEvent.VK_E) {
                    chopper.faster();
                    break;
                } else if (keyCode == KeyEvent.VK_Q) {
                    chopper.slower();
                    break;
                }
            }
        }
    }

}
