package Listener.ModuleListener.PlayerAndComponentsListener;

import Module.Cards.Card;
import Module.PlayerAndComponents.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerCardsPileListener {
    public ActionListener moveButtonListener(Player owner, Card movedCard, JButton hereButton) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.playerCardsPile.moveCardAndUpdateScreen(owner, movedCard, hereButton);
            }
        };
    }
}
