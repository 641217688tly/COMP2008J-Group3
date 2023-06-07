package Listener.ModuleListener.PlayerAndComponentsListener;

import Module.PlayerAndComponents.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Module.Game;

public class PlayerListener {

    public ActionListener handCardsButtonListener(Player player) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //打开玩家的手牌
                for (Player inTurnPlayer : Game.players) { //将所有玩家
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
                for (Player player : Game.players) { //将除了当前回合的玩家的形象都设置为不可视
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
                        player.actionNumber = 0;
                        player.interactivePlayers.clear();
                    }

                }

            }
        };
    }

    //对于多色收租卡(需要选择一个作用对象),为每个玩家(除了自己之外)创建一个Button,这个Listener负责控制该button的行为
    public ActionListener rentChooseButtonListener(Player inTurnPlayer, Player beChargedRentPlayer, int totalRent) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inTurnPlayer.hideAndRemoveRentChooseButtons(); //隐藏并删除所有玩家的choose按钮
                inTurnPlayer.interactivePlayers.add(beChargedRentPlayer);
                inTurnPlayer.actionNumber = inTurnPlayer.actionNumber - 1;
                inTurnPlayer.setIsInAction(false);
                inTurnPlayer.playerCardsPile.updateAndShowCards();
                beChargedRentPlayer.setIsInAction(true);
                beChargedRentPlayer.payForMoney(inTurnPlayer, totalRent);
            }
        };
    }
}
