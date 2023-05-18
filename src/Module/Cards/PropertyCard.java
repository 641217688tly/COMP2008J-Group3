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
    private String a;


    public PropertyCard(String name, ImageIcon cardImage, int[] rent, int houseCost, int houseCount, int mortgageValue ) {
        super(name, cardImage);
        this.rent = rent;
        this.houseCost = houseCost;
        this.hotelCost = hotelCost;
        this.mortgageValue = mortgageValue;
        this.houseCount = 0;
        
    }
    int[] m= {1,2};
    PropertyCard b = new PropertyCard("Black", null,m, 1, 0, 0);
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


    

    

    public static ArrayList<Card> initializeCardsForCardsPile() {
    ArrayList<Card> propertyCards = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
        var list= new int[]{3,8};
        var icon=new ImageIcon("images/Card/PBlue.pic.jpg") ;
    propertyCards.add(new PropertyCard("Blue", icon,list,0,0,0));
    }
    for (int i = 0; i < 2; i++) {
        var icon=new ImageIcon("images/Card/PB.pic.jpg") ;
        var list= new int[]{1,2};
    propertyCards.add(new PropertyCard("Brown", icon,list,0,0,0));
    }
    for (int i = 0; i < 2; i++) {
        var list= new int[]{};
        var icon=new ImageIcon("images/Card/PALL.pic.jpg") ;
    propertyCards.add(new PropertyCard("Utility", icon));
    }
    for (int i = 0; i < 3; i++) {
        var list= new int[]{2,4,7};
        var icon=new ImageIcon("images/Card/PG.pic.jpg") ;
    propertyCards.add(new PropertyCard("Green", icon,list,0,0,0));
    }
    for (int i = 0; i < 3; i++) {

        var icon=new ImageIcon("images/Card/PY.pic.jpg") ;
        var list= new int[]{2,4,7};
    propertyCards.add(new PropertyCard("Yellow", icon,list,0,0,0));
    }
    for (int i = 0; i < 3; i++) {
        var list= new int[]{2,3,6};

        var icon=new ImageIcon("images/Card/PR.pic.jpg") ;
    propertyCards.add(new PropertyCard("Red", icon,list,0,0,0));
    }
    for (int i = 0; i < 3; i++) {
        var list= new int[]{1,3,5};

        var icon=new ImageIcon("images/Card/PO.pic.jpg") ;
    propertyCards.add(new PropertyCard("Orange", icon,list,0,0,0));
    }
    for (int i = 0; i < 3; i++) {
        var list= new int[]{1,2,4};

        var icon=new ImageIcon("images/Card/PP.pic.jpg") ;
    propertyCards.add(new PropertyCard("Pink", icon,list,0,0,0));
    }
    for (int i = 0; i < 3; i++) {
        var list= new int[]{1,2,3};

        var icon=new ImageIcon("images/Card/PC.pic.jpg") ;
    propertyCards.add(new PropertyCard("Light Blue", icon,list,0,0,0));
    }
    for (int i = 0; i < 4; i++) {
        var list= new int[]{1,2,3,4};

        var icon=new ImageIcon("images/Card/PBL.pic.jpg") ;
    propertyCards.add(new PropertyCard("Railroad", icon,list,0,0,0));
    }
    return propertyCards;
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
