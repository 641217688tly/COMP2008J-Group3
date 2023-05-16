package Module.Cards;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Module.Game;
import Module.PlayerAndComponents.Player;

public class PropertyCard extends Card implements CardImplement {

    public PropertyCard(String name, ImageIcon cardImage) {
        super(name, cardImage);
    }

    private int[] rent;//zujin
    private int houseCost;//
    private int hotelCost;//
    private int mortgageValue;//diyajiazhi 
    private int houseCount;//fangwu shuliang
    private Color a;


    public PropertyCard(String name, ImageIcon cardImage, int[] rent, int houseCost, int houseCount, int mortgageValue,Color a ) {
        super(name, cardImage);
        this.rent = rent;
        this.houseCost = houseCost;
        this.hotelCost = hotelCost;
        this.mortgageValue = mortgageValue;
        this.houseCount = 0;
        this.a =a;
    }
    int[] m= {1,2};
    PropertyCard b = new PropertyCard("Black", null,m, 1, 0, 0, null);
    public int getRent(PropertyCard b) {
        if(houseCost==1){
            return m[0];
        }if (houseCost==2) {
            return m[1];
        } if (houseCost==3) {
            return m[2];
        } else {
            return (Integer) null;
        }   
    }

    public int getMortgageValue() {
        return mortgageValue;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public void addHouse() {
        //判断颜色
    }

    public void removeHouse() {
        houseCount--;
    }

    public void removeAllHouses() {
        houseCount = 0;
    }

    public boolean hasHotel() {
        return houseCount == 5;
    }


    

    

    // public static ArrayList<Card> initializeCardsForCardsPile() {
    // ArrayList<Card> propertyCards = new ArrayList<>();
    // for (int i = 0; i < 2; i++) {
    // propertyCards.add(new PropertyCard("Blue", null));
    // }
    // for (int i = 0; i < 2; i++) {
    // propertyCards.add(new PropertyCard("Brown", null));
    // }
    // for (int i = 0; i < 2; i++) {
    // propertyCards.add(new PropertyCard("Utility", null));
    // }
    // for (int i = 0; i < 3; i++) {
    // propertyCards.add(new PropertyCard("Green", null));
    // }
    // for (int i = 0; i < 3; i++) {
    // propertyCards.add(new PropertyCard("Yellow", null));
    // }
    // for (int i = 0; i < 3; i++) {
    // propertyCards.add(new PropertyCard("Red", null));
    // }
    // for (int i = 0; i < 3; i++) {
    // propertyCards.add(new PropertyCard("Orange", null));
    // }
    // for (int i = 0; i < 3; i++) {
    // propertyCards.add(new PropertyCard("Pink", null));
    // }
    // for (int i = 0; i < 3; i++) {
    // propertyCards.add(new PropertyCard("Light Blue", null));
    // }
    // for (int i = 0; i < 4; i++) {
    // propertyCards.add(new PropertyCard("Railroad", null));
    // }
    // return propertyCards;
    // }

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
