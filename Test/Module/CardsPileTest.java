package Module;

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
        boolean isAnyCardVisible = false;
        if (cardsPile.drawPile.peek().isDisplayable()) {
            isAnyCardVisible = true;
        }
        assertTrue(isAnyCardVisible);

        // 确保抽牌堆中有 108 张牌
        assertEquals(108, cardsPile.drawPile.size());
    }
}
