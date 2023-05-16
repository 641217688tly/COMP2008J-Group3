package Module.Cards;


import java.util.ArrayList;
//这里需要加上卡牌的颜色


import javax.swing.*;

public  class ActionCard extends Card {
    public ActionCard(String name, ImageIcon cardImage) {
        super(name, cardImage);
    }

    



    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> actionCards = new ArrayList<>();
       

        actionCards.add(new ActionCard("Double The Rent Cards",null));
        actionCards.add(new ActionCard("Double The Rent Cards",null));
        actionCards.add(new ActionCard("Deal Breaker",null));
        actionCards.add(new ActionCard("Deal Breaker",null));

        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard("Just Say No",null));
        }
        for (int i = 0; i < 4; i++) {
            actionCards.add(new ActionCard("Force Deal",null));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard("Debt Collector",null));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard("It’s My Birthday",null));
        }
        for (int i = 0; i < 10; i++) {
            actionCards.add(new ActionCard("Pass Go",null));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard("House",null));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard("Hotel",null));
        }
        return actionCards;
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
