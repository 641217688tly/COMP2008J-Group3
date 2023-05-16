package Module.Cards;

import java.awt.*;
import java.util.ArrayList;

public class PropertyWildCard extends PropertyCard {
    public PropertyWildCard(String name, Image image) {
        super(name,image);
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> propertyWildCards = new ArrayList<>();
        propertyWildCards.add(new PropertyWildCard("Dark Blue/Green",null));
        propertyWildCards.add(new PropertyWildCard("Green/Railroad",null));
        propertyWildCards.add(new PropertyWildCard("Utility/Railroad",null));
        propertyWildCards.add(new PropertyWildCard("LightBlue/Railroad",null));
        propertyWildCards.add(new PropertyWildCard("Light Blue/Brown",null));
        propertyWildCards.add(new PropertyWildCard("Pink/Orange",null));
        propertyWildCards.add(new PropertyWildCard("Pink/Orange",null));
        propertyWildCards.add(new PropertyWildCard("Red/Yellow",null));
        propertyWildCards.add(new PropertyWildCard("Red/Yellow",null));
        propertyWildCards.add(new PropertyWildCard("multi-colour Property Wildcards",null));
        propertyWildCards.add(new PropertyWildCard("multi-colour Property Wildcards",null));
        return propertyWildCards;
    }
}
