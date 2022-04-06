package thd.game.bin;

import thd.game.managers.GameLoopManager;
import thd.gameobjects.base.Position;

class Start {
    public static void main(String[] args) {
        var gameLoopManager = new GameLoopManager();
        gameLoopManager.startGame();
    }
}
