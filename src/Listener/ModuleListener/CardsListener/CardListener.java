package Listener.ModuleListener.CardsListener;

import Module.Cards.*;
import Module.Cards.CardsEnum.PropertyCardType;
import Module.Cards.CardsEnum.RentCardType;
import Module.PlayerAndComponents.Player;
import Module.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardListener {
    public ActionListener playCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.play();
            }
        };
    }

    public ActionListener depositCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.deposit();
            }
        };
    }

    public ActionListener discardCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.discard();
            }
        };
    }

    public ActionListener moveCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.move();
            }
        };
    }

    // ActionListener for selecting the property to collect rent after using
    // RentCard
    public ActionListener chosenButtonListener(Player propertyOwner, Card propertyCard, RentCard rentCard,
            boolean isDoubleRent) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((propertyCard instanceof PropertyCard || propertyCard instanceof PropertyWildCard)
                        && (rentCard.whetherRentCardCanBeUsed(propertyCard))) {
                    // Remove Choose Buttons from all cards in the Property
                    propertyOwner.property.hideAndRemoveChooseButtons();
                    // Calculate the total rent to be collected
                    int totalRent = propertyOwner.property.calculatedRent(propertyCard, isDoubleRent);
                    rentCard.totalRent = totalRent;

                    // Add the RentCard with the set rent to the cards buffer
                    propertyOwner.oneTurnCardsBuffer.add(rentCard);
                    propertyOwner.singleActionCardsBuffer.add(rentCard);

                    // Handling for non-wild RentCards
                    if (!rentCard.type.equals(RentCardType.WILD_RENT)) {
                        // Add all players to the interactive players queue, excluding the property
                        // owner
                        for (int i = 0; i < Game.players.size(); i++) {
                            if (Game.players.get(i) != propertyOwner) {
                                propertyOwner.interactivePlayers.add(Game.players.get(i));
                            }
                        }
                        // Decrease the property owner's action number
                        propertyOwner.actionNumber = propertyOwner.actionNumber - 1;
                        propertyOwner.setIsInAction(false);

                        // Close the property view
                        propertyOwner.property.closeButton.setVisible(true);
                        propertyOwner.whetherViewComponent = false;
                        propertyOwner.property.setVisible(false);
                        propertyOwner.interactivePlayers.get(0).setIsInAction(true);

                        // Show all players and update the current player's card display
                        for (Player player : Game.players) {
                            player.setVisible(true);
                            if (player.isPlayerTurn()) {
                                player.playerCardsPile.updateAndShowCards();
                            }
                        }

                        // Let the first interactive player pay the rent
                        propertyOwner.interactivePlayers.get(0).payForMoney(propertyOwner, totalRent);

                    } else { // Handling for wild RentCards
                        // Close the property view
                        propertyOwner.property.closeButton.setVisible(true);
                        propertyOwner.whetherViewComponent = false;
                        propertyOwner.property.setVisible(false);

                        // Show all players and update the current player's card display
                        for (Player player : Game.players) {
                            player.setVisible(true);
                            if (player.isPlayerTurn()) {
                                player.playerCardsPile.updateAndShowCards();
                            }
                        }

                        // Add Rent Choose buttons for all players (except the property owner)
                        propertyOwner.addAndPaintRentChooseButtons(propertyOwner, totalRent);
                        // Each button's action should include: 1. Add the player to the property
                        // owner's interactive players list; 2. Let the player pay the rent
                    }
                }
            }
        };
    }

    // Controls the behavior of the mortgage button for a card
    public ActionListener pledgeButtonListener(Player debtor, int totalRent, Card pledgeCard,
            boolean isInPropertyOrBank) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pledgeCard.setIsDisplayable(false);
                // Store the pledged card in an array
                if (isInPropertyOrBank) { // Card is in the Property
                    debtor.pledgeCardFromProperty.add(debtor.property.removeCardFromProperty(pledgeCard));
                } else { // Card is in the Bank
                    debtor.pledgeCardFromBank.add(debtor.bank.removeCardFromBank(pledgeCard));
                }
                // Renewal of debt
                debtor.debt = debtor.debt - pledgeCard.value;
                Player renter = debtor.interactivePlayers.get(0);
                // Judging the debt situation:
                if (debtor.debt <= 0) { // Be out of debt
                    // Shows the debtor's hand
                    debtor.handCardsButton.setVisible(true);
                    // Remove the buttons from your bank and home:
                    debtor.property.hideAndRemovePledgeButtons(debtor);
                    debtor.bank.hideAndRemovePledgeButtons(debtor);
                    // Add your cards to the opponent's bank and house:
                    for (Card card : debtor.pledgeCardFromBank) {
                        renter.bank.saveMoneyAndShowCards(card);
                    }
                    for (Card card : debtor.pledgeCardFromProperty) {
                        renter.property.placePropertyCardAndShowTable(card);
                    }
                    // Remove itself from the adversary's interaction queue:
                    renter.interactivePlayers.remove(0);
                    // Rollback debtor's parameters that were affected in this interaction:
                    debtor.debt = 0;
                    debtor.singleActionCardsBuffer.clear();
                    debtor.interactivePlayers.clear();
                    debtor.pledgeCardFromProperty.clear();
                    debtor.pledgeCardFromBank.clear();
                    debtor.setIsInAction(false);

                    // Determine the opponent's next move
                    if (renter.interactivePlayers.size() > 0) { // The opponent has to continue interacting with other
                                                                // players:
                        if (renter.singleActionCardsBuffer.size() > 1) {
                            Card tempCard = renter.singleActionCardsBuffer.get(0);
                            renter.singleActionCardsBuffer.clear();
                            renter.singleActionCardsBuffer.add(tempCard);
                        }
                        renter.interactivePlayers.get(0).setIsInAction(true);
                        renter.interactivePlayers.get(0).payForMoney(renter, totalRent);
                    } else { // The action of the opponent is all over
                        renter.setIsInAction(true);
                        renter.singleActionCardsBuffer.clear();
                        renter.interactivePlayers.clear();
                        renter.playerCardsPile.updateAndShowCards();
                    }

                } else { // The debt is not paid off
                    if (debtor.property.calculateTotalAssetsInProperty() == 0
                            && debtor.bank.calculateTotalAssetsInBank() == 0) { // 欠债人的房产和银行已经是空的了
                        debtor.handCardsButton.setVisible(true);
                        // Remove the buttons from your bank and home:
                        debtor.property.hideAndRemovePledgeButtons(debtor);
                        debtor.bank.hideAndRemovePledgeButtons(debtor);
                        // Add your cards to the opponent's bank and house:
                        for (Card card : debtor.pledgeCardFromBank) {
                            renter.bank.saveMoneyAndShowCards(card);
                        }
                        for (Card card : debtor.pledgeCardFromProperty) {
                            renter.property.placePropertyCardAndShowTable(card);
                        }
                        // Remove itself from the adversary's interaction queue:
                        renter.interactivePlayers.remove(0);
                        // Rollback debtor's parameters that were affected in this interaction:
                        debtor.debt = 0;
                        debtor.singleActionCardsBuffer.clear();
                        debtor.interactivePlayers.clear();
                        debtor.pledgeCardFromProperty.clear();
                        debtor.pledgeCardFromBank.clear();
                        debtor.setIsInAction(false);

                        // Determine the opponent's next move
                        if (renter.interactivePlayers.size() > 0) { // The opponent has to continue interacting with
                                                                    // other players:
                            if (renter.singleActionCardsBuffer.size() > 1) {
                                Card tempCard = renter.singleActionCardsBuffer.get(0);
                                renter.singleActionCardsBuffer.clear();
                                renter.singleActionCardsBuffer.add(tempCard);
                            }
                            renter.interactivePlayers.get(0).setIsInAction(true);
                            renter.interactivePlayers.get(0).payForMoney(renter, totalRent);
                        } else { // The action of the opponent is all over
                            // Delay for two seconds before showing the current player's card
                            renter.setIsInAction(true);
                            renter.singleActionCardsBuffer.clear();
                            renter.interactivePlayers.clear();
                            renter.playerCardsPile.updateAndShowCards();
                        }

                    } else { // Banks or houses have cards
                        // continue
                    }
                }
            }
        };
    }

    // Controls the behavior of the Steal button on the card (only steal one house
    // card (the card cannot be a full house))
    public ActionListener stealSinglePropertyButtonListener(Player propertyOwner, Card stolenCard) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stolenCard instanceof PropertyCard || stolenCard instanceof PropertyWildCard) {
                    PropertyCardType propertyCardType = null;
                    if (stolenCard instanceof PropertyCard) {
                        propertyCardType = ((PropertyCard) stolenCard).type;
                    } else {
                        propertyCardType = ((PropertyWildCard) stolenCard).currentType;
                    }
                    // Ensure that the selected property card by the player is either an incomplete
                    // set or a complete set with more cards than required
                    if ((propertyOwner.property.propertyNumberMap.get(propertyCardType) > 0
                            && propertyOwner.property.propertyNumberMap.get(propertyCardType) < PropertyCard
                                    .judgeCompleteSetNumber(propertyCardType))
                            || propertyOwner.property.propertyNumberMap.get(propertyCardType) > PropertyCard
                                    .judgeCompleteSetNumber(propertyCardType)) {
                        Player propertyThief = propertyOwner.interactivePlayers.get(0); // The player who steals the
                                                                                        // property
                        // Add the stolen card to the opponent's property:
                        propertyOwner.pledgeCardFromProperty
                                .add(propertyOwner.property.removeCardFromProperty(stolenCard));
                        propertyThief.property
                                .placePropertyCardAndShowTable(propertyOwner.pledgeCardFromProperty.get(0));
                        // Remove the "Steal" button from the property:
                        propertyOwner.property.hideAndRemoveStolenButtons(propertyOwner);
                        // Roll back the affected parameters of the stolen player in this interaction:
                        propertyOwner.property.closeButton.setVisible(true);
                        propertyOwner.debt = 0;
                        propertyOwner.singleActionCardsBuffer.clear();
                        propertyOwner.interactivePlayers.clear();
                        propertyOwner.pledgeCardFromProperty.clear();
                        propertyOwner.pledgeCardFromBank.clear();

                        // The thief's actions for this turn are complete
                        propertyThief.setIsInAction(true);
                        propertyThief.interactivePlayers.remove(0);
                        propertyThief.singleActionCardsBuffer.clear();
                        propertyThief.interactivePlayers.clear();
                        propertyThief.playerCardsPile.updateAndShowCards();

                        // Close the stolen player's property:
                        propertyOwner.whetherViewComponent = false;
                        propertyOwner.property.setVisible(false); // When the player clicks the close button, hide this
                                                                  // JPanel
                        for (Player player : Game.players) {
                            player.setVisible(true);
                            if (player.isPlayerTurn()) {
                                player.playerCardsPile.updateAndShowCards();
                            }
                        }
                    }
                }
            }
        };
    }

    // Control the behavior of the "Steal" button on the card (steal a complete set
    // of properties)
    public ActionListener stealWholePropertyButtonListener(Player propertyOwner, Card stolenCard) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // First card selection
                if (propertyOwner.pledgeCardFromProperty.size() == 0) {
                    if (stolenCard instanceof PropertyCard || stolenCard instanceof PropertyWildCard) {
                        PropertyCardType propertyCardType = null;
                        if (stolenCard instanceof PropertyCard) {
                            propertyCardType = ((PropertyCard) stolenCard).type;
                        } else {
                            propertyCardType = ((PropertyWildCard) stolenCard).currentType;
                        }

                        // Check if the selected card group forms a complete set in the owner's property
                        if (propertyOwner.property.propertyNumberMap.get(propertyCardType) >= PropertyCard
                                .judgeCompleteSetNumber(propertyCardType)) {
                            propertyOwner.pledgeCardFromProperty
                                    .add(propertyOwner.property.removeCardFromProperty(stolenCard));
                        }
                    }
                } else { // The player has already selected the type of property to steal next
                    if (stolenCard instanceof PropertyCard || stolenCard instanceof PropertyWildCard) {
                        PropertyCardType propertyCardType = null;
                        if (stolenCard instanceof PropertyCard) {
                            propertyCardType = ((PropertyCard) stolenCard).type;
                        } else {
                            propertyCardType = ((PropertyWildCard) stolenCard).currentType;
                        }
                        PropertyCardType expectedCardType = null;
                        if (propertyOwner.pledgeCardFromProperty.get(0) instanceof PropertyCard) {
                            expectedCardType = ((PropertyCard) propertyOwner.pledgeCardFromProperty.get(0)).type;
                        } else {
                            expectedCardType = ((PropertyWildCard) propertyOwner.pledgeCardFromProperty
                                    .get(0)).currentType;
                        }

                        // Check if the selected card group forms a complete set in the owner's property
                        if (propertyCardType.equals(expectedCardType)) {
                            // Add the stolen card to the pledge list
                            propertyOwner.pledgeCardFromProperty
                                    .add(propertyOwner.property.removeCardFromProperty(stolenCard));

                            if (propertyOwner.pledgeCardFromProperty.size() == PropertyCard
                                    .judgeCompleteSetNumber(expectedCardType)) { // Completed a full set of properties,
                                                                                 // end the turn
                                Player propertyThief = propertyOwner.interactivePlayers.get(0); // The player who steals
                                                                                                // the property
                                // Remove the "Steal" button from the property
                                propertyOwner.property.hideAndRemoveStolenButtons(propertyOwner);
                                // Add the properties to the thief's property
                                for (Card propertyCard : propertyOwner.pledgeCardFromProperty) {
                                    propertyThief.property.placePropertyCardAndShowTable(propertyCard);
                                }

                                // Roll back the affected parameters of the stolen player in this interaction
                                propertyOwner.property.closeButton.setVisible(true);
                                propertyOwner.debt = 0;
                                propertyOwner.singleActionCardsBuffer.clear();
                                propertyOwner.interactivePlayers.clear();
                                propertyOwner.pledgeCardFromProperty.clear();
                                propertyOwner.pledgeCardFromBank.clear();

                                // The thief's actions for this turn are complete
                                propertyThief.setIsInAction(true);
                                propertyThief.interactivePlayers.remove(0);
                                propertyThief.singleActionCardsBuffer.clear();
                                propertyThief.interactivePlayers.clear();
                                propertyThief.playerCardsPile.updateAndShowCards();

                                // Close the stolen player's property
                                propertyOwner.whetherViewComponent = false;
                                propertyOwner.property.setVisible(false); // When the player clicks the close button,
                                                                          // hide this JPanel
                                for (Player player : Game.players) {
                                    player.setVisible(true);
                                    if (player.isPlayerTurn()) {
                                        player.playerCardsPile.updateAndShowCards();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    // Control the behavior of the "Swap" button on the card
    public ActionListener swapPropertyButtonListener(Player inTurnPlayerOrForcedDealPlayer, Card swappedCard) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inTurnPlayerOrForcedDealPlayer.isPlayerTurn()) {
                    if (swappedCard instanceof PropertyCard || swappedCard instanceof PropertyWildCard) {
                        PropertyCardType propertyCardType = null;
                        if (swappedCard instanceof PropertyCard) {
                            propertyCardType = ((PropertyCard) swappedCard).type;
                        } else {
                            propertyCardType = ((PropertyWildCard) swappedCard).currentType;
                        }
                        // Make sure the selected property card is either an incomplete set or a
                        // complete set with more than the required number of PropertyCards
                        if ((inTurnPlayerOrForcedDealPlayer.property.propertyNumberMap.get(propertyCardType) > 0
                                && inTurnPlayerOrForcedDealPlayer.property.propertyNumberMap
                                        .get(propertyCardType) < PropertyCard.judgeCompleteSetNumber(propertyCardType))
                                || inTurnPlayerOrForcedDealPlayer.property.propertyNumberMap.get(
                                        propertyCardType) > PropertyCard.judgeCompleteSetNumber(propertyCardType)) {
                            // Remove all property buttons first
                            inTurnPlayerOrForcedDealPlayer.property.hideAndRemoveSwapButtons();
                            // Add the selected property to the queue
                            inTurnPlayerOrForcedDealPlayer.pledgeCardFromProperty
                                    .add(inTurnPlayerOrForcedDealPlayer.property.removeCardFromProperty(swappedCard));
                            // Close the property
                            inTurnPlayerOrForcedDealPlayer.whetherViewComponent = false;
                            inTurnPlayerOrForcedDealPlayer.property.setVisible(false); // Hide this JPanel when the
                                                                                       // player clicks the close button
                            for (Player player : Game.players) {
                                player.setVisible(true);
                                if (player.isPlayerTurn()) {
                                    player.playerCardsPile.setVisible(false);
                                }
                            }
                            // TODO Call the method in GameScreen to display the card? May not have time to
                            // implement
                        }
                    }
                } else {
                    if (swappedCard instanceof PropertyCard || swappedCard instanceof PropertyWildCard) {
                        PropertyCardType propertyCardType = null;
                        if (swappedCard instanceof PropertyCard) {
                            propertyCardType = ((PropertyCard) swappedCard).type;
                        } else {
                            propertyCardType = ((PropertyWildCard) swappedCard).currentType;
                        }
                        // Make sure the selected property card is either an incomplete set or a
                        // complete set with more than the required number of PropertyCards
                        if ((inTurnPlayerOrForcedDealPlayer.property.propertyNumberMap.get(propertyCardType) > 0
                                && inTurnPlayerOrForcedDealPlayer.property.propertyNumberMap
                                        .get(propertyCardType) < PropertyCard.judgeCompleteSetNumber(propertyCardType))
                                || inTurnPlayerOrForcedDealPlayer.property.propertyNumberMap.get(
                                        propertyCardType) > PropertyCard.judgeCompleteSetNumber(propertyCardType)) {
                            // Remove all property buttons first
                            inTurnPlayerOrForcedDealPlayer.property.hideAndRemoveSwapButtons();
                            // Add the selected property to the queue
                            inTurnPlayerOrForcedDealPlayer.pledgeCardFromProperty
                                    .add(inTurnPlayerOrForcedDealPlayer.property.removeCardFromProperty(swappedCard));
                            // Swap properties between the players
                            Player inTurnPlayer = inTurnPlayerOrForcedDealPlayer.interactivePlayers.get(0);
                            for (Card swappedProperty : inTurnPlayer.pledgeCardFromProperty) {
                                inTurnPlayerOrForcedDealPlayer.property.placePropertyCardAndShowTable(swappedProperty);
                            }
                            for (Card swappedProperty : inTurnPlayerOrForcedDealPlayer.pledgeCardFromProperty) {
                                inTurnPlayer.property.placePropertyCardAndShowTable(swappedProperty);
                            }
                            // Roll back the affected parameters for the player being swapped with
                            inTurnPlayerOrForcedDealPlayer.property.closeButton.setVisible(true);
                            inTurnPlayerOrForcedDealPlayer.debt = 0;
                            inTurnPlayerOrForcedDealPlayer.singleActionCardsBuffer.clear();
                            inTurnPlayerOrForcedDealPlayer.interactivePlayers.clear();
                            inTurnPlayerOrForcedDealPlayer.pledgeCardFromProperty.clear();
                            inTurnPlayerOrForcedDealPlayer.pledgeCardFromBank.clear();
                            // inTurnPlayerOrForcedDealPlayer.setIsInAction(false); // Already set this
                            // IsInAction to false when opening the property panel
                            // End the action for the initiating player
                            inTurnPlayer.setIsInAction(true);
                            inTurnPlayer.interactivePlayers.remove(0);
                            inTurnPlayer.singleActionCardsBuffer.clear();
                            inTurnPlayer.interactivePlayers.clear();
                            inTurnPlayer.playerCardsPile.updateAndShowCards();
                            inTurnPlayer.pledgeCardFromProperty.clear();
                            inTurnPlayer.pledgeCardFromBank.clear();
                            // Close the property
                            inTurnPlayerOrForcedDealPlayer.whetherViewComponent = false;
                            inTurnPlayerOrForcedDealPlayer.property.setVisible(false); // Hide this JPanel when the
                                                                                       // player clicks the close button
                            for (Player player : Game.players) {
                                player.setVisible(true);
                                if (player.isPlayerTurn()) {
                                    player.playerCardsPile.updateAndShowCards();
                                }
                            }
                            // TODO Call the method in GameScreen to display the card? May not have time to
                            // implement
                        }
                    }
                }
            }
        };
    }

}
