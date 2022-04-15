package thd.game.managers;

import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.Cloud;
import thd.gameobjects.unmovable.Flag;
import thd.gameview.GameView;
import thd.gameobjects.base.*;

import java.awt.*;

class GameObjectManager {


    private Tank tank;
    private Jet jet;
    private Bullet bullet;
    protected Chopper chopper;
    //protected Flag flag;
    private Cloud cloud;
    private GameObject gameObject;


    GameObjectManager(GameView gameView) {
        gameObject = new GameObject(gameView);
        tank = new Tank(gameView, 0.5);
        jet = new Jet(gameView, 0.5);
        bullet = new Bullet(gameView, 1, 100, 50);
        //flag = new Flag(gameView);
        chopper = new Chopper(gameView);
        cloud = new Cloud(gameView);

    }





    void updateGameObjects() {
        tank.addToCanvas();
        jet.addToCanvas();
        bullet.addToCanvas();
        //flag.addToCanvas();
        chopper.addToCanvas();
        cloud.addToCanvas();
        gameObject.addToCanvas();

        gameObject.updatePosition();
        chopper.updatePosition();
        //flag.updatePosition();
        bullet.updatePosition();
        tank.updatePosition();
        jet.updatePosition();
    }
}
