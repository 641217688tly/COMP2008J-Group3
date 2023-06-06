package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.PropertyListener;
import Module.Cards.ActionCard;
import Module.Cards.Card;
import Module.Cards.CardsEnum.PropertyCardType;
import Module.Cards.PropertyCard;
import Module.Cards.PropertyWildCard;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Property extends JPanel { //房产类
    public Point[][] cardsCoordinates;
    public Card[][] cardsTable;
    public ArrayList<JButton> hereButtons;
    private Player owner;
    private JButton closeButton; // 新增一个关闭按钮
    private PropertyListener propertyListener;
    private Image propertyBackgroundImage;

    public Property(Player owner) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        this.setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight);
        this.setVisible(false); // 初始时设为不可见

        this.hereButtons = new ArrayList<>();
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

    public boolean containsCard(Card card) {
        boolean flag = false;
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] == card) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
/*
Railroad(4) Red Orange Yellow Green Blue LightBlue Pink Brown(2) Utility(2)
C C C C C C C C C C C B
C C C C C C C C C C C B
C C C C C C C C C C C B
C C C C C C C C C C C B
H H H H H H H H H H C B
*/
    //-----------存牌:

    private void distributeCardLocation(Card card) {
        if (card instanceof PropertyCard) {
            if (((PropertyCard) card).type.equals(PropertyCardType.RAILROAD)) {
                for (int row = 0; row < 4; row++) {
                    if (cardsTable[row][0] == null) {
                        cardsTable[row][0] = card;
                        return;
                    }
                }
            } else if (((PropertyCard) card).type.equals(PropertyCardType.RED)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][1] == null) {
                        cardsTable[row][1] = card;
                        return;
                    }
                }
            } else if (((PropertyCard) card).type.equals(PropertyCardType.ORANGE)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][2] == null) {
                        cardsTable[row][2] = card;
                        return;
                    }
                }
            } else if (((PropertyCard) card).type.equals(PropertyCardType.YELLOW)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][3] == null) {
                        cardsTable[row][3] = card;
                        return;
                    }
                }
            } else if (((PropertyCard) card).type.equals(PropertyCardType.GREEN)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][4] == null) {
                        cardsTable[row][4] = card;
                        return;
                    }
                }
            } else if (((PropertyCard) card).type.equals(PropertyCardType.BLUE)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][5] == null) {
                        cardsTable[row][5] = card;
                        return;
                    }
                }
            } else if (((PropertyCard) card).type.equals(PropertyCardType.LIGHTBLUE)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][6] == null) {
                        cardsTable[row][6] = card;
                        return;
                    }
                }
            } else if (((PropertyCard) card).type.equals(PropertyCardType.PINK)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][7] == null) {
                        cardsTable[row][7] = card;
                        return;
                    }
                }
            } else if (((PropertyCard) card).type.equals(PropertyCardType.BROWN)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][8] == null) {
                        cardsTable[row][8] = card;
                        return;
                    }
                }
            } else if (((PropertyCard) card).type.equals(PropertyCardType.UTILITY)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][9] == null) {
                        cardsTable[row][9] = card;
                        return;
                    }
                }
            } else { //位置已满(已经凑够了一套房)
                for (int row = 0; row < 5; row++) {
                    if (cardsTable[row][10] == null) {
                        cardsTable[row][10] = card;
                        return;
                    }
                }
            }
        } else if (card instanceof PropertyWildCard) {
            if (((PropertyWildCard) card).currentType.equals(PropertyCardType.RAILROAD)) {
                for (int row = 0; row < 4; row++) {
                    if (cardsTable[row][0] == null) {
                        cardsTable[row][0] = card;
                        return;
                    }
                }
            } else if (((PropertyWildCard) card).currentType.equals(PropertyCardType.RED)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][1] == null) {
                        cardsTable[row][1] = card;
                        return;
                    }
                }
            } else if (((PropertyWildCard) card).currentType.equals(PropertyCardType.ORANGE)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][2] == null) {
                        cardsTable[row][2] = card;
                        return;
                    }
                }
            } else if (((PropertyWildCard) card).currentType.equals(PropertyCardType.YELLOW)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][3] == null) {
                        cardsTable[row][3] = card;
                        return;
                    }
                }
            } else if (((PropertyWildCard) card).currentType.equals(PropertyCardType.GREEN)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][4] == null) {
                        cardsTable[row][4] = card;
                        return;
                    }
                }
            } else if (((PropertyWildCard) card).currentType.equals(PropertyCardType.BLUE)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][5] == null) {
                        cardsTable[row][5] = card;
                        return;
                    }
                }
            } else if (((PropertyWildCard) card).currentType.equals(PropertyCardType.LIGHTBLUE)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][6] == null) {
                        cardsTable[row][6] = card;
                        return;
                    }
                }
            } else if (((PropertyWildCard) card).currentType.equals(PropertyCardType.PINK)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][7] == null) {
                        cardsTable[row][7] = card;
                        return;
                    }
                }
            } else if (((PropertyWildCard) card).currentType.equals(PropertyCardType.BROWN)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][8] == null) {
                        cardsTable[row][8] = card;
                        return;
                    }
                }
            } else if (((PropertyWildCard) card).currentType.equals(PropertyCardType.UTILITY)) {
                for (int row = 0; row < 3; row++) {
                    if (cardsTable[row][9] == null) {
                        cardsTable[row][9] = card;
                        return;
                    }
                }
            } else { //位置已满(已经凑够了一套房)
                for (int row = 0; row < 5; row++) {
                    if (cardsTable[row][10] == null) {
                        cardsTable[row][10] = card;
                        return;
                    }
                }
            }
        } else if (card instanceof ActionCard) { //存储酒店卡
            for (int column = 0; column < 10; column++) {
                if (cardsTable[4][column] == null) {
                    cardsTable[4][column] = card;
                    return;
                }
            }
        }
    }

    public void addAndPaintHereButtons(Card movedCard) {
        hereButtons.clear();
        if (owner.isPlayerTurn()) { //仅当玩家处于自己的回合时才能创建JButtons
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 11; column++) {
                    if (cardsTable[row][column] == null) {
                        JButton herebutton = new JButton("Here");
                        herebutton.setBounds(cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
                        Font buttonFont = new Font("Arial", Font.BOLD, 10);
                        herebutton.setFont(buttonFont); // 设置按钮的字体和字体大小
                        herebutton.addActionListener(propertyListener.moveButtonListener(owner, movedCard, herebutton));
                        this.add(herebutton);
                        hereButtons.add(herebutton);
                        herebutton.setVisible(true);
                    }
                }
            }
        }
    }

    public void moveCardAndUpdateScreen(Player owner, Card movedCard, JButton hereButton) {
        Point movedCardPoint = new Point(movedCard.getX(), movedCard.getY());
        Point hereButtonPoint = new Point(hereButton.getX(), hereButton.getY());
        Point movedCardIndex = new Point();
        Point hereButtonIndex = new Point();
        //先找到被移动的卡牌和空位在二维数组中的位置
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (movedCardPoint.x == owner.property.cardsCoordinates[row][column].x && movedCardPoint.getY() == owner.property.cardsCoordinates[row][column].y) {
                    movedCardIndex.setLocation(row, column);
                } else if (hereButtonPoint.x == owner.property.cardsCoordinates[row][column].x && hereButtonPoint.getY() == owner.property.cardsCoordinates[row][column].y) {
                    hereButtonIndex.setLocation(row, column);
                }
            }
        }
        //先改变卡牌的位置
        movedCard.setBounds(hereButtonPoint.x, hereButtonPoint.y, Card.cardWidth, Card.cardHeight);
        //再改变按钮的位置:
        hereButton.setBounds(movedCardPoint.x, movedCardPoint.y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
        //改变bank中卡牌的位置:
        owner.property.cardsTable[hereButtonIndex.x][hereButtonIndex.y] = movedCard;
        owner.property.cardsTable[movedCardIndex.x][movedCardIndex.y] = null;
        //隐藏所有的JButton:
        Iterator<JButton> iterator = owner.property.hereButtons.iterator();
        while (iterator.hasNext()) {
            JButton button = iterator.next();
            button.setVisible(false);
            owner.property.remove(button); //从JPanel中移除这个按钮
            iterator.remove(); //从ArrayList中移除这个按钮
        }
        owner.property.hereButtons.clear();
        //更新屏幕
        reallocateAllCards();
    }

    public void placePropertyCardAndShowTable(Card card) {
        //先将牌存进容器中:
        distributeCardLocation(card);
        reallocateAllCards(); //为所有的牌重新分配坐标和可视化状态
        this.add(card); //添加卡牌到JPanel中
    }

    //-------绘制方法:

    public void reallocateAllCards() {
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    Card card = cardsTable[row][column];
                    setCardBounds(card, cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, true, true);
                    //管理卡牌的开关
                    if (owner.isPlayerTurn()) {
                        card.openMoveButtonSwitch(true);
                        card.openDiscardButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openPlayButtonSwitch(false);
                        if (card instanceof PropertyWildCard) {
                            //为双色房产卡打开开关
                            ((PropertyWildCard) card).openReverseButtonSwitch(true);
                        }
                    } else {
                        card.openMoveButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openPlayButtonSwitch(false);
                        if (card instanceof PropertyWildCard) {
                            //为双色房产卡关闭开关
                            ((PropertyWildCard) card).openReverseButtonSwitch(false);
                        }
                    }
                }
            }
        }
        //设置ActionCard
        for (int column = 0; column < 11; column++) {
            if (cardsTable[4][column] != null) {
                Card card = cardsTable[4][column];
                setCardBounds(card, cardsCoordinates[4][column].x, cardsCoordinates[4][column].y, true, true);
                if (owner.isPlayerTurn()) {
                    card.openMoveButtonSwitch(true);
                    card.openPlayButtonSwitch(false);
                    card.openDiscardButtonSwitch(false);
                    card.openDiscardButtonSwitch(false);
                } else {
                    card.openMoveButtonSwitch(false);
                    card.openPlayButtonSwitch(false);
                    card.openDiscardButtonSwitch(false);
                    card.openDiscardButtonSwitch(false);
                }
            }
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
