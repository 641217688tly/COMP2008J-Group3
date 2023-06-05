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
    public static int playerCardsPileJPanelX = 0;
    public static int playerCardsPileJPanelY = (ApplicationStart.screenHeight * 4) / 5;
    public static int playerCardsPileJPanelWidth = (ApplicationStart.screenWidth * 11) / 12;
    public static int playerCardsPileJPanelHeight = (ApplicationStart.screenHeight) / 5;
    public ArrayList<Card> cardsList;
    private Player owner;
    private Image playerCardsPileImage;

    public PlayerCardsPile(Player owner) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        this.owner = owner;
        this.cardsList = new ArrayList<>();
        loadAndSetPlayerCardsPileBackground();
        this.setBounds(PlayerCardsPile.playerCardsPileJPanelX, PlayerCardsPile.playerCardsPileJPanelY, PlayerCardsPile.playerCardsPileJPanelWidth, PlayerCardsPile.playerCardsPileJPanelHeight);
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // 从文件中读取背景图片
            playerCardsPileImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerCardsPileBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAndShowCards() { //每次调用都需要清除列表中已有的牌并移除JPanel中牌对应的组件,相当于PlayerCardsPile的刷新方法
        this.cardsList.clear(); //将旧牌全部丢弃
        this.removeAll(); //将旧牌全部丢弃
        this.cardsList.addAll(owner.cardsList); // 获得玩家手上的牌
        for (int i = 0; i < cardsList.size(); i++) {
            cardsList.get(i).setIsDisplayable(false);
            cardsList.get(i).setIsCardFront(true); //正面朝上
        }
        paintCardsFrontUpToEleven();
    }

    private void setCardBounds(Card card, int x, int y, boolean isDisplayable, boolean isCardFront) {
        card.setCardJPanelBounds(x, y); //为Card重新分配它在该JPanel下的坐标
        card.setIsCardFront(isCardFront);
        card.setIsDisplayable(isDisplayable);
    }

    //-------绘制方法:

    private void paintCardsFrontUpToEleven() {
        for (int i = 0; i < cardsList.size(); i++) {
            if (i < 11) {
                this.add(cardsList.get(i));
                setCardBounds(cardsList.get(i), (ApplicationStart.screenWidth / 12) * i, 0, true, true);
            }
        }
    }

    private void paintPlayerCardsPile(Graphics g) {
        if (playerCardsPileImage != null) {
            if (owner.isPlayerTurn()) { //如果正处于玩家的回合
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
            paintPlayerCardsPile(g);
        }
    }
}
