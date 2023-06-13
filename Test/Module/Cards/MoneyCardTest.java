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
        // 初始化游戏和玩家对象，以及一个MoneyCard
        // 请根据你的项目情况进行修改
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
        // 在开始时，moneyCard应该在玩家的手中
        assertEquals(moneyCard, player.cardsTable[0]);

        // 测试丢弃卡牌
        moneyCard.discard();

        // 在丢弃后，moneyCard应该已经被从玩家手中移除
        assertNull(player.cardsTable[0]);
    }

    @Test
    public void testDeposit() {
        // 在开始时，玩家的行动次数应该大于0
        assertTrue(player.actionNumber > 0);
        int playerActionNumber = player.actionNumber;
        int moneyInBank = player.bank.calculateTotalAssetsInBank();
        // 测试存钱
        moneyCard.deposit();

        // 在存钱后，玩家的行动次数应该减少
        assertTrue(player.actionNumber < playerActionNumber);
        assertTrue(moneyInBank < player.bank.calculateTotalAssetsInBank());
    }

    @Test
    public void testPlay() {
        // 在开始时，玩家的行动次数应该大于0
        assertTrue(player.actionNumber > 0);
        int playerActionNumber = player.actionNumber;
        int moneyInBank = player.bank.calculateTotalAssetsInBank();
        // 测试存钱
        moneyCard.deposit();

        // 在存钱后，玩家的行动次数应该减少
        assertTrue(player.actionNumber < playerActionNumber);
        assertTrue(moneyInBank < player.bank.calculateTotalAssetsInBank());
    }
}

