package Listener.GUIListener;

import Module.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameScreenListener implements KeyListener {
    private Game game;

    public GameScreenListener(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'p' || e.getKeyChar() == 'P') {  // 如果按下的键是 "P" 或 "p"，则切换游戏的暂停状态
            game.setIsPaused(!game.getIsPaused());
            System.out.println(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
