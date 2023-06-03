package Module.Cards;


import Module.Cards.CardsEnum.PropertyCardType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PropertyCard extends Card {
    public PropertyCardType type;

    public PropertyCard(PropertyCardType type, ImageIcon cardImage, int value) {
        super(cardImage, value);
        this.type = type;
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> propertyCards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.BLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4));
        }
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.BROWN, new ImageIcon("images/Card/PropertyCard/PropertyCardBrown.jpg"), 1));
        }
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.UTILITY, new ImageIcon("images/Card/PropertyCard/PropertyCardUtility.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.GREEN, new ImageIcon("images/Card/PropertyCard/PropertyCardGreen.jpg"), 4));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.YELLOW, new ImageIcon("images/Card/PropertyCard/PropertyCardYellow.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.RED, new ImageIcon("images/Card/PropertyCard/PropertyCardRed.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.ORANGE, new ImageIcon("images/Card/PropertyCard/PropertyCardOrange.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.PINK, new ImageIcon("images/Card/PropertyCard/PropertyCardPink.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.LIGHTBLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardLightBlue.jpg"), 1));
        }
        for (int i = 0; i < 4; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.RAILROAD, new ImageIcon("images/Card/PropertyCard/PropertyCardRailroad.jpg"), 2));
        }
        return propertyCards;
    }


    @Override
    public void deposit() {

    }

    @Override
    public void play() {

    }

    @Override
    public void drawCard(Graphics g) {
        if (isDisplayable) {
            if (isCardFront) { //牌的正面
                g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            } else {
                g.drawImage(cardBackImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            }
        }
    }
}
