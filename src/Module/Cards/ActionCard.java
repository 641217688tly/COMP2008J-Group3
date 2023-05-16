package Module.Cards;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
public class ActionCard extends Card {
    public ActionCard(String name, ImageIcon image) {
        super(name,image);
    }


    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> actionCards = new ArrayList<>();
        ImageIcon doubleIcon = new ImageIcon("images/Card/ADouble.pic.jpg");
        ImageIcon dealIcon = new ImageIcon("images/Card/ADeal.pic.jpg");
        actionCards.add(new ActionCard("Double The Rent Cards",doubleIcon));
        actionCards.add(new ActionCard("Double The Rent Cards",doubleIcon));
        actionCards.add(new ActionCard("Deal Breaker",dealIcon));
        actionCards.add(new ActionCard("Deal Breaker",dealIcon));
        

        for (int i = 0; i < 3; i++) {
            var justIcon = new ImageIcon("images/Card/AJustno.pic.jpg");
            actionCards.add(new ActionCard("Just Say No",justIcon));  
        }
        for (int i = 0; i < 4; i++) {
            var icon= new ImageIcon("images/Card/AForce.pic.jpg");
            actionCards.add(new ActionCard("Force Deal",null));
        }
        for (int i = 0; i < 3; i++) {
            var icon= new ImageIcon("images/Card/ADebt.pic.jpg");
            actionCards.add(new ActionCard("Debt Collector",icon));
        }
        for (int i = 0; i < 3; i++) {
            var icon= new ImageIcon("images/Card/ABirthday.pic.jpg");
            actionCards.add(new ActionCard("It's My Birthday",icon));
        }
        for (int i = 0; i < 10; i++) {
            var icon= new ImageIcon("images/Card/APass.pic.jpg");
            actionCards.add(new ActionCard("Pass Go",icon));
        }
        for (int i = 0; i < 3; i++) {
            var icon= new ImageIcon("images/Card/AHouse.pic.jpg");
            actionCards.add(new ActionCard("House",icon));
        }
        for (int i = 0; i < 3; i++) {
            var icon= new ImageIcon("images/Card/AHotel.pic.jpg");
            actionCards.add(new ActionCard("Hotel",icon));
        }
        return actionCards;
    }

    @Override
    public void play() {

    }

    @Override
    public void putBack() {

    }
}
