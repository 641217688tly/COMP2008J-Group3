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
                card.owner.property.updatePropertiesNumber(); // Refresh the player's number of houses to avoid a bug where the multicolor card is rotated and the player has amassed three houses but the game doesn't end
            }
        };
    }
}
