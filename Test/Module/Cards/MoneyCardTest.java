package Module.Cards;

import Module.PlayerAndComponents.Player;
import Module.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyCardTest {
    Player player;
    Game game;
    MoneyCard moneyCard;

    @BeforeEach
    public void setup() {
        // Initialize the game and player objects, and a MoneyCard
        // Please modify it according to your project
        game = new Game();
        game.addPlayers(Arrays.asList("Player1", "Player2"));
        game.startNewGame();
        player = Game.players.get(0);
        moneyCard = new MoneyCard(new ImageIcon("images/Card/MoneyCard/1.jpg"), 1);
        moneyCard.owner = player;
        player.cardsTable[0] = moneyCard;
    }


    @Test
    public void testDiscard() {
        // At the beginning, the moneyCard should be in the player's hand
        assertEquals(moneyCard, player.cardsTable[0]);

        // Test discarding cards
        moneyCard.discard();

        // After discarding, the moneyCard should have been removed from the player's hand
        assertNull(player.cardsTable[0]);
    }

    @Test
    public void testDeposit() {
        // At the beginning, the number of actions of the player should be greater than 0
        assertTrue(player.actionNumber > 0);
        int playerActionNumber = player.actionNumber;
        int moneyInBank = player.bank.calculateTotalAssetsInBank();
        // Test saving money
        moneyCard.deposit();

        // After saving money, the number of actions a player takes should decrease
        assertTrue(player.actionNumber < playerActionNumber);
        assertTrue(moneyInBank < player.bank.calculateTotalAssetsInBank());
    }

    @Test
    public void testPlay() {
        // At the beginning, the number of actions of the player should be greater than 0
        assertTrue(player.actionNumber > 0);
        int playerActionNumber = player.actionNumber;
        int moneyInBank = player.bank.calculateTotalAssetsInBank();
        //  Test saving money
        moneyCard.deposit();

        // After saving money, the number of actions a player takes should decrease
        assertTrue(player.actionNumber < playerActionNumber);
        assertTrue(moneyInBank < player.bank.calculateTotalAssetsInBank());
    }
}

