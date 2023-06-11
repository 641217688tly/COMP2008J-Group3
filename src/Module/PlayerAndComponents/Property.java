/*
Railroad(4) Red Orange Yellow Green Blue LightBlue Pink Brown(2) Utility(2)
C C C C C C C C C C C B
C C C C C C C C C C C B
C C C C C C C C C C C B
C C C C C C C C C C C B
H H H H H H H H H H C B
*/
package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.CardsListener.CardListener;
import Listener.ModuleListener.PlayerAndComponentsListener.PropertyListener;
import Module.Cards.*;
import Module.Cards.CardsEnum.ActionCardType;
import Module.Cards.CardsEnum.PropertyCardType;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Property extends JPanel { //房产类
    public Point[][] cardsCoordinates;
    public Card[][] cardsTable;
    public Map<PropertyCardType, Integer> propertyNumberMap;
    public ArrayList<JButton> hereButtons;
    private Player owner;
    public JButton closeButton; // 新增一个关闭按钮
    private PropertyListener propertyListener;
    private Image propertyBackgroundImage;

    public Property(Player owner) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        this.setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight);
        this.setVisible(false); // 初始时设为不可见

        this.propertyNumberMap = new HashMap<>();
        this.hereButtons = new ArrayList<>();
        this.cardsTable = new Card[5][11];
        this.owner = owner;
        this.propertyListener = new PropertyListener();
        loadAndSetPlayerCardsPileBackground();
        initButtons();
        initCardsCoordinates();
        countPropertiesNumber();
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

    public boolean whetherRentCardCanPlay(RentCard rentCard) { //检查Property是否为空,或者是虽然不为空的但没有可以用于收租的房产
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (this.cardsTable[row][column] instanceof PropertyCard || this.cardsTable[row][column] instanceof PropertyWildCard) {
                    if (rentCard.whetherRentCardCanBeUsed(this.cardsTable[row][column])) {
                        return true;
                    }
                }
            }
        }
        return false;
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

    public void countPropertiesNumber() {
        for (PropertyCardType propertyType : PropertyCardType.values()) {
            propertyNumberMap.put(propertyType, 0);
        }
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    Card property = cardsTable[row][column];
                    if (property instanceof PropertyCard) {
                        for (PropertyCardType propertyType : PropertyCardType.values()) {
                            if (((PropertyCard) property).type.equals(propertyType)) {
                                propertyNumberMap.put(propertyType, propertyNumberMap.get(propertyType) + 1);
                            }
                        }
                    } else if (property instanceof PropertyWildCard) {
                        for (PropertyCardType propertyType : PropertyCardType.values()) {
                            if (((PropertyWildCard) property).currentType.equals(propertyType)) {
                                propertyNumberMap.put(propertyType, propertyNumberMap.get(propertyType) + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    public int calculatedRent(Card propertyCard, boolean isDoubleRent) { //给定房产卡的类型和租金是否翻倍,计算最后的总租金
        PropertyCardType searchedType = null;
        int cardsNumber = 0;
        int totalRent = 0;
        ArrayList<Integer> columns = new ArrayList<>();
        if (propertyCard instanceof PropertyCard) {
            searchedType = ((PropertyCard) propertyCard).type;
        } else if (propertyCard instanceof PropertyWildCard) {
            searchedType = ((PropertyWildCard) propertyCard).currentType;
        }
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] instanceof PropertyCard || cardsTable[row][column] instanceof PropertyWildCard) {
                    if (cardsTable[row][column] instanceof PropertyCard) {
                        if (searchedType.equals(((PropertyCard) cardsTable[row][column]).type)) {
                            cardsNumber++;
                            columns.add(column);
                        }
                    }
                    if (cardsTable[row][column] instanceof PropertyWildCard) {
                        if (searchedType.equals(((PropertyWildCard) cardsTable[row][column]).currentType)) {
                            cardsNumber++;
                            columns.add(column);
                        }
                    }
                }
            }
        }
        totalRent = RentCard.obtainRentNumber(searchedType, cardsNumber) + calculateExtraRent(searchedType, cardsNumber, columns);
        if (isDoubleRent) {
            return totalRent * 2;
        } else {
            return totalRent;
        }
    }

    private int calculateExtraRent(PropertyCardType propertyType, int propertyNumber, ArrayList<Integer> columns) {
        int extraRent = 0;
        if (propertyNumber >= PropertyCard.judgeCompleteSet(propertyType)) {
            for (Integer column : columns) {
                boolean flag = false;
                for (int row = 0; row < 5; row++) {
                    if (cardsTable[row][column] != null) {
                        if (cardsTable[row][column] instanceof ActionCard) {
                            ActionCard card = (ActionCard) cardsTable[row][column];
                            if (card.type.equals(ActionCardType.HOTEL)) {
                                extraRent = 4;
                                flag = true;
                                break;
                            } else if (card.type.equals(ActionCardType.HOUSE)) {
                                extraRent = 3;
                                flag = true;
                                break;
                            }
                        }
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
        return extraRent;
    }

    public int calculateTotalAssetsInProperty() { //计算银行的总资产
        int totalAssets = 0;
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (this.cardsTable[row][column] != null) {
                    totalAssets = totalAssets + this.cardsTable[row][column].value;
                }
            }
        }
        return totalAssets;
    }

    public void addAndPaintStealWholePropertyButtons() { //创建用于选择想偷窃的房产的选择按钮
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    JButton stealButton = new JButton("Steal");
                    stealButton.setBounds(1 * Card.cardWidth / 5, 0, 3 * Card.cardWidth / 5, Card.cardHeight / 8);
                    Font buttonFont = new Font("Arial", Font.BOLD, 8);
                    stealButton.setFont(buttonFont); // 设置按钮的字体和字体大小
                    stealButton.addActionListener((new CardListener()).stealWholePropertyButtonListener(owner, cardsTable[row][column]));
                    cardsTable[row][column].add(stealButton);
                    stealButton.setVisible(true);
                }
            }
        }
    }

    public void addAndPaintStealSinglePropertyButtons() { //创建用于选择想偷窃的房产的选择按钮
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    JButton stealButton = new JButton("Steal");
                    stealButton.setBounds(1 * Card.cardWidth / 5, 0, 3 * Card.cardWidth / 5, Card.cardHeight / 8);
                    Font buttonFont = new Font("Arial", Font.BOLD, 8);
                    stealButton.setFont(buttonFont); // 设置按钮的字体和字体大小
                    stealButton.addActionListener((new CardListener()).stealSinglePropertyButtonListener(owner, cardsTable[row][column]));
                    cardsTable[row][column].add(stealButton);
                    stealButton.setVisible(true);
                }
            }
        }

    }

    public void hideAndRemoveStolenButtons(Player stolenPlayer) {
        //先删除房产中现存卡牌上的按钮
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    int lastIndex = cardsTable[row][column].getComponentCount() - 1;
                    cardsTable[row][column].getComponent(lastIndex).setVisible(false);
                    cardsTable[row][column].remove(lastIndex);
                }
            }
        }
        //再删除被添加到抵押数组中卡牌上的按钮:
        if (stolenPlayer.pledgeCardFromProperty.size() > 0) {
            for (Card card : stolenPlayer.pledgeCardFromProperty) {
                int lastIndex = card.getComponentCount() - 1;
                card.getComponent(lastIndex).setVisible(false);
                card.remove(lastIndex);
            }
        }
    }

    public void addAndPaintPledgeButtons(int totalRent) { //创建用于选择抵押债务的选择按钮
        if (owner.isInAction()) {
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 11; column++) {
                    if (cardsTable[row][column] != null) {
                        JButton pledgeButton = new JButton("Pledge");
                        pledgeButton.setBounds(1 * Card.cardWidth / 5, 0, 3 * Card.cardWidth / 5, Card.cardHeight / 8);
                        Font buttonFont = new Font("Arial", Font.BOLD, 8);
                        pledgeButton.setFont(buttonFont); // 设置按钮的字体和字体大小
                        pledgeButton.addActionListener((new CardListener()).pledgeButtonListener(owner, totalRent, cardsTable[row][column], true));
                        cardsTable[row][column].add(pledgeButton);
                        pledgeButton.setVisible(true);
                    }
                }
            }
        }
    }

    public void hideAndRemovePledgeButtons(Player debtor) { //可能导致BUG:移除PropertyCard和PropertyWildCard中最新被添加的PledgeButtons
        //先删除房产中现存卡牌上的按钮
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    int lastIndex = cardsTable[row][column].getComponentCount() - 1;
                    cardsTable[row][column].getComponent(lastIndex).setVisible(false);
                    cardsTable[row][column].remove(lastIndex);
                }
            }
        }
        //再删除被添加到抵押数组中卡牌上的按钮:
        if (debtor.pledgeCardFromProperty.size() > 0) {
            for (Card card : debtor.pledgeCardFromProperty) {
                int lastIndex = card.getComponentCount() - 1;
                card.getComponent(lastIndex).setVisible(false);
                card.remove(lastIndex);
            }
        }
    }

    public void addAndPaintChooseButtons(RentCard rentCard, boolean isDoubleRent) { //该选择按钮用于选择RentCard要收租的房产
        if (owner.isPlayerTurn()) { //仅当玩家处于自己的回合时才能创建JButtons
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 11; column++) {
                    if (cardsTable[row][column] instanceof PropertyCard || cardsTable[row][column] instanceof PropertyWildCard) {
                        JButton chosenButton = new JButton("Use");
                        chosenButton.setBounds(1 * Card.cardWidth / 5, 0, 3 * Card.cardWidth / 5, Card.cardHeight / 8);
                        Font buttonFont = new Font("Arial", Font.BOLD, 10);
                        chosenButton.setFont(buttonFont); // 设置按钮的字体和字体大小
                        chosenButton.addActionListener((new CardListener()).chosenButtonListener(owner, cardsTable[row][column], rentCard, isDoubleRent));
                        cardsTable[row][column].add(chosenButton);
                        chosenButton.setVisible(true);
                    }
                }
            }
        }
    }

    public void hideAndRemoveChooseButtons() { //可能导致BUG:移除PropertyCard和PropertyWildCard中最新被添加的choose按钮
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] instanceof PropertyCard || cardsTable[row][column] instanceof PropertyWildCard) {
                    int lastIndex = cardsTable[row][column].getComponentCount() - 1;
                    cardsTable[row][column].getComponent(lastIndex).setVisible(false);
                    cardsTable[row][column].remove(lastIndex);
                }
            }
        }
    }

    public void addAndPaintHereButtons(Card movedCard) { //在自己回合中的Player调用Card的Move按钮后将会创建HereButtons以辅助Card的移动
        hereButtons.clear();
        if (owner.isPlayerTurn()) { //仅当玩家处于自己的回合时才能创建JButtons
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 11; column++) {
                    if (cardsTable[row][column] == null) {
                        JButton hereButton = new JButton("Here");
                        hereButton.setBounds(cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
                        Font buttonFont = new Font("Arial", Font.BOLD, 10);
                        hereButton.setFont(buttonFont); // 设置按钮的字体和字体大小
                        hereButton.addActionListener(propertyListener.moveButtonListener(owner, movedCard, hereButton));
                        this.add(hereButton);
                        hereButtons.add(hereButton);
                        hereButton.setVisible(true);
                    }
                }
            }
        }
    }

    public void moveCardAndUpdateScreen(Player owner, Card movedCard, JButton hereButton) { //实现卡牌的移动效果
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

    public Card removeCardFromProperty(Card removedCard) {
        for (int row = 0; row < 5; row++) {
            boolean flag = false;
            for (int column = 0; column < 11; column++) {
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
        countPropertiesNumber();
        return removedCard;
    }


    //-----------存牌:

    public void placePropertyCardAndShowTable(Card card) { //将新进入Property的Card按序排放
        card.owner = this.owner;
        //先将牌存进容器中:
        for (int row = 0; row < 4; row++) {
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
        reallocateAllCards(); //为所有的牌重新分配坐标和可视化状态
        this.add(card); //添加卡牌到JPanel中
        countPropertiesNumber();
    }

    //-------绘制方法:

    public void reallocateAllCards() {
        for (int row = 0; row < 5; row++) {
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
        //reallocateAllCards();
    }


}
