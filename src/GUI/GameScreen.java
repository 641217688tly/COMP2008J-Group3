package GUI;

import Listener.GUIListener.GameScreenListener;
import Module.Game;
import Module.PlayerAndComponents.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Module.GameEngine;

public class GameScreen extends JPanel {
    private Image gameScreenBackground; // 存储背景图像的变量
    private GameScreenListener gameScreenListener;
    private Game game;
    public GameEngine gameEngine;

    public GameScreen(Game game) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight); // 设置GameScreen的大小和位置

        loadAndSetBackgroundImage();
        setPreferredSize(new Dimension(ApplicationStart.screenWidth, ApplicationStart.screenHeight)); // 设置GameScreen的理想大小
        this.game = game;
        this.gameEngine = new GameEngine(game, this);
        this.gameScreenListener = new GameScreenListener(game);
        this.setFocusable(true); //设置面板可以获取焦点
        this.requestFocusInWindow(); //请求焦点
    }

    // 加载并设置背景图片
    private void loadAndSetBackgroundImage() {
        try {
            // 从文件中读取背景图片
            gameScreenBackground = ImageIO.read(new File("images/GUI/GameScreenBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addComponentsIntoJPanel() {
        for (int i = 0; i < Game.players.size(); i++) {
            Player player = Game.players.get(i);
            //将Player添加到JPanel中
            this.add(player);
            //将Player的PlayerCardsPile添加到JPanel中
            this.add(player.playerCardsPile);
            //将Player的Bank添加到JPanel中:
            this.add(player.bank);
            //将Player的Property添加到JPanel中:
            this.add(player.property);
            //将Player的HandCards添加到JPanel中:
            this.add(player.handCards);
        }
        //将中央的牌堆CardsPile添加到JPanel中
        this.add(Game.cardsPile);
    }

    private void paintBackground(Graphics g) {
        if (gameScreenBackground != null) { // 如果背景图片已加载
            g.drawImage(gameScreenBackground, 0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight, null);
        }
    }

    private void paintDiscardReminder(Graphics g) { //当回合结束前,如果玩家手上的牌大于7张,就提醒玩家弃牌!
        if (Game.players.get(0).isPlayerTurn()) {
            if (Game.players.get(0).actionNumber <= 0) {
                if (Game.players.get(0).isInAction()) {
                    boolean flag = true;
                    for (Player player : Game.players) {
                        if (player.interactivePlayers.size() > 0) { //玩家间的交互如果没完成,则
                            flag = false;
                            break;
                        }
                    }
                    if (Game.players.get(0).numberOfHandCards() <= 7) { //牌若小于等于7
                        flag = false;
                    }
                    if (flag) {
                        g.setColor(Color.RED); // 设置文本颜色
                        g.setFont(new Font("Arial", Font.BOLD, 30)); // 设置字体和大小
                        g.drawString("Discard to 7 remaining!", 5 * ApplicationStart.screenWidth / 12 - ApplicationStart.screenWidth / 40, 4 * ApplicationStart.screenHeight / 5 - ApplicationStart.screenHeight / 10);
                    }
                }
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        paintBackground(g);
        paintDiscardReminder(g);
    }

}
