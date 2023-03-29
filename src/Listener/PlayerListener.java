package Listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/*
该类中不应该涉及到对物体(牌库,银行,牌等)坐标的更改,而是单纯的监听鼠标的行为,对坐标的更改将在JavaBean类中被实现
属性:
鼠标是否单击
鼠标是否移动
鼠标是否长按
....
 */
public class PlayerListener implements KeyListener {


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
