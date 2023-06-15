package Module.Cards;

import Listener.ModuleListener.CardsListener.PropertyWildCardListener;
import Module.Cards.CardsEnum.PropertyCardType;
import Module.Game;
import Module.Cards.CardsEnum.PropertyWildCardType;
import Module.PlayerAndComponents.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PropertyWildCard extends Card {
    public boolean reverseButtonSwitch = false;
    public PropertyWildCardType type;
    public PropertyCardType currentType; //当前是哪一种PropertyCardType
    private ImageIcon reversedCardImage;
    private PropertyWildCardListener propertyWildCardListener;
    private JButton reverseButton;

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> propertyWildCards = new ArrayList<>();
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.BLUE_GREEN, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardBlueGreen_Blue.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardBlueGreen_Green.jpg"), 4));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.GREEN_RAILROAD, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardGreenRailroad_Green.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardGreenRailroad_Railroad.jpg"), 4));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.UTILITY_RAILROAD, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardUtilityRailroad_Utility.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardRailroadUtility_Railroad.jpg"), 2));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.LIGHTBLUE_RAILROAD, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardLightBlueRailroad_LightBlue.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardLightBlueRailroad_Railroad.jpg"), 4));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.LIGHTBLUE_BROWN, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardLightBlueBrown_LightBlue.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardLightBlueBrown_Brown.jpg"), 1));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.PINK_ORANGE, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardPinkOrange_Orange.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardPinkOrange_Pink.jpg"), 2));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.PINK_ORANGE, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardPinkOrange_Orange.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardPinkOrange_Pink.jpg"), 2));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.RED_YELLOW, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardRedYellow_Yellow.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardRedYellow_Red.jpg"), 3));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.RED_YELLOW, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardRedYellow_Yellow.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardRedYellow_Red.jpg"), 3));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.MULTI_COLOUR, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardMultiColour.jpg"), 0));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.MULTI_COLOUR, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardMultiColour.jpg"), 0));
        return propertyWildCards;
    }

    public PropertyWildCard(PropertyWildCardType type, ImageIcon cardImage, ImageIcon reversedCardImage, int value) {
        super(cardImage, value);
        this.type = type;
        this.reversedCardImage = reversedCardImage;
        this.propertyWildCardListener = new PropertyWildCardListener();
        initCurrentType(type);
        initButtons();
    }

    public PropertyWildCard(PropertyWildCardType type, ImageIcon cardImage, int value) { //为多色卡设置的构造方法
        super(cardImage, value);
        this.type = type;
        this.propertyWildCardListener = new PropertyWildCardListener();
        initCurrentType(type);
        initButtons();
    }

    private void initButtons() {
        JButton button = new JButton("");
        button.setBounds(0, 0, cardHeight / 8, cardHeight / 8);
        Font buttonFont = new Font("Arial", Font.BOLD, 5);
        button.setFont(buttonFont); // 设置按钮的字体和字体大小
        button.addActionListener(propertyWildCardListener.reverseButtonListener(this));
        reverseButton = button;
        this.add(this.reverseButton);
    }

    public void initCurrentType(PropertyWildCardType type) {
        switch (type) {
            case BLUE_GREEN:
                currentType = PropertyCardType.GREEN;
                break;
            case GREEN_RAILROAD:
                currentType = PropertyCardType.GREEN;
                break;
            case UTILITY_RAILROAD:
                currentType = PropertyCardType.UTILITY;
                break;
            case LIGHTBLUE_RAILROAD:
                currentType = PropertyCardType.LIGHTBLUE;
                break;
            case LIGHTBLUE_BROWN:
                currentType = PropertyCardType.LIGHTBLUE;
                break;
            case PINK_ORANGE:
                currentType = PropertyCardType.ORANGE;
                break;
            case RED_YELLOW:
                currentType = PropertyCardType.YELLOW;
                break;
            case MULTI_COLOUR:
                currentType = PropertyCardType.RAILROAD;
                break;
        }
    }

    public void openReverseButtonSwitch(boolean reverseButtonSwitch) {
        this.reverseButtonSwitch = reverseButtonSwitch;
    }

    public void switchCardColor() {
        switch (type) {
            case BLUE_GREEN:
                currentType = (currentType == PropertyCardType.GREEN) ? PropertyCardType.BLUE : PropertyCardType.GREEN;
                break;
            case GREEN_RAILROAD:
                currentType = (currentType == PropertyCardType.GREEN) ? PropertyCardType.RAILROAD : PropertyCardType.GREEN;
                break;
            case UTILITY_RAILROAD:
                currentType = (currentType == PropertyCardType.UTILITY) ? PropertyCardType.RAILROAD : PropertyCardType.UTILITY;
                break;
            case LIGHTBLUE_RAILROAD:
                currentType = (currentType == PropertyCardType.LIGHTBLUE) ? PropertyCardType.RAILROAD : PropertyCardType.LIGHTBLUE;
                break;
            case LIGHTBLUE_BROWN:
                currentType = (currentType == PropertyCardType.LIGHTBLUE) ? PropertyCardType.BROWN : PropertyCardType.LIGHTBLUE;
                break;
            case PINK_ORANGE:
                currentType = (currentType == PropertyCardType.ORANGE) ? PropertyCardType.PINK : PropertyCardType.ORANGE;
                break;
            case RED_YELLOW:
                currentType = (currentType == PropertyCardType.YELLOW) ? PropertyCardType.RED : PropertyCardType.YELLOW;
                break;
            case MULTI_COLOUR:
                PropertyCardType[] allPropertyCardTypes = PropertyCardType.values();
                for (int i = 0; i < allPropertyCardTypes.length; i++) {
                    if (allPropertyCardTypes[i] == currentType) {
                        //TODO 可能存在BUG
                        currentType = allPropertyCardTypes[(i + 1) % allPropertyCardTypes.length];
                        break;
                    }
                }
                break;
        }
    }

    @Override
    public void play() { //(被)使用
        //play:放置房产牌
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) {
                    if (owner.isInAction()) {
                        owner.oneTurnCardsBuffer.add(this);
                        for (int i = 0; i < owner.cardsTable.length; i++) { //把牌从玩家上手清除
                            if (owner.cardsTable[i] == this) {
                                owner.cardsTable[i] = null;
                                break;
                            }
                        }
                        if (!owner.whetherViewComponent) { //如果被调用的时候玩家正在看的是PlayerCardsPile
                            owner.playerCardsPile.updateAndShowCards(); //直接更新PlayerCardsPile
                        } else { //如果被调用的时候玩家正在看的是组件
                            owner.handCards.updateAndShowCards(); //直接更新HandCards
                        }
                        owner.property.placePropertyCardAndShowTable(this);
                        owner.actionNumber = owner.actionNumber - 1;
                    }
                }
            }
        }
    }

    @Override
    public void deposit() { //(被)储蓄-需要更新银行
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) {
                    if (owner.isInAction()) {
                        owner.oneTurnCardsBuffer.add(this);
                        for (int i = 0; i < owner.cardsTable.length; i++) { //把牌从玩家上手清除
                            if (owner.cardsTable[i] == this) {
                                owner.cardsTable[i] = null;
                                break;
                            }
                        }
                        if (!owner.whetherViewComponent) { //如果被调用的时候玩家正在看的是PlayerCardsPile
                            owner.playerCardsPile.updateAndShowCards(); //直接更新PlayerCardsPile
                        } else { //如果被调用的时候玩家正在看的是组件
                            owner.handCards.updateAndShowCards(); //直接更新HandCards
                        }
                        owner.bank.saveMoneyAndShowCards(this);
                        owner.actionNumber = owner.actionNumber - 1;
                    }
                }
            }
        }
    }

    @Override
    public void discard() { //(被)丢弃-仅供处于自己回合的玩家调用-需要更新玩家的HandCards或PlayerCardsPile的状态
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                for (int i = 0; i < owner.cardsTable.length; i++) { //把牌从玩家上手清除
                    if (owner.cardsTable[i] == this) {
                        owner.cardsTable[i] = null;
                        break;
                    }
                }
                if (!owner.whetherViewComponent) { //如果被调用的时候玩家正在看的是PlayerCardsPile
                    owner.playerCardsPile.updateAndShowCards(); //直接更新PlayerCardsPile
                } else { //如果被调用的时候玩家正在看的是组件
                    owner.handCards.updateAndShowCards(); //直接更新HandCards
                }
                Game.cardsPile.recycleCardIntoDiscardPile(this); //把牌塞进牌堆的废牌区
            }
        }
    }

    @Override
    public void move() {
        //先判断自己所属的容器:
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.containsCard(this)) {
                    if (owner.whetherViewComponent) { //玩家正在看HandCards
                        //给HandCards内的空位置加上按钮
                        owner.handCards.addAndPaintHereButtons(this);
                    } else { //玩家正在看PlayerCardsPile
                        //给PlayerCardsPile内的空位置加上按钮
                        owner.playerCardsPile.addAndPaintHereButtons(this);
                    }
                } else if (owner.bank.containsCard(this)) {
                    owner.bank.addAndPaintHereButtons(this);
                } else if (owner.property.containsCard(this)) {
                    owner.property.addAndPaintHereButtons(this);
                }
            }
        }
    }

    //-------绘制方法:

    private void paintReverseButton() {
        if (reverseButtonSwitch) {
            reverseButton.setVisible(true);
        } else {
            reverseButton.setVisible(false);
        }
    }

    private void paintMULTICOLOURCardColour(Graphics g) { //为多色卡呈现其当前的Type
        g.setColor(Color.RED); // 设置文本颜色
        g.setFont(new Font("Arial", Font.BOLD, 18)); // 设置字体和大小
        g.drawString(currentType.toString(), Player.playerWidth / 6, 3 * Player.playerHeight / 8);

    }

    @Override
    public void paintCard(Graphics g) {
        paintReverseButton();
        if (isDisplayable) {
            if (isCardFront) {
                switch (type) {
                    case BLUE_GREEN:
                        if (currentType == PropertyCardType.GREEN) {
                            g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        } else {
                            g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        }
                        break;
                    case GREEN_RAILROAD:
                        if (currentType == PropertyCardType.GREEN) {
                            g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        } else {
                            g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        }
                        break;
                    case UTILITY_RAILROAD:
                        if (currentType == PropertyCardType.UTILITY) {
                            g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        } else {
                            g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        }
                        break;
                    case LIGHTBLUE_RAILROAD:
                        if (currentType == PropertyCardType.LIGHTBLUE) {
                            g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        } else {
                            g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        }
                        break;
                    case LIGHTBLUE_BROWN:
                        if (currentType == PropertyCardType.LIGHTBLUE) {
                            g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        } else {
                            g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        }
                        break;
                    case PINK_ORANGE:
                        if (currentType == PropertyCardType.ORANGE) {
                            g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        } else {
                            g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        }
                        break;
                    case RED_YELLOW:
                        if (currentType == PropertyCardType.YELLOW) {
                            g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        } else {
                            g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        }
                        break;
                    case MULTI_COLOUR:
                        g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                        paintMULTICOLOURCardColour(g);
                        break;
                }
            } else {
                g.drawImage(cardBackImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            }
        }
    }

}
