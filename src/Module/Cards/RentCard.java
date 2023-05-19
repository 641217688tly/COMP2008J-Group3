package Module.Cards;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class RentCard extends Card  {
    public int value;
    public RentCard(String name, ImageIcon image,int value) {
        super(name,image);
        this.value= value;
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> rentCards = new ArrayList<>();
        var icon= new ImageIcon("images/Card/RentGB.pic.jpg");
        rentCards.add(new RentCard("Dark Blue/Green",icon,1));
        rentCards.add(new RentCard("Dark Blue/Green",new ImageIcon("images/Card/RentGB.pic.jpg"),1));
        rentCards.add(new RentCard("Red/Yellow",new ImageIcon("images/Card/RentRY.pic.jpg"),1));
        rentCards.add(new RentCard("Red/Yellow",new ImageIcon("images/Card/RentRY.pic.jpg"),1));
        rentCards.add(new RentCard("Pink/Orange",new ImageIcon("images/Card/RentPO.pic.jpg"),1));
        rentCards.add(new RentCard("Pink/Orange",new ImageIcon("images/Card/RentPO.pic.jpg"),1));
        rentCards.add(new RentCard("Light Blue/Brown",new ImageIcon("images/Card/RentBC.pic.jpg"),1));
        rentCards.add(new RentCard("Light Blue/Brown",new ImageIcon("images/Card/RentBC.pic.jpg"),1));
        rentCards.add(new RentCard("Railroad/Utility",new ImageIcon("images/Card/RentBW.pic.jpg"),1));
        rentCards.add(new RentCard("Railroad/Utility",new ImageIcon("images/Card/RentBW.pic.jpg"),1));
        for (int i = 0; i < 3; i++) {
            rentCards.add(new RentCard("Wild Rent",new ImageIcon("images/Card/Rent all.pic.jpg"),1));
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
