package Module.Cards;

import Module.Game;
import Module.Cards.CardsEnum.ActionCardType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ActionCard extends Card {
    public ActionCardType type;

    public ActionCard(ActionCardType type, ImageIcon image, int value) {
        super(image, value);
        this.type = type;
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> actionCards = new ArrayList<>();
        actionCards.add(new ActionCard(ActionCardType.DOUBLE_RENT, new ImageIcon("images/Card/ActionCard/ActionCardDoubleRent.jpg"), 1));
        actionCards.add(new ActionCard(ActionCardType.DOUBLE_RENT, new ImageIcon("images/Card/ActionCard/ActionCardDoubleRent.jpg"), 1));
        actionCards.add(new ActionCard(ActionCardType.DEAL_BREAKER, new ImageIcon("images/Card/ActionCard/ActionCardDealBreaker.jpg"), 5));
        actionCards.add(new ActionCard(ActionCardType.DEAL_BREAKER, new ImageIcon("images/Card/ActionCard/ActionCardDealBreaker.jpg"), 5));
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.JUST_SAY_NO, new ImageIcon("images/Card/ActionCard/ActionCardSayNo.jpg"), 4));
        }
        for (int i = 0; i < 4; i++) {
            actionCards.add(new ActionCard(ActionCardType.FORCE_DEAL, new ImageIcon("images/Card/ActionCard/ActionCardForcedDeal.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.DEBT_COLLECTOR, new ImageIcon("images/Card/ActionCard/ActionCardDebtCollector.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.BIRTHDAY, new ImageIcon("images/Card/ActionCard/ActionCardBirthday.jpg"), 2));
        }
        for (int i = 0; i < 10; i++) {
            actionCards.add(new ActionCard(ActionCardType.PASS_GO, new ImageIcon("images/Card/ActionCard/ActionCardPassGo.jpg"), 1));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.HOUSE, new ImageIcon("images/Card/ActionCard/ActionCardHouse.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.HOTEL, new ImageIcon("images/Card/ActionCard/ActionCardHotel.jpg"), 4));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.SLY_DEAL, new ImageIcon("images/Card/ActionCard/ActionCardSlyDeal.jpg"), 3));
        }
        return actionCards;
    }

    private void playHouseAndHotel() {
        if (this.type.equals(ActionCardType.HOUSE) || this.type.equals(ActionCardType.HOTEL)) {
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
                        //将牌存进房产中并刷新房产的状态
                        owner.property.placePropertyCardAndShowTable(this);
                        owner.actionNumber = owner.actionNumber - 1;
                    }
                }
            }
        }
    }


    @Override
    public void play() { //(被)使用
        playHouseAndHotel();
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
