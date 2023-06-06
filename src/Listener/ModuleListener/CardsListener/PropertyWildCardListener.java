package Listener.ModuleListener.CardsListener;

import Module.Cards.PropertyWildCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertyWildCardListener {

    public ActionListener reverseButtonListener(PropertyWildCard card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.switchCardColor();
            }
        };
    }
}
