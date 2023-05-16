package Module.Cards;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class PropertyCard extends Card implements CardImplement{
    public PropertyCard(String name,ImageIcon cardImage ) {
        super(name,cardImage);
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> propertyCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard("Blue",null));
        }
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard("Brown",null));
        }
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard("Utility",null));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Green",null));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Yellow",null));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Red",null));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Orange",null));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Pink",null));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Light Blue",null));
        }
        for (int i = 0; i < 4; i++) {
            propertyCards.add(new PropertyCard("Railroad",null));
        }
        return propertyCards;
    }

    @Override
    public void play() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'play'");
    }

    @Override
    public void putBack() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putBack'");
    }
}
