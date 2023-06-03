package Listener.ModuleListener.CardsListener;

import Module.Cards.Card;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardListener {
    public ActionListener playAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Card card = (Card) ((JButton) e.getSource()).getParent();
            card.play();
        }
    };

    public ActionListener depositAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Card card = (Card) ((JButton) e.getSource()).getParent();
            card.deposit();
        }
    };

    public ActionListener discardAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Card card = (Card) ((JButton) e.getSource()).getParent();
            card.discard();
        }
    };
}
