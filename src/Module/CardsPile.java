package Module;

import GUI.ApplicationStart;
import Module.Cards.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Stack;

public class CardsPile extends JPanel {
    public Stack<Card> drawPile; // Draw pile
    public Stack<Card> discardPile; // Discard pile

    public int cardsPileJPanelX = (ApplicationStart.screenWidth * 5) / 12;
    public int cardsPileJPanelY = (ApplicationStart.screenHeight * 2) / 5;
    public int cardsPileJPanelWidth = (ApplicationStart.screenWidth * 2) / 12;
    public int cardsPileJPanelHeight = (ApplicationStart.screenHeight) / 5;

    public int drawPileX = 0; // X-coordinate of the draw pile relative to CardsPile JPanel
    public int drawPileY = 0; // Y-coordinate of the draw pile relative to CardsPile JPanel
    public int discardPileX = (ApplicationStart.screenWidth) / 12; // X-coordinate of the discard pile relative to CardsPile JPanel
    public int discardPileY = 0; // Y-coordinate of the discard pile relative to CardsPile JPanel

    public static int drawPileHeight = (ApplicationStart.screenHeight) / 5;
    public static int drawPileWidth = (ApplicationStart.screenWidth) / 12;
    public static int discardPileHeight = (ApplicationStart.screenHeight) / 5;
    public static int discardPileWidth = (ApplicationStart.screenWidth) / 12;
    private Image cardsPileBackground;

    public CardsPile() {
        this.setLayout(null); // Need to manually set the position and size of each component
        this.setBounds(cardsPileJPanelX, cardsPileJPanelY, cardsPileJPanelWidth, cardsPileJPanelHeight); // Set the size and position of this JPanel (CardsPile)
        loadAndSetBackgroundImage();
        initializeCardsPile(); // Add all cards to the draw pile
    }

    public void initializeCardsPile() {
        this.drawPile = new Stack<Card>(); // Draw pile
        this.discardPile = new Stack<Card>(); // Discard pile
        drawPile.addAll(ActionCard.initializeCardsForCardsPile());
        drawPile.addAll(MoneyCard.initializeCardsForCardsPile());
        drawPile.addAll(PropertyCard.initializeCardsForCardsPile());
        drawPile.addAll(PropertyWildCard.initializeCardsForCardsPile());
        drawPile.addAll(RentCard.initializeCardsForCardsPile());
        Collections.shuffle(drawPile); // Shuffle the cards
        for (int i = 0; i < drawPile.size(); i++) {
            this.setCardBounds(drawPile.get(i), drawPileX, drawPileY, false, false); // Set as invisible with the back facing up
        }
        paintPeekCard(); // Set the first card as visible
    }

    private void loadAndSetBackgroundImage() {
        try {
            // Read the background image from a file
            cardsPileBackground = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerCardsPileBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCardBounds(Card card, int x, int y, boolean isDisplayable, boolean isCardFront) {
        card.setCardJPanelBounds(x, y); // Assign coordinates within this JPanel to the card
        card.setIsDisplayable(isDisplayable);
        card.setIsCardFront(isCardFront);
    }

    public Card[] drawCardFromDrawPile(int number) { // Draw cards, all drawn cards are: 1. Without buttons, 2. Not visible
        Card[] cards = new Card[number];
        if (drawPile.size() < number) {
            if (drawPile.size() > 0) {
                drawPile.peek().setIsDisplayable(false);
                this.remove(drawPile.peek());
            }
            reuseCardsFromDiscardPile();
        }
        for (int i = 0; i < number; i++) {
            Card popCard = this.drawPile.pop();
            popCard.setIsDisplayable(false);
            this.remove(popCard);
            cards[i] = popCard;
        }
        paintPeekCard();
        return cards;
    }

    private void reuseCardsFromDiscardPile() { // Take cards from the discard pile when there are no cards in the draw pile
        if (discardPile.size() > 0) {
            discardPile.peek().setIsDisplayable(false);
            this.remove(discardPile.peek());
        }
        for (Card card : discardPile) {
            setCardBounds(card, drawPileX, drawPileY, false, false);
        }
        drawPile.addAll(discardPile);
        Collections.shuffle(drawPile);
        paintPeekCard();
    }

    public void recycleCardIntoDiscardPile(Card card) { // Recycle discarded cards
        // Set the previously displayed card as not displayable:
        if (discardPile.size() > 0) {
            discardPile.peek().setIsDisplayable(false);
            this.remove(discardPile.peek());
        }
        card.owner = null;
        // Hide all buttons on the card:
        card.openMoveButtonSwitch(false);
        card.openPlayButtonSwitch(false);
        card.openDepositButtonSwitch(false);
        card.openDiscardButtonSwitch(false);
        this.discardPile.push(card);
        paintPeekCard();
    }

    // Drawing methods:

    private void paintPeekCard() { // Add the card at the peek position as a component and show it
        if (drawPile.size() > 0) {
            this.add(drawPile.peek());
            setCardBounds(drawPile.peek(), drawPileX, drawPileY, true, false);
            drawPile.peek().setIsDisplayable(true); // Set the top card as displayable
        }
        if (discardPile.size() > 0) {
            this.add(discardPile.peek());
            setCardBounds(discardPile.peek(), discardPileX, discardPileY, true, true);
            discardPile.peek().setIsDisplayable(true); // Set the top card as displayable
        }
    }

    public void paintCardsPile(Graphics g) {
        if (cardsPileBackground != null) { // If the background image is loaded
            // Draw the background image on the panel, filling the entire panel
            g.drawImage(cardsPileBackground, drawPileX, drawPileY, drawPileWidth, drawPileHeight, null);
            g.drawImage(cardsPileBackground, discardPileX, discardPileY, discardPileWidth, discardPileHeight, null);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the parent's method to ensure normal drawing
        paintCardsPile(g);
    }
}
