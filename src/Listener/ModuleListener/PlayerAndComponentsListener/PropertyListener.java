package Listener.ModuleListener.PlayerAndComponentsListener;

import Module.Cards.Card;
import Module.Game;
import Module.PlayerAndComponents.Player;
import Module.PlayerAndComponents.Property;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertyListener {

    public ActionListener closeButtonListener(Player owner, Property property) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.whetherViewComponent = false;
                property.setVisible(false); // 当玩家点击关闭按钮时，隐藏这个JPanel
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
                owner.property.moveCardAndUpdateScreen(owner, movedCard, hereButton);
            }
        };
    }

}
