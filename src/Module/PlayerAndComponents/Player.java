package Module.PlayerAndComponents;
/*
Player类应该主管玩家的一系列动作;同时应该具有如玩家的房产,玩家的银行,玩家的手牌等属性:
属性:
玩家名
玩家的手牌
玩家的房产
玩家的银行
玩家的行动次数(default=3) (待定)
倒计时(玩家需要在指定的时间内打出手牌,否则将自动pass或者自动交租/交房产)

方法:
放置房产
存钱
交租
pass(行动次数没使用完前可以跳过自己的回合)
改变房产颜色
打出手牌:
    收租
    收钱
    再抽三张
    say no(不消耗行动次数)
    ....
*/

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.PlayerListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Player extends JPanel {
    public static int playerHeight = (ApplicationStart.screenHeight) / 5;
    public static int playerWidth = (ApplicationStart.screenWidth) / 12;
    public String name;
    public int playerX;
    public int playerY;
    private int actionsNumber = 3; //行动次数
    private int countDown = 180; //倒计时
    //组件:
    private PlayerListener playerListener;
    private PlayerCards playerCards; //玩家的手牌区
    private Bank bank; //玩家的银行
    private Property property; //玩家的房产区
    private Image[] images = new Image[5];
    private Image playerImage;

    public Player(String name) {
        this.name = name;
        this.playerListener = new PlayerListener();
        this.playerCards = new PlayerCards();
        this.bank = new Bank();
        this.property = new Property();
        loadAndSetPlayerImage();
    }

    private void loadAndSetPlayerImage() {
        for (int i = 0; i < 5; i++) {
            try {
                images[i] = ImageIO.read(new File("images/Players/player" + i + ".jpeg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public void setPlayerImage(Image image) {
        this.playerImage = image;
    }

    public Image[] images() {
        return this.images;
    }

    private void drawPlayer(Graphics g) {
        if (playerImage != null) { // 如果背景图片已加载
            g.drawImage(playerImage, playerX, playerY, playerWidth, playerHeight, this);
        }
    }

    // 重写paintComponent方法以在面板上绘制背景图片
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        drawPlayer(g);
    }
}
