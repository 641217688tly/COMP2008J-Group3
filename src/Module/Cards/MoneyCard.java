package Module.Cards;

import Module.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MoneyCard extends Card {
    public MoneyCard(ImageIcon cardImage, int value) {
        super(cardImage, value);
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> moneyCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) { // 初始化6张1$MoneyCard
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/1.jpg"), 1));
        }
        for (int i = 0; i < 5; i++) { // 初始化5张2$MoneyCar
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/2.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) { // 初始化3张3$MoneyCard
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/3.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) { // 初始化3张4$MoneyCard
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/4.jpg"), 4));
        }
        for (int i = 0; i < 2; i++) { // 初始化2张5$MoneyCard
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/5.jpg"), 5));
        }
        for (int i = 0; i < 1; i++) { // 初始化1张10$MoneyCard
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/10.jpg"), 10));
        }
        return moneyCards;
    }

    @Override
    public void play() { //(被)使用
        //play()功能 == deposit()功能
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) { //由于每次存钱都会消耗行动次数,因此要求玩家行动次数大于0
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
                        //将牌存进银行并刷新银行的状态
                        owner.bank.saveMoneyAndShowCards(this);
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
                        //将牌存进银行并刷新银行的状态
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
