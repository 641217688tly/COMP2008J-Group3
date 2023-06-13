package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.CardsListener.CardListener;
import Listener.ModuleListener.PlayerAndComponentsListener.BankListener;
import Module.Cards.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Bank extends JPanel {
    public Point[][] cardsCoordinates;
    public Card[][] cardsTable;
    public ArrayList<JButton> hereButtons;
    private Player owner;
    private JButton closeButton; // 新增一个关闭按钮
    private BankListener bankListener;
    private Image bankImage;

    public Bank(Player owner) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        this.setBounds(0, ApplicationStart.screenHeight / 5 - (ApplicationStart.screenHeight / 25), ApplicationStart.screenWidth, 3 * ApplicationStart.screenHeight / 5 + (ApplicationStart.screenHeight / 25)); // 设置提示框的位置和大小
        this.setVisible(false); // 初始时设为不可见

        this.hereButtons = new ArrayList<>();
        this.cardsTable = new Card[3][12];
        this.owner = owner;
        this.bankListener = new BankListener();
        loadAndSetPlayerCardsPileBackground();
        initButtons();
        initCardsCoordinates();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // 从文件中读取背景图片
            bankImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerComponentsBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        closeButton = new JButton("Close");
        this.add(closeButton); // 将关闭按钮添加到这个JPanel
        closeButton.setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight / 25); // 这个值可能需要调整，以便将关闭按钮放在适当的位置
        closeButton.addActionListener(bankListener.closeButtonListener(owner, this));
    }

    private void initCardsCoordinates() {
        cardsCoordinates = new Point[3][12];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                cardsCoordinates[row][column] = new Point((ApplicationStart.screenWidth / 12) * column, ApplicationStart.screenHeight / 25 + row * ApplicationStart.screenHeight / 5);
            }
        }
    }

    private void setCardBounds(Card card, int x, int y, boolean isDisplayable, boolean isCardFront) {
        card.setCardJPanelBounds(x, y); //为Card重新分配它在该JPanel下的坐标
        card.setIsDisplayable(isDisplayable);
        card.setIsCardFront(isCardFront);
    }

    public boolean containsCard(Card card) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                if (cardsTable[row][column] == card) {
                    return true;
                }
            }
        }
        return false;
    }

    public int calculateTotalAssetsInBank() { //计算银行的总资产
        int totalAssets = 0;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                if (this.cardsTable[row][column] != null) {
                    totalAssets = this.cardsTable[row][column].value + totalAssets;
                }
            }
        }
        return totalAssets;
    }

    //-------为Bank添加和移除临时按钮的方法:

    public void addAndPaintPledgeButtons(int totalRent) { //创建用于选择抵押债务的选择按钮
        if (owner.isInAction()) {
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 12; column++) {
                    if (cardsTable[row][column] != null) {
                        JButton pledgeButton = new JButton("Pledge");
                        pledgeButton.setBounds(1 * Card.cardWidth / 5, 0, 3 * Card.cardWidth / 5, Card.cardHeight / 8);
                        Font buttonFont = new Font("Arial", Font.BOLD, 8);
                        pledgeButton.setFont(buttonFont); // 设置按钮的字体和字体大小
                        pledgeButton.addActionListener((new CardListener()).pledgeButtonListener(owner, totalRent, cardsTable[row][column], false));
                        cardsTable[row][column].add(pledgeButton);
                        pledgeButton.setVisible(true);
                    }
                }
            }
        }
    }

    public void hideAndRemovePledgeButtons(Player debtor) { //可能导致BUG:移除PropertyCard和PropertyWildCard中最新被添加的PledgeButtons
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                if (cardsTable[row][column] != null) {
                    int lastIndex = cardsTable[row][column].getComponentCount() - 1;
                    cardsTable[row][column].getComponent(lastIndex).setVisible(false);
                    cardsTable[row][column].remove(lastIndex);
                }
            }
        }
        //再删除被添加到抵押数组中卡牌上的按钮:
        if (debtor.pledgeCardFromBank.size() > 0) {
            for (Card card : debtor.pledgeCardFromBank) {
                int lastIndex = card.getComponentCount() - 1;
                card.getComponent(lastIndex).setVisible(false);
                card.remove(lastIndex);
            }
        }
    }

    public void addAndPaintHereButtons(Card movedCard) { ///HereButton:用于实现Card在Bank内的自由移动
        //仅当玩家在自己的回合时,且使用了Card上的Move按钮后Here按钮才会被创建
        hereButtons.clear();
        if (owner.isPlayerTurn()) {
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 12; column++) {
                    if (cardsTable[row][column] == null) {
                        JButton herebutton = new JButton("Here");
                        herebutton.setBounds(cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
                        Font buttonFont = new Font("Arial", Font.BOLD, 10);
                        herebutton.setFont(buttonFont); // 设置按钮的字体和字体大小
                        herebutton.addActionListener(bankListener.moveButtonListener(owner, movedCard, herebutton));
                        this.add(herebutton);
                        hereButtons.add(herebutton);
                        herebutton.setVisible(true);
                    }
                }
            }
        }
    }

    public void moveCardAndUpdateScreen(Player owner, Card movedCard, JButton hereButton) { //实现Card的移动效果
        Point movedCardPoint = new Point(movedCard.getX(), movedCard.getY());
        Point hereButtonPoint = new Point(hereButton.getX(), hereButton.getY());
        Point movedCardIndex = new Point();
        Point hereButtonIndex = new Point();
        //先找到被移动的卡牌和空位在二维数组中的位置
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                if (movedCardPoint.x == owner.bank.cardsCoordinates[row][column].x && movedCardPoint.getY() == owner.bank.cardsCoordinates[row][column].y) {
                    movedCardIndex.setLocation(row, column);
                } else if (hereButtonPoint.x == owner.bank.cardsCoordinates[row][column].x && hereButtonPoint.getY() == owner.bank.cardsCoordinates[row][column].y) {
                    hereButtonIndex.setLocation(row, column);
                }
            }
        }
        //先改变卡牌的位置
        movedCard.setBounds(hereButtonPoint.x, hereButtonPoint.y, Card.cardWidth, Card.cardHeight);
        //再改变按钮的位置:
        hereButton.setBounds(movedCardPoint.x, movedCardPoint.y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
        //改变bank中卡牌的位置:
        owner.bank.cardsTable[hereButtonIndex.x][hereButtonIndex.y] = movedCard;
        owner.bank.cardsTable[movedCardIndex.x][movedCardIndex.y] = null;
        //隐藏所有的JButton:
        Iterator<JButton> iterator = owner.bank.hereButtons.iterator();
        while (iterator.hasNext()) {
            JButton button = iterator.next();
            button.setVisible(false);
            owner.bank.remove(button); //从JPanel中移除这个按钮
            iterator.remove(); //从ArrayList中移除这个按钮
        }
        owner.bank.hereButtons.clear();
        //更新屏幕
        paintAllCardsFront();
    }

    //-------存钱和取钱方法:

    public Card removeCardFromBank(Card removedCard) {
        for (int row = 0; row < 3; row++) {
            boolean flag = false;
            for (int column = 0; column < 12; column++) {
                if (cardsTable[row][column] == removedCard) {
                    cardsTable[row][column] = null;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        this.remove(removedCard);
        return removedCard;
    }

    public void saveMoneyAndShowCards(Card card) {//每次调用都会添加钱卡进银行并且刷新Card的呈现状态
        card.owner = this.owner;
        //先将牌存进容器中:
        for (int row = 0; row < 3; row++) {
            boolean flag = false;
            for (int column = 0; column < 12; column++) {
                if (cardsTable[row][column] == null) {
                    cardsTable[row][column] = card;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        //为所有牌重新分配坐标并设置状态
        paintAllCardsFront();
        this.add(card);
    }

    //-------绘制方法:

    public void paintAllCardsFront() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                if (cardsTable[row][column] != null) {
                    Card card = cardsTable[row][column];
                    setCardBounds(card, cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, true, true);
                    if (owner.isPlayerTurn()) {
                        card.openMoveButtonSwitch(true);
                        card.openPlayButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                    } else {
                        card.openMoveButtonSwitch(false);
                        card.openPlayButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                    }
                }
            }
        }
    }

    private void paintBankPile(Graphics g) {
        if (bankImage != null) {
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 12; column++) {
                    g.drawImage(bankImage, cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintBankPile(g);
    }
}