package Module.Cards;

import Module.Cards.CardsEnum.PropertyWildCardType;

import javax.swing.*;
import java.util.ArrayList;

public class PropertyWildCard extends Card {
    public PropertyWildCardType type;

    public PropertyWildCard(PropertyWildCardType type, ImageIcon cardImage, int value) {
        super(cardImage, value);
        this.type = type;
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> propertyWildCards = new ArrayList<>();
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.BLUE_GREEN, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardBlueGreen.jpg"), 4));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.GREEN_RAILROAD, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardGreenRailroad.jpg"), 4));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.UTILITY_RAILROAD, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardUtilityRailroad.jpg"), 2));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.LIGHTBLUE_RAILROAD, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardLightBlueRailroad.jpg"), 4));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.LIGHTBLUE_BROWN, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardLightBlueBrown.jpg"), 1));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.PINK_ORANGE, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardPinkOrange.jpg"), 2));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.PINK_ORANGE, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardPinkOrange.jpg"), 2));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.RED_YELLOW, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardRedYellow.jpg"), 3));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.RED_YELLOW, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardRedYellow.jpg"), 3));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.MULTI_COLOUR, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardMultiColour.jpg"), 0));
        propertyWildCards.add(new PropertyWildCard(PropertyWildCardType.MULTI_COLOUR, new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardMultiColour.jpg"), 0));
        return propertyWildCards;
    }

    @Override
    public void deposit() {

    }

    @Override
    public void play() {

    }
}
