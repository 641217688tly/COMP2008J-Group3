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
    private PropertyCardType currentType; //当前是哪一种PropertyCardType
    private ImageIcon reversedCardImage;
    private PropertyWildCardListener propertyWildCardListener;
    private JButton reverseButton;

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> propertyWildCards = new ArrayList<>();
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.BLUE_GREEN, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardBlueGreen_Blue.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardBlueGreen_Green.jpg"), 4));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.GREEN_RAILROAD, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardGreenRailroad_Green.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardGreenRailroad_Railroad.jpg"), 4));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.UTILITY_RAILROAD, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardUtilityRailroad_Utility.jpg"), new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardUtilityRailroad_Railroad.jpg"), 2));
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

    public void initCurrentType(PropertyWildCardType type) {
        if (type == PropertyWildCardType.BLUE_GREEN) {
            currentType = PropertyCardType.GREEN;
        } else if (type == PropertyWildCardType.GREEN_RAILROAD) {
            currentType = PropertyCardType.GREEN;
        } else if (type == PropertyWildCardType.UTILITY_RAILROAD) {
            currentType = PropertyCardType.UTILITY;
        } else if (type == PropertyWildCardType.LIGHTBLUE_RAILROAD) {
            currentType = PropertyCardType.LIGHTBLUE;
        } else if (type == PropertyWildCardType.LIGHTBLUE_BROWN) {
            currentType = PropertyCardType.LIGHTBLUE;
        } else if (type == PropertyWildCardType.PINK_ORANGE) {
            currentType = PropertyCardType.ORANGE;
        } else if (type == PropertyWildCardType.RED_YELLOW) {
            currentType = PropertyCardType.YELLOW;
        } else if (type == PropertyWildCardType.MULTI_COLOUR) {
            currentType = PropertyCardType.GREEN;
        }
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

    public void switchCardColor() {
        if (type == PropertyWildCardType.BLUE_GREEN) {
            if (currentType == PropertyCardType.GREEN) {
                currentType = PropertyCardType.BLUE;
            } else {
                currentType = PropertyCardType.GREEN;
            }
        } else if (type == PropertyWildCardType.GREEN_RAILROAD) {
            if (currentType == PropertyCardType.GREEN) {
                currentType = PropertyCardType.RAILROAD;
            } else {
                currentType = PropertyCardType.GREEN;
            }
        } else if (type == PropertyWildCardType.UTILITY_RAILROAD) {
            if (currentType == PropertyCardType.UTILITY) {
                currentType = PropertyCardType.RAILROAD;
            } else {
                currentType = PropertyCardType.UTILITY;
            }
        } else if (type == PropertyWildCardType.LIGHTBLUE_RAILROAD) {
            if (currentType == PropertyCardType.LIGHTBLUE) {
                currentType = PropertyCardType.RAILROAD;
            } else {
                currentType = PropertyCardType.LIGHTBLUE;
            }
        } else if (type == PropertyWildCardType.LIGHTBLUE_BROWN) {
            if (currentType == PropertyCardType.LIGHTBLUE) {
                currentType = PropertyCardType.BROWN;
            } else {
                currentType = PropertyCardType.LIGHTBLUE;
            }
        } else if (type == PropertyWildCardType.PINK_ORANGE) {
            if (currentType == PropertyCardType.ORANGE) {
                currentType = PropertyCardType.PINK;
            } else {
                currentType = PropertyCardType.ORANGE;
            }
        } else if (type == PropertyWildCardType.RED_YELLOW) {
            if (currentType == PropertyCardType.YELLOW) {
                currentType = PropertyCardType.RED;
            } else {
                currentType = PropertyCardType.YELLOW;
            }
        } else if (type == PropertyWildCardType.MULTI_COLOUR) {
            PropertyCardType[] allPropertyCardTypes = PropertyCardType.values(); //获取所有但色卡的类型
            for (int i = 0; i < allPropertyCardTypes.length; i++) {
                if (allPropertyCardTypes[i].equals(currentType)) {
                    currentType = allPropertyCardTypes[(i + 1) % 10];
                    break;
                }
            }
        }
    }

    @Override
    public void play() { //(被)使用
        //play:放置房产牌
        if (owner != null) {
            if (owner.actionNumber > 0) {
                for (int i = 0; i < owner.cardsList.size(); i++) { //把牌从玩家上手清除
                    if (owner.cardsList.get(i) == this) {
                        owner.cardsList.remove(i);
                        break;
                    }
                }
                if (!owner.whetherViewComponent) { //如果被调用的时候玩家正在看的是PlayerCardsPile
                    owner.playerCardsPile.updateAndShowCards(); //直接更新PlayerCardsPile
                } else { //如果被调用的时候玩家正在看的是组件
                    owner.handCards.updateAndShowCards(); //直接更新HandCards
                }
                //将牌上的按钮全部隐藏:
                this.playButtonSwitch = false;
                this.depositButtonSwitch = false;
                this.discardButtonSwitch = false;
                controlButtons();
                //将牌存进房产中并刷新房产的状态
                owner.property.placePropertyCardAndShowTable(this);
                owner.actionNumber = owner.actionNumber - 1;
            }
        }
    }

    @Override
    public void deposit() { //(被)储蓄-需要更新银行
        if (owner != null) {
            if (owner.actionNumber > 0) { //由于每次存钱都会消耗行动次数,因此要求玩家行动次数大于0
                for (int i = 0; i < owner.cardsList.size(); i++) { //把牌从玩家上手清除
                    if (owner.cardsList.get(i) == this) {
                        owner.cardsList.remove(i);
                        break;
                    }
                }
                if (!owner.whetherViewComponent) { //如果被调用的时候玩家正在看的是PlayerCardsPile
                    owner.playerCardsPile.updateAndShowCards(); //直接更新PlayerCardsPile
                } else { //如果被调用的时候玩家正在看的是组件
                    owner.handCards.updateAndShowCards(); //直接更新HandCards
                }
                //将牌上的按钮全部隐藏:
                this.playButtonSwitch = false;
                this.depositButtonSwitch = false;
                this.discardButtonSwitch = false;
                controlButtons();
                //将牌存进银行并刷新银行的状态
                owner.bank.saveMoneyAndShowCards(this);
                owner.actionNumber = owner.actionNumber - 1;
            }
        }
    }

    @Override
    public void discard() { //(被)丢弃-仅供处于自己回合的玩家调用-需要更新玩家的HandCards或PlayerCardsPile的状态
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                for (int i = 0; i < owner.cardsList.size(); i++) { //把牌从玩家上手清除
                    if (owner.cardsList.get(i) == this) {
                        owner.cardsList.remove(i);
                        break;
                    }
                }
                if (!owner.whetherViewComponent) { //如果被调用的时候玩家正在看的是PlayerCardsPile
                    owner.playerCardsPile.updateAndShowCards(); //直接更新PlayerCardsPile
                } else { //如果被调用的时候玩家正在看的是组件
                    owner.handCards.updateAndShowCards(); //直接更新HandCards
                }
                //将牌上的按钮全部隐藏:
                this.playButtonSwitch = false;
                this.depositButtonSwitch = false;
                this.discardButtonSwitch = false;
                controlButtons();
                //将牌扔进废牌堆
                this.owner = null;
                Game.cardsPile.recycleCardIntoDiscardPile(this); //把牌塞进牌堆的废牌区
            }
        }
    }

    public void openReverseButtonSwitch(boolean reverseButtonSwitch) {
        this.reverseButtonSwitch = reverseButtonSwitch;
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
        g.setFont(new Font("Arial", Font.BOLD, 20)); // 设置字体和大小
        g.drawString(currentType.toString(), Player.playerWidth / 6, 3 * Player.playerHeight / 8);

    }

    @Override
    public void paintCard(Graphics g) {
        paintReverseButton();
        if (isDisplayable) {
            if (isCardFront) { //牌的正面
                if (type == PropertyWildCardType.BLUE_GREEN) {
                    if (currentType == PropertyCardType.GREEN) {
                        g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    } else {
                        g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    }
                } else if (type == PropertyWildCardType.GREEN_RAILROAD) {
                    if (currentType == PropertyCardType.GREEN) {
                        g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    } else {
                        g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    }
                } else if (type == PropertyWildCardType.UTILITY_RAILROAD) {
                    if (currentType == PropertyCardType.UTILITY) {
                        g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    } else {
                        g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    }
                } else if (type == PropertyWildCardType.LIGHTBLUE_RAILROAD) {
                    if (currentType == PropertyCardType.LIGHTBLUE) {
                        g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    } else {
                        g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    }
                } else if (type == PropertyWildCardType.LIGHTBLUE_BROWN) {
                    if (currentType == PropertyCardType.LIGHTBLUE) {
                        g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    } else {
                        g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    }
                } else if (type == PropertyWildCardType.PINK_ORANGE) {
                    if (currentType == PropertyCardType.ORANGE) {
                        g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    } else {
                        g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    }
                } else if (type == PropertyWildCardType.RED_YELLOW) {
                    if (currentType == PropertyCardType.YELLOW) {
                        g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    } else {
                        g.drawImage(reversedCardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    }
                } else if (type == PropertyWildCardType.MULTI_COLOUR) {
                    g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
                    paintMULTICOLOURCardColour(g);
                }
            } else {
                g.drawImage(cardBackImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            }
        }
    }
}
