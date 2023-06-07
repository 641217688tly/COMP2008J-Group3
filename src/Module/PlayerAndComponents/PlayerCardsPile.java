package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.HandCardsListener;
import Listener.ModuleListener.PlayerAndComponentsListener.PlayerCardsPileListener;
import Module.Cards.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class PlayerCardsPile extends JPanel { // 该类为呈现当前回合下玩家手上的卡牌
    public static int playerCardsPileJPanelX = 0;
    public static int playerCardsPileJPanelY = (ApplicationStart.screenHeight * 4) / 5;
    public static int playerCardsPileJPanelWidth = (ApplicationStart.screenWidth * 11) / 12;
    public static int playerCardsPileJPanelHeight = (ApplicationStart.screenHeight) / 5;
    public Card[] cardsTable;
    public Point[] cardsCoordinates;
    public ArrayList<JButton> hereButtons;
    private PlayerCardsPileListener playerCardsPileListener;
    private Player owner;
    private Image playerCardsPileImage;

    public PlayerCardsPile(Player owner) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        this.setBounds(PlayerCardsPile.playerCardsPileJPanelX, PlayerCardsPile.playerCardsPileJPanelY, PlayerCardsPile.playerCardsPileJPanelWidth, PlayerCardsPile.playerCardsPileJPanelHeight);

        this.playerCardsPileListener = new PlayerCardsPileListener();
        this.hereButtons = new ArrayList<>();
        this.owner = owner;
        this.cardsTable = new Card[12];
        loadAndSetPlayerCardsPileBackground();
        initCardsCoordinates();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // 从文件中读取背景图片
            playerCardsPileImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerCardsPileBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCardsCoordinates() {
        cardsCoordinates = new Point[12];
        for (int column = 0; column < 12; column++) {
            cardsCoordinates[column] = new Point((ApplicationStart.screenWidth / 12) * column, 0);
        }
    }

    public void updateAndShowCards() { //每次调用都需要清除列表中已有的牌并移除JPanel中牌对应的组件,相当于PlayerCardsPile的刷新方法
        this.cardsTable = owner.cardsTable; //克隆玩家的数组
        this.removeAll(); //将旧牌(组件)全部丢弃,但这会导致按钮也丢失
        paintCardsFrontUpToEleven();
    }

    //-------绘制方法:

    public void addAndPaintHereButtons(Card movedCard) {
        hereButtons.clear();
        if (owner.isPlayerTurn()) { //仅当玩家处于自己的回合时才能创建JButtons
            for (int i = 0; i < 11; i++) {
                if (cardsTable[i] == null) {
                    JButton herebutton = new JButton("Here");
                    herebutton.setBounds(cardsCoordinates[i].x, cardsCoordinates[i].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
                    Font buttonFont = new Font("Arial", Font.BOLD, 10);
                    herebutton.setFont(buttonFont); // 设置按钮的字体和字体大小
                    herebutton.addActionListener(playerCardsPileListener.moveButtonListener(owner, movedCard, herebutton));
                    this.add(herebutton);
                    hereButtons.add(herebutton);
                    herebutton.setVisible(true);
                }
            }
        }
    }

    public void moveCardAndUpdateScreen(Player owner, Card movedCard, JButton hereButton) {
        Point movedCardPoint = new Point(movedCard.getX(), movedCard.getY());
        Point hereButtonPoint = new Point(hereButton.getX(), hereButton.getY());
        int movedCardIndex = 0;
        int hereButtonIndex = 0;
        for (int i = 0; i < owner.playerCardsPile.cardsCoordinates.length; i++) {
            if (movedCardPoint.x == owner.playerCardsPile.cardsCoordinates[i].x && movedCardPoint.getY() == owner.playerCardsPile.cardsCoordinates[i].y) {
                movedCardIndex = i;
            } else if (hereButtonPoint.x == owner.handCards.cardsCoordinates[i].x && hereButtonPoint.getY() == owner.playerCardsPile.cardsCoordinates[i].y) {
                hereButtonIndex = i;
            }
        }

        //先改变卡牌的位置
        movedCard.setBounds(hereButtonPoint.x, hereButtonPoint.y, Card.cardWidth, Card.cardHeight);
        //再改变按钮的位置:
        hereButton.setBounds(movedCardPoint.x, movedCardPoint.y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
        //改变cardsTable中卡牌的位置:
        owner.cardsTable[hereButtonIndex] = movedCard;
        owner.playerCardsPile.cardsTable[hereButtonIndex] = movedCard;
        owner.cardsTable[movedCardIndex] = null;
        owner.playerCardsPile.cardsTable[movedCardIndex] = null;
        //隐藏所有的JButton:
        Iterator<JButton> iterator = owner.playerCardsPile.hereButtons.iterator();
        while (iterator.hasNext()) {
            JButton button = iterator.next();
            button.setVisible(false);
            owner.playerCardsPile.remove(button); //从JPanel中移除这个按钮
            iterator.remove(); //从ArrayList中移除这个按钮
        }
        owner.playerCardsPile.hereButtons.clear();
        //更新屏幕
        paintCardsFrontUpToEleven();
    }

    private void paintCardsFrontUpToEleven() {
        if (owner.isInAction() && owner.isPlayerTurn()) { //既是玩家的回合,也是该回合的玩家在行动
            this.setVisible(true);
        } else if (owner.isInAction() && !owner.isPlayerTurn()) { //虽然玩家在行动,但不是该玩家的回合
            this.setVisible(false);
            return;
        } else if (!owner.isInAction() && !owner.isPlayerTurn()) { //玩家既不在行动,也不是该玩家的回合
            this.setVisible(false);
            return;
        }
        for (int i = 0; i < cardsTable.length; i++) {
            if (cardsTable[i] != null) {
                Card card = cardsTable[i];
                card.setCardJPanelBounds(cardsCoordinates[i].x, cardsCoordinates[i].y); //为Card重新分配它在该JPanel下的坐标
                if (owner.isPlayerTurn()) { //处于自己的回合
                    if (owner.isInAction) {//处于行动中
                        card.setIsCardFront(true);
                        card.openPlayButtonSwitch(true);
                        card.openDepositButtonSwitch(true);
                        card.openDiscardButtonSwitch(true);
                        card.openMoveButtonSwitch(true);
                        card.setIsDisplayable(true);
                    } else { //处于自己的回合但不在行动中
                        card.setIsCardFront(false);
                        card.openPlayButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openMoveButtonSwitch(false);
                        card.setIsDisplayable(false);
                    }
                } else { //不处于自己的回合
                    if (owner.isInAction) { //处于行动中
                        card.setIsCardFront(true);
                        card.openPlayButtonSwitch(true);
                        card.openDepositButtonSwitch(true);
                        card.openDiscardButtonSwitch(true);
                        card.openMoveButtonSwitch(true);
                        card.setIsDisplayable(false);
                    } else { //不处于自己的回合,也不在行动中
                        card.setIsCardFront(false);
                        card.openPlayButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openMoveButtonSwitch(false);
                        card.setIsDisplayable(false);
                    }
                }
                this.add(card);
            }
        }
    }

    private void paintPlayerCardsPile(Graphics g) {
        if (playerCardsPileImage != null) {
            if (owner.isPlayerTurn()) { //如果正处于玩家的回合
                if (owner.isInAction) {
                    for (int i = 0; i < 11; i++) {
                        g.drawImage(playerCardsPileImage, cardsCoordinates[i].x, cardsCoordinates[i].y, ApplicationStart.screenWidth / 12, playerCardsPileJPanelHeight, null);
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (owner.isPlayerTurn()) {
            if (owner.isInAction) {
                super.paintComponent(g); // 调用父类方法以确保正常绘制
                paintPlayerCardsPile(g);
            }

        }
    }
}
