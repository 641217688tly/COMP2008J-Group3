package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.HandCardsListener;
import Module.Cards.ActionCard;
import Module.Cards.Card;
import Module.Cards.CardsEnum.ActionCardType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class HandCards extends JPanel { //This class is a button on the player's border that is used to view the player's hand
    public Card[] cardsTable;
    public Point[] cardsCoordinates;
    public ArrayList<JButton> hereButtons;
    private Player owner;
    public JButton closeButton; // Add a close button
    private HandCardsListener handCardsListener;
    private Image handCardsImage;

    public HandCards(Player owner) {
        this.setLayout(null); // The position and size of each component need to be set manually
        this.setBounds(0, 3 * ApplicationStart.screenHeight / 5 - (ApplicationStart.screenHeight / 25), ApplicationStart.screenWidth, ApplicationStart.screenHeight / 5 + (ApplicationStart.screenHeight / 25)); // 设置提示框的位置和大小
        this.setVisible(false); // Initially set to invisible

        this.cardsTable = new Card[12];
        this.hereButtons = new ArrayList<>();
        this.owner = owner;
        this.handCardsListener = new HandCardsListener();
        loadAndSetPlayerCardsPileBackground();
        initButtons();
        initCardsCoordinates();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // Read a background image from a file
            handCardsImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerComponentsBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCardsCoordinates() {
        cardsCoordinates = new Point[12];
        for (int column = 0; column < 12; column++) {
            cardsCoordinates[column] = new Point((ApplicationStart.screenWidth / 12) * column, ApplicationStart.screenHeight / 25);
        }
    }

    private void initButtons() {
        closeButton = new JButton("Close");
        this.add(closeButton); // Add the close button to the JPanel
        closeButton.setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight / 25); // This value may need to be adjusted to place the close button in the proper place
        closeButton.addActionListener(handCardsListener.closeButtonListener(owner, this));
    }

    public void addAndPaintHereButtons(Card movedCard) {
        hereButtons.clear();
        if (owner.isPlayerTurn()) { //JButtons can only be created when the player is in their turn
            for (int i = 0; i < 12; i++) {
                if (cardsTable[i] == null) {
                    JButton herebutton = new JButton("Here");
                    herebutton.setBounds(cardsCoordinates[i].x, cardsCoordinates[i].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
                    Font buttonFont = new Font("Arial", Font.BOLD, 10);
                    herebutton.setFont(buttonFont); // Sets the font and font size of the button
                    herebutton.addActionListener(handCardsListener.moveButtonListener(owner, movedCard, herebutton));
                    this.add(herebutton);
                    hereButtons.add(herebutton);
                    herebutton.setVisible(true);
                }
            }
        }
    }

    public void moveCardAndUpdateScreen(Player owner, Card movedCard, JButton hereButton) {
        Point movedCardPoint = new Point(movedCard.getX(), movedCard.getY());
        Point hereButtonPoint = new Point(hereButton.getX(), hereButton.getY());
        int movedCardIndex = 0;
        int hereButtonIndex = 0;
        for (int i = 0; i < owner.handCards.cardsCoordinates.length; i++) {
            if (movedCardPoint.x == owner.handCards.cardsCoordinates[i].x && movedCardPoint.getY() == owner.handCards.cardsCoordinates[i].y) {
                movedCardIndex = i;
            } else if (hereButtonPoint.x == owner.handCards.cardsCoordinates[i].x && hereButtonPoint.getY() == owner.handCards.cardsCoordinates[i].y) {
                hereButtonIndex = i;
            }
        }

        //Change the position of the cards first
        movedCard.setBounds(hereButtonPoint.x, hereButtonPoint.y, Card.cardWidth, Card.cardHeight);
        //Change the position of the button:
        hereButton.setBounds(movedCardPoint.x, movedCardPoint.y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
        //Change the position of cards in the cardsTable:
        owner.cardsTable[hereButtonIndex] = movedCard;
        owner.handCards.cardsTable[hereButtonIndex] = movedCard;
        owner.cardsTable[movedCardIndex] = null;
        owner.handCards.cardsTable[movedCardIndex] = null;
        //Hide all JButtons:
        Iterator<JButton> iterator = owner.handCards.hereButtons.iterator();
        while (iterator.hasNext()) {
            JButton button = iterator.next();
            button.setVisible(false);
            owner.handCards.remove(button); //Remove the button from the JPanel
            iterator.remove(); //Remove the button from the ArrayList
        }
        owner.handCards.hereButtons.clear();
        //Update the screen
        paintAllCards();
    }

    public void updateAndShowCards() { //Each call needs to clear the existing cards from the list and remove the components from the JPanel, equivalent to the refresh method for HandCards
        this.cardsTable = owner.cardsTable; //Clone an array of players
        this.removeAll(); //Discard all the old cards (components), but this will cause the buttons to be lost as well
        this.add(closeButton); 
        //Add the missing button
        paintAllCards();
    }

    //-------paint method:

    private void paintAllCards() {
        for (int i = 0; i < cardsTable.length; i++) {
            if (cardsTable[i] != null) {
                Card card = cardsTable[i];
                card.setCardJPanelBounds(cardsCoordinates[i].x, cardsCoordinates[i].y); //为Card重新分配它在该JPanel下的坐标
                if (owner.isPlayerTurn()) { //Be in your own turn
                    if (owner.isInAction()) {//in action
                        if (owner.interactivePlayers.size() > 0) {
                            card.setIsCardFront(true);
                            card.openPlayButtonSwitch(false);
                            card.openDepositButtonSwitch(false);
                            card.openDiscardButtonSwitch(false);
                            card.openMoveButtonSwitch(false);
                            if (card instanceof ActionCard) {
                                if (((ActionCard) card).type.equals(ActionCardType.JUST_SAY_NO)) {
                                    card.openPlayButtonSwitch(true);
                                }
                            }
                        } else {
                            card.setIsCardFront(true);
                            card.openPlayButtonSwitch(true);
                            card.openDepositButtonSwitch(true);
                            card.openDiscardButtonSwitch(true);
                            card.openMoveButtonSwitch(true);
                        }
                    } else { //in turn but not in action
                        card.setIsCardFront(false);
                        card.openPlayButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openMoveButtonSwitch(false);
                    }
                } else { //not own turn
                    if (owner.isInAction()) { //in  action
                        card.setIsCardFront(true);
                        card.openPlayButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openMoveButtonSwitch(false);
                        if (card instanceof ActionCard) {
                            if (((ActionCard) card).type.equals(ActionCardType.JUST_SAY_NO)) {
                                card.openPlayButtonSwitch(true);
                            }
                        }
                    } else { //not in my turn and not in the action
                        card.setIsCardFront(false);
                        card.openPlayButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openMoveButtonSwitch(false);
                    }
                }
                card.setIsDisplayable(true);
                this.add(card);
            }
        }
    }

    private void paintHandCardsPile(Graphics g) {
        if (handCardsImage != null) {
            for (int i = 0; i < 12; i++) {
                g.drawImage(handCardsImage, cardsCoordinates[i].x, cardsCoordinates[i].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintHandCardsPile(g);
    }
}
