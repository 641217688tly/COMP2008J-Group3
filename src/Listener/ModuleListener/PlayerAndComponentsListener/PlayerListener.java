package Listener.ModuleListener.PlayerAndComponentsListener;

import Module.PlayerAndComponents.Bank;
import Module.PlayerAndComponents.PlayerCardsPile;
import Module.PlayerAndComponents.Property;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerListener  {
    public ActionListener playerCardsButtonListener(PlayerCardsPile playerCardsPile) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerCardsPile.setVisible(true);
            }
        };
    }

    public ActionListener bankButtonListener(Bank bank) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bank.setVisible(true);
            }
        };
    }

    public ActionListener propertyButtonListener(Property property) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                property.setVisible(true);
            }
        };
    }
}
