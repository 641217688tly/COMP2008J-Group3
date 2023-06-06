package Listener.ModuleListener.CardsListener;

import Module.Cards.Card;

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
}
