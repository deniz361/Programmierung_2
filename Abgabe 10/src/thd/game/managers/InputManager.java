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
            movement(keyCode);
            controlFasterAndSlower(keyCode);
            changeDirection(keyCode);

            if (keyCode == KeyEvent.VK_SPACE) {
                chopper.shoot();
            }
            if (!DIAGONAL_MOVEMENT_ALLOWED){
                break;
            }


        }
    }

    private void changeDirection(int keyCode) {
        if (keyCode == KeyEvent.VK_Q && !chopper.chopperLanded()) {
            chopper.changeDirectionOfTheChopperToLeft();
        }
        if (keyCode == KeyEvent.VK_E && !chopper.chopperLanded()) {
            chopper.changeDirectionOfTheChopperToRight();
        }
    }

    private void movement(int keyCode) {
        if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN && !chopper.chopperLanded()) {
            chopper.down();
            chopper.flyDown = false;
        }
        if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT && !chopper.chopperLanded()) {
            chopper.left();
            chopper.flyDown = false;
        }
        if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT && !chopper.chopperLanded()) {
            chopper.right();
            chopper.flyDown = false;
        }
        if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
            chopper.up();
            chopper.flyDown = false;
        }
    }

    private void controlFasterAndSlower(int keyCode) {
        if (keyCode == KeyEvent.VK_T) {
            if (!gameView.timerIsActive("faster", this)) {
                gameView.activateTimer("faster", this, 200);
                chopper.faster();
            }
        }
        if (keyCode == KeyEvent.VK_R) {
            if (!gameView.timerIsActive("slower", this)) {
                gameView.activateTimer("slower", this, 200);
                chopper.slower();
            }
        }
    }
}

