package Module.Cards;

import Module.Cards.CardsEnum.PropertyWildCardType;
import Module.Cards.CardsEnum.RentCardType;
import Module.PlayerAndComponents.Player;
import Module.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyWildCardTest {
    Player player;
    Game game;
    PropertyWildCard propertyWildCard;

    @BeforeEach
    public void setup() {
        // Initialize the game and player objects, as well as a PropertyWildCard
        // Please modify according to your project's requirements
        game = new Game();
        game.addPlayers(Arrays.asList("Player1", "Player2"));
        game.startNewGame();
        player = Game.players.get(0);
        propertyWildCard = new PropertyWildCard(PropertyWildCardType.BLUE_GREEN,
                new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardBlueGreen_Blue.jpg"),
                new ImageIcon("images/Card/PropertyWildCard/PropertyWildCardBlueGreen_Green.jpg"), 4);
        propertyWildCard.owner = player;
        player.cardsTable[0] = propertyWildCard;
    }

    @Test
    public void testDiscard() {
        // At the beginning, the propertyWildCard should be in the player's hand
        assertEquals(propertyWildCard, player.cardsTable[0]);

        // Test discarding the card
        propertyWildCard.discard();

        // After discarding, the propertyWildCard should have been removed from the
        // player's hand
        assertNull(player.cardsTable[0]);
    }

    @Test
    public void testDeposit() {
        // At the beginning, the player's action number should be greater than 0
        assertTrue(player.actionNumber > 0);
        int playerActionNumber = player.actionNumber;
        int moneyInBank = player.bank.calculateTotalAssetsInBank();

        // Test depositing money
        propertyWildCard.deposit();

        // After depositing, the player's action number should decrease
        assertTrue(player.actionNumber < playerActionNumber);
        assertTrue(moneyInBank < player.bank.calculateTotalAssetsInBank());
    }

    @Test
    public void testPlay() {
        // At the beginning, the player's action number should be greater than 0
        assertTrue(player.actionNumber > 0);
        int playerActionNumber = player.actionNumber;
        assertFalse(player.property.whetherHasPropertyCards(
                new RentCard(RentCardType.WILD_RENT, new ImageIcon("images/Card/RentCard/RentAllColor.jpg"), 3)));

        // Test playing the card
        propertyWildCard.play();

        // After playing, the player's action number should decrease
        assertTrue(player.actionNumber < playerActionNumber);
        assertTrue(player.property.whetherHasPropertyCards(
                new RentCard(RentCardType.WILD_RENT, new ImageIcon("images/Card/RentCard/RentAllColor.jpg"), 3)));
    }

}
