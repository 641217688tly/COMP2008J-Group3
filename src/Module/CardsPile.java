package Module;
/*
CardsPile类应该负责发牌功能,所有未发的牌和已经打出来的牌都应该放在牌堆中
属性:
牌库1:装载尚未被抽取的牌
牌库2:回收玩家打出的牌,如果牌库1被用完,那么就将牌库2中的牌洗牌后装载回牌库1
坐标
碰撞体积(当牌被拖进牌堆,牌堆应该收容它)

方法:
为玩家发牌
回收打出的牌
牌不够时洗牌库2装牌库1
 */

import GUI.ApplicationStart;
import Module.Cards.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    public void initializeCardsPile() {
        this.drawPile = new Stack<Card>(); //抽牌堆
        this.discardPile = new Stack<Card>(); //废牌回收堆
        drawPile.addAll(ActionCard.initializeCardsForCardsPile());
        drawPile.addAll(MoneyCard.initializeCardsForCardsPile());
        drawPile.addAll(PropertyCard.initializeCardsForCardsPile());
        drawPile.addAll(PropertyWildCard.initializeCardsForCardsPile());
        drawPile.addAll(RentCard.initializeCardsForCardsPile());
        Collections.shuffle(drawPile);
    }

    // 加载并设置背景图片
    private void loadAndSetBackgroundImage() {
        try {
            // 从文件中读取背景图片
            cardsPileBackground = ImageIO.read(new File("images/PlayerCardsPileBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Card> drawCardFromDrawPile(int number) { //抽牌
        ArrayList<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            drawnCards.add(this.drawPile.pop());
        }
        return drawnCards;
    }

    public void pushCardIntoDiscardPile(Card card) { //回收废牌
        this.discardPile.push(card);
    }

    private void drawPeekCard() {
        //TODO 将抽牌堆和废牌堆中最上方的牌给画出
    }

    public void drawCardsPile(Graphics g) {
        if (cardsPileBackground != null) { // 如果背景图片已加载
            // 在面板上绘制背景图片，使其填充整个面板
            g.drawImage(cardsPileBackground, drawPileX, drawPileY, drawPileWidth, drawPileHeight, null);
            g.drawImage(cardsPileBackground, discardPileX, discardPileY, discardPileWidth, discardPileHeight, null);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        drawCardsPile(g);
    }
}

