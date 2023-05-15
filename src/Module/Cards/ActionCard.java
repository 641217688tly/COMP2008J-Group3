package Module.Cards;

import java.util.ArrayList;
//这里需要加上卡牌的颜色


import javax.swing.*;

public abstract class ActionCard extends Card {
    public ActionCard(String name, ImageIcon cardImage) {
        super(name, cardImage);
    }

    @Override
    public void play() {
        // TODO: 实现 play 方法
    }

    @Override
    public void putBack() {
        // TODO: 实现 putBack 方法
    }



    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> actionCards = new ArrayList<>();

        actionCards.add(new ActionCard("Double The Rent Cards"));
        actionCards.add(new ActionCard("Double The Rent Cards"));
        actionCards.add(new ActionCard("Deal Breaker"));
        actionCards.add(new ActionCard("Deal Breaker"));

        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard("Just Say No"),);
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
