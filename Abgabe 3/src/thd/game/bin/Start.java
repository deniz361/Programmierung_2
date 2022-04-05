package thd.game.bin;

import thd.game.managers.GameLoopManager;
import thd.gameobjects.base.Position;

class Start {
    public static void main(String[] args) {
        Position position1 = new Position(100, 110);
        var position2 = new Position(200, 300);

        var gameLoopManager = new GameLoopManager();
        gameLoopManager.startGame();

    }
}
