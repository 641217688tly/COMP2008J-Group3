package Module.Cards;

import Module.Cards.CardsEnum.PropertyCardType;
import Module.Game;
import Module.Cards.CardsEnum.ActionCardType;
import Module.PlayerAndComponents.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ActionCard extends Card {
    public ActionCardType type;

    public ActionCard(ActionCardType type, ImageIcon image, int value) {
        super(image, value);
        this.type = type;
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> actionCards = new ArrayList<>();
        actionCards.add(new ActionCard(ActionCardType.DOUBLE_RENT,
                new ImageIcon("images/Card/ActionCard/ActionCardDoubleRent.jpg"), 1));
        actionCards.add(new ActionCard(ActionCardType.DOUBLE_RENT,
                new ImageIcon("images/Card/ActionCard/ActionCardDoubleRent.jpg"), 1));
        actionCards.add(new ActionCard(ActionCardType.DEAL_BREAKER,
                new ImageIcon("images/Card/ActionCard/ActionCardDealBreaker.jpg"), 5));
        actionCards.add(new ActionCard(ActionCardType.DEAL_BREAKER,
                new ImageIcon("images/Card/ActionCard/ActionCardDealBreaker.jpg"), 5));
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.JUST_SAY_NO,
                    new ImageIcon("images/Card/ActionCard/ActionCardSayNo.jpg"), 4));
        }
        for (int i = 0; i < 4; i++) {
            actionCards.add(new ActionCard(ActionCardType.FORCE_DEAL,
                    new ImageIcon("images/Card/ActionCard/ActionCardForcedDeal.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.DEBT_COLLECTOR,
                    new ImageIcon("images/Card/ActionCard/ActionCardDebtCollector.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.BIRTHDAY,
                    new ImageIcon("images/Card/ActionCard/ActionCardBirthday.jpg"), 2));
        }
        for (int i = 0; i < 10; i++) {
            actionCards.add(new ActionCard(ActionCardType.PASS_GO,
                    new ImageIcon("images/Card/ActionCard/ActionCardPassGo.jpg"), 1));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.HOUSE,
                    new ImageIcon("images/Card/ActionCard/ActionCardHouse.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.HOTEL,
                    new ImageIcon("images/Card/ActionCard/ActionCardHotel.jpg"), 4));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.SLY_DEAL,
                    new ImageIcon("images/Card/ActionCard/ActionCardSlyDeal.jpg"), 3));
        }
        return actionCards;
    }

    private void updatePlayerInteractiveState() {
        // Each time an ActionCard is used, the player starts a new round of
        // interaction, so make sure that the data from the last interaction is cleared
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.isInAction()) {
                    owner.debt = 0;
                    owner.interactivePlayers.clear();
                    owner.singleActionCardsBuffer.clear();
                    owner.pledgeCardFromProperty.clear();
                    owner.pledgeCardFromBank.clear();
                }
            }
        }
    }

    private boolean whetherSlyDealOrForcedDealCardCanPlay() {
        for (Player player : Game.players) {
            if (player != owner) {
                for (PropertyCardType propertyType : PropertyCardType.values()) {
                    if ((player.property.propertyNumberMap.get(propertyType) > 0 && player.property.propertyNumberMap
                            .get(propertyType) < PropertyCard.judgeCompleteSetNumber(propertyType))
                            || player.property.propertyNumberMap.get(propertyType) > PropertyCard
                                    .judgeCompleteSetNumber(propertyType)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean whetherDealBreakerCardCanPlay() { // Determine if the player on the field has a full set of
                                                      // properties
        for (Player player : Game.players) {
            if (player != owner) {
                for (PropertyCardType propertyType : PropertyCardType.values()) {
                    if (player.property.propertyNumberMap.get(propertyType) >= PropertyCard
                            .judgeCompleteSetNumber(propertyType)) { // 如果有一套完整的房产
                        return true;
                    }
                }
            }

        }
        return false;
    }

    // -------The different ActionCard methods:

    private void playHouseOrHotel() {
        if (this.type.equals(ActionCardType.HOUSE) || this.type.equals(ActionCardType.HOTEL)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            for (int i = 0; i < owner.cardsTable.length; i++) { // Remove the card from the player's
                                                                                // hand
                                if (owner.cardsTable[i] == this) {
                                    owner.cardsTable[i] = null;
                                    break;
                                }
                            }
                            if (!owner.whetherViewComponent) { // If the player is viewing PlayerCardsPile when this is
                                                               // called
                                owner.playerCardsPile.updateAndShowCards(); // Update and show PlayerCardsPile directly
                            } else { // If the player is viewing a component when this is called
                                owner.handCards.updateAndShowCards(); // Update and show HandCards directly
                            }
                            // Hide all buttons on the card:
                            this.playButtonSwitch = false;
                            this.depositButtonSwitch = false;
                            this.discardButtonSwitch = false;
                            this.moveButtonSwitch = false;
                            controlButtons();
                            // Place the card into the property and update the property status
                            owner.property.placePropertyCardAndShowTable(this);
                            owner.actionNumber = owner.actionNumber - 1;
                        }
                    }
                }
            }
        }
    }

    private void playDoubleRent() {
        if (this.type.equals(ActionCardType.DOUBLE_RENT)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            owner.oneTurnCardsBuffer.add(this); // Add RentCard to the player's cardsBuffer
                            owner.actionNumber = owner.actionNumber - 1;
                            discard();
                        }
                    }
                }
            }
        }
    }

    private void playPassGo() {
        if (this.type.equals(ActionCardType.PASS_GO)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            owner.drawCards(Game.cardsPile.drawCardFromDrawPile(2));
                            owner.actionNumber = owner.actionNumber - 1;
                            discard();
                        }
                    }
                }
            }
        }
    }

    private void playBirthday() {
        if (this.type.equals(ActionCardType.BIRTHDAY)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            updatePlayerInteractiveState();
                            // Add all players to the interactive queue
                            for (int i = 0; i < Game.players.size(); i++) {
                                if (Game.players.get(i) != owner) {
                                    owner.interactivePlayers.add(Game.players.get(i));
                                }
                            }
                            // Reduce player's action number
                            owner.actionNumber = owner.actionNumber - 1;
                            owner.setIsInAction(false);
                            owner.interactivePlayers.get(0).setIsInAction(true);
                            for (Player player : Game.players) {
                                player.setVisible(true);
                                if (player.isPlayerTurn()) {
                                    player.playerCardsPile.updateAndShowCards();
                                }
                            }
                            // Allow the first player in the interactive queue to pay money
                            owner.interactivePlayers.get(0).payForMoney(owner, 2);
                            discard();
                        }
                    }
                }
            }
        }
    }

    private void playDebtCollector() {
        if (this.type.equals(ActionCardType.DEBT_COLLECTOR)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            updatePlayerInteractiveState();
                            // Add all players to the interactive queue
                            for (int i = 0; i < Game.players.size(); i++) {
                                if (Game.players.get(i) != owner) {
                                    owner.interactivePlayers.add(Game.players.get(i));
                                }
                            }
                            // Reduce player's action number
                            owner.actionNumber = owner.actionNumber - 1;
                            owner.setIsInAction(false);
                            owner.interactivePlayers.get(0).setIsInAction(true);
                            for (Player player : Game.players) {
                                player.setVisible(true);
                                if (player.isPlayerTurn()) {
                                    player.playerCardsPile.updateAndShowCards();
                                }
                            }
                            // Allow the first player in the interactive queue to pay money
                            owner.interactivePlayers.get(0).payForMoney(owner, 5);
                            discard();
                        }
                    }
                }
            }
        }
    }

    private void playForcedDeal() {
        if (this.type.equals(ActionCardType.FORCE_DEAL)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            if (whetherSlyDealOrForcedDealCardCanPlay()) {
                                // Check if there are incomplete property sets owned by other players
                                boolean flag = false;
                                for (PropertyCardType propertyType : PropertyCardType.values()) {
                                    if ((owner.property.propertyNumberMap.get(propertyType) > 0
                                            && owner.property.propertyNumberMap.get(propertyType) < PropertyCard
                                                    .judgeCompleteSetNumber(propertyType))
                                            || owner.property.propertyNumberMap.get(propertyType) > PropertyCard
                                                    .judgeCompleteSetNumber(propertyType)) {
                                        flag = true;
                                        break;
                                    }
                                }
                                if (flag) {
                                    updatePlayerInteractiveState();
                                    // Add choose buttons for all players except the card user
                                    owner.addAndPaintForcedDealChooseButtons(owner);
                                    owner.oneTurnCardsBuffer.add(this);
                                    // Add swap property buttons for the player's properties
                                    owner.property.addAndPaintSwapPropertyButtons();
                                    // Hide other players and their cards
                                    for (Player player : Game.players) {
                                        player.setVisible(false);
                                        if (player.isPlayerTurn()) {
                                            player.playerCardsPile.setVisible(false);
                                        }
                                    }
                                    owner.whetherViewComponent = true;
                                    owner.property.setVisible(true);
                                    owner.property.reallocateAllCards();

                                    discard();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void playSlyDeal() {
        if (this.type.equals(ActionCardType.SLY_DEAL)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            if (whetherSlyDealOrForcedDealCardCanPlay()) {
                                // Check if there are properties owned by other players
                                updatePlayerInteractiveState();
                                // If there are, add choose buttons for all players except the card user
                                owner.addAndPaintSlyDealChooseButtons(owner);
                                owner.oneTurnCardsBuffer.add(this);
                                discard();
                            }
                        }
                    }
                }
            }
        }
    }

    private void playDealBreaker() {
        if (this.type.equals(ActionCardType.DEAL_BREAKER)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            if (whetherDealBreakerCardCanPlay()) {
                                // Check if there are properties owned by other players
                                updatePlayerInteractiveState();
                                // If there are, add choose buttons for all players except the card user
                                owner.addAndPaintDealBreakerChooseButtons(owner);
                                owner.oneTurnCardsBuffer.add(this);
                                discard();
                            }
                        }
                    }
                }
            }
        }
    }

    private void playJustSayNo() {
        if (this.type.equals(ActionCardType.JUST_SAY_NO)) {
            if (owner != null) {
                if (owner.isInAction()) { // If the player is in action, they have the right to use the SayNoCard
                    if (owner.isPlayerTurn()) { // The player using the SayNo card is in their own turn
                        owner.oneTurnCardsBuffer.add(this);
                        owner.singleActionCardsBuffer.add(this);
                    } else {
                        if (owner.isInAction()) { // The player currently using the SayNo card is in action
                            owner.singleActionCardsBuffer.add(this);
                        }
                    }
                    // Close the card display:
                    owner.whetherViewComponent = false;
                    owner.handCards.closeButton.setVisible(true);
                    owner.handCards.setVisible(false);
                    owner.handCards.removeAll(); // To prevent conflicts on the JPanel components

                    Player tempOwner = owner;
                    discard(); // Since discard() sets the card's owner to null, we need to temporarily store
                               // the owner
                    // Let the opponent take their turn
                    tempOwner.interactivePlayers.get(0).setIsInAction(true);
                    tempOwner.setIsInAction(false); // Temporarily end their own turn
                    tempOwner.interactivePlayers.get(0).respondSayNoCard(); // Transfer the choice to the opponent

                    for (Player player : Game.players) {
                        player.setVisible(true);
                        if (player.isPlayerTurn()) {
                            player.playerCardsPile.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void play() { // Play (used)
        playHouseOrHotel();
        playDoubleRent();
        playPassGo();
        playJustSayNo();
        playBirthday();
        playDebtCollector();
        playSlyDeal();
        playDealBreaker();
        playForcedDeal();
    }

    @Override
    public void deposit() { // Deposit (used) - needs to update the bank
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) {
                    if (owner.isInAction()) {
                        owner.oneTurnCardsBuffer.add(this);
                        for (int i = 0; i < owner.cardsTable.length; i++) { // Remove the card from the player's hand
                            if (owner.cardsTable[i] == this) {
                                owner.cardsTable[i] = null;
                                break;
                            }
                        }
                        if (!owner.whetherViewComponent) { // If the player is viewing PlayerCardsPile
                            owner.playerCardsPile.updateAndShowCards(); // Update PlayerCardsPile directly
                        } else { // If the player is viewing components
                            owner.handCards.updateAndShowCards(); // Update HandCards directly
                        }
                        // Hide all buttons on the card:
                        this.playButtonSwitch = false;
                        this.depositButtonSwitch = false;
                        this.discardButtonSwitch = false;
                        this.moveButtonSwitch = false;
                        controlButtons();
                        // Store the card in the bank and update the bank's status
                        owner.bank.saveMoneyAndShowCards(this);
                        owner.actionNumber = owner.actionNumber - 1;
                    }
                }
            }
        }
    }

    @Override
    public void discard() { // Discard (used) - only called by the player in their own turn - needs to
                            // update the player's HandCards or PlayerCardsPile status
        if (owner != null) {
            if (owner.isPlayerTurn() || owner.isInAction()) {
                for (int i = 0; i < owner.cardsTable.length; i++) { // Remove the card from the player's hand
                    if (owner.cardsTable[i] == this) {
                        owner.cardsTable[i] = null;
                        break;
                    }
                }
                if (!owner.whetherViewComponent) { // If the player is viewing PlayerCardsPile
                    owner.playerCardsPile.updateAndShowCards(); // Update PlayerCardsPile directly
                } else { // If the player is viewing components
                    owner.handCards.updateAndShowCards(); // Update HandCards directly
                }
                // Hide all buttons on the card:
                this.playButtonSwitch = false;
                this.depositButtonSwitch = false;
                this.discardButtonSwitch = false;
                this.moveButtonSwitch = false;
                controlButtons();
                // Discard the card
                this.owner = null;
                Game.cardsPile.recycleCardIntoDiscardPile(this); // Place the card into the discard pile
            }
        }
    }

    @Override
    public void move() {
        // First check the container the card belongs to:
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.containsCard(this)) {
                    if (owner.whetherViewComponent) { // The player is viewing HandCards
                        // Add buttons to the empty positions in HandCards
                        owner.handCards.addAndPaintHereButtons(this);
                    } else { // The player is viewing PlayerCardsPile
                        // Add buttons to the empty positions in PlayerCardsPile
                        owner.playerCardsPile.addAndPaintHereButtons(this);
                    }
                } else if (owner.bank.containsCard(this)) {
                    owner.bank.addAndPaintHereButtons(this);
                } else if (owner.property.containsCard(this)) {
                    owner.property.addAndPaintHereButtons(this);
                }
            }
        }
    }

    // Drawing methods:

    @Override
    public void paintCard(Graphics g) {
        if (isDisplayable) {
            if (isCardFront) { // Front side of the card
                g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            } else {
                g.drawImage(cardBackImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            }
        }
    }
}
