package Module.Cards;

import Module.Cards.CardsEnum.PropertyCardType;
import Module.Cards.CardsEnum.RentCardType;
import Module.PlayerAndComponents.Player;
import Module.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RentCardTest {
    private Player player;
    private Game game;
    private RentCard rentCard;

    @BeforeEach
    public void setup() {
        game = new Game();
        game.addPlayers(Arrays.asList("Player1", "Player2"));
        game.startNewGame();
        player = Game.players.get(0);
        rentCard = new RentCard(RentCardType.WILD_RENT, new ImageIcon("images/Card/RentCard/RentAllColor.jpg"), 3);
        rentCard.owner = player;
        player.cardsTable[0] = rentCard;
    }

    @Test
    public void testDiscard() {
        // At the beginning, the rentCard should be in the player's hand
        assertEquals(rentCard, player.cardsTable[0]);

        // Test discard card
        rentCard.discard();

        // After discarding, the rentCard should have been removed from the player's hand
        assertNull(player.cardsTable[0]);
    }

    @Test
    public void testDeposit() {
        // At the beginning, the player's action number should be greater than 0
        assertTrue(player.actionNumber > 0);
        int playerActionNumber = player.actionNumber;
        int moneyInBank = player.bank.calculateTotalAssetsInBank();

        // Test deposit
        rentCard.deposit();

        // After deposit, the player's action number should decrease
        assertTrue(player.actionNumber < playerActionNumber);
        assertTrue(moneyInBank < player.bank.calculateTotalAssetsInBank());
    }

    @Test
    public void testPlay() {
        assertTrue(player.containsCard(rentCard));

        player.setPlayerTurn(true);
        player.setIsInAction(true);
        player.property.placePropertyCardAndShowTable(new PropertyCard(PropertyCardType.BLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4));
        rentCard.play();

        assertFalse(player.containsCard(rentCard));
    }
}
