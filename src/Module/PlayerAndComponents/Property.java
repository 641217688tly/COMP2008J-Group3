package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.PropertyListener;
import Module.Cards.ActionCard;
import Module.Cards.Card;
import Module.Cards.PropertyCard;
import Module.Cards.PropertyWildCard;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Property extends JPanel { //房产类
    public Point[][] cardsCoordinates;
    public Card[][] cardsTable;
    private Player owner;
    private JButton closeButton; // 新增一个关闭按钮
    private PropertyListener propertyListener;
    private Image propertyBackgroundImage;


    public Property(Player owner) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        this.setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight);
        this.setVisible(false); // 初始时设为不可见


        this.cardsTable = new Card[5][11];
        this.owner = owner;
        this.propertyListener = new PropertyListener();
        loadAndSetPlayerCardsPileBackground();
        initButtons();
        initCardsCoordinates();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // 从文件中读取背景图片
            propertyBackgroundImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerComponentsBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        closeButton = new JButton("Close");
        this.add(closeButton); // 将关闭按钮添加到这个JPanel
        closeButton.setBounds(11 * ApplicationStart.screenWidth / 12, 0, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight); // 这个值可能需要调整，以便将关闭按钮放在适当的位置
        closeButton.addActionListener(propertyListener.closeButtonListener(owner, this));
    }

    private void initCardsCoordinates() {
        cardsCoordinates = new Point[5][11];
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                cardsCoordinates[row][column] = new Point((ApplicationStart.screenWidth / 12) * column, row * ApplicationStart.screenHeight / 5);
            }
        }
    }

    private void setCardBounds(Card card, int x, int y, boolean isDisplayable, boolean isCardFront) {
        card.setCardJPanelBounds(x, y); //为Card重新分配它在该JPanel下的坐标
        card.setIsDisplayable(isDisplayable);
        card.setIsCardFront(isCardFront);
    }
/*
C C C C C C C C C C C B
C C C C C C C C C C C B
C C C C C C C C C C C B
C C C C C C C C C C C B
H H H H H H H H H H H B
*/
    //-----------存牌:

    public void placePropertyCardAndShowTable(Card card) {
        //先将牌存进容器中:
        if (card instanceof PropertyCard || card instanceof PropertyWildCard) {
            for (int row = 0; row < 4; row++) { //存储房产卡
                boolean flag = false;
                for (int column = 0; column < 11; column++) {
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
        } else if (card instanceof ActionCard) { //存储酒店卡
            for (int column = 0; column < 11; column++) {
                if (cardsTable[4][column] == null) {
                    cardsTable[4][column] = card;
                    break;
                }
            }
        }
        reallocateAllCards(); //为所有的牌重新分配坐标和可视化状态
        this.add(card); //添加卡牌到JPanel中
    }


    //-------绘制方法:

    public void reallocateAllCards() {
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    setCardBounds(cardsTable[row][column], cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, true, true);
                    //管理卡牌的开关
                    cardsTable[row][column].openDiscardButtonSwitch(false);
                    cardsTable[row][column].openDepositButtonSwitch(false);
                    cardsTable[row][column].openPlayButtonSwitch(false);
                    if (owner.isPlayerTurn()) {
                        if (cardsTable[row][column] instanceof PropertyWildCard) {
                            //为双色房产卡打开开关
                            ((PropertyWildCard) cardsTable[row][column]).openReverseButtonSwitch(true);
                        }
                    } else {
                        if (cardsTable[row][column] instanceof PropertyWildCard) {
                            //为双色房产卡关闭开关
                            ((PropertyWildCard) cardsTable[row][column]).openReverseButtonSwitch(false);
                        }
                    }
                }
            }
        }
        //设置ActionCard
        for (int column = 0; column < 11; column++) {
            //ActionCard card = (ActionCard) cardsTable[4][column];
        }

    }

    private void paintPropertyPile(Graphics g) {
        if (propertyBackgroundImage != null) {
            for (int i = 0; i < 11; i++) {
                g.drawImage(propertyBackgroundImage, (ApplicationStart.screenWidth / 12) * i, 0, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(propertyBackgroundImage, (ApplicationStart.screenWidth / 12) * i, 1 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(propertyBackgroundImage, (ApplicationStart.screenWidth / 12) * i, 2 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(propertyBackgroundImage, (ApplicationStart.screenWidth / 12) * i, 3 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(propertyBackgroundImage, (ApplicationStart.screenWidth / 12) * i, 4 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        paintPropertyPile(g);
    }


}
