package Module.Cards;

import Module.Cards.CardsEnum.ActionCardType;
import Module.Cards.CardsEnum.PropertyCardType;
import Module.Game;
import Module.Cards.CardsEnum.RentCardType;
import Module.PlayerAndComponents.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RentCard extends Card {
    public RentCardType type;
    public int totalRent = 0;

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> rentCards = new ArrayList<>();

        rentCards
                .add(new RentCard(RentCardType.BLUE_GREEN, new ImageIcon("images/Card/RentCard/RentGreenBlue.jpg"), 1));
        rentCards
                .add(new RentCard(RentCardType.BLUE_GREEN, new ImageIcon("images/Card/RentCard/RentGreenBlue.jpg"), 1));
        rentCards
                .add(new RentCard(RentCardType.RED_YELLOW, new ImageIcon("images/Card/RentCard/RentRedYellow.jpg"), 1));
        rentCards
                .add(new RentCard(RentCardType.RED_YELLOW, new ImageIcon("images/Card/RentCard/RentRedYellow.jpg"), 1));
        rentCards.add(
                new RentCard(RentCardType.PINK_ORANGE, new ImageIcon("images/Card/RentCard/RentPinkOrange.jpg"), 1));
        rentCards.add(
                new RentCard(RentCardType.PINK_ORANGE, new ImageIcon("images/Card/RentCard/RentPinkOrange.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.LIGHTBLUE_BROWN,
                new ImageIcon("images/Card/RentCard/RentLightBlueBrown.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.LIGHTBLUE_BROWN,
                new ImageIcon("images/Card/RentCard/RentLightBlueBrown.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.RAILROAD_UTILITY,
                new ImageIcon("images/Card/RentCard/RentRailroadUtility.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.RAILROAD_UTILITY,
                new ImageIcon("images/Card/RentCard/RentRailroadUtility.jpg"), 1));
        for (int i = 0; i < 3; i++) {
            rentCards.add(
                    new RentCard(RentCardType.WILD_RENT, new ImageIcon("images/Card/RentCard/RentAllColor.jpg"), 3));
        }
        return rentCards;
    }

    public RentCard(RentCardType type, ImageIcon image, int value) {
        super(image, value);
        this.type = type;
    }

    public static int obtainRentNumber(PropertyCardType type, int cardsNumber) {
        switch (type) {
            case BLUE:
                if (cardsNumber == 1) {
                    return 3;
                } else if (cardsNumber >= 2) {
                    return 8;
                }
                break;
            case BROWN:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber >= 2) {
                    return 2;
                }
                break;
            case GREEN:
                if (cardsNumber == 1) {
                    return 2;
                } else if (cardsNumber == 2) {
                    return 4;
                } else if (cardsNumber >= 3) {
                    return 7;
                }
                break;
            case LIGHTBLUE:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber == 2) {
                    return 2;
                } else if (cardsNumber >= 3) {
                    return 3;
                }
                break;
            case ORANGE:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber == 2) {
                    return 3;
                } else if (cardsNumber >= 3) {
                    return 5;
                }
                break;
            case PINK:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber == 2) {
                    return 2;
                } else if (cardsNumber >= 3) {
                    return 4;
                }
                break;
            case RAILROAD:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber == 2) {
                    return 2;
                } else if (cardsNumber == 3) {
                    return 3;
                } else if (cardsNumber >= 4) {
                    return 4;
                }
                break;
            case RED:
                if (cardsNumber == 1) {
                    return 2;
                } else if (cardsNumber == 2) {
                    return 3;
                } else if (cardsNumber >= 3) {
                    return 6;
                }
                break;
            case YELLOW:
                if (cardsNumber == 1) {
                    return 2;
                } else if (cardsNumber == 2) {
                    return 4;
                } else if (cardsNumber >= 3) {
                    return 6;
                }
                break;
            case UTILITY:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber >= 2) {
                    return 2;
                }
                break;
        }
        return 0;
    }

    public boolean whetherRentCardCanBeUsed(Card propertyCard) {
        PropertyCardType propertyCardType = null;
        if (propertyCard instanceof PropertyCard) {
            propertyCardType = ((PropertyCard) propertyCard).type;
        } else if (propertyCard instanceof PropertyWildCard) {
            propertyCardType = ((PropertyWildCard) propertyCard).currentType;
        } else {
            return false;
        }
        switch (propertyCardType) {
            case BLUE:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.BLUE_GREEN)) {
                    return true;
                }
                break;
            case BROWN:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.LIGHTBLUE_BROWN)) {
                    return true;
                }
                break;
            case GREEN:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.BLUE_GREEN)) {
                    return true;
                }
                break;
            case LIGHTBLUE:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.LIGHTBLUE_BROWN)) {
                    return true;
                }
                break;
            case ORANGE:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.PINK_ORANGE)) {
                    return true;
                }
                break;
            case PINK:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.PINK_ORANGE)) {
                    return true;
                }
                break;
            case RAILROAD:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RAILROAD_UTILITY)) {
                    return true;
                }
                break;
            case RED:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RED_YELLOW)) {
                    return true;
                }
                break;
            case YELLOW:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RED_YELLOW)) {
                    return true;
                }
                break;
            case UTILITY:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RAILROAD_UTILITY)) {
                    return true;
                }
                break;
        }
        return false;
    }

    private void updatePlayerInteractiveState() {
        // Clear the data from the previous interaction each time the RentCard is used,
        // indicating the start of a new interaction
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.isInAction()) {
                    owner.interactivePlayers.clear();
                    owner.singleActionCardsBuffer.clear();
                    owner.debt = 0;
                    owner.pledgeCardFromProperty.clear();
                    owner.pledgeCardFromBank.clear();
                }
            }
        }
    }

    // For multicolor cards, select one player; for dual-color cards, select all
    // players
    @Override
    public void play() {
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) {
                    if (owner.isInAction()) {
                        if (owner.property.whetherHasPropertyCards(this)) {
                            updatePlayerInteractiveState();
                            // Check if there is a double card
                            Integer oldRentCardIndex = null;
                            Integer doubleCardIndex = null;
                            for (int i = 0; i < owner.oneTurnCardsBuffer.size(); i++) {
                                if (owner.oneTurnCardsBuffer.get(i) instanceof RentCard) {
                                    oldRentCardIndex = i;
                                } else if (owner.oneTurnCardsBuffer.get(i) instanceof ActionCard) {
                                    if (((ActionCard) owner.oneTurnCardsBuffer.get(i)).type
                                            .equals(ActionCardType.DOUBLE_RENT)) {
                                        doubleCardIndex = i;
                                    }
                                }
                            }
                            // Check if the double card is used, be careful of cases like: double rent card,
                            // current rent card (double card already used), and double other card rent card
                            boolean isDoubleRentUsed = ((oldRentCardIndex == null && doubleCardIndex != null)
                                    || (doubleCardIndex != null && oldRentCardIndex != null
                                            && oldRentCardIndex < doubleCardIndex));
                            Player tempOwner = this.owner;
                            // Remove the card from the player's hand
                            discard();
                            // Force the player's property to be opened
                            tempOwner.whetherViewComponent = true;
                            for (Player player : Game.players) {
                                // Set all players and PlayerCardsPile to invisible
                                player.setVisible(false);
                                if (player.isPlayerTurn()) {
                                    player.playerCardsPile.setVisible(false);
                                }
                            }
                            tempOwner.property.setVisible(true);
                            tempOwner.property.reallocateAllCards();
                            tempOwner.property.closeButton.setVisible(false); // Hide the close button until the player
                                                                              // has selected the properties
                            // Add temporary "choose" buttons to all property cards of the player who used
                            // the RentCard
                            tempOwner.property.addAndPaintChooseButtons(this, isDoubleRentUsed);
                            // Add event listeners to the choose buttons to get: 1. the selected card type,
                            // 2. the total count of this card type, 3. calculate the payment amount, 4.
                            // check if the player has used a double rent card before, if so, double the
                            // result
                            // inTurnPlayer.actionNumber = inTurnPlayer.actionNumber - 1;
                            // The statement for deducting the player's action number cannot be placed here!
                            // It must wait until the player adds interactive objects to the
                            // interactivePlayers list to deduct the action number
                            // Otherwise, the game will directly move to the next turn
                        }
                    }
                }
            }
        }
    }

    @Override
    public void deposit() {
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) {
                    if (owner.isInAction()) {
                        owner.oneTurnCardsBuffer.add(this);
                        for (int i = 0; i < owner.cardsTable.length; i++) {
                            if (owner.cardsTable[i] == this) {
                                owner.cardsTable[i] = null;
                                break;
                            }
                        }
                        if (!owner.whetherViewComponent) {
                            owner.playerCardsPile.updateAndShowCards();
                        } else {
                            owner.handCards.updateAndShowCards();
                        }
                        // Save the card in the bank and update the bank's state
                        owner.bank.saveMoneyAndShowCards(this);
                        owner.actionNumber = owner.actionNumber - 1;
                    }
                }
            }
        }
    }

    @Override
    public void discard() {
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                for (int i = 0; i < owner.cardsTable.length; i++) {
                    if (owner.cardsTable[i] == this) {
                        owner.cardsTable[i] = null;
                        break;
                    }
                }
                if (!owner.whetherViewComponent) {
                    owner.playerCardsPile.updateAndShowCards();
                } else {
                    owner.handCards.updateAndShowCards();
                }
                Game.cardsPile.recycleCardIntoDiscardPile(this);
            }
        }
    }

    @Override
    public void move() {
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.containsCard(this)) {
                    if (owner.whetherViewComponent) {
                        owner.handCards.addAndPaintHereButtons(this);
                    } else {
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

    // -------Drawing methods:

    @Override
    public void paintCard(Graphics g) {
        if (isDisplayable) {
            if (isCardFront) {
                g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            } else {
                g.drawImage(cardBackImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            }
        }
    }

}
