package Module.Cards;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class MoneyCard extends Card implements CardImplement {
    private int value;

    public MoneyCard(String name, ImageIcon cardImage, int value) {
        super(name, cardImage);
        this.value = value;
    }

    @Override
    public void play() {
        // TODO: 实现 play 方法
    }

    @Override
    public void putBack() {
        // TODO: 实现 putBack 方法
    }

    public int getValue() {
        return value;
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> moneyCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) { // 初始化6张1$MoneyCard
            moneyCards.add(new MoneyCard("1M",null,1));
        }
        for (int i = 0; i < 5; i++) { // 初始化5张2$MoneyCard
            moneyCards.add(new MoneyCard("2M",null,2));
        }
        for (int i = 0; i < 4; i++) { // 初始化4张3$MoneyCard
            moneyCards.add(new MoneyCard("3M",null,3));
        }
        for (int i = 0; i < 3; i++) { // 初始化3张4$MoneyCard
            moneyCards.add(new MoneyCard("4M",null,4));
        }
        for (int i = 0; i < 2; i++) { // 初始化2张5$MoneyCard
            moneyCards.add(new MoneyCard("5M",null,5));
        }
        for (int i = 0; i < 1; i++) { // 初始化1张10$MoneyCard
            moneyCards.add(new MoneyCard("10M",null,10));
        }
        return moneyCards;
    }
}
