package Module.Cards;

import Module.Cards.CardsEnum.RentCardType;

import javax.swing.*;
import java.util.ArrayList;

public class RentCard extends Card {
    public RentCardType type;

    public RentCard(RentCardType type, ImageIcon image, int value) {
        super(image, value);
        this.type = type;
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> rentCards = new ArrayList<>();

        rentCards.add(new RentCard(RentCardType.BLUE_GREEN, new ImageIcon("images/Card/RentCard/RentGreenBlue.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.BLUE_GREEN, new ImageIcon("images/Card/RentCard/RentGreenBlue.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.RED_YELLOW, new ImageIcon("images/Card/RentCard/RentRedYellow.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.RED_YELLOW, new ImageIcon("images/Card/RentCard/RentRedYellow.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.PINK_ORANGE, new ImageIcon("images/Card/RentCard/RentPinkOrange.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.PINK_ORANGE, new ImageIcon("images/Card/RentCard/RentPinkOrange.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.LIGHTBLUE_BROWN, new ImageIcon("images/Card/RentCard/RentLightBlueBrown.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.LIGHTBLUE_BROWN, new ImageIcon("images/Card/RentCard/RentLightBlueBrown.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.RAILROAD_UTILITY, new ImageIcon("images/Card/RentCard/RentRailroadUtility.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.RAILROAD_UTILITY, new ImageIcon("images/Card/RentCard/RentRailroadUtility.jpg"), 1));
        for (int i = 0; i < 3; i++) {
            rentCards.add(new RentCard(RentCardType.WILD_RENT, new ImageIcon("images/Card/RentCard/RentAllColor.jpg"), 3));
        }
        return rentCards;
    }


    @Override
    public void deposit() {

    }

    @Override
    public void play() {

    }
}
