package Module.Cards;

import java.util.ArrayList;

public class PropertyWildCard extends PropertyCard {
    public PropertyWildCard(String name) {
        super(name);
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> propertyWildCards = new ArrayList<>();
        propertyWildCards.add(new PropertyWildCard("Dark Blue/Green"));
        propertyWildCards.add(new PropertyWildCard("Green/Railroad"));
        propertyWildCards.add(new PropertyWildCard("Utility/Railroad"));
        propertyWildCards.add(new PropertyWildCard("LightBlue/Railroad"));
        propertyWildCards.add(new PropertyWildCard("Light Blue/Brown"));
        propertyWildCards.add(new PropertyWildCard("Pink/Orange"));
        propertyWildCards.add(new PropertyWildCard("Pink/Orange"));
        propertyWildCards.add(new PropertyWildCard("Red/Yellow"));
        propertyWildCards.add(new PropertyWildCard("Red/Yellow"));
        propertyWildCards.add(new PropertyWildCard("multi-colour Property Wildcards"));
        propertyWildCards.add(new PropertyWildCard("multi-colour Property Wildcards"));
        return propertyWildCards;
    }
}
