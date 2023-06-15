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

        // When the game starts, execute the startNewGame() method to initialize some data and methods
        game.startNewGame();

        int delay = 1000 / 60; // Delay for 60 updates per second
        Timer timer = new Timer(delay, null);
        timer.addActionListener(e -> {
            if (game.isGameOver()) {
                // Start a new timer and exit the program after 5 seconds
                new Timer(10000, ex -> {
                    timer.stop();
                    exitGame();
                }).start();
            } else {
                game.updateGame();
            }
            gameScreen.repaint();
        });
        timer.start();
    }

    private void exitGame() {
        exit = true;
        System.exit(0);
    }
}
