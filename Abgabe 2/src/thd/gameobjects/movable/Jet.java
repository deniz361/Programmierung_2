package thd.gameobjects.movable;

import thd.gameobjects.base.Position;
import thd.gameview.GameView;


/**
 * Die Klasse zu dem Objekt Jet.
 */
public class Jet {

    private final Position position;
    private double speedInPixel;
    private final GameView gameView;
    private String jet;
    private String leerzeichen;
    private String leerzeichen2;
    private String leerzeichen3;
    private String leerzeichen4;
    private String leerzeichen5;
    private String leerzeichen6;
    private String leerzeichen7;

    //WIDTH = 960;
    //HEIGHT = 540;


    /**
     * Erstellt das Objekt Jet.
     * @param gameView Damit Gameview weitergegeben werden kann
     * @param speed Die Geschwindigkeit vom Jet
     */
    public Jet(GameView gameView, double speed) {
        this.gameView = gameView;
        position = new Position(450, 100);
        speedInPixel = speed;
        leerzeichen = " ".repeat(27);
        leerzeichen2 = " ".repeat(26);
        leerzeichen3 = " ".repeat(25);
        leerzeichen4 = " ".repeat(11);
        leerzeichen5 = " ".repeat(9);
        leerzeichen6 = " ".repeat(7);
        leerzeichen7 = " ".repeat(14);

        jet = leerzeichen + "LL  \n" + leerzeichen2 + "LCL\n" + leerzeichen3 + "LccL\n" + leerzeichen4 + "LLLLLLL      LCCCL\n" + leerzeichen5 + "LLWWWWWWWL" +
                "    LccCCL\n" + leerzeichen6 + "LLWWWWWWLLLLLLLLCCCCCL\n    LLLCCLLLLLLCCCCCCCCCCCCCCLL\n LLLCCCCCCCCCLLLLLLLLLLLLLLCCCL\nLCCCCCCCCCCCLWWWWWWWWWWWWWLLLL\n" +
                "LLLLLLLLLLLLLLRRRRRRRRLLLL\n" + leerzeichen7 + "LLLLLLLL";

    }

    /**
     * fügt das Objekt zu Gameview hinzu.
     * @param size die Größe der Pixel
     * @param rotation die Rotation
     */
    public void addToCanvas(int size, int rotation) {
        gameView.addBlockImageToCanvas(jet, position.x, position.y, size, rotation);
    }


    /**
     * Bewegt das Objekt.
     */
    public void updatePosition() {
        position.left(speedInPixel);
    }
}
