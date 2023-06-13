package Module.PlayerAndComponents;

import Module.Cards.Card;
import Module.Cards.MoneyCard;
import Module.Game;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void moveToNextTurn() {
        Game game = new Game();
        List<String> playerNames = new ArrayList<>();
        playerNames.add("player1");
        playerNames.add("player2");

        game.addPlayers(playerNames);
        Player player1 = game.players.get(0);
        Player player2 = game.players.get(1);
        player1.setPlayerTurn(true);
        player2.setPlayerTurn(false);

        // 假设一些状态
        player1.oneTurnCardsBuffer.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/1.jpg"), 1));
        player1.singleActionCardsBuffer.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/1.jpg"), 1));
        player1.interactivePlayers.add(game.players.get(1));
        player1.debt = 100;
        player1.actionNumber = 1;

        player2.oneTurnCardsBuffer.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/1.jpg"), 1));
        player2.singleActionCardsBuffer.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/1.jpg"), 1));
        player2.interactivePlayers.add(game.players.get(1));
        player2.debt = 100;
        player2.actionNumber = 1;

        player1.moveToNextTurn();
        player2.moveToNextTurn();

        assertTrue(player1.oneTurnCardsBuffer.isEmpty());
        assertTrue(player1.singleActionCardsBuffer.isEmpty());
        assertTrue(player1.interactivePlayers.isEmpty());
        assertEquals(0, player1.debt);
        assertEquals(3, player1.actionNumber);
        assertTrue(player1.isInAction());

        assertTrue(player2.oneTurnCardsBuffer.isEmpty());
        assertTrue(player2.singleActionCardsBuffer.isEmpty());
        assertTrue(player2.interactivePlayers.isEmpty());
        assertEquals(0, player2.debt);
        assertEquals(3, player2.actionNumber);
        assertFalse(player2.isInAction());
    }

    @Test
    void drawCards() {
        Game game = new Game();
        List<String> playerNames = new ArrayList<>();
        playerNames.add("player1");
        playerNames.add("player2");

        game.addPlayers(playerNames);
        Player player1 = game.players.get(0);
        Player player2 = game.players.get(0);
        Card[] cards = new Card[5];
        for (int i = 0; i < 5; i++) {
            cards[i] = new MoneyCard(new ImageIcon("images/Card/MoneyCard/1.jpg"), 1);
        }

        player1.drawCards(cards);
        player2.drawCards(cards);

        for (Card card : cards) {
            assertTrue(player1.containsCard(card));
            assertTrue(player2.containsCard(card));
        }
    }
}
