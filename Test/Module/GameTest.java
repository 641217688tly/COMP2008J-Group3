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

        // 确保游戏开始后，第一个玩家的回合开始
        assertTrue(game.players.get(0).isPlayerTurn());

        // 确保游戏开始后，所有玩家手中都有5张卡片
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

        // 确保在游戏开始时，游戏没有结束
        assertFalse(game.isGameOver());

        //为player1添加足够多的房产卡以确保他达到游戏胜利的条件:
        for (Card card : PropertyCard.initializeCardsForCardsPile()) {
            Game.players.get(0).property.placePropertyCardAndShowTable(card);
        }
        assertTrue(game.isGameOver());
    }
}
