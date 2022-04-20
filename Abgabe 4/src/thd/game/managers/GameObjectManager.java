package thd.game.managers;

import thd.gameobjects.movable.*;
import thd.gameobjects.unmovable.Cloud;
import thd.gameobjects.unmovable.Flag;
import thd.gameview.GameView;
import thd.gameobjects.base.*;

import java.awt.*;
import java.util.LinkedList;

class GameObjectManager {



    private LinkedList<GameObject> gameObjects;
    protected Chopper chopper;


    GameObjectManager(GameView gameView) {

        gameObjects = new LinkedList<>();
        gameObjects.add(new Jet(gameView, 0.5));
        gameObjects.add(new Tank(gameView, 0.5));
        //gameObjects.add(new Bullet(gameView, 1, 100, 50));
        gameObjects.add(new Cloud(gameView));



        chopper = new Chopper(gameView);

    }





    void updateGameObjects() {


        for (GameObject gameObject : gameObjects) {
            gameObject.updateStatus();
            gameObject.updatePosition();
            gameObject.addToCanvas();
        }

        chopper.updateStatus();
        chopper.addToCanvas();
        chopper.updatePosition();


    }
}
