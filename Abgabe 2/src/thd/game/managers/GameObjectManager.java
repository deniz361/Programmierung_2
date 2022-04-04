package thd.game.managers;

import thd.gameobjects.movable.*;
import thd.gameview.GameView;
import thd.gameobjects.base.*;

import java.awt.*;

class GameObjectManager {

    private Tank tank;
    private Jet jet;
    private Bullet bullet;
    private RandomBall randomBall;
    private Position targetPosition;
    private FollowerBall followerBall;


    GameObjectManager(GameView gameView) {
        this.tank = new Tank(gameView, 0.5);
        this.jet = new Jet(gameView, 0.5);
        this.bullet = new Bullet(gameView, 1, 100, 50);
        this.targetPosition = new Position(800, 200);
        this.randomBall = new RandomBall(gameView, 4, 50, targetPosition, Color.YELLOW);
        this.followerBall = new FollowerBall(gameView, randomBall);

    }



    void updateGameObjects() {
        tank.addToCanvas(5, 0);
        jet.addToCanvas(5, 0);
        bullet.addToCanvas();
        randomBall.addToCanvas();
        followerBall.addToCanvas();


        followerBall.updatePosition();
        randomBall.updatePosition();
        bullet.updatePosition();
        tank.updatePosition();
        jet.updatePosition();
    }
}
