package Module;

import Module.Cards.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardsPileTest {

    @Test
    public void testInitializeCardsPile() {
        CardsPile cardsPile = new CardsPile();
        cardsPile.initializeCardsPile();
        // 确保抽牌堆已经初始化
        assertTrue(!cardsPile.drawPile.empty());
        // 确保废牌堆为空
        assertTrue(cardsPile.discardPile.empty());
        // 确保栈顶部的牌被设置为可视
        boolean isCardVisible = false;
        if (cardsPile.drawPile.peek().isCardDisplayable()) {
            isCardVisible = true;
        }
        assertTrue(isCardVisible);

        // 确保抽牌堆中有 108 张牌
        assertEquals(108, cardsPile.drawPile.size());
    }

    @Test
    public void testDrawCardFromDrawPile() {
        CardsPile cardsPile = new CardsPile();
        cardsPile.initializeCardsPile();

        // 测试抽牌堆初始化后有牌
        assertTrue(!cardsPile.drawPile.empty());

        // 抽一张牌
        Card[] drawnCard = cardsPile.drawCardFromDrawPile(1);

        // 确保抽出的牌不为null，且数量为1
        assertNotNull(drawnCard);
        assertEquals(1, drawnCard.length);

        // 确保抽牌堆中现在少了一张牌
        assertEquals(107, cardsPile.drawPile.size());
    }

    @Test
    public void testRecycleCardIntoDiscardPile() {
        CardsPile cardsPile = new CardsPile();
        cardsPile.initializeCardsPile();

        // 抽一张牌
        Card[] drawnCard = cardsPile.drawCardFromDrawPile(1);

        // 回收这张牌
        cardsPile.recycleCardIntoDiscardPile(drawnCard[0]);

        // 确保废牌堆中现在有一张牌
        assertEquals(1, cardsPile.discardPile.size());

        // 确保这张牌是刚刚回收的那张
        assertSame(drawnCard[0], cardsPile.discardPile.peek());
    }

}
