package Listener.ModuleListener.PlayerAndComponentsListener;

import Module.Cards.ActionCard;
import Module.Cards.Card;
import Module.Cards.CardsEnum.ActionCardType;
import Module.Cards.RentCard;
import Module.PlayerAndComponents.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Module.Game;

public class PlayerListener {

    public ActionListener handCardsButtonListener(Player player) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //// Open the player's hand
                for (Player inTurnPlayer : Game.players) {
                    inTurnPlayer.setVisible(false);
                    if (inTurnPlayer.isPlayerTurn()) {
                        inTurnPlayer.playerCardsPile.setVisible(false);
                    }
                }
                player.whetherViewComponent = true;
                player.handCards.setVisible(true);
                player.handCards.updateAndShowCards();
            }
        };
    }

    public ActionListener bankButtonListener(Player player) { // Open the player's bank
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Player player : Game.players) {
                    player.setVisible(false);
                    if (player.isPlayerTurn()) {
                        player.playerCardsPile.setVisible(false);
                    }
                }
                player.whetherViewComponent = true;
                player.bank.setVisible(true);
                player.bank.paintAllCardsFront();
            }
        };
    }

    public ActionListener propertyButtonListener(Player player) { //// Open the player's property
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Player player : Game.players) {
                    player.setVisible(false);
                    if (player.isPlayerTurn()) {
                        player.playerCardsPile.setVisible(false);
                    }
                }
                player.whetherViewComponent = true;
                player.property.setVisible(true);
                player.property.reallocateAllCards();
            }
        };
    }

    public ActionListener skipButtonListener(Player player) { // Skip your turn
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.isPlayerTurn()) {
                    if (player.isInAction()) {
                        player.interactivePlayers.clear();
                        player.actionNumber = 0;

                    }

                }

            }
        };
    }

    // For multicolor rent cards (where you need to select an action object), create
    // a Button for each player (except yourself),
    // and this Listener is responsible for controlling the behavior of that button
    public ActionListener rentChooseButtonListener(Player renter, Player beChargedRentPlayer, int totalRent) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renter.hideAndRemoveRentChooseButtons(); // Hide and remove the choose button for all players
                renter.interactivePlayers.add(beChargedRentPlayer);
                renter.actionNumber = renter.actionNumber - 1;
                renter.setIsInAction(false);
                renter.playerCardsPile.updateAndShowCards();
                beChargedRentPlayer.setIsInAction(true);
                beChargedRentPlayer.payForMoney(renter, totalRent);
            }
        };
    }

    // For the DealBreaker card (which requires you to select an action object),
    // create a Button for each player with a full house (except yourself).This
    // Listener is responsible for controlling the behavior of that button
    public ActionListener dealBreakerChooseButtonListener(Player breaker, Player stolenPlayer,
            ArrayList<Player> playersWhoHasTempButton) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                breaker.hideAndRemoveDealBreakerChooseButtons(playersWhoHasTempButton); //// Hide and remove the choose
                                                                                        //// button for all players
                breaker.interactivePlayers.add(stolenPlayer);
                breaker.actionNumber = breaker.actionNumber - 1;
                breaker.setIsInAction(false);
                stolenPlayer.setIsInAction(true);
                breaker.playerCardsPile.updateAndShowCards();
                stolenPlayer.payForWholeProperty(breaker);
            }
        };
    }

    // For the forceDeal card (which requires you to select an action object),
    // create a Button for each player who owns a house (except yourself),
    // and this Listener is responsible for controlling the behavior of that button
    public ActionListener forcedDealChooseButtonListener(Player inTurnPlayer, Player forcedDealPlayer,
            ArrayList<Player> playersWhoHasTempButton) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inTurnPlayer.hideAndRemoveForcedDealChooseButtons(playersWhoHasTempButton); // Hide and remove the
                                                                                            // choose button for all
                                                                                            // players
                inTurnPlayer.interactivePlayers.add(forcedDealPlayer);
                inTurnPlayer.actionNumber = inTurnPlayer.actionNumber - 1;
                inTurnPlayer.setIsInAction(false);
                forcedDealPlayer.setIsInAction(true);
                inTurnPlayer.playerCardsPile.updateAndShowCards();
                forcedDealPlayer.swapProperty(inTurnPlayer);
            }
        };
    }

    // For the SlyDeal card (which requires you to choose an action object), create
    // a Button for each player who owns a house (except yourself), and this
    // Listener is responsible for controlling the behavior of that button
    public ActionListener slyDealChooseButtonListener(Player thief, Player stolenPlayer,
            ArrayList<Player> playersWhoHasTempButton) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thief.hideAndRemoveSlyDealChooseButtons(playersWhoHasTempButton); // Hide and remove all buttons on the
                                                                                  // temporary player whose buttons have
                                                                                  // been added
                thief.interactivePlayers.add(stolenPlayer);
                thief.actionNumber = thief.actionNumber - 1;
                thief.setIsInAction(false);
                stolenPlayer.setIsInAction(true);
                thief.playerCardsPile.updateAndShowCards();
                stolenPlayer.payForProperty(thief);
            }
        };
    }

    // The player who is acting says No to the opponent's action and this Listener
    // does not need to be updated
    public ActionListener sayNoButtonListener(Player owner) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // First, check if there is a SayNoCard, and if there is, click it before it
                // takes effect
                if (owner.whetherHasSayNoCard()) {
                    // Force the player's hand to open, and then give him the option to play the
                    // SayNo card
                    for (Player player : Game.players) {
                        player.setVisible(false);
                        if (player.isPlayerTurn()) {
                            player.playerCardsPile.setVisible(false);
                        }
                    }
                    owner.whetherViewComponent = true;
                    owner.handCardsButton.setVisible(true);
                    owner.handCards.setVisible(true);
                    owner.handCards.closeButton.setVisible(false); // Hide the close button until the player plays the
                                                                   // Say No card
                    owner.handCards.updateAndShowCards();
                    // Hide all of SayNo's buttons
                    owner.sayNoButton.setVisible(false);
                    owner.abandonSayNoButton.setVisible(false);
                    // If the InTurnPlayer is rejected, then the abandonSayNoButton monitor is not
                    // cleared, //
                    // because the opponent may also use SayNoCard, and if the resistance is given
                    // up, then abandonSayNoButton and its original monitor need to be used directly
                }
            }
        };
    }

    // The player in turn has abandoned a series of actions such as collecting rent
    // and acquiring properties from other players (the interaction is about to
    // end):

    public ActionListener inTurnPlayerAbandonSayNoButtonListener(Player inTurnPlayer) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inTurnPlayer.isPlayerTurn()) {
                    // End the player's own turn:
                    inTurnPlayer.setIsInAction(false);

                    // Roll back the affected parameters of the opponent in this interaction:
                    Player competitor = inTurnPlayer.interactivePlayers.get(0);
                    competitor.debt = 0;
                    competitor.singleActionCardsBuffer.clear();
                    competitor.interactivePlayers.clear();
                    competitor.handCardsButton.setVisible(true);
                    competitor.pledgeCardFromProperty.clear();
                    competitor.pledgeCardFromBank.clear();
                    ActionListener[] abandonSayNoButtonActionListeners = competitor.abandonSayNoButton
                            .getActionListeners();
                    for (ActionListener actionListener : abandonSayNoButtonActionListeners) {
                        competitor.abandonSayNoButton.removeActionListener(actionListener);
                    }
                    competitor.setIsInAction(false);

                    // Remove the opponent from the player's interaction list:
                    inTurnPlayer.interactivePlayers.remove(0);
                    // Restore the player's buttons:
                    inTurnPlayer.handCardsButton.setVisible(true);
                    // Hide the player's SayNo buttons and remove their listeners:
                    inTurnPlayer.sayNoButton.setVisible(false);
                    inTurnPlayer.abandonSayNoButton.setVisible(false);
                    ActionListener[] inTurnPlayerAbandonSayNoButtonActionListeners = inTurnPlayer.abandonSayNoButton
                            .getActionListeners();
                    for (ActionListener actionListener : inTurnPlayerAbandonSayNoButtonActionListeners) {
                        inTurnPlayer.abandonSayNoButton.removeActionListener(actionListener);
                    }

                    // Check if there are any further interactions:
                    if (inTurnPlayer.interactivePlayers.size() > 0) { // The player needs to continue interacting with
                                                                      // another player:
                        Card playedCard = inTurnPlayer.singleActionCardsBuffer.get(0);
                        if (inTurnPlayer.singleActionCardsBuffer.size() > 1) {
                            inTurnPlayer.singleActionCardsBuffer.clear();
                            inTurnPlayer.singleActionCardsBuffer.add(playedCard);
                        }
                        Player nextCompetitor = inTurnPlayer.interactivePlayers.get(0);
                        nextCompetitor.setIsInAction(true); // Set the next opponent's state to active

                        if (playedCard instanceof RentCard) { // Let the next opponent respond based on the card played
                                                              // by the current player this turn
                            nextCompetitor.payForMoney(inTurnPlayer,
                                    ((RentCard) inTurnPlayer.singleActionCardsBuffer.get(0)).totalRent);
                        } else if (playedCard instanceof ActionCard) {
                            if (((ActionCard) playedCard).type.equals(ActionCardType.BIRTHDAY)) {
                                nextCompetitor.payForMoney(inTurnPlayer, 2);
                            } else if (((ActionCard) playedCard).type.equals(ActionCardType.DEBT_COLLECTOR)) {
                                nextCompetitor.payForMoney(inTurnPlayer, 5);
                            } else if (((ActionCard) playedCard).type.equals(ActionCardType.SLY_DEAL)) {
                                nextCompetitor.payForProperty(inTurnPlayer);
                            } else if (((ActionCard) playedCard).type.equals(ActionCardType.FORCE_DEAL)) {
                                nextCompetitor.swapProperty(inTurnPlayer);
                            } else if (((ActionCard) playedCard).type.equals(ActionCardType.DEAL_BREAKER)) {
                                nextCompetitor.payForWholeProperty(inTurnPlayer);
                            }
                        }

                    } else { // All actions for the player in turn have ended
                        inTurnPlayer.setIsInAction(true);
                        inTurnPlayer.singleActionCardsBuffer.clear();
                        inTurnPlayer.interactivePlayers.clear();
                        inTurnPlayer.playerCardsPile.updateAndShowCards();
                    }
                }
            }
        };
    }

    // The player has abandoned the Say No card and chosen to pay the rent (the
    // interaction is about to end):

    public ActionListener abandonSayNoAndPayForMoneyButtonListener(Player debtor, int totalRent) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (debtor.isInAction()) {
                    // Add pledge buttons to the debtor's bank and properties:
                    debtor.property.addAndPaintPledgeButtons(totalRent);
                    debtor.bank.addAndPaintPledgeButtons(totalRent);

                    // The restoration of the debtor's hand cards button operation is performed
                    // after all pledge activities are completed
                    // debtor.handCardsButton.setVisible(true);

                    // Hide the SayNoCard switch and clear the listeners:
                    debtor.sayNoButton.setVisible(false);
                    debtor.abandonSayNoButton.setVisible(false);
                    ActionListener[] abandonSayNoButtonActionListeners = debtor.abandonSayNoButton.getActionListeners();
                    for (ActionListener actionListener : abandonSayNoButtonActionListeners) {
                        debtor.abandonSayNoButton.removeActionListener(actionListener);
                    }

                    // Remove the abandonSayNoButton listener from the player in turn as well
                    Player inTurnPlayer = debtor.interactivePlayers.get(0);
                    ActionListener[] inTurnPlayerAbandonSayNoButtonActionListeners = inTurnPlayer.abandonSayNoButton
                            .getActionListeners();
                    for (ActionListener actionListener : inTurnPlayerAbandonSayNoButtonActionListeners) {
                        inTurnPlayer.abandonSayNoButton.removeActionListener(actionListener);
                    }
                }
            }
        };
    }

    // The propertyOwner has abandoned the Say No card and chosen to give up a
    // PropertyCard (the interaction is about to end):

    public ActionListener abandonSayNoAndPayForSinglePropertyButtonListener(Player propertyOwner) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                propertyOwner.setIsInAction(false);
                propertyOwner.interactivePlayers.get(0).setIsInAction(true); // The player who initiated Sly Deal
                                                                             // selects a property from the victim

                // Force the victim's property to be opened:
                for (Player player : Game.players) {
                    player.setVisible(false);
                    if (player.isPlayerTurn()) {
                        player.playerCardsPile.setVisible(false);
                    }
                }
                propertyOwner.whetherViewComponent = true;
                propertyOwner.property.closeButton.setVisible(false);
                propertyOwner.property.setVisible(true);
                propertyOwner.property.reallocateAllCards();

                // Add "steal" buttons to the stolen player's property cards
                propertyOwner.property.addAndPaintStealSinglePropertyButtons();
                propertyOwner.handCardsButton.setVisible(true);

                // Hide the SayNoCard switch and clear the listeners:
                propertyOwner.sayNoButton.setVisible(false);
                propertyOwner.abandonSayNoButton.setVisible(false);
                ActionListener[] abandonSayNoButtonActionListeners = propertyOwner.abandonSayNoButton
                        .getActionListeners();
                for (ActionListener actionListener : abandonSayNoButtonActionListeners) {
                    propertyOwner.abandonSayNoButton.removeActionListener(actionListener);
                }

                // Remove the abandonSayNoButton listener from the player in turn as well
                Player inTurnPlayer = propertyOwner.interactivePlayers.get(0);
                ActionListener[] inTurnPlayerAbandonSayNoButtonActionListeners = inTurnPlayer.abandonSayNoButton
                        .getActionListeners();
                for (ActionListener actionListener : inTurnPlayerAbandonSayNoButtonActionListeners) {
                    inTurnPlayer.abandonSayNoButton.removeActionListener(actionListener);
                }
            }
        };
    }

    // The propertyOwner has abandoned the Say No card and agreed to swap properties
    public ActionListener abandonSayNoAndAcceptSwapPropertyButtonListener(Player propertyOwner) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                propertyOwner.setIsInAction(false);
                propertyOwner.interactivePlayers.get(0).setIsInAction(true); // The player who initiated Sly Deal
                                                                             // selects a property from the victim

                // Force the victim's property to be opened:
                for (Player player : Game.players) {
                    player.setVisible(false);
                    if (player.isPlayerTurn()) {
                        player.playerCardsPile.setVisible(false);
                    }
                }
                propertyOwner.whetherViewComponent = true;
                propertyOwner.property.closeButton.setVisible(false);
                propertyOwner.property.setVisible(true);
                propertyOwner.property.reallocateAllCards();

                // Add "swap" buttons to the player's property cards
                propertyOwner.property.addAndPaintSwapPropertyButtons();
                propertyOwner.handCardsButton.setVisible(true);

                // Hide the SayNoCard switch and clear the listeners:
                propertyOwner.sayNoButton.setVisible(false);
                propertyOwner.abandonSayNoButton.setVisible(false);
                ActionListener[] abandonSayNoButtonActionListeners = propertyOwner.abandonSayNoButton
                        .getActionListeners();
                for (ActionListener actionListener : abandonSayNoButtonActionListeners) {
                    propertyOwner.abandonSayNoButton.removeActionListener(actionListener);
                }

                // Remove the abandonSayNoButton listener from the player in turn as well
                Player inTurnPlayer = propertyOwner.interactivePlayers.get(0);
                ActionListener[] inTurnPlayerAbandonSayNoButtonActionListeners = inTurnPlayer.abandonSayNoButton
                        .getActionListeners();
                for (ActionListener actionListener : inTurnPlayerAbandonSayNoButtonActionListeners) {
                    inTurnPlayer.abandonSayNoButton.removeActionListener(actionListener);
                }
            }
        };
    }

    // The propertyOwner has abandoned the Say No card and chosen to give up a whole
    // set of PropertyCards (the interaction is about to end):
    public ActionListener abandonSayNoAndPayForWholePropertyButtonListener(Player propertyOwner) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                propertyOwner.setIsInAction(false);
                propertyOwner.interactivePlayers.get(0).setIsInAction(true); // The player who initiated Deal Breaker
                                                                             // selects a property from the victim

                // Force the victim's property to be opened:
                for (Player player : Game.players) {
                    player.setVisible(false);
                    if (player.isPlayerTurn()) {
                        player.playerCardsPile.setVisible(false);
                    }
                }
                propertyOwner.whetherViewComponent = true;
                propertyOwner.property.closeButton.setVisible(false);
                propertyOwner.property.setVisible(true);
                propertyOwner.property.reallocateAllCards();

                // Add "steal" buttons to the stolen player's property cards
                propertyOwner.property.addAndPaintStealWholePropertyButtons();
                propertyOwner.handCardsButton.setVisible(true);

                // Hide the SayNoCard switch and clear the listeners:
                propertyOwner.sayNoButton.setVisible(false);
                propertyOwner.abandonSayNoButton.setVisible(false);
                ActionListener[] abandonSayNoButtonActionListeners = propertyOwner.abandonSayNoButton
                        .getActionListeners();
                for (ActionListener actionListener : abandonSayNoButtonActionListeners) {
                    propertyOwner.abandonSayNoButton.removeActionListener(actionListener);
                }

                // Remove the abandonSayNoButton listener from the player in turn as well
                Player inTurnPlayer = propertyOwner.interactivePlayers.get(0);
                ActionListener[] inTurnPlayerAbandonSayNoButtonActionListeners = inTurnPlayer.abandonSayNoButton
                        .getActionListeners();
                for (ActionListener actionListener : inTurnPlayerAbandonSayNoButtonActionListeners) {
                    inTurnPlayer.abandonSayNoButton.removeActionListener(actionListener);
                }
            }
        };
    }

}
