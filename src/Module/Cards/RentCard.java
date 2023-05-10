package Module.Cards;

import java.util.ArrayList;

public class RentCard extends Card {
    public RentCard(String name) {
        super(name);
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> rentCards = new ArrayList<>();
        rentCards.add(new RentCard("Dark Blue/Green"));
        rentCards.add(new RentCard("Dark Blue/Green"));
        rentCards.add(new RentCard("Red/Yellow"));
        rentCards.add(new RentCard("Red/Yellow"));
        rentCards.add(new RentCard("Pink/Orange"));
        rentCards.add(new RentCard("Pink/Orange"));
        rentCards.add(new RentCard("Light Blue/Brown"));
        rentCards.add(new RentCard("Light Blue/Brown"));
        rentCards.add(new RentCard("Railroad/Utility"));
        rentCards.add(new RentCard("Railroad/Utility"));
        for (int i = 0; i < 3; i++) {
            rentCards.add(new RentCard("Wild Rent"));
        }
        return rentCards;
    }
}
