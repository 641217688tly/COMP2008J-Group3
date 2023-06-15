package Module;

import Module.Cards.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardsPileTest {

    @Test
    public void testInitializeCardsPile() {
        CardsPile cardsPile = new CardsPile();
        cardsPile.initializeCardsPile();

        // Ensure the draw pile has been initialized
        assertTrue(!cardsPile.drawPile.empty());

        // Ensure the discard pile is empty
        assertTrue(cardsPile.discardPile.empty());

        // Ensure the top card of the draw pile is set to visible
        boolean isCardVisible = false;
        if (cardsPile.drawPile.peek().isCardDisplayable()) {
            isCardVisible = true;
        }
        assertTrue(isCardVisible);

        // Ensure the draw pile has 108 cards
        assertEquals(108, cardsPile.drawPile.size());
    }

    @Test
    public void testDrawCardFromDrawPile() {
        CardsPile cardsPile = new CardsPile();
        cardsPile.initializeCardsPile();

        // Test that the draw pile has cards after initialization
        assertTrue(!cardsPile.drawPile.empty());

        // Draw a card
        Card[] drawnCard = cardsPile.drawCardFromDrawPile(1);

        // Ensure the drawn card is not null and the quantity is 1
        assertNotNull(drawnCard);
        assertEquals(1, drawnCard.length);

        // Ensure the draw pile has one less card now
        assertEquals(107, cardsPile.drawPile.size());
    }

    @Test
    public void testRecycleCardIntoDiscardPile() {
        CardsPile cardsPile = new CardsPile();
        cardsPile.initializeCardsPile();

        // Draw a card
        Card[] drawnCard = cardsPile.drawCardFromDrawPile(1);

        // Recycle the card into the discard pile
        cardsPile.recycleCardIntoDiscardPile(drawnCard[0]);

        // Ensure the discard pile now has one card
        assertEquals(1, cardsPile.discardPile.size());

        // Ensure the card in the discard pile is the one recycled
        assertSame(drawnCard[0], cardsPile.discardPile.peek());
    }

}
