package Module.Cards;

import java.util.ArrayList;

public class PropertyCard extends Card {
    public PropertyCard(String name) {
        super(name);
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> propertyCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard("Blue"));
        }
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard("Brown"));
        }
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard("Utility"));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Green"));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Yellow"));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Red"));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Orange"));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Pink"));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard("Light Blue"));
        }
        for (int i = 0; i < 4; i++) {
            propertyCards.add(new PropertyCard("Railroad"));
        }
        return propertyCards;
    }
}
