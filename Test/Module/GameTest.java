package Module;

import Module.Cards.Card;
import Module.Cards.PropertyCard;
import Module.PlayerAndComponents.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void startNewGame() {
        Game game = new Game();
        List<String> playerNames = new ArrayList<>();
        playerNames.add("player1");
        playerNames.add("player2");

        game.addPlayers(playerNames);
        game.startNewGame();

        // Ensure that the turn of the first player begins after the game starts
        assertTrue(game.players.get(0).isPlayerTurn());

        // Ensure that all players have 5 cards in hand after the game starts
        for (Player player : game.players) {
            assertEquals(5, player.numberOfHandCards());
        }
    }

    @Test
    void isGameOver() {
        Game game = new Game();
        List<String> playerNames = new ArrayList<>();
        playerNames.add("player1");
        playerNames.add("player2");

        game.addPlayers(playerNames);
        game.startNewGame();

        // Ensure the game is not over at the start of the game
        assertFalse(game.isGameOver());

        // Add enough property cards to player1 to ensure they meet the winning
        // condition
        for (Card card : PropertyCard.initializeCardsForCardsPile()) {
            Game.players.get(0).property.placePropertyCardAndShowTable(card);
        }

        assertTrue(game.isGameOver());
    }

}
