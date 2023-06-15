package Listener.ModuleListener.PlayerAndComponentsListener;

import Module.Cards.ActionCard;
import Module.Cards.Card;
import Module.Cards.CardsEnum.ActionCardType;
import Module.Cards.RentCard;
import Module.PlayerAndComponents.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Module.Game;

public class PlayerListener {

    public ActionListener handCardsButtonListener(Player player) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //打开玩家的手牌
                for (Player inTurnPlayer : Game.players) {
                    inTurnPlayer.setVisible(false);
                    if (inTurnPlayer.isPlayerTurn()) {
                        inTurnPlayer.playerCardsPile.setVisible(false);
                    }
                }
                player.whetherViewComponent = true;
                player.handCards.setVisible(true);
                player.handCards.updateAndShowCards();
            }
        };
    }

    public ActionListener bankButtonListener(Player player) { //打开玩家的银行
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Player player : Game.players) {
                    player.setVisible(false);
                    if (player.isPlayerTurn()) {
                        player.playerCardsPile.setVisible(false);
                    }
                }
                player.whetherViewComponent = true;
                player.bank.setVisible(true);
                player.bank.paintAllCardsFront();
            }
        };
    }

    public ActionListener propertyButtonListener(Player player) { //打开玩家的房产
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Player player : Game.players) {
                    player.setVisible(false);
                    if (player.isPlayerTurn()) {
                        player.playerCardsPile.setVisible(false);
                    }
                }
                player.whetherViewComponent = true;
                player.property.setVisible(true);
                player.property.reallocateAllCards();
            }
        };
    }

    public ActionListener skipButtonListener(Player player) { //跳过自己的回合
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.isPlayerTurn()) {
                    if (player.isInAction()) {
                        player.interactivePlayers.clear();
                        player.actionNumber = 0;

                    }

                }

            }
        };
    }

    //对于多色收租卡(需要选择一个作用对象),为每个玩家(除了自己之外)创建一个Button,这个Listener负责控制该button的行为
    public ActionListener rentChooseButtonListener(Player renter, Player beChargedRentPlayer, int totalRent) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renter.hideAndRemoveRentChooseButtons(); //隐藏并删除所有玩家的choose按钮
                renter.interactivePlayers.add(beChargedRentPlayer);
                renter.actionNumber = renter.actionNumber - 1;
                renter.setIsInAction(false);
                renter.playerCardsPile.updateAndShowCards();
                beChargedRentPlayer.setIsInAction(true);
                beChargedRentPlayer.payForMoney(renter, totalRent);
            }
        };
    }

    //对于DealBreaker牌(需要选择一个作用对象),为每个玩家有一套完整房产的玩家(除了自己之外)创建一个Button,这个Listener负责控制该button的行为
    public ActionListener dealBreakerChooseButtonListener(Player breaker, Player stolenPlayer, ArrayList<Player> playersWhoHasTempButton) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                breaker.hideAndRemoveDealBreakerChooseButtons(playersWhoHasTempButton); // 隐藏并删除所有被添加按钮的临时玩家身上的按钮
                breaker.interactivePlayers.add(stolenPlayer);
                breaker.actionNumber = breaker.actionNumber - 1;
                breaker.setIsInAction(false);
                stolenPlayer.setIsInAction(true);
                breaker.playerCardsPile.updateAndShowCards();
                stolenPlayer.payForWholeProperty(breaker);
            }
        };
    }

    //对于forceDeal牌(需要选择一个作用对象),为每个玩家有房产的玩家(除了自己之外)创建一个Button,这个Listener负责控制该button的行为
    public ActionListener forcedDealChooseButtonListener(Player inTurnPlayer, Player forcedDealPlayer, ArrayList<Player> playersWhoHasTempButton) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inTurnPlayer.hideAndRemoveForcedDealChooseButtons(playersWhoHasTempButton); // 隐藏并删除所有被添加按钮的临时玩家身上的按钮
                inTurnPlayer.interactivePlayers.add(forcedDealPlayer);
                inTurnPlayer.actionNumber = inTurnPlayer.actionNumber - 1;
                inTurnPlayer.setIsInAction(false);
                forcedDealPlayer.setIsInAction(true);
                inTurnPlayer.playerCardsPile.updateAndShowCards();
                forcedDealPlayer.swapProperty(inTurnPlayer);
            }
        };
    }

    //对于SlyDeal牌(需要选择一个作用对象),为每个玩家有房产的玩家(除了自己之外)创建一个Button,这个Listener负责控制该button的行为
    public ActionListener slyDealChooseButtonListener(Player thief, Player stolenPlayer, ArrayList<Player> playersWhoHasTempButton) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thief.hideAndRemoveSlyDealChooseButtons(playersWhoHasTempButton); // 隐藏并删除所有被添加按钮的临时玩家身上的按钮
                thief.interactivePlayers.add(stolenPlayer);
                thief.actionNumber = thief.actionNumber - 1;
                thief.setIsInAction(false);
                stolenPlayer.setIsInAction(true);
                thief.playerCardsPile.updateAndShowCards();
                stolenPlayer.payForProperty(thief);
            }
        };
    }

    //正在行动的玩家对对手的行动Say No,这个Listener不需要被更新
    public ActionListener sayNoButtonListener(Player owner) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //先判断有没有SayNoCard,如果有,点击后再发挥作用
                if (owner.whetherHasSayNoCard()) {
                    //强制打开该玩家的手牌,然后让他选择使用SayNo牌
                    for (Player player : Game.players) {
                        player.setVisible(false);
                        if (player.isPlayerTurn()) {
                            player.playerCardsPile.setVisible(false);
                        }
                    }
                    owner.whetherViewComponent = true;
                    owner.handCardsButton.setVisible(true);
                    owner.handCards.setVisible(true);
                    owner.handCards.closeButton.setVisible(false); //隐藏关闭按钮直到玩家打出了Say No牌
                    owner.handCards.updateAndShowCards();
                    //将SayNo的Button都隐藏
                    owner.sayNoButton.setVisible(false);
                    owner.abandonSayNoButton.setVisible(false);
                    //如果拒绝的是InTurnPlayer,那么先不清除abandonSayNoButton的监视器,因为对手可能也会使用SayNoCard,此时如果放弃了抵抗,那么就需要直接使用abandonSayNoButton及其最初的监视器了
                }
            }
        };
    }

    //InTurn玩家放弃了对其他玩家收租/收取房产等一系列行为(本次互动即将结束):
    public ActionListener inTurnPlayerAbandonSayNoButtonListener(Player inTurnPlayer) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inTurnPlayer.isPlayerTurn()) {
                    //结束自己的行动:
                    inTurnPlayer.setIsInAction(false);

                    //将对手在本次互动中受影响的参数回滚:
                    Player competitor = inTurnPlayer.interactivePlayers.get(0);
                    competitor.debt = 0;
                    competitor.singleActionCardsBuffer.clear();
                    competitor.interactivePlayers.clear();
                    competitor.handCardsButton.setVisible(true);
                    competitor.pledgeCardFromProperty.clear();
                    competitor.pledgeCardFromBank.clear();
                    ActionListener[] abandonSayNoButtonActionListeners = competitor.abandonSayNoButton.getActionListeners();
                    for (ActionListener actionListener : abandonSayNoButtonActionListeners) {
                        competitor.abandonSayNoButton.removeActionListener(actionListener);
                    }
                    competitor.setIsInAction(false);

                    //将对手从自己的互动列表中移除:
                    inTurnPlayer.interactivePlayers.remove(0);
                    //恢复自己的按钮:
                    inTurnPlayer.handCardsButton.setVisible(true);
                    //隐藏自己的SayNo按钮并移除它们的监视器:
                    inTurnPlayer.sayNoButton.setVisible(false);
                    inTurnPlayer.abandonSayNoButton.setVisible(false);
                    ActionListener[] inTurnPlayerAbandonSayNoButtonActionListeners = inTurnPlayer.abandonSayNoButton.getActionListeners();
                    for (ActionListener actionListener : inTurnPlayerAbandonSayNoButtonActionListeners) {
                        inTurnPlayer.abandonSayNoButton.removeActionListener(actionListener);
                    }

                    //判断接下来是否还有其他互动
                    if (inTurnPlayer.interactivePlayers.size() > 0) { //自己还要继续与别的玩家交互:
                        Card playedCard = inTurnPlayer.singleActionCardsBuffer.get(0);
                        if (inTurnPlayer.singleActionCardsBuffer.size() > 1) {
                            inTurnPlayer.singleActionCardsBuffer.clear();
                            inTurnPlayer.singleActionCardsBuffer.add(playedCard);
                        }
                        Player nextCompetitor = inTurnPlayer.interactivePlayers.get(0);
                        nextCompetitor.setIsInAction(true); //将下一个对手的状态设置为行动

                        if (playedCard instanceof RentCard) { //让下一个对手根据自己本回合出的牌进行回复
                            nextCompetitor.payForMoney(inTurnPlayer, ((RentCard) inTurnPlayer.singleActionCardsBuffer.get(0)).totalRent);
                        } else if (playedCard instanceof ActionCard) {
                            if (((ActionCard) playedCard).type.equals(ActionCardType.BIRTHDAY)) {
                                nextCompetitor.payForMoney(inTurnPlayer, 2);
                            } else if (((ActionCard) playedCard).type.equals(ActionCardType.DEBT_COLLECTOR)) {
                                nextCompetitor.payForMoney(inTurnPlayer, 5);
                            } else if (((ActionCard) playedCard).type.equals(ActionCardType.SLY_DEAL)) {
                                nextCompetitor.payForProperty(inTurnPlayer);
                            } else if (((ActionCard) playedCard).type.equals(ActionCardType.FORCE_DEAL)) {
                                nextCompetitor.swapProperty(inTurnPlayer);
                            } else if (((ActionCard) playedCard).type.equals(ActionCardType.DEAL_BREAKER)) {
                                nextCompetitor.payForWholeProperty(inTurnPlayer);
                            }
                        }

                    } else { //自己的本次action全部结束
                        inTurnPlayer.setIsInAction(true);
                        inTurnPlayer.singleActionCardsBuffer.clear();
                        inTurnPlayer.interactivePlayers.clear();
                        inTurnPlayer.playerCardsPile.updateAndShowCards();
                    }
                }
            }
        };
    }


    //player放弃了Say No,选择交租金(本次互动即将结束):
    public ActionListener abandonSayNoAndPayForMoneyButtonListener(Player debtor, int totalRent) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (debtor.isInAction()) {
                    //为欠债人的银行和房产添加抵债按钮:
                    debtor.property.addAndPaintPledgeButtons(totalRent);
                    debtor.bank.addAndPaintPledgeButtons(totalRent);

                    //恢复欠债人的手牌的按钮的操作是在完成了所有抵债活动之后进行的
                    //debtor.handCardsButton.setVisible(true);

                    //隐藏SayNoCard的开关并清空监视器:
                    debtor.sayNoButton.setVisible(false);
                    debtor.abandonSayNoButton.setVisible(false);
                    ActionListener[] abandonSayNoButtonActionListeners = debtor.abandonSayNoButton.getActionListeners();
                    for (ActionListener actionListener : abandonSayNoButtonActionListeners) {
                        debtor.abandonSayNoButton.removeActionListener(actionListener);
                    }

                    //将收租人的abandonSayNoButton的监听器也移除掉
                    Player inTurnPlayer = debtor.interactivePlayers.get(0);
                    ActionListener[] inTurnPlayerAbandonSayNoButtonActionListeners = inTurnPlayer.abandonSayNoButton.getActionListeners();
                    for (ActionListener actionListener : inTurnPlayerAbandonSayNoButtonActionListeners) {
                        inTurnPlayer.abandonSayNoButton.removeActionListener(actionListener);
                    }
                }
            }
        };
    }

    //propertyOwner放弃了Say No,选择交出一个PropertyCard(本次互动即将结束):
    public ActionListener abandonSayNoAndPayForSinglePropertyButtonListener(Player propertyOwner) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                propertyOwner.setIsInAction(false);
                propertyOwner.interactivePlayers.get(0).setIsInAction(true); //由SlyDeal的发起者选取被偷窃者的一张房产

                //强制打开被偷者的房产:
                for (Player player : Game.players) {
                    player.setVisible(false);
                    if (player.isPlayerTurn()) {
                        player.playerCardsPile.setVisible(false);
                    }
                }
                propertyOwner.whetherViewComponent = true;
                propertyOwner.property.closeButton.setVisible(false);
                propertyOwner.property.setVisible(true);
                propertyOwner.property.reallocateAllCards();

                //为被偷的玩家的房产牌添加"偷窃"按钮
                propertyOwner.property.addAndPaintStealSinglePropertyButtons();
                propertyOwner.handCardsButton.setVisible(true);

                //隐藏SayNoCard的开关并清空监视器:
                propertyOwner.sayNoButton.setVisible(false);
                propertyOwner.abandonSayNoButton.setVisible(false);
                ActionListener[] abandonSayNoButtonActionListeners = propertyOwner.abandonSayNoButton.getActionListeners();
                for (ActionListener actionListener : abandonSayNoButtonActionListeners) {
                    propertyOwner.abandonSayNoButton.removeActionListener(actionListener);
                }

                //将偷窃者的abandonSayNoButton的监听器也移除掉
                Player inTurnPlayer = propertyOwner.interactivePlayers.get(0);
                ActionListener[] inTurnPlayerAbandonSayNoButtonActionListeners = inTurnPlayer.abandonSayNoButton.getActionListeners();
                for (ActionListener actionListener : inTurnPlayerAbandonSayNoButtonActionListeners) {
                    inTurnPlayer.abandonSayNoButton.removeActionListener(actionListener);
                }
            }
        };
    }

    //propertyOwner放弃了Say No并同意交换卡牌
    public ActionListener abandonSayNoAndAcceptSwapPropertyButtonListener(Player propertyOwner) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                propertyOwner.setIsInAction(false);
                propertyOwner.interactivePlayers.get(0).setIsInAction(true); //由SlyDeal的发起者选取被偷窃者的一张房产

                //强制打开被交换者的房产:
                for (Player player : Game.players) {
                    player.setVisible(false);
                    if (player.isPlayerTurn()) {
                        player.playerCardsPile.setVisible(false);
                    }
                }
                propertyOwner.whetherViewComponent = true;
                propertyOwner.property.closeButton.setVisible(false);
                propertyOwner.property.setVisible(true);
                propertyOwner.property.reallocateAllCards();

                //为被迫交换翻唱的玩家的房产牌添加"swap"按钮
                propertyOwner.property.addAndPaintSwapPropertyButtons();
                propertyOwner.handCardsButton.setVisible(true);

                //隐藏SayNoCard的开关并清空监视器:
                propertyOwner.sayNoButton.setVisible(false);
                propertyOwner.abandonSayNoButton.setVisible(false);
                ActionListener[] abandonSayNoButtonActionListeners = propertyOwner.abandonSayNoButton.getActionListeners();
                for (ActionListener actionListener : abandonSayNoButtonActionListeners) {
                    propertyOwner.abandonSayNoButton.removeActionListener(actionListener);
                }

                //将交换者的abandonSayNoButton的监听器也移除掉
                Player inTurnPlayer = propertyOwner.interactivePlayers.get(0);
                ActionListener[] inTurnPlayerAbandonSayNoButtonActionListeners = inTurnPlayer.abandonSayNoButton.getActionListeners();
                for (ActionListener actionListener : inTurnPlayerAbandonSayNoButtonActionListeners) {
                    inTurnPlayer.abandonSayNoButton.removeActionListener(actionListener);
                }
            }
        };
    }

    //propertyOwner放弃了Say No,选择交出一整套PropertyCard(本次互动即将结束):
    public ActionListener abandonSayNoAndPayForWholePropertyButtonListener(Player propertyOwner) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                propertyOwner.setIsInAction(false);
                propertyOwner.interactivePlayers.get(0).setIsInAction(true); //由DealBreaker的发起者选取被偷窃者的一张房产

                //强制打开被偷者的房产:
                for (Player player : Game.players) {
                    player.setVisible(false);
                    if (player.isPlayerTurn()) {
                        player.playerCardsPile.setVisible(false);
                    }
                }
                propertyOwner.whetherViewComponent = true;
                propertyOwner.property.closeButton.setVisible(false);
                propertyOwner.property.setVisible(true);
                propertyOwner.property.reallocateAllCards();

                //为被偷的玩家的房产牌添加"偷窃"按钮
                propertyOwner.property.addAndPaintStealWholePropertyButtons();
                propertyOwner.handCardsButton.setVisible(true);

                //隐藏SayNoCard的开关并清空监视器:
                propertyOwner.sayNoButton.setVisible(false);
                propertyOwner.abandonSayNoButton.setVisible(false);
                ActionListener[] abandonSayNoButtonActionListeners = propertyOwner.abandonSayNoButton.getActionListeners();
                for (ActionListener actionListener : abandonSayNoButtonActionListeners) {
                    propertyOwner.abandonSayNoButton.removeActionListener(actionListener);
                }

                //将偷窃者的abandonSayNoButton的监听器也移除掉
                Player inTurnPlayer = propertyOwner.interactivePlayers.get(0);
                ActionListener[] inTurnPlayerAbandonSayNoButtonActionListeners = inTurnPlayer.abandonSayNoButton.getActionListeners();
                for (ActionListener actionListener : inTurnPlayerAbandonSayNoButtonActionListeners) {
                    inTurnPlayer.abandonSayNoButton.removeActionListener(actionListener);
                }
            }
        };
    }

}
