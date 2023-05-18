package Module.Cards;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class PropertyWildCard extends PropertyCard {
    public int[] color2;
    public PropertyWildCard(String name, ImageIcon cardImage, int[] rent, int houseCost, int houseCount, int mortgageValue) {
    super(name, cardImage, rent, houseCost, houseCount, mortgageValue );}
    public PropertyWildCard(String name, ImageIcon cardImage, int[] color1,int[] color2, int houseCost, int houseCount, int mortgageValue) {
        super(name, cardImage,color1, houseCost, houseCount, mortgageValue);
        this.color2= color2;
    }
    

    // public static ArrayList<Card> initializeCardsForCardsPile() {
    //     ArrayList<Card> propertyWildCards = new ArrayList<>();
    //     var list= new int[]{3,8};
    //     var icon=new ImageIcon("images/Card/PBlue.pic.jpg") ;
    //     propertyWildCards.add(new PropertyWildCard("Blue", icon,list,0,0,0,"blue"));
    //     propertyWildCards.add(new PropertyWildCard("Green/Railroad",null));
    //     propertyWildCards.add(new PropertyWildCard("Utility/Railroad",null));
    //     propertyWildCards.add(new PropertyWildCard("LightBlue/Railroad",null));
    //     propertyWildCards.add(new PropertyWildCard("Light Blue/Brown",null));
    //     propertyWildCards.add(new PropertyWildCard("Pink/Orange",null));
    //     propertyWildCards.add(new PropertyWildCard("Pink/Orange",null));
    //     propertyWildCards.add(new PropertyWildCard("Red/Yellow",null));
    //     propertyWildCards.add(new PropertyWildCard("Red/Yellow",null));
    //     propertyWildCards.add(new PropertyWildCard("multi-colour Property Wildcards",null));
    //     propertyWildCards.add(new PropertyWildCard("multi-colour Property Wildcards",null));
    //     return propertyWildCards;
    // }
}
