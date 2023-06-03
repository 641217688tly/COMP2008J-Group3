package Module;

import javax.swing.*;
import java.awt.*;

public final class GameEngine {
    private JPanel gameScreen;
    private Game game;
    private boolean exit = false;
    private static final double TARGET_TIME_BETWEEN_UPDATES = 1000000000.0 / 60;
    private static final int MAX_UPDATES_BEFORE_RENDER = 5;

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

        double now = System.nanoTime();
        double lastUpdateTime = System.nanoTime();

        while (!exit) {
            if (game.isGameOver()) {
                exitGame();
            } else {
                System.out.println(1);
                if (!game.isPaused()) {
                    int updateCount = 0;
                    while (now - lastUpdateTime > TARGET_TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                        game.updateGame();
                        lastUpdateTime += TARGET_TIME_BETWEEN_UPDATES;
                        updateCount++;
                    }
                    gameScreen.paintImmediately(new Rectangle(0, 0, gameScreen.getWidth(), gameScreen.getHeight()));
                    while (now - lastUpdateTime < TARGET_TIME_BETWEEN_UPDATES) {
                        try {
                            Thread.sleep(1);
                        } catch (Exception e) {
                            // Ignore exception
                        }
                        now = System.nanoTime();
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        exit = false;
    }

    private void exitGame() {
        exit = true;
        System.exit(0);
    }
}
