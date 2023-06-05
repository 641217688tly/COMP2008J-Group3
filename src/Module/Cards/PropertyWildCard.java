package Module.Cards;

import Module.Game;
import Module.Cards.CardsEnum.PropertyWildCardType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PropertyWildCard extends Card {
    public PropertyWildCardType type;

    public PropertyWildCard(PropertyWildCardType type, ImageIcon cardImage, int value) {
        super(cardImage, value);
        this.type = type;
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> propertyWildCards = new ArrayList<>();
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.BLUE_GREEN, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardBlueGreen.jpg"), 4));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.GREEN_RAILROAD, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardGreenRailroad.jpg"), 4));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.UTILITY_RAILROAD, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardUtilityRailroad.jpg"), 2));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.LIGHTBLUE_RAILROAD, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardLightBlueRailroad.jpg"), 4));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.LIGHTBLUE_BROWN, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardLightBlueBrown.jpg"), 1));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.PINK_ORANGE, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardPinkOrange.jpg"), 2));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.PINK_ORANGE, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardPinkOrange.jpg"), 2));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.RED_YELLOW, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardRedYellow.jpg"), 3));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.RED_YELLOW, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardRedYellow.jpg"), 3));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.MULTI_COLOUR, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardMultiColour.jpg"), 0));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.MULTI_COLOUR, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardMultiColour.jpg"), 0));
        return propertyWildCards;
    }

    @Override
    public void play() { //(被)使用

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
                }else{ //如果被调用的时候玩家正在看的是组件
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
            if (owner.isPlayerTurn()){
                for (int i = 0; i < owner.cardsList.size(); i++) { //把牌从玩家上手清除
                    if (owner.cardsList.get(i) == this) {
                        owner.cardsList.remove(i);
                        break;
                    }
                }
                if (!owner.whetherViewComponent) { //如果被调用的时候玩家正在看的是PlayerCardsPile
                    owner.playerCardsPile.updateAndShowCards(); //直接更新PlayerCardsPile
                }else{ //如果被调用的时候玩家正在看的是组件
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
