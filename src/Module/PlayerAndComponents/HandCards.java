package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.HandCardsListener;
import Module.Cards.ActionCard;
import Module.Cards.Card;
import Module.Cards.CardsEnum.ActionCardType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class HandCards extends JPanel { //该类为玩家边框上的按钮,用于查看玩家的手牌
    public Card[] cardsTable;
    public Point[] cardsCoordinates;
    public ArrayList<JButton> hereButtons;
    private Player owner;
    public JButton closeButton; // 新增一个关闭按钮
    private HandCardsListener handCardsListener;
    private Image handCardsImage;

    public HandCards(Player owner) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        this.setBounds(0, 3 * ApplicationStart.screenHeight / 5 - (ApplicationStart.screenHeight / 25), ApplicationStart.screenWidth, ApplicationStart.screenHeight / 5 + (ApplicationStart.screenHeight / 25)); // 设置提示框的位置和大小
        this.setVisible(false); // 初始时设为不可见

        this.cardsTable = new Card[12];
        this.hereButtons = new ArrayList<>();
        this.owner = owner;
        this.handCardsListener = new HandCardsListener();
        loadAndSetPlayerCardsPileBackground();
        initButtons();
        initCardsCoordinates();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // 从文件中读取背景图片
            handCardsImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerComponentsBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCardsCoordinates() {
        cardsCoordinates = new Point[12];
        for (int column = 0; column < 12; column++) {
            cardsCoordinates[column] = new Point((ApplicationStart.screenWidth / 12) * column, ApplicationStart.screenHeight / 25);
        }
    }

    private void initButtons() {
        closeButton = new JButton("Close");
        this.add(closeButton); // 将关闭按钮添加到这个JPanel
        closeButton.setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight / 25); // 这个值可能需要调整，以便将关闭按钮放在适当的位置
        closeButton.addActionListener(handCardsListener.closeButtonListener(owner, this));
    }

    public void addAndPaintHereButtons(Card movedCard) {
        hereButtons.clear();
        if (owner.isPlayerTurn()) { //仅当玩家处于自己的回合时才能创建JButtons
            for (int i = 0; i < 12; i++) {
                if (cardsTable[i] == null) {
                    JButton herebutton = new JButton("Here");
                    herebutton.setBounds(cardsCoordinates[i].x, cardsCoordinates[i].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
                    Font buttonFont = new Font("Arial", Font.BOLD, 10);
                    herebutton.setFont(buttonFont); // 设置按钮的字体和字体大小
                    herebutton.addActionListener(handCardsListener.moveButtonListener(owner, movedCard, herebutton));
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
        for (int i = 0; i < owner.handCards.cardsCoordinates.length; i++) {
            if (movedCardPoint.x == owner.handCards.cardsCoordinates[i].x && movedCardPoint.getY() == owner.handCards.cardsCoordinates[i].y) {
                movedCardIndex = i;
            } else if (hereButtonPoint.x == owner.handCards.cardsCoordinates[i].x && hereButtonPoint.getY() == owner.handCards.cardsCoordinates[i].y) {
                hereButtonIndex = i;
            }
        }

        //先改变卡牌的位置
        movedCard.setBounds(hereButtonPoint.x, hereButtonPoint.y, Card.cardWidth, Card.cardHeight);
        //再改变按钮的位置:
        hereButton.setBounds(movedCardPoint.x, movedCardPoint.y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
        //改变cardsTable中卡牌的位置:
        owner.cardsTable[hereButtonIndex] = movedCard;
        owner.handCards.cardsTable[hereButtonIndex] = movedCard;
        owner.cardsTable[movedCardIndex] = null;
        owner.handCards.cardsTable[movedCardIndex] = null;
        //隐藏所有的JButton:
        Iterator<JButton> iterator = owner.handCards.hereButtons.iterator();
        while (iterator.hasNext()) {
            JButton button = iterator.next();
            button.setVisible(false);
            owner.handCards.remove(button); //从JPanel中移除这个按钮
            iterator.remove(); //从ArrayList中移除这个按钮
        }
        owner.handCards.hereButtons.clear();
        //更新屏幕
        paintAllCards();
    }

    public void updateAndShowCards() { //每次调用都需要清除列表中已有的牌并移除JPanel中牌对应的组件,相当于HandCards的刷新方法
        this.cardsTable = owner.cardsTable; //克隆玩家的数组
        this.removeAll(); //将旧牌(组件)全部丢弃,但这会导致按钮也丢失
        this.add(closeButton); //将被丢失的按钮加上
        paintAllCards();
    }

    //-------绘制方法:

    private void paintAllCards() {
        for (int i = 0; i < cardsTable.length; i++) {
            if (cardsTable[i] != null) {
                Card card = cardsTable[i];
                card.setCardJPanelBounds(cardsCoordinates[i].x, cardsCoordinates[i].y); //为Card重新分配它在该JPanel下的坐标
                if (owner.isPlayerTurn()) { //处于自己的回合
                    if (owner.isInAction()) {//处于行动中
                        if (owner.interactivePlayers.size() > 0) {
                            card.setIsCardFront(true);
                            card.openPlayButtonSwitch(false);
                            card.openDepositButtonSwitch(false);
                            card.openDiscardButtonSwitch(false);
                            card.openMoveButtonSwitch(false);
                            if (card instanceof ActionCard) {
                                if (((ActionCard) card).type.equals(ActionCardType.JUST_SAY_NO)) {
                                    card.openPlayButtonSwitch(true);
                                }
                            }
                        } else {
                            card.setIsCardFront(true);
                            card.openPlayButtonSwitch(true);
                            card.openDepositButtonSwitch(true);
                            card.openDiscardButtonSwitch(true);
                            card.openMoveButtonSwitch(true);
                        }
                    } else { //处于自己的回合但不在行动中
                        card.setIsCardFront(false);
                        card.openPlayButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openMoveButtonSwitch(false);
                    }
                } else { //不处于自己的回合
                    if (owner.isInAction()) { //处于行动中
                        card.setIsCardFront(true);
                        card.openPlayButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openMoveButtonSwitch(false);
                        if (card instanceof ActionCard) {
                            if (((ActionCard) card).type.equals(ActionCardType.JUST_SAY_NO)) {
                                card.openPlayButtonSwitch(true);
                            }
                        }
                    } else { //不处于自己的回合,也不在行动中
                        card.setIsCardFront(false);
                        card.openPlayButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openMoveButtonSwitch(false);
                    }
                }
                card.setIsDisplayable(true);
                this.add(card);
            }
        }
    }

    private void paintHandCardsPile(Graphics g) {
        if (handCardsImage != null) {
            for (int i = 0; i < 12; i++) {
                g.drawImage(handCardsImage, cardsCoordinates[i].x, cardsCoordinates[i].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintHandCardsPile(g);
    }
}
