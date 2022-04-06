package thd.game.managers;

import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.Flag;
import thd.gameview.GameView;
import thd.gameobjects.base.*;

import java.awt.*;

class GameObjectManager {


    private Tank tank;
    private Jet jet;
    private Bullet bullet;
    private Chopper chopper;
    private Flag flag;


    GameObjectManager(GameView gameView) {
        tank = new Tank(gameView, 0.5);
        jet = new Jet(gameView, 0.5);
        bullet = new Bullet(gameView, 1, 100, 50);
        flag = new Flag(gameView);
        chopper = new Chopper(gameView);

    }





    void updateGameObjects() {
        tank.addToCanvas();
        jet.addToCanvas();
        bullet.addToCanvas();
        flag.addToCanvas();
        chopper.addToCanvas();


        chopper.updatePosition();
        flag.updatePosition();
        bullet.updatePosition();
        tank.updatePosition();
        jet.updatePosition();
    }
}
