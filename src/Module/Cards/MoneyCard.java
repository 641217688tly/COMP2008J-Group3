
package Module.Cards;
import Module.Player;

public class MoneyCard extends Card {
    private int value;

    public MoneyCard(String name, CardType cardType, int value) {
        super(name, cardType);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public void playCard(Player player, Player targetPlayer, Card targetCard) {
        player.addMoney(this.value);
    }
}

