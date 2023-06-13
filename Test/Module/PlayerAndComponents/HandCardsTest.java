package Module.PlayerAndComponents;


import Module.Cards.Card;
import Module.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HandCardsTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.addPlayers(Arrays.asList("Player1", "Player2"));
        game.startNewGame();
    }

    @Test
    void updateAndShowCards() {
        Player player = Game.players.get(0);
        HandCards handCards = player.handCards;
        handCards.updateAndShowCards();
        for (int i = 0; i < player.cardsTable.length; i++) {
            assertTrue(handCards.cardsTable[i] == player.cardsTable[i]);
        }
    }
}