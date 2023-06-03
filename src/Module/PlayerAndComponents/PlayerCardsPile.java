/*
属性：
cards（保存玩家手牌的列表，类型为List<Card>）
方法：
初始化方法，设置JDialog的布局，根据cards列表中的卡牌，创建卡片展示组件并添加到JDialog中
updateCards()（更新手牌列表）
*/
package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Module.Cards.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerCardsPile extends JPanel { // 该类为呈现当前回合下玩家手上的卡牌
    //属性:
    public static ArrayList<Card> playerCards;
    public static int playerCardsPileJPanelX = 0;
    public static int playerCardsPileJPanelY = (ApplicationStart.screenHeight * 4) / 5;
    public static int playerCardsPileJPanelWidth = (ApplicationStart.screenWidth * 10) / 12;
    public static int playerCardsPileJPanelHeight = (ApplicationStart.screenHeight) / 5;
    private Player owner;
    private Image playerCardsPileImage;

    public PlayerCardsPile(Player owner) {
        this.owner = owner;
        loadAndSetPlayerCardsPileBackground();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // 从文件中读取背景图片
            playerCardsPileImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerCardsPileBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawPlayerCardsPile(Graphics g) {
        if (playerCardsPileImage != null) {
            if (owner.isPlayerTurn()) {
                for (int i = 0; i < 11; i++) {
                    g.drawImage(playerCardsPileImage, (ApplicationStart.screenWidth / 12) * i, 0, ApplicationStart.screenWidth / 12, playerCardsPileJPanelHeight, null);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (owner.isPlayerTurn()) {
            super.paintComponent(g); // 调用父类方法以确保正常绘制
            drawPlayerCardsPile(g);
        }
    }
}
