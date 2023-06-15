package Listener.ModuleListener.PlayerAndComponentsListener;

import Module.Cards.Card;
import Module.PlayerAndComponents.HandCards;
import Module.PlayerAndComponents.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Module.Game;

import javax.swing.*;

public class HandCardsListener {

    public ActionListener closeButtonListener(Player owner, HandCards handCards) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.whetherViewComponent = false;
                handCards.setVisible(false); // When the player clicks the Close button, hide the JPanel
                handCards.removeAll(); //Primarily to prevent collisions on JPanel components
                for (Player player : Game.players) {
                    player.setVisible(true);
                    if (player.isPlayerTurn()) {
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
                owner.handCards.moveCardAndUpdateScreen(owner, movedCard, hereButton);
            }
        };
    }


}
