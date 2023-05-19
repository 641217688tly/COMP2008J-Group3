package Module.Cards;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class PropertyWildCard extends Card {
    public String color1;
    public String color2;
    public PropertyWildCard(String name, ImageIcon cardImage, String color1, String color2) {
    super(name, cardImage);
    this.color1=color1;
    this.color2=color2;
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

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> propertyWildCards = new ArrayList<>();
        // var icon=new ImageIcon("images/Card/PBlue.pic.jpg") ;
        // propertyWildCards.add(new PropertyWildCard("Blue", icon,));
        propertyWildCards.add(new PropertyWildCard("Green/Railroad",new ImageIcon("images/Card/PGBL.pic.jpg"),"Green","Black"));
        propertyWildCards.add(new PropertyWildCard("Utility/Railroad",new ImageIcon("images/Card/PLBBL.pic.jpg"),"Utility","Black"));
        propertyWildCards.add(new PropertyWildCard("LightBlue/Railroad",new ImageIcon("images/Card/PCBL.pic.jpg"),"LightBlue","Black"));
        propertyWildCards.add(new PropertyWildCard("Light Blue/Brown",new ImageIcon("images/Card/PCB.pic.jpg"),"LightBlue","Brown"));
        propertyWildCards.add(new PropertyWildCard("Pink/Orange",new ImageIcon("images/Card/POP.pic.jpg"),"Pink","Orange"));
        propertyWildCards.add(new PropertyWildCard("Pink/Orange",new ImageIcon("images/Card/POP.pic.jpg"),"Pink","Orange"));
        propertyWildCards.add(new PropertyWildCard("Red/Yellow",new ImageIcon("images/Card/PYR.pic.jpg"),"Red","Yellow"));
        propertyWildCards.add(new PropertyWildCard("Red/Yellow",new ImageIcon("images/Card/PYR.pic.jpg"),"Red","Yellow"));
        propertyWildCards.add(new PropertyWildCard("multi-colour Property Wildcards",new ImageIcon("images/Card/PALL.pic.jpg"),null,null));
        propertyWildCards.add(new PropertyWildCard("multi-colour Property Wildcards",new ImageIcon("images/Card/PALL.pic.jpg"),null,null));
        return propertyWildCards;
    }
}
