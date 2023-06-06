package Module.Cards;

import Module.Game;
import Module.Cards.CardsEnum.PropertyCardType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PropertyCard extends Card {
    public PropertyCardType type;

    public PropertyCard(PropertyCardType type, ImageIcon cardImage, int value) {
        super(cardImage, value);
        this.type = type;
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> propertyCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.BLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4));
        }
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.BROWN, new ImageIcon("images/Card/PropertyCard/PropertyCardBrown.jpg"), 1));
        }
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.UTILITY, new ImageIcon("images/Card/PropertyCard/PropertyCardUtility.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.GREEN, new ImageIcon("images/Card/PropertyCard/PropertyCardGreen.jpg"), 4));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.YELLOW, new ImageIcon("images/Card/PropertyCard/PropertyCardYellow.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.RED, new ImageIcon("images/Card/PropertyCard/PropertyCardRed.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.ORANGE, new ImageIcon("images/Card/PropertyCard/PropertyCardOrange.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.PINK, new ImageIcon("images/Card/PropertyCard/PropertyCardPink.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.LIGHTBLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardLightBlue.jpg"), 1));
        }
        for (int i = 0; i < 4; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.RAILROAD, new ImageIcon("images/Card/PropertyCard/PropertyCardRailroad.jpg"), 2));
        }
        return propertyCards;
    }

    @Override
    public void play() { //(被)使用
        //play:放置房产牌
        if (owner != null) {
            if (owner.actionNumber > 0) {
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
                //将牌上的按钮全部隐藏:
                this.playButtonSwitch = false;
                this.depositButtonSwitch = false;
                this.discardButtonSwitch = false;
                this.moveButtonSwitch = false;
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
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) {
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
                    //将牌上的按钮全部隐藏:
                    this.playButtonSwitch = false;
                    this.depositButtonSwitch = false;
                    this.discardButtonSwitch = false;
                    this.moveButtonSwitch = false;
                    controlButtons();
                    //将牌存进银行并刷新银行的状态
                    owner.bank.saveMoneyAndShowCards(this);
                    owner.actionNumber = owner.actionNumber - 1;
                }
            } else { //被迫掏钱的倒霉蛋
                //TODO
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
                //将牌上的按钮全部隐藏:
                this.playButtonSwitch = false;
                this.depositButtonSwitch = false;
                this.discardButtonSwitch = false;
                this.moveButtonSwitch = false;
                controlButtons();
                //将牌扔进废牌堆
                this.owner = null;
                Game.cardsPile.recycleCardIntoDiscardPile(this); //把牌塞进牌堆的废牌区
            }
        }
    }

    @Override
    public void move() {
        //先判断自己所属的容器:
        if (owner != null) {
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

    //-------绘制方法:

    @Override
    public void paintCard(Graphics g) {
        if (isDisplayable) {
            if (isCardFront) { //牌的正面
                g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            } else {
                g.drawImage(cardBackImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            }
        }
    }
}
