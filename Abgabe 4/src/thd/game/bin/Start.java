package thd.game.bin;

import thd.game.managers.GameLoopManager;

class Start {
    public static void main(String[] args) {
        var gameLoopManager = new GameLoopManager();
        gameLoopManager.startGame();
    }
}
