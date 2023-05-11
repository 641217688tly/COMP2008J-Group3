package Module.Cards;

import java.util.ArrayList;

public class MoneyCard extends Card {
    public MoneyCard(String name) {
        super(name);
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> moneyCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) { // 初始化6张1$MoneyCard
            moneyCards.add(new MoneyCard("1M"));
        }
        for (int i = 0; i < 5; i++) { // 初始化5张2$MoneyCard
            moneyCards.add(new MoneyCard("2M"));
        }
        for (int i = 0; i < 4; i++) { // 初始化4张3$MoneyCard
            moneyCards.add(new MoneyCard("3M"));
        }
        for (int i = 0; i < 3; i++) { // 初始化3张4$MoneyCard
            moneyCards.add(new MoneyCard("4M"));
        }
        for (int i = 0; i < 2; i++) { // 初始化2张5$MoneyCard
            moneyCards.add(new MoneyCard("5M"));
        }
        for (int i = 0; i < 1; i++) { // 初始化1张10$MoneyCard
            moneyCards.add(new MoneyCard("10M"));
        }
        return moneyCards;
    }
}
