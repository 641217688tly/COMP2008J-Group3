package Module.Cards;

import Module.Game;
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
            propertyCards.add(new PropertyCard(PropertyCardType.BLUE,
                    new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4));
        }
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.BROWN,
                    new ImageIcon("images/Card/PropertyCard/PropertyCardBrown.jpg"), 1));
        }
        for (int i = 0; i < 2; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.UTILITY,
                    new ImageIcon("images/Card/PropertyCard/PropertyCardUtility.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.GREEN,
                    new ImageIcon("images/Card/PropertyCard/PropertyCardGreen.jpg"), 4));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.YELLOW,
                    new ImageIcon("images/Card/PropertyCard/PropertyCardYellow.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.RED,
                    new ImageIcon("images/Card/PropertyCard/PropertyCardRed.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.ORANGE,
                    new ImageIcon("images/Card/PropertyCard/PropertyCardOrange.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.PINK,
                    new ImageIcon("images/Card/PropertyCard/PropertyCardPink.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.LIGHTBLUE,
                    new ImageIcon("images/Card/PropertyCard/PropertyCardLightBlue.jpg"), 1));
        }
        for (int i = 0; i < 4; i++) {
            propertyCards.add(new PropertyCard(PropertyCardType.RAILROAD,
                    new ImageIcon("images/Card/PropertyCard/PropertyCardRailroad.jpg"), 2));
        }
        return propertyCards;
    }

    public static int judgeCompleteSetNumber(PropertyCardType propertyType) {
        int completeSetNumber;

        switch (propertyType) {
            case RAILROAD:
                completeSetNumber = 4;
                break;
            case RED:
                completeSetNumber = 3;
                break;
            case ORANGE:
                completeSetNumber = 3;
                break;
            case YELLOW:
                completeSetNumber = 3;
                break;
            case GREEN:
                completeSetNumber = 3;
                break;
            case BLUE:
                completeSetNumber = 3;
                break;
            case LIGHTBLUE:
                completeSetNumber = 3;
                break;
            case PINK:
                completeSetNumber = 3;
                break;
            case BROWN:
                completeSetNumber = 2;
                break;
            case UTILITY:
                completeSetNumber = 2;
                break;
            default:
                completeSetNumber = 0;
                break;
        }
        return completeSetNumber;
    }

    @Override
    public void play() { // (Used)
        // play: Place a property card
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) {
                    if (owner.isInAction()) {
                        owner.oneTurnCardsBuffer.add(this);
                        for (int i = 0; i < owner.cardsTable.length; i++) { // Remove the card from the player's hand
                            if (owner.cardsTable[i] == this) {
                                owner.cardsTable[i] = null;
                                break;
                            }
                        }
                        if (!owner.whetherViewComponent) { // If the player is viewing PlayerCardsPile
                            owner.playerCardsPile.updateAndShowCards(); // Update PlayerCardsPile directly
                        } else { // If the player is viewing components
                            owner.handCards.updateAndShowCards(); // Update HandCards directly
                        }
                        // Place the card in the property and update the property's status
                        owner.property.placePropertyCardAndShowTable(this);
                        owner.actionNumber = owner.actionNumber - 1;
                    }
                }
            }
        }
    }

    @Override
    public void deposit() { // (Used) - needs to update the bank
        //As a result of our final discussion, we removed the saving feature of the house cards,
        // which would have resulted in a lack of enough house cards to be distributed to players,
        // so that players would never be able to achieve victory conditions
      /*  if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) {
                    if (owner.isInAction()) {
                        owner.oneTurnCardsBuffer.add(this);
                        for (int i = 0; i < owner.cardsTable.length; i++) { // Remove the card from the player's hand
                            if (owner.cardsTable[i] == this) {
                                owner.cardsTable[i] = null;
                                break;
                            }
                        }
                        if (!owner.whetherViewComponent) { // If the player is viewing PlayerCardsPile
                            owner.playerCardsPile.updateAndShowCards(); // Update PlayerCardsPile directly
                        } else { // If the player is viewing components
                            owner.handCards.updateAndShowCards(); // Update HandCards directly
                        }
                        // Store the card in the bank and update the bank's status
                        owner.bank.saveMoneyAndShowCards(this);
                        owner.actionNumber = owner.actionNumber - 1;
                    }
                }
            }
        }*/
    }

    @Override
    public void discard() { // (Used) - only called by the player during their turn - needs to update the
                            // player's HandCards or PlayerCardsPile status
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                for (int i = 0; i < owner.cardsTable.length; i++) { // Remove the card from the player's hand
                    if (owner.cardsTable[i] == this) {
                        owner.cardsTable[i] = null;
                        break;
                    }
                }
                if (!owner.whetherViewComponent) { // If the player is viewing PlayerCardsPile
                    owner.playerCardsPile.updateAndShowCards(); // Update PlayerCardsPile directly
                } else { // If the player is viewing components
                    owner.handCards.updateAndShowCards(); // Update HandCards directly
                }
                // Place the card into the discard pile
                Game.cardsPile.recycleCardIntoDiscardPile(this);
            }
        }
    }

    @Override
    public void move() {
        // First check the container the card belongs to:
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.containsCard(this)) {
                    if (owner.whetherViewComponent) { // The player is viewing HandCards
                        // Add buttons to the empty positions in HandCards
                        owner.handCards.addAndPaintHereButtons(this);
                    } else { // The player is viewing PlayerCardsPile
                        // Add buttons to the empty positions in PlayerCardsPile
                        owner.playerCardsPile.addAndPaintHereButtons(this);
                    }
                } else if (owner.bank.containsCard(this)) {
                    owner.bank.addAndPaintHereButtons(this);
                } else if (owner.property.containsCard(this)) {
                    owner.property.addAndPaintHereButtons(this);
                }
            }
        }
    }

    // Drawing methods:

    @Override
    public void paintCard(Graphics g) {
        if (isDisplayable) {
            if (isCardFront) { // Front side of the card
                g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            } else {
                g.drawImage(cardBackImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            }
        }
    }
}
