/*
Railroad(4) Red Orange Yellow Green Blue LightBlue Pink Brown(2) Utility(2)
C C C C C C C C C C C B
C C C C C C C C C C C B
C C C C C C C C C C C B
C C C C C C C C C C C B
H H H H H H H H H H C B
*/
package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.CardsListener.CardListener;
import Listener.ModuleListener.PlayerAndComponentsListener.PropertyListener;
import Module.Cards.*;
import Module.Cards.CardsEnum.ActionCardType;
import Module.Cards.CardsEnum.PropertyCardType;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Property extends JPanel { //Property Class
    public Point[][] cardsCoordinates;
    public Card[][] cardsTable;
    public Map<PropertyCardType, Integer> propertyNumberMap;
    public ArrayList<JButton> hereButtons;
    private Player owner;
    public JButton closeButton;
    private PropertyListener propertyListener;
    private Image propertyBackgroundImage;

    public Property(Player owner) {
        this.setLayout(null);
        this.setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight);
        this.setVisible(false);

        this.propertyNumberMap = new HashMap<>();
        this.hereButtons = new ArrayList<>();
        this.cardsTable = new Card[5][11];
        this.owner = owner;
        this.propertyListener = new PropertyListener();
        loadAndSetPlayerCardsPileBackground();
        initButtons();
        initCardsCoordinates();
        updatePropertiesNumber();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // 从文件中读取背景图片
            propertyBackgroundImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerComponentsBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        closeButton = new JButton("Close");
        this.add(closeButton); // Add the close button to the JPanel
        closeButton.setBounds(11 * ApplicationStart.screenWidth / 12, 0, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight); // This value may need to be adjusted to put the close button in place
        closeButton.addActionListener(propertyListener.closeButtonListener(owner, this));
    }

    private void initCardsCoordinates() {
        cardsCoordinates = new Point[5][11];
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                cardsCoordinates[row][column] = new Point((ApplicationStart.screenWidth / 12) * column, row * ApplicationStart.screenHeight / 5);
            }
        }
    }

    private void setCardBounds(Card card, int x, int y, boolean isDisplayable, boolean isCardFront) {
        card.setCardJPanelBounds(x, y); // Reassign the Card to its coordinates under the JPanel
        card.setIsDisplayable(isDisplayable);
        card.setIsCardFront(isCardFront);
    }

    public boolean containsCard(Card card) {
        boolean flag = false;
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] == card) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public int calculateTotalAssetsInProperty() { // Calculate the total assets of the property
        int totalAssets = 0;
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (this.cardsTable[row][column] != null) {
                    totalAssets = totalAssets + this.cardsTable[row][column].value;
                }
            }
        }
        return totalAssets;
    }

    public boolean whetherHasPropertyCards(RentCard rentCard) { // Check if the Property is empty, or if it is not empty but there is no property available for rent collection
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (this.cardsTable[row][column] instanceof PropertyCard || this.cardsTable[row][column] instanceof PropertyWildCard) {
                    if (rentCard.whetherRentCardCanBeUsed(this.cardsTable[row][column])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void updatePropertiesNumber() { // Update the number of properties for each type of player
        for (PropertyCardType propertyType : PropertyCardType.values()) {
            propertyNumberMap.put(propertyType, 0);
        }
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    Card property = cardsTable[row][column];
                    if (property instanceof PropertyCard) {
                        for (PropertyCardType propertyType : PropertyCardType.values()) {
                            if (((PropertyCard) property).type.equals(propertyType)) {
                                propertyNumberMap.put(propertyType, propertyNumberMap.get(propertyType) + 1);
                            }
                        }
                    } else if (property instanceof PropertyWildCard) {
                        for (PropertyCardType propertyType : PropertyCardType.values()) {
                            if (((PropertyWildCard) property).currentType.equals(propertyType)) {
                                propertyNumberMap.put(propertyType, propertyNumberMap.get(propertyType) + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    //------- Methods of calculating the rent charged by RentCard for the property:

    public int calculatedRent(Card propertyCard, boolean isDoubleRent) { //Given the type of property card and whether the rent has doubled, calculate the final total rent
        PropertyCardType searchedType = null;
        int cardsNumber = 0;
        int totalRent = 0;
        ArrayList<Integer> columns = new ArrayList<>();
        if (propertyCard instanceof PropertyCard) {
            searchedType = ((PropertyCard) propertyCard).type;
        } else if (propertyCard instanceof PropertyWildCard) {
            searchedType = ((PropertyWildCard) propertyCard).currentType;
        }
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] instanceof PropertyCard || cardsTable[row][column] instanceof PropertyWildCard) {
                    if (cardsTable[row][column] instanceof PropertyCard) {
                        if (searchedType.equals(((PropertyCard) cardsTable[row][column]).type)) {
                            cardsNumber++;
                            columns.add(column);
                        }
                    }
                    if (cardsTable[row][column] instanceof PropertyWildCard) {
                        if (searchedType.equals(((PropertyWildCard) cardsTable[row][column]).currentType)) {
                            cardsNumber++;
                            columns.add(column);
                        }
                    }
                }
            }
        }
        totalRent = RentCard.obtainRentNumber(searchedType, cardsNumber) + calculateExtraRent(searchedType, cardsNumber, columns);
        if (isDoubleRent) {
            return totalRent * 2;
        } else {
            return totalRent;
        }
    }

    private int calculateExtraRent(PropertyCardType propertyType, int propertyNumber, ArrayList<Integer> columns) {
        int extraRent = 0;
        if (propertyNumber >= PropertyCard.judgeCompleteSetNumber(propertyType)) {
            for (Integer column : columns) {
                boolean flag = false;
                for (int row = 0; row < 5; row++) {
                    if (cardsTable[row][column] != null) {
                        if (cardsTable[row][column] instanceof ActionCard) {
                            ActionCard card = (ActionCard) cardsTable[row][column];
                            if (card.type.equals(ActionCardType.HOTEL)) {
                                extraRent = 4;
                                flag = true;
                                break;
                            } else if (card.type.equals(ActionCardType.HOUSE)) {
                                extraRent = 3;
                                flag = true;
                                break;
                            }
                        }
                    }
                }
                if (flag) {
                    break;
                }
            }
        }
        return extraRent;
    }

    //------- Methods for adding and removing temporary buttons for Property:

    public void addAndPaintSwapPropertyButtons() { // Create a select button for selecting the property you want to exchange
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    JButton stealButton = new JButton("Swap");
                    stealButton.setBounds(1 * Card.cardWidth / 5, 0, 3 * Card.cardWidth / 5, Card.cardHeight / 8);
                    Font buttonFont = new Font("Arial", Font.BOLD, 8);
                    stealButton.setFont(buttonFont); // 设置按钮的字体和字体大小
                    stealButton.addActionListener((new CardListener()).swapPropertyButtonListener(owner, cardsTable[row][column]));
                    cardsTable[row][column].add(stealButton);
                    stealButton.setVisible(true);
                }
            }
        }
    }

    public void hideAndRemoveSwapButtons() {
        // Remove buttons from existing cards in the property first
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    int lastIndex = cardsTable[row][column].getComponentCount() - 1;
                    cardsTable[row][column].getComponent(lastIndex).setVisible(false);
                    cardsTable[row][column].remove(lastIndex);
                }
            }
        }
        //Then delete the button on the card that was added to the mortgage array:
        if (owner.pledgeCardFromProperty.size() > 0) {
            for (Card card : owner.pledgeCardFromProperty) {
                int lastIndex = card.getComponentCount() - 1;
                card.getComponent(lastIndex).setVisible(false);
                card.remove(lastIndex);
            }
        }
    }

    public void addAndPaintStealWholePropertyButtons() { // Create a select button for selecting the property you want to steal
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    JButton stealButton = new JButton("Steal");
                    stealButton.setBounds(1 * Card.cardWidth / 5, 0, 3 * Card.cardWidth / 5, Card.cardHeight / 8);
                    Font buttonFont = new Font("Arial", Font.BOLD, 8);
                    stealButton.setFont(buttonFont);
                    stealButton.addActionListener((new CardListener()).stealWholePropertyButtonListener(owner, cardsTable[row][column]));
                    cardsTable[row][column].add(stealButton);
                    stealButton.setVisible(true);
                }
            }
        }
    }

    public void addAndPaintStealSinglePropertyButtons() { // Create a select button for selecting the property you want to steal
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    JButton stealButton = new JButton("Steal");
                    stealButton.setBounds(1 * Card.cardWidth / 5, 0, 3 * Card.cardWidth / 5, Card.cardHeight / 8);
                    Font buttonFont = new Font("Arial", Font.BOLD, 8);
                    stealButton.setFont(buttonFont);
                    stealButton.addActionListener((new CardListener()).stealSinglePropertyButtonListener(owner, cardsTable[row][column]));
                    cardsTable[row][column].add(stealButton);
                    stealButton.setVisible(true);
                }
            }
        }

    }

    public void hideAndRemoveStolenButtons(Player stolenPlayer) {
        // Remove buttons from existing cards in the property first
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    int lastIndex = cardsTable[row][column].getComponentCount() - 1;
                    cardsTable[row][column].getComponent(lastIndex).setVisible(false);
                    cardsTable[row][column].remove(lastIndex);
                }
            }
        }
        // Then delete the button on the card that was added to the mortgage array:
        if (stolenPlayer.pledgeCardFromProperty.size() > 0) {
            for (Card card : stolenPlayer.pledgeCardFromProperty) {
                int lastIndex = card.getComponentCount() - 1;
                card.getComponent(lastIndex).setVisible(false);
                card.remove(lastIndex);
            }
        }
    }

    public void addAndPaintPledgeButtons(int totalRent) { // Create a select button for selecting mortgage debt
        if (owner.isInAction()) {
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 11; column++) {
                    if (cardsTable[row][column] != null) {
                        JButton pledgeButton = new JButton("Pledge");
                        pledgeButton.setBounds(1 * Card.cardWidth / 5, 0, 3 * Card.cardWidth / 5, Card.cardHeight / 8);
                        Font buttonFont = new Font("Arial", Font.BOLD, 8);
                        pledgeButton.setFont(buttonFont);
                        pledgeButton.addActionListener((new CardListener()).pledgeButtonListener(owner, totalRent, cardsTable[row][column], true));
                        cardsTable[row][column].add(pledgeButton);
                        pledgeButton.setVisible(true);
                    }
                }
            }
        }
    }

    public void hideAndRemovePledgeButtons(Player debtor) { // This may cause a BUG: The latest PledgeButtons added in PropertyCard and PropertyWildCard are removed
        // Remove buttons from existing cards in the property first
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    int lastIndex = cardsTable[row][column].getComponentCount() - 1;
                    cardsTable[row][column].getComponent(lastIndex).setVisible(false);
                    cardsTable[row][column].remove(lastIndex);
                }
            }
        }
        // Then delete the button on the card that was added to the mortgage array:
        if (debtor.pledgeCardFromProperty.size() > 0) {
            for (Card card : debtor.pledgeCardFromProperty) {
                int lastIndex = card.getComponentCount() - 1;
                card.getComponent(lastIndex).setVisible(false);
                card.remove(lastIndex);
            }
        }
    }

    public void addAndPaintChooseButtons(RentCard rentCard, boolean isDoubleRent) { // This select button is used to select the property that RentCard wants to rent
        if (owner.isPlayerTurn()) { // JButtons can only be created when the player is in their turn
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 11; column++) {
                    if (cardsTable[row][column] instanceof PropertyCard || cardsTable[row][column] instanceof PropertyWildCard) {
                        JButton chosenButton = new JButton("Use");
                        chosenButton.setBounds(1 * Card.cardWidth / 5, 0, 3 * Card.cardWidth / 5, Card.cardHeight / 8);
                        Font buttonFont = new Font("Arial", Font.BOLD, 10);
                        chosenButton.setFont(buttonFont);
                        chosenButton.addActionListener((new CardListener()).chosenButtonListener(owner, cardsTable[row][column], rentCard, isDoubleRent));
                        cardsTable[row][column].add(chosenButton);
                        chosenButton.setVisible(true);
                    }
                }
            }
        }
    }

    public void hideAndRemoveChooseButtons() {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] instanceof PropertyCard || cardsTable[row][column] instanceof PropertyWildCard) {
                    int lastIndex = cardsTable[row][column].getComponentCount() - 1;
                    cardsTable[row][column].getComponent(lastIndex).setVisible(false);
                    cardsTable[row][column].remove(lastIndex);
                }
            }
        }
    }

    public void addAndPaintHereButtons(Card movedCard) {// After the Player in his turn calls the Card's Move button, HereButtons are created to assist the Card's move
        hereButtons.clear();
        if (owner.isPlayerTurn()) {
            for (int row = 0; row < 5; row++) {
                for (int column = 0; column < 11; column++) {
                    if (cardsTable[row][column] == null) {
                        JButton hereButton = new JButton("Here");
                        hereButton.setBounds(cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
                        Font buttonFont = new Font("Arial", Font.BOLD, 10);
                        hereButton.setFont(buttonFont);
                        hereButton.addActionListener(propertyListener.moveButtonListener(owner, movedCard, hereButton));
                        this.add(hereButton);
                        hereButtons.add(hereButton);
                        hereButton.setVisible(true);
                    }
                }
            }
        }
    }

    public void moveCardAndUpdateScreen(Player owner, Card movedCard, JButton hereButton) { // Implement the card movement effect
        Point movedCardPoint = new Point(movedCard.getX(), movedCard.getY());
        Point hereButtonPoint = new Point(hereButton.getX(), hereButton.getY());
        Point movedCardIndex = new Point();
        Point hereButtonIndex = new Point();
        // First find the position of the moved card and the vacancy in the two-dimensional array
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (movedCardPoint.x == owner.property.cardsCoordinates[row][column].x && movedCardPoint.getY() == owner.property.cardsCoordinates[row][column].y) {
                    movedCardIndex.setLocation(row, column);
                } else if (hereButtonPoint.x == owner.property.cardsCoordinates[row][column].x && hereButtonPoint.getY() == owner.property.cardsCoordinates[row][column].y) {
                    hereButtonIndex.setLocation(row, column);
                }
            }
        }
        // Change the position of the card first
        movedCard.setBounds(hereButtonPoint.x, hereButtonPoint.y, Card.cardWidth, Card.cardHeight);
        // Then change the button position:
        hereButton.setBounds(movedCardPoint.x, movedCardPoint.y, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5);
        // Change the position of cards in the bank:
        owner.property.cardsTable[hereButtonIndex.x][hereButtonIndex.y] = movedCard;
        owner.property.cardsTable[movedCardIndex.x][movedCardIndex.y] = null;
        // Hide all Jbuttons:
        Iterator<JButton> iterator = owner.property.hereButtons.iterator();
        while (iterator.hasNext()) {
            JButton button = iterator.next();
            button.setVisible(false);
            owner.property.remove(button); // Remove the button from JPanel
            iterator.remove(); // Remove this button from ArrayList
        }
        owner.property.hereButtons.clear();
        // Update the screen
        reallocateAllCards();
    }

    //------- How to place and take out the property:

    public Card removeCardFromProperty(Card removedCard) {
        for (int row = 0; row < 5; row++) {
            boolean flag = false;
            for (int column = 0; column < 11; column++) {
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
        updatePropertiesNumber();
        return removedCard;
    }

    public void placePropertyCardAndShowTable(Card card) { // Discharge the new Card into the Property in sequence
        card.owner = this.owner;
        // First store the card in the container:
        for (int row = 0; row < 4; row++) {
            boolean flag = false;
            for (int column = 0; column < 11; column++) {
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
        reallocateAllCards(); // Reassign coordinates and visualizations for all cards
        this.add(card); // Add cards to JPanel
        updatePropertiesNumber();
    }

    //------- Painting Methods:

    public void reallocateAllCards() {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 11; column++) {
                if (cardsTable[row][column] != null) {
                    Card card = cardsTable[row][column];
                    setCardBounds(card, cardsCoordinates[row][column].x, cardsCoordinates[row][column].y, true, true);
                    // Management card switch
                    if (owner.isPlayerTurn()) {
                        card.openMoveButtonSwitch(true);
                        card.openDiscardButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openPlayButtonSwitch(false);
                        if (card instanceof PropertyWildCard) {
                            // Turn on the switch for the two-color property card
                            ((PropertyWildCard) card).openReverseButtonSwitch(true);
                        }
                    } else {
                        card.openMoveButtonSwitch(false);
                        card.openDiscardButtonSwitch(false);
                        card.openDepositButtonSwitch(false);
                        card.openPlayButtonSwitch(false);
                        if (card instanceof PropertyWildCard) {
                            // Turn off switch for two-color real estate card
                            ((PropertyWildCard) card).openReverseButtonSwitch(false);
                        }
                    }
                }
            }
        }
    }

    private void paintPropertyPile(Graphics g) {
        if (propertyBackgroundImage != null) {
            for (int i = 0; i < 11; i++) {
                g.drawImage(propertyBackgroundImage, (ApplicationStart.screenWidth / 12) * i, 0, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(propertyBackgroundImage, (ApplicationStart.screenWidth / 12) * i, 1 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(propertyBackgroundImage, (ApplicationStart.screenWidth / 12) * i, 2 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(propertyBackgroundImage, (ApplicationStart.screenWidth / 12) * i, 3 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(propertyBackgroundImage, (ApplicationStart.screenWidth / 12) * i, 4 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
            }
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        paintPropertyPile(g);
    }
}
