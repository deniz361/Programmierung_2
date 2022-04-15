package thd.gameobjects.unmovable;


import thd.gameobjects.base.GameObject;
import thd.gameview.GameView;

/** Für die verschiedenen Wolken. */
public class Cloud extends GameObject {

    /** Die Wolke wird vorerst auf die Position x=700 und y=100 gesetzt.
     * @param gameView gibt GameView weiter */
    public Cloud(GameView gameView) {
        super(gameView);

    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("Cloud.png",100,100,2,0);
        gameView.addImageToCanvas("Cloud.png",300,50,2,0);
        gameView.addImageToCanvas("Cloud.png",400,100,2,0);
        gameView.addImageToCanvas("Cloud.png",600,50,2,0);
        gameView.addImageToCanvas("Cloud.png",700,100,2,0);

    }
}
