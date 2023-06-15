package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.CardsListener.CardListener;
import Listener.ModuleListener.PlayerAndComponentsListener.BankListener;
import Module.Cards.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Bank extends JPanel {
    public Point[][] cardsCoordinates;
    public Card[][] cardsTable;
    public ArrayList<JButton> hereButtons;
    private Player owner;
    private JButton closeButton; // add a new close button
    private BankListener bankListener;
    private Image bankImage;

    public Bank(Player owner) {
        this.setLayout(null); //need to set the button position and size by hands 
        this.setBounds(0, ApplicationStart.screenHeight / 5 - (ApplicationStart.screenHeight / 25), ApplicationStart.screenWidth, 3 * ApplicationStart.screenHeight / 5 + (ApplicationStart.screenHeight / 25)); // 设置提示框的位置和大小
        this.setVisible(false); // 初始时设为不可见

        this.hereButtons = new ArrayList<>();
        this.cardsTable = new Card[3][12];
        this.owner = owner;
        this.bankListener = new BankListener();
        loadAndSetPlayerCardsPileBackground();
        initButtons();
        initCardsCoordinates();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
           // Load the background image from a file
            bankImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerComponentsBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        closeButton = new JButton("Close");
        this.add(closeButton); //add the close button into the Jpanel 
        closeButton.setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight / 25); // 这个值可能需要调整，以便将关闭按钮放在适当的位置
        closeButton.addActionListener(bankListener.closeButtonListener(owner, this));
    }

    private void initCardsCoordinates() {
        cardsCoordinates = new Point[3][12];
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                cardsCoordinates[row][column] = new Point((ApplicationStart.screenWidth / 12) * column, ApplicationStart.screenHeight / 25 + row * ApplicationStart.screenHeight / 5);
            }
        }
    }

    private void setCardBounds(Card card, int x, int y, boolean isDisplayable, boolean isCardFront) {
        card.setCardJPanelBounds(x, y); //Reassign the Card its coordinates under the JPanel
        card.setIsDisplayable(isDisplayable);
        card.setIsCardFront(isCardFront);
    }

    public boolean containsCard(Card card) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                if (cardsTable[row][column] == card) {
                    return true;
                }
            }
        }
        return false;
    }

    public int calculateTotalAssetsInBank() { //Calculate the total assets of the bank
        int totalAssets = 0;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                if (this.cardsTable[row][column] != null) {
                    totalAssets = this.cardsTable[row][column].value + totalAssets;
                }
            }
        }
        return totalAssets;
    }

    //-------To add and remove temporary buttons from the Bank:

    public void addAndPaintPledgeButtons(int totalRent) { //Create a selection button to select mortgage debt
        if (owner.isInAction()) {
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 12; column++) {
                    if (cardsTable[row][column] != null) {
                        JButton pledgeButton = new JButton("Pledge");
                        pledgeButton.setBounds(1 * Card.cardWidth / 5, 0, 3 * Card.cardWidth / 5, Card.cardHeight / 8);
                        Font buttonFont = new Font("Arial", Font.BOLD, 8);
                        pledgeButton.setFont(buttonFont); // Sets the font and font size of the button
                        pledgeButton.addActionListener((new CardListener()).pledgeButtonListener(owner, totalRent, cardsTable[row][column], false));
                        cardsTable[row][column].add(pledgeButton);
                        pledgeButton.setVisible(true);
                    }
                }
            }
        }
    }

    public void hideAndRemovePledgeButtons(Player debtor) { //Possible BUG: Remove the newly added PledgeButtons from PropertyCard and PropertyWildCard
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                if (cardsTable[row][column] != null) {
                    int lastIndex = cardsTable[row][column].getComponentCount() - 1;
                    cardsTable[row][column].getComponent(lastIndex).setVisible(false);
                    cardsTable[row][column].remove(lastIndex);
                }
            }
        }
        //Then remove the button from the card that was added to the mortgage array:
        if (debtor.pledgeCardFromBank.size() > 0) {
            for (Card card : debtor.pledgeCardFromBank) {
                int lastIndex = card.getComponentCount() - 1;
                card.getComponent(lastIndex).setVisible(false);
                card.remove(lastIndex);
            }
        }
    }

    public void addAndPaintHereButtons(Card movedCard) { ///HereButton:It is used to realize the free movement of the Card in the Bank
        //The Here button will only be created if the player is on their turn and uses the Move button on the Card
        hereButtons.clear();
        if (owner.isPlayerTurn()) {
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 12; column++) {
                    if (cardsTable[row][column] == null) {
                        JButton herebutton = new JButton("Here");
                        herebutton.setBounds(cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
                        Font buttonFont = new Font("Arial", Font.BOLD, 10);
                        herebutton.setFont(buttonFont); // Sets the font and font size of the button
                        herebutton.addActionListener(bankListener.moveButtonListener(owner, movedCard, herebutton));
                        this.add(herebutton);
                        hereButtons.add(herebutton);
                        herebutton.setVisible(true);
                    }
                }
            }
        }
    }

    public void moveCardAndUpdateScreen(Player owner, Card movedCard, JButton hereButton) { //实现Card的移动效果
        Point movedCardPoint = new Point(movedCard.getX(), movedCard.getY());
        Point hereButtonPoint = new Point(hereButton.getX(), hereButton.getY());
        Point movedCardIndex = new Point();
        Point hereButtonIndex = new Point();
        //First, we find the position of the moved card and the slot in the two-dimensional array
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                if (movedCardPoint.x == owner.bank.cardsCoordinates[row][column].x && movedCardPoint.getY() == owner.bank.cardsCoordinates[row][column].y) {
                    movedCardIndex.setLocation(row, column);
                } else if (hereButtonPoint.x == owner.bank.cardsCoordinates[row][column].x && hereButtonPoint.getY() == owner.bank.cardsCoordinates[row][column].y) {
                    hereButtonIndex.setLocation(row, column);
                }
            }
        }
        //Change the position of the cards first
        movedCard.setBounds(hereButtonPoint.x, hereButtonPoint.y, Card.cardWidth, Card.cardHeight);
        //Change the button's position again::
        hereButton.setBounds(movedCardPoint.x, movedCardPoint.y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
        //Change the position of cards in the bank:
        owner.bank.cardsTable[hereButtonIndex.x][hereButtonIndex.y] = movedCard;
        owner.bank.cardsTable[movedCardIndex.x][movedCardIndex.y] = null;
        //Hide all JButtons:
        Iterator<JButton> iterator = owner.bank.hereButtons.iterator();
        while (iterator.hasNext()) {
            JButton button = iterator.next();
            button.setVisible(false);
            owner.bank.remove(button); //Hide botton JButtons
            iterator.remove(); //Remove the button from the ArrayList
        }
        owner.bank.hereButtons.clear();
        //update the screen
        paintAllCardsFront();
    }

    //-------How to deposit and withdraw money:

    public Card removeCardFromBank(Card removedCard) {
        for (int row = 0; row < 3; row++) {
            boolean flag = false;
            for (int column = 0; column < 12; column++) {
                if (cardsTable[row][column] == removedCard) {
                    cardsTable[row][column] = null;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        this.remove(removedCard);
        return removedCard;
    }

    public void saveMoneyAndShowCards(Card card) {//Each call will add money to the bank and refresh the Card's rendering state
        card.owner = this.owner;
        //First, store the cards in the container:
        for (int row = 0; row < 3; row++) {
            boolean flag = false;
            for (int column = 0; column < 12; column++) {
                if (cardsTable[row][column] == null) {
                    cardsTable[row][column] = card;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
        }
        //Reassign coordinates and set states for all cards
        paintAllCardsFront();
        this.add(card);
    }

    //-------Drawing method:

    public void paintAllCardsFront() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 12; column++) {
                if (cardsTable[row][column] != null) {
                    Card card = cardsTable[row][column];
                    setCardBounds(card, cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, true, true);
                    if (owner.isPlayerTurn()) {
                        card.openMoveButtonSwitch(true);
                        card.openPlayButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                    } else {
                        card.openMoveButtonSwitch(false);
                        card.openPlayButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                    }
                }
            }
        }
    }

    private void paintBankPile(Graphics g) {
        if (bankImage != null) {
            for (int row = 0; row < 3; row++) {
                for (int column = 0; column < 12; column++) {
                    g.drawImage(bankImage, cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintBankPile(g);
    }
}