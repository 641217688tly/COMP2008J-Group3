package Module.Cards;

import Module.Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MoneyCard extends Card {
    public MoneyCard(ImageIcon cardImage, int value) {
        super(cardImage, value);
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> moneyCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) { // Initialize 6 $1 MoneyCards
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/1.jpg"), 1));
        }
        for (int i = 0; i < 5; i++) { // Initialize 5 $2 MoneyCards
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/2.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) { // Initialize 3 $3 MoneyCards
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/3.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) { // Initialize 3 $4 MoneyCards
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/4.jpg"), 4));
        }
        for (int i = 0; i < 2; i++) { // Initialize 2 $5 MoneyCards
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/5.jpg"), 5));
        }
        for (int i = 0; i < 1; i++) { // Initialize 1 $10 MoneyCard
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/10.jpg"), 10));
        }
        return moneyCards;
    }

    @Override
    public void play() { // (Used) - same as deposit() function
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) { // Require the player to have action points greater than 0, as depositing
                                              // money consumes action points
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
        }
    }

    @Override
    public void deposit() { // Deposit (Used) - needs to update the bank
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
                        // Store the card in the bank and update the bank's status
                        owner.bank.saveMoneyAndShowCards(this);
                        owner.actionNumber = owner.actionNumber - 1;
                    }

                }
            }
        }
    }

    @Override
    public void discard() { // Discard (Used) - only called by the player during their turn - needs to
                            // update the player's HandCards or PlayerCardsPile status
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
                Game.cardsPile.recycleCardIntoDiscardPile(this); // Place the card into the discard pile
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
