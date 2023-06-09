package Listener.ModuleListener.CardsListener;

import Module.Cards.*;
import Module.Cards.CardsEnum.RentCardType;
import Module.PlayerAndComponents.Player;
import Module.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardListener {
    public ActionListener playCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.play();
            }
        };
    }

    public ActionListener depositCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.deposit();
            }
        };
    }

    public ActionListener discardCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.discard();
            }
        };
    }

    public ActionListener moveCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.move();
            }
        };
    }

    //用于RentCard被使用后挑选要收租金的房产
    public ActionListener chosenButtonListener(Player propertyOwner, Card propertyCard, RentCard rentCard, boolean isDoubleRent) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((propertyCard instanceof PropertyCard || propertyCard instanceof PropertyWildCard) && (rentCard.whetherRentCardCanBeUsed(propertyCard))) {
                    //为Property中的所有Card删去Choose Button:
                    propertyOwner.property.hideAndRemoveChooseButtons();
                    //计算要收取的租金:
                    int totalRent = propertyOwner.property.calculatedRent(propertyCard, isDoubleRent);
                    rentCard.totalRent = totalRent;

                    //将被设置过租金金额的RentCard添加到cardsBuffer中
                    propertyOwner.oneTurnCardsBuffer.add(rentCard);
                    propertyOwner.singleActionCardsBuffer.add(rentCard);
                    //针对多色收租卡和双色收租卡分类讨论:

                    //非多色收租卡:
                    if (!rentCard.type.equals(RentCardType.WILD_RENT)) {
                        //将所有玩家都加入交互队列中
                        for (int i = 0; i < Game.players.size(); i++) {
                            if (Game.players.get(i) != propertyOwner) {
                                propertyOwner.interactivePlayers.add(Game.players.get(i));
                            }
                        }
                        //玩家的行动次数减少
                        propertyOwner.actionNumber = propertyOwner.actionNumber - 1;
                        propertyOwner.setIsInAction(false);
                        //退出房产界面:
                        propertyOwner.property.closeButton.setVisible(true);
                        propertyOwner.whetherViewComponent = false;
                        propertyOwner.property.setVisible(false);
                        propertyOwner.interactivePlayers.get(0).setIsInAction(true);
                        for (Player player : Game.players) {
                            player.setVisible(true);
                            if (player.isPlayerTurn()) {
                                player.playerCardsPile.updateAndShowCards();
                            }
                        }
                        //让需要交租的第一个玩家进行交互
                        propertyOwner.interactivePlayers.get(0).payForMoney(propertyOwner, totalRent);

                    } else { //多色收租卡:
                        //退出房产界面:
                        propertyOwner.property.closeButton.setVisible(true);
                        propertyOwner.whetherViewComponent = false;
                        propertyOwner.property.setVisible(false);
                        for (Player player : Game.players) {
                            player.setVisible(true);
                            if (player.isPlayerTurn()) {
                                player.playerCardsPile.updateAndShowCards();
                            }
                        }
                        propertyOwner.addAndPaintRentChooseButtons(propertyOwner, totalRent);//给所有玩家(除了自己之外)都加上一个Choose按钮,将该按钮的事件应该包括:1.将玩家添加owner的interactivePlayers中; 2.让需要交租的玩家进行交互
                    }
                }
            }
        };
    }

    public ActionListener pledgeButtonListener(Player debtor, int totalRent, Card pledgeCard, boolean isInPropertyOrBank) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pledgeCard.setIsDisplayable(false);
                //先将被抵押的牌存储进数组
                if (isInPropertyOrBank) { //在Property中:
                    debtor.pledgeCardFromProperty.add(debtor.property.removeCardFromProperty(pledgeCard));
                } else { //在Bank中:
                    debtor.pledgeCardFromBank.add(debtor.bank.removeCardFromBank(pledgeCard));
                }
                //更新债务
                debtor.debt = debtor.debt - pledgeCard.value;
                Player renter = debtor.interactivePlayers.get(0);
                //判断债务情况:
                if (debtor.debt <= 0) { //债务还清
                    //显示欠债人的手牌
                    debtor.handCardsButton.setVisible(true);
                    //将自己的银行和房产上的按钮给删除:
                    debtor.property.hideAndRemovePledgeButtons(debtor);
                    debtor.bank.hideAndRemovePledgeButtons(debtor);
                    //将自己抵押的Card都添加到对手的银行和房产中:
                    for (Card card : debtor.pledgeCardFromBank) {
                        renter.bank.saveMoneyAndShowCards(card);
                    }
                    for (Card card : debtor.pledgeCardFromProperty) {
                        renter.property.placePropertyCardAndShowTable(card);
                    }
                    //从对手的交互队列中移除自己:
                    renter.interactivePlayers.remove(0);
                    //将debtor在本次互动中受影响的参数回滚:
                    debtor.debt = 0;
                    debtor.singleActionCardsBuffer.clear();
                    debtor.interactivePlayers.clear();
                    debtor.pledgeCardFromProperty.clear();
                    debtor.pledgeCardFromBank.clear();
                    debtor.setIsInAction(false);

                    //判断对手接下来的行动
                    if (renter.interactivePlayers.size() > 0) { //对手还要继续与别的玩家交互:
                        if (renter.singleActionCardsBuffer.size() > 1) {
                            Card tempCard = renter.singleActionCardsBuffer.get(0);
                            renter.singleActionCardsBuffer.clear();
                            renter.singleActionCardsBuffer.add(tempCard);
                        }
                        renter.interactivePlayers.get(0).setIsInAction(true);
                        renter.interactivePlayers.get(0).payForMoney(renter, totalRent);
                    } else { //对手的本次action全部结束
                        renter.setIsInAction(true);
                        renter.singleActionCardsBuffer.clear();
                        renter.interactivePlayers.clear();
                        renter.playerCardsPile.updateAndShowCards();
                    }

                } else { //债务没有还清
                    if (debtor.property.calculateTotalAssetsInProperty() == 0 && debtor.bank.calculateTotalAssetsInBank() == 0) { //欠债人的房产和银行已经是空的了
                        debtor.handCardsButton.setVisible(true);
                        //将自己的银行和房产上的按钮给删除:
                        debtor.property.hideAndRemovePledgeButtons(debtor);
                        debtor.bank.hideAndRemovePledgeButtons(debtor);
                        //将自己抵押的Card都添加到对手的银行和房产中:
                        for (Card card : debtor.pledgeCardFromBank) {
                            renter.bank.saveMoneyAndShowCards(card);
                        }
                        for (Card card : debtor.pledgeCardFromProperty) {
                            renter.property.placePropertyCardAndShowTable(card);
                        }
                        //从对手的交互队列中移除自己:
                        renter.interactivePlayers.remove(0);
                        //将debtor在本次互动中受影响的参数回滚:
                        debtor.debt = 0;
                        debtor.singleActionCardsBuffer.clear();
                        debtor.interactivePlayers.clear();
                        debtor.pledgeCardFromProperty.clear();
                        debtor.pledgeCardFromBank.clear();
                        debtor.setIsInAction(false);

                        //判断对手接下来的行动
                        if (renter.interactivePlayers.size() > 0) { //对手还要继续与别的玩家交互:
                            if (renter.singleActionCardsBuffer.size() > 1) {
                                Card tempCard = renter.singleActionCardsBuffer.get(0);
                                renter.singleActionCardsBuffer.clear();
                                renter.singleActionCardsBuffer.add(tempCard);
                            }
                            renter.interactivePlayers.get(0).setIsInAction(true);
                            renter.interactivePlayers.get(0).payForMoney(renter, totalRent);
                        } else { //对手的本次action全部结束
                            //延迟两秒再呈现当前的玩家的卡牌
                            renter.setIsInAction(true);
                            renter.singleActionCardsBuffer.clear();
                            renter.interactivePlayers.clear();
                            renter.playerCardsPile.updateAndShowCards();
                        }

                    } else { //银行或者房产还有牌
                        //continue
                    }
                }
            }
        };
    }

    public ActionListener stealSinglePropertyButtonListener(Player propertyOwner, Card stolenCard) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player propertyThief = propertyOwner.interactivePlayers.get(0); //房产偷窃者
                //将被偷的Card添加到对手的房产中:
                propertyOwner.pledgeCardFromProperty.add(stolenCard);
                propertyThief.property.placePropertyCardAndShowTable(propertyOwner.property.removeCardFromProperty(stolenCard));
                //将房产上的Steal按钮给删除:
                propertyOwner.property.hideAndRemoveStolenButtons(propertyOwner);
                //将被偷着在本次互动中受影响的参数进行回滚:
                propertyOwner.property.closeButton.setVisible(true);
                propertyOwner.debt = 0;
                propertyOwner.singleActionCardsBuffer.clear();
                propertyOwner.interactivePlayers.clear();
                propertyOwner.pledgeCardFromProperty.clear();
                propertyOwner.pledgeCardFromBank.clear();
                //propertyOwner.setIsInAction(false); //已经在打开房产面板时将该IsInAction设置为false了

                //小偷的本次action全部结束
                propertyThief.setIsInAction(true);
                propertyThief.interactivePlayers.remove(0);
                propertyThief.singleActionCardsBuffer.clear();
                propertyThief.interactivePlayers.clear();
                propertyThief.playerCardsPile.updateAndShowCards();

                //关闭被偷玩家的房产:
                propertyOwner.whetherViewComponent = false;
                propertyOwner.property.setVisible(false); // 当玩家点击关闭按钮时，隐藏这个JPanel
                for (Player player : Game.players) {
                    player.setVisible(true);
                    if (player.isPlayerTurn()) {
                        player.playerCardsPile.updateAndShowCards();
                    }
                }
            }
        };
    }
}
