package thd.game.managers;

import thd.gameobjects.movable.Chopper;
import thd.gameview.GameView;

import java.awt.event.KeyEvent;


class InputManager {

    /**
     * Die Klassen variable, um diagonales Movement zu erlauben
     */
    public static final boolean DIAGONAL_MOVEMENT_ALLOWED = true;
    private final GameView gameView;
    private final Chopper chopper;


    InputManager(GameView gameView, Chopper chopper) {
        this.gameView = gameView;
        this.chopper = chopper;
    }

    void updateUserInputs() {
        Integer[] pressedKeys = gameView.getKeyCodesOfCurrentlyPressedKeys();
        for (int keyCode : pressedKeys) {
            if (chopper.exploded) {
                break;
            }
            if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN && !chopper.chopperLanded()) {
                chopper.down();
                chopper.setFlyDownFalse();
            }
            if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT && !chopper.chopperLanded()) {
                chopper.left();
                chopper.changeDirectionToLeft();
                chopper.setFlyDownFalse();
            }
            if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT && !chopper.chopperLanded()) {
                chopper.right();
                chopper.changeDirectionToRight();
                chopper.setFlyDownFalse();
            }
            if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                chopper.up();
                chopper.setFlyDownFalse();
            }
            if (keyCode == KeyEvent.VK_E) {
                if (!gameView.timerIsActive("faster", this)) {
                    gameView.activateTimer("faster", this, 200);
                    chopper.faster();
                }
            }
            if (keyCode == KeyEvent.VK_Q) {
                if (!gameView.timerIsActive("slower", this)) {
                    gameView.activateTimer("slower", this, 200);
                    chopper.slower();
                }
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

