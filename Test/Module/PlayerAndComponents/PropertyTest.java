package Module.PlayerAndComponents;

import Module.Cards.Card;
import Module.Game;
import Module.Cards.CardsEnum.PropertyCardType;
import Module.Cards.PropertyCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PropertyTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.addPlayers(Arrays.asList("Player1", "Player2"));
        game.startNewGame();
    }


    @Test
    void calculateTotalAssetsInProperty() {
        Player player = Game.players.get(0);
        Property property = player.property;
        // Assuming Card value as 5
        Card card1 = new PropertyCard(PropertyCardType.BLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4);
        Card card2 = new PropertyCard(PropertyCardType.BLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4);
        property.placePropertyCardAndShowTable(card1);
        property.placePropertyCardAndShowTable(card2);

        int totalAssets = property.calculateTotalAssetsInProperty();
        assertEquals(card1.value + card2.value, totalAssets);
    }

    @Test
    void updatePropertiesNumber() {
        Player player = Game.players.get(0);
        Property property = player.property;
        // Assuming card placed on the table is considered as one property
        Card card1 = new PropertyCard(PropertyCardType.BLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4);
        Card card2 = new PropertyCard(PropertyCardType.BLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4);
        property.placePropertyCardAndShowTable(card1);
        property.placePropertyCardAndShowTable(card2);

        property.updatePropertiesNumber();
        assertEquals(2, property.propertyNumberMap.get(PropertyCardType.BLUE));
    }

    @Test
    void removeCardFromProperty() {
        Player player = Game.players.get(0);
        Property property = player.property;
        Card card1 = new PropertyCard(PropertyCardType.BLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4);
        Card card2 = new PropertyCard(PropertyCardType.BLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4);
        property.placePropertyCardAndShowTable(card1);
        property.placePropertyCardAndShowTable(card2);

        property.removeCardFromProperty(card1);
        property.updatePropertiesNumber();
        assertEquals(1, property.propertyNumberMap.get(PropertyCardType.BLUE));
    }

    @Test
    void placePropertyCardAndShowTable() {
        Player player = Game.players.get(0);
        Property property = player.property;
        Card card1 = new PropertyCard(PropertyCardType.BLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4);
        Card card2 = new PropertyCard(PropertyCardType.BLUE, new ImageIcon("images/Card/PropertyCard/PropertyCardBlue.jpg"), 4);
        property.placePropertyCardAndShowTable(card1);
        property.placePropertyCardAndShowTable(card2);

        property.updatePropertiesNumber();
        assertEquals(2, property.propertyNumberMap.get(PropertyCardType.BLUE));
    }
}
