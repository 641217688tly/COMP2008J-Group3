package Module.Cards;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class RentCard extends Card implements CardImplement {
    public RentCard(String name, ImageIcon cardImage) {
        super(name, cardImage);
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> rentCards = new ArrayList<>();
        rentCards.add(new RentCard("Dark Blue/Green",null));
        rentCards.add(new RentCard("Dark Blue/Green",null));
        rentCards.add(new RentCard("Red/Yellow",null));
        rentCards.add(new RentCard("Red/Yellow",null));
        rentCards.add(new RentCard("Pink/Orange",null));
        rentCards.add(new RentCard("Pink/Orange",null));
        rentCards.add(new RentCard("Light Blue/Brown",null));
        rentCards.add(new RentCard("Light Blue/Brown",null));
        rentCards.add(new RentCard("Railroad/Utility",null));
        rentCards.add(new RentCard("Railroad/Utility",null));
        for (int i = 0; i < 3; i++) {
            rentCards.add(new RentCard("Wild Rent",null));
        }
        return rentCards;
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
