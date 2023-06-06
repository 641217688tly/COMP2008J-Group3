package Listener.ModuleListener.PlayerAndComponentsListener;

import Module.Cards.Card;
import Module.PlayerAndComponents.Bank;
import Module.PlayerAndComponents.Player;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Module.Game;

import javax.swing.*;

public class BankListener {

    public ActionListener closeButtonListener(Player owner, Bank bank) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.whetherViewComponent = false;
                bank.setVisible(false); // 当玩家点击关闭按钮时，隐藏这个JPanel
                for (Player player : Game.players) {
                    if (!player.isPlayerTurn()) {
                        player.setVisible(true);
                    }else {
                        player.playerCardsPile.setVisible(true);
                        player.playerCardsPile.updateAndShowCards();
                    }
                }
            }
        };
    }

    public ActionListener moveButtonListener(Player owner, Card movedCard, JButton hereButton) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.bank.moveCardAndUpdateScreen(owner, movedCard, hereButton);
            }
        };
    }

}
