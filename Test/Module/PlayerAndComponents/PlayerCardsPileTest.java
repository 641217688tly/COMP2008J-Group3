package Module.PlayerAndComponents;

import Module.Cards.Card;
import Module.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerCardsPileTest {

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
        PlayerCardsPile playerCardsPile = player.playerCardsPile;
        playerCardsPile.updateAndShowCards();
        for (int i = 0; i < player.cardsTable.length; i++) {
            assertTrue(playerCardsPile.cardsTable[i] == player.cardsTable[i]);
        }
    }

}