package Module.Cards;

import java.util.ArrayList;

public class ActionCard extends Card {
    public ActionCard(String name) {
        super(name);
    }


    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> actionCards = new ArrayList<>();

        actionCards.add(new ActionCard("Double The Rent Cards"));
        actionCards.add(new ActionCard("Double The Rent Cards"));
        actionCards.add(new ActionCard("Deal Breaker"));
        actionCards.add(new ActionCard("Deal Breaker"));

        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard("Just Say No"));
        }
        for (int i = 0; i < 4; i++) {
            actionCards.add(new ActionCard("Force Deal"));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard("Debt Collector"));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard("It’s My Birthday"));
        }
        for (int i = 0; i < 10; i++) {
            actionCards.add(new ActionCard("Pass Go"));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard("House"));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard("Hotel"));
        }
        return actionCards;
    }
}
