package Module;

import javax.swing.*;

public final class GameEngine {
    private JPanel gameScreen;
    private Game game;
    private boolean exit = false;

    public GameEngine(Game game, JPanel gameScr) {
        this.game = game;
        this.gameScreen = gameScr;
    }

    public void run() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        // 在游戏开始后,执行startNewGame()方法来初始化一些数据和方法
        game.startNewGame();

        int delay = 1000 / 60; // Delay for 60 updates per second
        new Timer(delay, e -> {
            if (game.isGameOver()) {
                exitGame();
            } else {
                game.updateGame();
                gameScreen.repaint();
            }
        }).start();

    }

    private void exitGame() {
        exit = true;
        System.exit(0);
    }
}
