package thd.gameobjects.movable;

import thd.gameobjects.base.GameObject;
import thd.gameobjects.base.Position;
import thd.gameview.GameView;

import java.awt.*;
import java.util.Random;

public class FollowerBall extends GameObject {


    private Position targetPosition;
    private final Color color;
    private Random random;
    private RandomBall followMe;


    public FollowerBall(GameView gameView, RandomBall followMe) {
        super(gameView);
        this.gameView = gameView;
        this.position = new Position();
        this.followMe = followMe;
        this.targetPosition = new Position(followMe.getPosition().x, followMe.getPosition().y);
        this.speedInPixel = 3;
        this.height = 50;
        this.width = 50;
        this.color = Color.GREEN;
        this.random = new Random();
    }

    @Override
    public void updatePosition() {

        targetPosition.x = followMe.getPosition().x;
        targetPosition.y = followMe.getPosition().y;

        double distance = position.distance(targetPosition);

        if (distance >= speedInPixel) {
            position.right((targetPosition.x - position.x) / distance * speedInPixel);
            position.down((targetPosition.y - position.y) / distance * speedInPixel);
        } else {
            calculateRandomTargetPosition();
        }
    }

    private void calculateRandomTargetPosition() {
        targetPosition.x = random.nextDouble(width / 2.0, (GameView.WIDTH + 1) - width / 2.0);
        targetPosition.y = random.nextDouble(height / 2.0, (GameView.HEIGHT + 1) - height / 2.0);

    }

    @Override
    public void addToCanvas() {
        gameView.addOvalToCanvas(position.x, position.y, width, height, 2, true, color);
        gameView.addOvalToCanvas(targetPosition.x, targetPosition.y, width, height, 2, false, Color.BLACK);
    }
}
