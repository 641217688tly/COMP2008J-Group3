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
    public static int playerCardsPileJPanelWidth = (ApplicationStart.screenWidth * 10) / 12;
    public static int playerCardsPileJPanelHeight = (ApplicationStart.screenHeight) / 5;
    private Player owner;
    private ArrayList<Card> cardsList;
    private Image playerCardsPileImage;

    public PlayerCardsPile(Player owner) {
        this.owner = owner;
        this.cardsList = new ArrayList<>();
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

    public void drawCardFromCardsPile(ArrayList<Card> cards) { //加牌
        cardsList.addAll(cards);
        for (int i = 0; i < cardsList.size(); i++) {
            this.add(cardsList.get(i)); //将Card添加到CardsPile这一JPanel中,但不展示出来
            cardsList.get(i).setIsDisplayable(false);
            cardsList.get(i).setIsCardFront(true);

        }
    }

    private void setCardBounds(Card card, int x, int y, boolean isDisplayable, boolean isCardFront) {
        card.setCardJPanelBounds(x, y); //为Card重新分配它在该JPanel下的坐标
        card.setIsCardFront(isCardFront);
        card.setIsDisplayable(isDisplayable);
    }

    //-------绘制方法:

    private void drawCardsUpToEleven() {
        for (int i = 0; i < cardsList.size(); i++) {
            if (i < 11) {
                setCardBounds(cardsList.get(i), (ApplicationStart.screenWidth / 12) * i, 0, true, true);
            }
        }
    }

    private void drawPlayerCardsPile(Graphics g) {
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
            drawCardsUpToEleven();
        }
    }
}
