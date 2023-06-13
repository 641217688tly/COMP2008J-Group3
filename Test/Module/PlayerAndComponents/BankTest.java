package Module.PlayerAndComponents;

import Module.Cards.Card;
import Module.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.addPlayers(Arrays.asList("Player1", "Player2"));
        game.startNewGame();
    }

    @Test
    void testSaveMoneyAndShowCards() {
        Player player = Game.players.get(0);
        Bank bank = player.bank;
        Card card = Game.cardsPile.drawCardFromDrawPile(1)[0];

        assertFalse(bank.containsCard(card));

        bank.saveMoneyAndShowCards(card);
        assertTrue(bank.containsCard(card));
    }

    @Test
    void testRemoveCardFromBank() {
        Player player = Game.players.get(0);
        Bank bank = player.bank;
        Card card = Game.cardsPile.drawCardFromDrawPile(1)[0];

        bank.saveMoneyAndShowCards(card);
        assertTrue(bank.containsCard(card));

        bank.removeCardFromBank(card);
        assertFalse(bank.containsCard(card));
    }

    @Test
    void testCalculateTotalAssetsInBank() {
        Player player = Game.players.get(0);
        Bank bank = player.bank;
        Card card = Game.cardsPile.drawCardFromDrawPile(1)[0];

        int initialAssets = bank.calculateTotalAssetsInBank();

        bank.saveMoneyAndShowCards(card);
        int assetsAfterAddingCard = bank.calculateTotalAssetsInBank();

        // Assuming the value of the card is stored in the value field.
        assertEquals(initialAssets + card.value, assetsAfterAddingCard);
    }

}
