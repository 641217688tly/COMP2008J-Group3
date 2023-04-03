package Module.Cards;

public class ActionCard extends Card{
}
/*
 * public class ActionCard extends Card {
    private ActionType actionType;
    private int actionValue;

    public ActionCard(String name, CardType cardType, ActionType actionType, int actionValue) {
        super(name, cardType);
        this.actionType = actionType;
        this.actionValue = actionValue;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public int getActionValue() {
        return this.actionValue;
    }

    @Override
    public void playCard(Player player, Player targetPlayer, Card targetCard) {
        switch (this.actionType) {
            case MONEY:
                player.addMoney(this.actionValue);
                break;
            case RENT:
                targetPlayer.removeMoney(this.actionValue);
                player.addMoney(this.actionValue);
                break;
            case DRAW:
                for (int i = 0; i < this.actionValue; i++) {
                    player.addCard(Deck.drawCard());
                }
                break;
            case ADD_PROPERTY:
                player.addCard(targetCard);
                break;
            case STEAL:
                targetPlayer.removeCard(targetCard);
                player.addCard(targetCard);
                break;
            default:
                break;
        }
    }
}
 */