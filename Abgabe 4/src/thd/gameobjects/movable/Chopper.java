package thd.gameobjects.movable;

import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

import java.awt.*;

/** Der Spieler steuert den Chopper.*/
public class Chopper extends GameObject {

    /** Der Konstruktor nur mit GameView.*/
    public Chopper(GameView gameView) {
        super(gameView);
    }


    /** Fügt den Chopper zu GameView hinzu */
    @Override
    public void addToCanvas() {
        gameView.addTextToCanvas("X", GameView.WIDTH / 2.0, GameView.HEIGHT / 2.0, 50, Color.BLACK, 0);
    }

    @Override
    public void updatePosition() {

    }





}
