package thd.gameobjects.movable;


import thd.gameobjects.base.Position;
import thd.gameview.GameView;

import java.awt.*;
import java.util.Random;

public class RandomBall {

    private final Position position;
    private double speedInPixel;
    private final GameView gameView;
    private Position targetPosition;
    private final int height;
    private final int width;
    private final Color color;
    private double distance;
    private Random random;


    public RandomBall(GameView gameView, double speed, int size, Position targetPosition, Color color) {
        this.gameView = gameView;
        this.position = new Position();
        this.targetPosition = targetPosition;
        this.speedInPixel = speed;
        this.height = size;
        this.width = size;
        this.color = color;
        this.distance = 0;
        this.random = new Random();
    }

    public void updatePosition() {

        distance = position.distance(targetPosition);

        if (position.x < targetPosition.x) {
            position.right((targetPosition.x - position.x) / distance * speedInPixel);
        } else if (position.x > targetPosition.x) {
            position.left(-((targetPosition.x - position.x) / distance * speedInPixel));
        }
        if (position.y < targetPosition.y) {
            position.down((targetPosition.y - position.y) / distance * speedInPixel);
        } else if (position.y > targetPosition.y) {
            position.up((targetPosition.y - position.y) / distance * speedInPixel);
        }

        if (distance <= 5) {
            calculateRandomTargetPosition();
        }
    }

    public void calculateRandomTargetPosition() {
        targetPosition.x = random.nextInt(width / 2, (GameView.WIDTH + 1) - width / 2);
        targetPosition.y = random.nextInt(height / 2, (GameView.HEIGHT + 1) - height / 2);

    }

    public void addToCanvas() {
        gameView.addOvalToCanvas(position.x, position.y, width, height, 2, true, color);
        gameView.addOvalToCanvas(targetPosition.x, targetPosition.y, width, height, 2, false, Color.BLACK);
    }

    public Position getPosition() {
        return position;
    }
}
