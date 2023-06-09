package Module;

import GUI.ApplicationStart;
import Module.Cards.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Stack;

public class CardsPile extends JPanel {
    public Stack<Card> drawPile; // 抽牌堆
    public Stack<Card> discardPile; // 废牌堆

    public int cardsPileJPanelX = (ApplicationStart.screenWidth * 5) / 12;
    public int cardsPileJPanelY = (ApplicationStart.screenHeight * 2) / 5;
    public int cardsPileJPanelWidth = (ApplicationStart.screenWidth * 2) / 12;
    public int cardsPileJPanelHeight = (ApplicationStart.screenHeight) / 5;

    public int drawPileX = 0; //抽排堆相对于CardsPile的JPanel的X坐标
    public int drawPileY = 0; //抽排堆相对于CardsPile的JPanel的Y坐标
    public int discardPileX = (ApplicationStart.screenWidth) / 12; //废牌堆相对于CardsPile的JPanel的X坐标
    public int discardPileY = 0; //废牌堆相对于CardsPile的JPanel的Y坐标

    public static int drawPileHeight = (ApplicationStart.screenHeight) / 5;
    public static int drawPileWidth = (ApplicationStart.screenWidth) / 12;
    public static int discardPileHeight = (ApplicationStart.screenHeight) / 5;
    public static int discardPileWidth = (ApplicationStart.screenWidth) / 12;
    private Image cardsPileBackground;

    public CardsPile() {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        this.setBounds(cardsPileJPanelX, cardsPileJPanelY, cardsPileJPanelWidth, cardsPileJPanelHeight); // 设置CardsPile这一JPanel的大小和位置
        loadAndSetBackgroundImage();
        initializeCardsPile(); //向抽牌堆中加入所有的卡牌
    }

    private void initializeCardsPile() {
        this.drawPile = new Stack<Card>(); //抽牌堆
        this.discardPile = new Stack<Card>(); //废牌回收堆
        drawPile.addAll(ActionCard.initializeCardsForCardsPile());
        //drawPile.addAll(MoneyCard.initializeCardsForCardsPile());
        drawPile.addAll(PropertyCard.initializeCardsForCardsPile());
        drawPile.addAll(PropertyWildCard.initializeCardsForCardsPile());
        drawPile.addAll(RentCard.initializeCardsForCardsPile());
        Collections.shuffle(drawPile); //洗牌
        for (int i = 0; i < drawPile.size(); i++) {
            this.setCardBounds(drawPile.get(i), drawPileX, drawPileY, false, false); //设置为不可视且背面朝上
        }
        paintPeekCard(); //将第一张牌设置为可视
    }

    private void loadAndSetBackgroundImage() {
        try {
            // 从文件中读取背景图片
            cardsPileBackground = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerCardsPileBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCardBounds(Card card, int x, int y, boolean isDisplayable, boolean isCardFront) {
        card.setCardJPanelBounds(x, y); //为Card分配它在该JPanel下的坐标
        card.setIsDisplayable(isDisplayable);
        card.setIsCardFront(isCardFront);
    }

    public Card[] drawCardFromDrawPile(int number) { //抽牌,所有抽出来的牌都:1.没有按钮 2.不可视
        Card[] cards = new Card[number];
        if (drawPile.size() < number) {
            drawPile.peek().setIsDisplayable(false);
            this.remove(drawPile.peek());
            reuseCardsFromDiscardPile();
        }
        for (int i = 0; i < number; i++) {
            Card popCard = this.drawPile.pop();
            popCard.setIsDisplayable(false);
            this.remove(popCard);
            cards[i] = popCard;
        }
        paintPeekCard();
        return cards;
    }

    private void reuseCardsFromDiscardPile() { //牌堆没有牌时从废牌堆里拿牌
        if (discardPile.size() > 0) {
            discardPile.peek().setIsDisplayable(false);
            this.remove(discardPile.peek());
        }
        for (Card card : discardPile) {
            setCardBounds(card, drawPileX, drawPileY, false, false);
        }
        drawPile.addAll(discardPile);
        Collections.shuffle(drawPile);
        paintPeekCard();
    }

    public void recycleCardIntoDiscardPile(Card card) { //回收废牌
        //先将原本被展示的卡牌设置为不可被展示:
        if (discardPile.size() > 0) {
            discardPile.peek().setIsDisplayable(false);
            this.remove(discardPile.peek());
        }
        card.owner = null;
        //将牌上的按钮全部隐藏:
        card.openMoveButtonSwitch(false);
        card.openPlayButtonSwitch(false);
        card.openDepositButtonSwitch(false);
        card.openDiscardButtonSwitch(false);
        this.discardPile.push(card);
        paintPeekCard();
    }

    //----------绘制方法:

    private void paintPeekCard() { //会将peek位置的牌添加为组件并展示
        if (drawPile.size() > 0) {
            this.add(drawPile.peek());
            setCardBounds(drawPile.peek(), drawPileX, drawPileY, true, false);
            drawPile.peek().setIsDisplayable(true); //将栈顶的牌设置为允许被展示
        }
        if (discardPile.size() > 0) {
            this.add(discardPile.peek());
            setCardBounds(discardPile.peek(), discardPileX, discardPileY, true, true);
            discardPile.peek().setIsDisplayable(true); //将栈顶的牌设置为允许被展示
        }
    }

    public void paintCardsPile(Graphics g) {
        if (cardsPileBackground != null) { // 如果背景图片已加载
            // 在面板上绘制背景图片，使其填充整个面板
            g.drawImage(cardsPileBackground, drawPileX, drawPileY, drawPileWidth, drawPileHeight, null);
            g.drawImage(cardsPileBackground, discardPileX, discardPileY, discardPileWidth, discardPileHeight, null);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        paintCardsPile(g);
    }
}

