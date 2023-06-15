package Module;

import GUI.ApplicationStart;
import Module.Cards.Card;
import Module.Cards.CardsEnum.PropertyCardType;
import Module.Cards.PropertyCard;
import Module.PlayerAndComponents.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements IGame {
    public static int[] playersJPanelXCoordinate = {(ApplicationStart.screenWidth * 11) / 12, 0, (ApplicationStart.screenWidth * 2) / 12, (ApplicationStart.screenWidth * 9) / 12, (ApplicationStart.screenWidth * 11) / 12};
    public static int[] playersJPanelYCoordinate = {(ApplicationStart.screenHeight * 4) / 5, (ApplicationStart.screenHeight * 2) / 5, 0, 0, (ApplicationStart.screenHeight * 2) / 5};
    public static CardsPile cardsPile = new CardsPile(); // Central card area
    public static ArrayList<Player> players = new ArrayList<>(); // All Player objects (including Bank, Property, PlayerCards, and PlayerCardsPile components) 
    public boolean isMoveToNextTurn = false;
    private int counter = 1;

    public void addPlayers(List<String> playerNames) { // Create all player objects and add them to the Game's players list after setting the number of players and their names in the setup interface
        Game.players.clear();
        for (int i = 0; i < playerNames.size(); i++) {
            Player player = new Player(playerNames.get(i), Player.images[i], Game.playersJPanelXCoordinate[i], Game.playersJPanelYCoordinate[i]);
            players.add(player);
        }
    }

    @Override
    public void startNewGame() {
        Game.players.get(0).setPlayerTurn(true); // Start the turn with player 1
        for (Player player : Game.players) {
            player.drawCards(Game.cardsPile.drawCardFromDrawPile(5)); // Each player draws five cards at the start of their turn
            player.moveToNextTurn(); // Set the turn count and open/close buttons for cards
        }
    }

    @Override
    public void updateGame() {
        moveToNextPlayerTurn();
        isGameOver();
    }

    @Override
    public void moveToNextPlayerTurn() {
        if (Game.players.get(0).actionNumber == 0) { // If the player has no more actions left
            boolean isInteractionComplete = true; // Check if the interaction between players is complete and if any player holds more than seven cards
            for (Player player : Game.players) {
                if (player.interactivePlayers.size() > 0) {
                    isInteractionComplete = false;
                    break;
                }
            }
            if (Game.players.get(0).numberOfHandCards() > 7) {
                isInteractionComplete = false;
            }
            if (isInteractionComplete) {
                Game.players.get(0).setPlayerTurn(false);
                reDistributePlayersLocation(); // Rotate players and update their positions in the array
                Game.players.get(0).setPlayerTurn(true); // Set the turn to the next player

                // Replenish cards:
                int cardsCount = 0;
                for (Card card : Game.players.get(0).cardsTable) {
                    if (card != null) {
                        cardsCount++;
                    }
                }
                if (counter >= Game.players.size()) {
                    if (cardsCount <= 7) {
                        if (cardsCount == 0) {
                            Game.players.get(0).drawCards(Game.cardsPile.drawCardFromDrawPile(5));
                        }  else {
                            Game.players.get(0).drawCards(Game.cardsPile.drawCardFromDrawPile(2));
                        }
                    }
                } else {
                    counter++;
                }

                isMoveToNextTurn = true; // Start displaying the message for the next turn
                Timer timer = new Timer(3000, e -> {
                    isMoveToNextTurn = false; // Stop displaying the message after 5 seconds
                });
                timer.setRepeats(false); // Run the timer only once
                timer.start();

                for (Player player : Game.players) {
                    player.moveToNextTurn();
                }
            }
        }
    }

    private void reDistributePlayersLocation() {
        // Rotate the players list counter-clockwise by 1
        Collections.rotate(Game.players, -1);
        // Update the players' coordinates according to their new order
        for (int i = 0; i < Game.players.size(); i++) {
            Player player = Game.players.get(i);
            player.playerJPanelX = playersJPanelXCoordinate[i];
            player.playerJPanelY = playersJPanelYCoordinate[i];
            player.setBounds(player.playerJPanelX, player.playerJPanelY, Player.playerWidth, Player.playerHeight);
        }
    }

    @Override
    public boolean isGameOver() {
        for (Player player : Game.players) {
            int wholeSetNumber = 0;
            for (PropertyCardType propertyType : PropertyCardType.values()) {
                if (player.property.propertyNumberMap.get(propertyType) >= PropertyCard.judgeCompleteSetNumber(propertyType)) {
                    wholeSetNumber++;
                    if (wholeSetNumber >= 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
