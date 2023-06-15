package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.PlayerListener;
import Module.Cards.*;
import Module.Cards.CardsEnum.ActionCardType;
import Module.Cards.CardsEnum.PropertyCardType;
import Module.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends JPanel {
    public static int playerHeight = (ApplicationStart.screenHeight) / 5;
    public static int playerWidth = (ApplicationStart.screenWidth) / 12;
    public static Image[] images = new Image[5];

    public String name;
    public Card[] cardsTable;
    public ArrayList<Card> oneTurnCardsBuffer; //Used for the cards used in each of the player's actions in that turn
    public ArrayList<Card> singleActionCardsBuffer; //Used to store the cards used in an action, where the card at index=0 is the card used first in the action
    public ArrayList<Player> interactivePlayers;
    public ArrayList<Card> pledgeCardFromBank;
    public ArrayList<Card> pledgeCardFromProperty;
    public int playerJPanelX;
    public int playerJPanelY;
    public int actionNumber = 3; //num of action
    public int debt = 0;
    public boolean whetherViewComponent = false;
    private boolean isInAction = false;
    private boolean isPlayerTurn = false;

    private Image playerImage;
    public Bank bank; //player bank
    public Property property; //player property
    public HandCards handCards; //player handscard
    public PlayerCardsPile playerCardsPile; //player card pile
    private PlayerListener playerListener;
    public JButton handCardsButton;
    public JButton bankButton;
    public JButton propertyButton;
    public JButton skipButton;
    public JButton sayNoButton;
    public JButton abandonSayNoButton;


    static {
        loadAndSetPlayerImage();
    }

    private static void loadAndSetPlayerImage() {
        for (int i = 0; i < 5; i++) {
            try {
                Player.images[i] = ImageIO.read(new File("images/Module/PlayerAndComponents/Player/player" + (i + 1) + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Player(String name, Image playerImage, int playerJPanelX, int playerJPanelY) {
        this.setLayout(null); // The position and size of each component need to be set manually
        setOpaque(false);

        this.name = name;
        this.playerImage = playerImage;
        this.playerJPanelX = playerJPanelX;
        this.playerJPanelY = playerJPanelY;
        this.cardsTable = new Card[12];
        this.oneTurnCardsBuffer = new ArrayList<>();
        this.singleActionCardsBuffer = new ArrayList<>();
        this.pledgeCardFromBank = new ArrayList<>();
        this.pledgeCardFromProperty = new ArrayList<>();
        this.playerCardsPile = new PlayerCardsPile(this);
        this.handCards = new HandCards(this);
        this.bank = new Bank(this);
        this.property = new Property(this);
        this.interactivePlayers = new ArrayList<>();
        this.playerListener = new PlayerListener();

        initButtons();
        this.setBounds(this.playerJPanelX, this.playerJPanelY, playerWidth, playerHeight); // 设置Player的大小和位置
    }

    private void initButtons() {
        bankButton = createButton(10, "B", 0, playerHeight * 3 / 4, playerWidth / 3, playerHeight / 4, this.playerListener.bankButtonListener(this));
        handCardsButton = createButton(10, "C", playerWidth / 3, playerHeight * 3 / 4, playerWidth / 3, playerHeight / 4, this.playerListener.handCardsButtonListener(this));
        propertyButton = createButton(10, "P", playerWidth * 2 / 3, playerHeight * 3 / 4, playerWidth / 3, playerHeight / 4, this.playerListener.propertyButtonListener(this));
        skipButton = createButton(10, "S", playerWidth * 2 / 3, 0, playerWidth / 3, playerHeight / 4, this.playerListener.skipButtonListener(this));
        sayNoButton = createButton(8, "Say No", 0, playerHeight * 2 / 4, playerWidth / 2, playerHeight / 4, this.playerListener.sayNoButtonListener(this));
        abandonSayNoButton = createButton(8, "Waive", playerWidth / 2, playerHeight * 2 / 4, playerWidth / 2, playerHeight / 4, null);

        this.add(handCardsButton);
        this.add(bankButton);
        this.add(propertyButton);
        this.add(skipButton);
        this.add(sayNoButton);
        this.add(abandonSayNoButton);
        sayNoButton.setVisible(false);
        abandonSayNoButton.setVisible(false);
    }

    private JButton createButton(int fontSize, String text, int x, int y, int width, int height, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        Font buttonFont = new Font("Arial", Font.BOLD, fontSize); // 设置形状为粗体，大小为10的Arial字体
        button.setFont(buttonFont); // Sets the font and font size of the button
        button.addActionListener(listener);
        return button;
    }

    public void moveToNextTurn() {
        this.oneTurnCardsBuffer.clear();
        this.interactivePlayers.clear();
        this.singleActionCardsBuffer.clear();
        this.debt = 0;
        this.actionNumber = 3;
        handCards.updateAndShowCards();
        if (this.isPlayerTurn) {
            Timer timer = new Timer(3000, e -> {
                isInAction = true;
                playerCardsPile.updateAndShowCards();
            });
            timer.setRepeats(false);  // Let the timer run only once
            timer.start();
        } else {
            isInAction = false;
        }
    }

    public void drawCards(Card[] cards) { //Draw cards
        for (int i = 0, j = 0; i < cardsTable.length; i++) {
            if (cardsTable[i] == null) {
                cards[j].owner = this;
                cardsTable[i] = cards[j];
                j = j + 1;
                if (j >= cards.length) {
                    break;
                }
            }
        }
    }

    //-------Here are some ways to determine the cards a Player has::

    public boolean containsCard(Card card) {
        boolean flag = false;
        for (int column = 0; column < 12; column++) {
            if (cardsTable[column] == card) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public int numberOfHandCards() {
        int counter = 0;
        for (int i = 0; i < cardsTable.length; i++) {
            if (cardsTable[i] != null) {
                counter++;
            }
        }
        return counter;
    }

    public boolean whetherHasSayNoCard() {
        for (int i = 0; i < cardsTable.length; i++) {
            if (cardsTable[i] != null) {
                if (cardsTable[i] instanceof ActionCard) {
                    if (((ActionCard) cardsTable[i]).type.equals(ActionCardType.JUST_SAY_NO)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean whetherPlayerHasSingleProperty() { // Determines if the player owns a separate property (determines if it is part of a set of properties)
        for (PropertyCardType propertyType : PropertyCardType.values()) {
            if ((this.property.propertyNumberMap.get(propertyType) > 0 && this.property.propertyNumberMap.get(propertyType) < PropertyCard.judgeCompleteSetNumber(propertyType)) || this.property.propertyNumberMap.get(propertyType) > PropertyCard.judgeCompleteSetNumber(propertyType)) {
                return true;
            }
        }
        return false;
    }

    public boolean whetherPlayerHasWholeProperty() { //Determine if the player owns a full set of properties
        for (PropertyCardType propertyType : PropertyCardType.values()) {
            if (this.property.propertyNumberMap.get(propertyType) >= PropertyCard.judgeCompleteSetNumber(propertyType)) { //如果有一套完整的房产
                return true;
            }
        }
        return false;
    }

 //------- methods responding to ActionCard and RentCard:

// When TODO inTurnPlayer uses sayNoCard, the other cards in the hand still have Play,discard, etc buttons and can still be used   
 //// Respond to sayNoCard
    public void respondSayNoCard() {
        this.setIsInAction(true); //Set the player who responds to the SayNoCard to be free to act
        //Turn on the sayNo card: Let the player choose whether to use the sayNo card or not:
        this.sayNoButton.setVisible(true);
        this.abandonSayNoButton.setVisible(true);
        if (this.isPlayerTurn()) {
            //Close the player's deck and hand to avoid bugs when players directly use sayNo cards:
            this.playerCardsPile.setVisible(false);
            this.handCardsButton.setVisible(false);
            abandonSayNoButton.addActionListener(playerListener.inTurnPlayerAbandonSayNoButtonListener(this));
        } else { //If it's a regular player who responds to InTurnPlayer
            //Close the player's hand to avoid bugs when the player directly uses the sayNO card:
            this.handCardsButton.setVisible(false);
            //Nothing needs to be done, because the Listener on abandonSayNoButton hasn't been cleared yet, so clicking on abandonSayNoButton will directly trigger the surrender of rent/property, and so on
        }
    }

    //Add Use buttons to the player's house and cards in the bank, and set the player's debt to totalRent
    public void payForMoney(Player renter, int totalRent) { //The renter is the player in the current turn and the renter
        if (this.bank.calculateTotalAssetsInBank() == 0 && this.property.calculateTotalAssetsInProperty() == 0) { //Nothing. Can't pay your debts
            //Remove itself from the adversary's interaction queue::
            renter.interactivePlayers.remove(0);
            this.setIsInAction(false);
            //Determine the opponent's next move
            if (renter.interactivePlayers.size() > 0) { //The opponent has to continue to interact with other players:
                renter.interactivePlayers.get(0).setIsInAction(true);
                if (renter.singleActionCardsBuffer.size() > 1) {
                    Card tempCard = renter.singleActionCardsBuffer.get(0);
                    renter.singleActionCardsBuffer.clear();
                    renter.singleActionCardsBuffer.add(tempCard);
                }
                renter.interactivePlayers.get(0).payForMoney(renter, totalRent);
            } else { //The action of the opponent is all over
                Timer timer = new Timer(3000, a -> {
                    //Delay for two seconds before showing the current player's card
                    renter.setIsInAction(true);
                    renter.singleActionCardsBuffer.clear();
                    renter.interactivePlayers.clear();
                    renter.bank.paintAllCardsFront();
                    renter.property.reallocateAllCards();
                    renter.handCards.updateAndShowCards();
                    renter.playerCardsPile.updateAndShowCards();

                });
                timer.setRepeats(false); // make sure the timer only runs once
                timer.start(); // start the timer
            }

        } else { //The player still has cards in the bank or house that can be repaid
            this.interactivePlayers.add(renter);
            this.debt = totalRent;
            //Hide the hand button switch to avoid bugs (hide hands for the first time):
            this.handCardsButton.setVisible(false);
            //Toggle the sayNo button: Let the player choose whether to use the sayNo card or not:
            this.sayNoButton.setVisible(true);
            this.abandonSayNoButton.setVisible(true);
            abandonSayNoButton.addActionListener(playerListener.abandonSayNoAndPayForMoneyButtonListener(this, totalRent));
        }
    }

    public void payForProperty(Player propertyThief) {
        this.setIsInAction(true);
        this.interactivePlayers.add(propertyThief);
        //Hide the hand button switch to avoid bugs (hide hands for the first time):
        this.handCardsButton.setVisible(false);
        //Toggle the sayNo button: Let the player choose whether to use the sayNo card or not:
        this.sayNoButton.setVisible(true);
        this.abandonSayNoButton.setVisible(true);
        abandonSayNoButton.addActionListener(playerListener.abandonSayNoAndPayForSinglePropertyButtonListener(this));
    }

    public void swapProperty(Player inTurnPlayer) { //交换房产
        this.setIsInAction(true);
        this.interactivePlayers.add(inTurnPlayer);
        //Hide the hand button switch to avoid bugs (hide hands for the first time):
        this.handCardsButton.setVisible(false);
        //Toggle the sayNo button: Let the player choose whether to use the sayNo card or not:
        this.sayNoButton.setVisible(true);
        this.abandonSayNoButton.setVisible(true);
        abandonSayNoButton.addActionListener(playerListener.abandonSayNoAndAcceptSwapPropertyButtonListener(this));
    }

    public void payForWholeProperty(Player propertyBreaker) {
        this.setIsInAction(true);
        this.interactivePlayers.add(propertyBreaker);
        //Hide the hand button switch to avoid bugs (hide hands for the first time):
        this.handCardsButton.setVisible(false);
        //Toggle the sayNo button: Let the player choose whether to use the sayNo card or not:
        this.sayNoButton.setVisible(true);
        this.abandonSayNoButton.setVisible(true);
        abandonSayNoButton.addActionListener(playerListener.abandonSayNoAndPayForWholePropertyButtonListener(this));
    }

    //-------To add or remove a temporary button::

    public void addAndPaintDealBreakerChooseButtons(Player breaker) { //When certain cards need to specify which player to use, create a choose button for each player
        ArrayList<Player> playersWhoHasTempButton = new ArrayList<>();
        for (Player player : Game.players) {
            if (player != breaker) {
                if (player.whetherPlayerHasWholeProperty()) { //If the player owns a whole set of properties
                    playersWhoHasTempButton.add(player);
                    JButton chosenButton = new JButton("↓");
                    chosenButton.setBounds(Player.playerWidth / 3, Player.playerHeight / 3, playerWidth / 3, playerHeight / 4);
                    Font buttonFont = new Font("Arial", Font.BOLD, 10);
                    chosenButton.setFont(buttonFont); // Sets the font and font size of the button
                    chosenButton.addActionListener(playerListener.dealBreakerChooseButtonListener(breaker, player, playersWhoHasTempButton));
                    player.add(chosenButton);
                    chosenButton.setVisible(true);
                }
            }
        }
    }

    public void hideAndRemoveDealBreakerChooseButtons(ArrayList<Player> playersWhoHasTempButton) {
        for (Player player : playersWhoHasTempButton) {
            int lastIndex = player.getComponentCount() - 1;
            player.getComponent(lastIndex).setVisible(false);
            player.remove(lastIndex);
        }
    }

    public void addAndPaintForcedDealChooseButtons(Player inTurnPlayer) { //When certain cards need to specify which player to use, create a choose button for each player
        ArrayList<Player> playersWhoHasTempButton = new ArrayList<>();
        for (Player player : Game.players) {
            if (player != inTurnPlayer) {
                if (player.whetherPlayerHasSingleProperty()) { //If the player has an incomplete set of properties
                    playersWhoHasTempButton.add(player);
                    JButton chosenButton = new JButton("↓");
                    chosenButton.setBounds(Player.playerWidth / 3, Player.playerHeight / 3, playerWidth / 3, playerHeight / 4);
                    Font buttonFont = new Font("Arial", Font.BOLD, 10);
                    chosenButton.setFont(buttonFont); // Sets the font and font size of the button
                    chosenButton.addActionListener(playerListener.forcedDealChooseButtonListener(inTurnPlayer, player, playersWhoHasTempButton));
                    player.add(chosenButton);
                    chosenButton.setVisible(true);
                }
            }
        }
    }

    public void hideAndRemoveForcedDealChooseButtons(ArrayList<Player> playersWhoHasTempButton) {
        for (Player player : playersWhoHasTempButton) {
            int lastIndex = player.getComponentCount() - 1;
            player.getComponent(lastIndex).setVisible(false);
            player.remove(lastIndex);
        }
    }

    public void addAndPaintSlyDealChooseButtons(Player thief) { //When certain cards need to specify which player to use, create a choose button for each player
        ArrayList<Player> playersWhoHasTempButton = new ArrayList<>();
        for (Player player : Game.players) {
            if (player != thief) {
                if (player.whetherPlayerHasSingleProperty()) { //If the player has an incomplete set of properties
                    playersWhoHasTempButton.add(player);
                    JButton chosenButton = new JButton("↓");
                    chosenButton.setBounds(Player.playerWidth / 3, Player.playerHeight / 3, playerWidth / 3, playerHeight / 4);
                    Font buttonFont = new Font("Arial", Font.BOLD, 10);
                    chosenButton.setFont(buttonFont); // Sets the font and font size of the button
                    chosenButton.addActionListener(playerListener.slyDealChooseButtonListener(thief, player, playersWhoHasTempButton));
                    player.add(chosenButton);
                    chosenButton.setVisible(true);
                }
            }
        }
    }

    public void hideAndRemoveSlyDealChooseButtons(ArrayList<Player> playersWhoHasTempButton) {
        for (Player player : playersWhoHasTempButton) {
            int lastIndex = player.getComponentCount() - 1;
            player.getComponent(lastIndex).setVisible(false);
            player.remove(lastIndex);
        }
    }

    public void addAndPaintRentChooseButtons(Player renter, int totalRent) { //When certain cards need to specify which player to use, create a choose button for each player
        for (int i = 0; i < Game.players.size(); i++) {
            if (!Game.players.get(i).isPlayerTurn()) { //For players who are not in their turn
                JButton chosenButton = new JButton("↓");
                chosenButton.setBounds(Player.playerWidth / 3, Player.playerHeight / 3, playerWidth / 3, playerHeight / 4);
                Font buttonFont = new Font("Arial", Font.BOLD, 10);
                chosenButton.setFont(buttonFont); // Sets the font and font size of the button
                chosenButton.addActionListener(playerListener.rentChooseButtonListener(renter, Game.players.get(i), totalRent));
                Game.players.get(i).add(chosenButton);
                chosenButton.setVisible(true);
            }
        }
    }

    public void hideAndRemoveRentChooseButtons() {
        for (int i = 0; i < Game.players.size(); i++) {
            if (!Game.players.get(i).isPlayerTurn()) { //For players who are not in their turn
                int lastIndex = Game.players.get(i).getComponentCount() - 1;
                Game.players.get(i).getComponent(lastIndex).setVisible(false);
                Game.players.get(i).remove(lastIndex);
            }
        }
    }

    //-------get and set methods::

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void setPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    public boolean isInAction() {
        return isInAction;
    }

    public void setIsInAction(boolean inAction) {
        isInAction = inAction;
    }

    //-------Drawing method:

    private void paintPlayerImage(Graphics g) {
        if (playerImage != null) { // If the background image is loaded
            g.drawImage(playerImage, 0, 0, playerWidth, playerHeight, null);
        }
    }

    private void paintPlayerName(Graphics g) { //Draws the player's name
        if (playerImage != null) { // If the background image is loaded
            g.setColor(Color.BLACK); // Setting the text color
            g.setFont(new Font("Arial", Font.BOLD, 20)); // Set the font and size
            FontMetrics fm = g.getFontMetrics();
            int textHeight = fm.getAscent(); //Gets the baseline height of Name
            g.drawString(name, 0, textHeight); // Draw the player name in the top left corner inside the image
        }
    }

    private void paintChosenMark(Graphics g) { //Players who are allowed to act will be marked on their avatars
        if (isInAction) {
            g.setColor(Color.GREEN); // Setting the text color
            g.setFont(new Font("Arial", Font.BOLD, 20)); // Set the font and size
            g.drawString("Move", Player.playerWidth / 5 + Player.playerWidth / 15, 2 * Player.playerHeight / 8 + Player.playerHeight / 16);
        }
    }

    private void paintDebtNumber(Graphics g) { //Players who are allowed to act will be marked on their avatars
        if (isInAction) {
            if (this.debt > 0) {
                g.setColor(Color.RED); // Setting the text color
                g.setFont(new Font("Arial", Font.BOLD, 18)); // Set the font and size
                g.drawString("Debt: " + this.debt, Player.playerWidth / 5 + Player.playerWidth / 25, 3 * Player.playerHeight / 8 + Player.playerHeight / 10);
            }
        }
    }

    private void paintPlayerActionNumber(Graphics g) { //Players in their turn will have a number of actions on their avatar
        if (isPlayerTurn) {
            g.setColor(Color.GREEN); // Setting the text color
            g.setFont(new Font("Arial", Font.BOLD, 18)); // Set the font and size
            g.drawString("Actions: " + this.actionNumber, Player.playerWidth / 7, 5 * Player.playerHeight / 8 - Player.playerHeight / 16);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Use a superclass method to ensure proper drawing
        paintPlayerImage(g);
        paintPlayerName(g);
        paintChosenMark(g);
        paintPlayerActionNumber(g);
        paintDebtNumber(g);
    }

}
