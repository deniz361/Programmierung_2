package thd.game.managers;

import thd.gameobjects.movable.Chopper;
import thd.gameview.GameView;

import java.awt.event.KeyEvent;


class InputManager {

    /**
     * Die Klassen variable, um diagonales Movement zu erlauben
     */
    public static final boolean DIAGONAL_MOVEMENT_ALLOWED = true;
    private GameView gameView;
    private Chopper chopper;


    InputManager(GameView gameView, Chopper chopper) {
        this.gameView = gameView;
        this.chopper = chopper;
    }

    void updateUserInputs() {
        Integer[] pressedKeys = gameView.getKeyCodesOfCurrentlyPressedKeys();
        for (int keyCode : pressedKeys) {
            if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
                chopper.left();
                chopper.changeDirectionToLeft();
            }
            if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                chopper.down();
            }
            if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
                chopper.right();
                chopper.changeDirectionToRight();
            }
            if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                chopper.up();
            }
            if (keyCode == KeyEvent.VK_SPACE) {
                chopper.shoot();
            }
            if (!DIAGONAL_MOVEMENT_ALLOWED){
                break;
            }





                /*
                else if (keyCode == KeyEvent.VK_E) {
                    chopper.faster();
                    break;
                } else if (keyCode == KeyEvent.VK_Q) {
                    chopper.slower();
                    break;
                }
                 */
        }
    }
}

