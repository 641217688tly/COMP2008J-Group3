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
                for (Player inTurnPlayer : Game.players) {
                    if (inTurnPlayer.isPlayerTurn()) {
                        inTurnPlayer.playerCardsPile.setVisible(false);
                        inTurnPlayer.playerCardsPile.removeAll();
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
                    if (!player.isPlayerTurn()) {
                        player.setVisible(false);
                    }else{
                        player.playerCardsPile.setVisible(false);
                    }
                }
                player.whetherViewComponent = true;
                player.bank.setVisible(true);
                player.bank.paintAllCards();
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
                player.property.paintAllCardsFront();
            }
        };
    }
}
