package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.PlayerCardsPileListener;
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

public class PlayerCardsPile extends JPanel { // This class shows the cards in the player's hand for the current turn
    public static int playerCardsPileJPanelX = 0;
    public static int playerCardsPileJPanelY = (ApplicationStart.screenHeight * 4) / 5;
    public static int playerCardsPileJPanelWidth = (ApplicationStart.screenWidth * 11) / 12;
    public static int playerCardsPileJPanelHeight = (ApplicationStart.screenHeight) / 5;
    public Card[] cardsTable;
    public Point[] cardsCoordinates;
    public ArrayList<JButton> hereButtons;
    private PlayerCardsPileListener playerCardsPileListener;
    private Player owner;
    private Image playerCardsPileImage;

    public PlayerCardsPile(Player owner) {
        this.setLayout(null);
        this.setBounds(PlayerCardsPile.playerCardsPileJPanelX, PlayerCardsPile.playerCardsPileJPanelY, PlayerCardsPile.playerCardsPileJPanelWidth, PlayerCardsPile.playerCardsPileJPanelHeight);

        this.playerCardsPileListener = new PlayerCardsPileListener();
        this.hereButtons = new ArrayList<>();
        this.owner = owner;
        this.cardsTable = new Card[12];
        loadAndSetPlayerCardsPileBackground();
        initCardsCoordinates();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            playerCardsPileImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerCardsPileBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCardsCoordinates() {
        cardsCoordinates = new Point[12];
        for (int column = 0; column < 12; column++) {
            cardsCoordinates[column] = new Point((ApplicationStart.screenWidth / 12) * column, 0);
        }
    }

    public void moveCardAndUpdateScreen(Player owner, Card movedCard, JButton hereButton) {
        Point movedCardPoint = new Point(movedCard.getX(), movedCard.getY());
        Point hereButtonPoint = new Point(hereButton.getX(), hereButton.getY());
        int movedCardIndex = 0;
        int hereButtonIndex = 0;
        for (int i = 0; i < owner.playerCardsPile.cardsCoordinates.length; i++) {
            if (movedCardPoint.x == owner.playerCardsPile.cardsCoordinates[i].x && movedCardPoint.getY() == owner.playerCardsPile.cardsCoordinates[i].y) {
                movedCardIndex = i;
            } else if (hereButtonPoint.x == owner.handCards.cardsCoordinates[i].x && hereButtonPoint.getY() == owner.playerCardsPile.cardsCoordinates[i].y) {
                hereButtonIndex = i;
            }
        }

        // Change the position of the card first
        movedCard.setBounds(hereButtonPoint.x, hereButtonPoint.y, Card.cardWidth, Card.cardHeight);
        //Then change the button position:
        hereButton.setBounds(movedCardPoint.x, movedCardPoint.y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
        //Change the position of cards in the cardsTable:
        owner.cardsTable[hereButtonIndex] = movedCard;
        owner.playerCardsPile.cardsTable[hereButtonIndex] = movedCard;
        owner.cardsTable[movedCardIndex] = null;
        owner.playerCardsPile.cardsTable[movedCardIndex] = null;
        //Hide all JButtons:
        Iterator<JButton> iterator = owner.playerCardsPile.hereButtons.iterator();
        while (iterator.hasNext()) {
            JButton button = iterator.next();
            button.setVisible(false);
            owner.playerCardsPile.remove(button); //Remove the button from JPanel
            iterator.remove(); //Remove this button from ArrayList
        }
        owner.playerCardsPile.hereButtons.clear();
        //Update screen
        paintCardsUpToEleven();
    }

    public void updateAndShowCards() { //Each call needs to clear the existing cards in the list and remove the corresponding components of the cards in the JPanel, equivalent to the refresh method of PlayerCardsPile
        this.cardsTable = owner.cardsTable; //Clone an array of players
        this.removeAll(); //Discard all the old cards (components), but this will cause the button to be lost as well
        paintCardsUpToEleven();
    }

    //-------Painting methods:

    public void addAndPaintHereButtons(Card movedCard) {
        hereButtons.clear();
        if (owner.isPlayerTurn()) { //JButtons can only be created when the player is on their turn
            for (int i = 0; i < 11; i++) {
                if (cardsTable[i] == null) {
                    JButton herebutton = new JButton("Here");
                    herebutton.setBounds(cardsCoordinates[i].x, cardsCoordinates[i].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
                    Font buttonFont = new Font("Arial", Font.BOLD, 10);
                    herebutton.setFont(buttonFont);
                    herebutton.addActionListener(playerCardsPileListener.moveButtonListener(owner, movedCard, herebutton));
                    this.add(herebutton);
                    hereButtons.add(herebutton);
                    herebutton.setVisible(true);
                }
            }
        }
    }

    private void paintCardsUpToEleven() {
        if (owner.isInAction() && owner.isPlayerTurn()) { //Both the player's turn and the player of that turn are acting
            this.setVisible(true);
        } else if (owner.isInAction() && !owner.isPlayerTurn()) { //Although the player is acting, it is not that player's turn
            this.setVisible(false);
            return;
        } else if (!owner.isInAction() && owner.isPlayerTurn()) { //The player's turn, but the player doesn't act
            this.setVisible(false);
            return;
        } else if (!owner.isInAction() && !owner.isPlayerTurn()) { //The player is not acting, nor is it that player's turn
            this.setVisible(false);
            return;
        }
        for (int i = 0; i < cardsTable.length; i++) {
            if (cardsTable[i] != null) {
                Card card = cardsTable[i];
                card.setCardJPanelBounds(cardsCoordinates[i].x, cardsCoordinates[i].y); //Reassign the Card's coordinates under the JPanel
                if (owner.isPlayerTurn()) {  //On their own turn
                    if (owner.isInAction()) {//In action
                        card.setIsCardFront(true);
                        card.openPlayButtonSwitch(true);
                        card.openDepositButtonSwitch(true);
                        card.openDiscardButtonSwitch(true);
                        card.openMoveButtonSwitch(true);
                        card.setIsDisplayable(true);
                    } else { //In their turn but not in action
                        card.setIsCardFront(false);
                        card.openPlayButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openMoveButtonSwitch(false);
                        card.setIsDisplayable(false);
                    }
                } else { //Not in their own turn
                    if (owner.isInAction()) { //In action
                        card.setIsCardFront(true);
                        card.openPlayButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openMoveButtonSwitch(false);
                        if (card instanceof ActionCard) {
                            if (((ActionCard) card).type.equals(ActionCardType.JUST_SAY_NO)) {
                                card.openPlayButtonSwitch(true);
                            } else {
                                card.openPlayButtonSwitch(false);
                            }
                        }
                        card.setIsDisplayable(false);
                    } else { //Not in their turn, not in the action
                        card.setIsCardFront(false);
                        card.openPlayButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openMoveButtonSwitch(false);
                        card.setIsDisplayable(false);
                    }
                }
                this.add(card);
            }
        }
    }

    private void paintPlayerCardsPile(Graphics g) {
        if (playerCardsPileImage != null) {
            if (owner.isPlayerTurn()) {
                if (owner.isInAction()) {
                    for (int i = 0; i < 11; i++) {
                        g.drawImage(playerCardsPileImage, cardsCoordinates[i].x, cardsCoordinates[i].y, ApplicationStart.screenWidth / 12, playerCardsPileJPanelHeight, null);
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (owner.isPlayerTurn()) {
            if (owner.isInAction()) {
                super.paintComponent(g);
                paintPlayerCardsPile(g);
            }
        }
    }
}
