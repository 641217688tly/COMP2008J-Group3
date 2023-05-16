package Module.Cards;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
public class MoneyCard extends Card {
    public int value;

    public MoneyCard(String name, ImageIcon cardImage, int value) {
        super(name, cardImage);
        this.value = value;
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> moneyCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) { // 初始化6张1$MoneyCard
            var icon = new ImageIcon("images/Card/1.pic.jpg");
            moneyCards.add(new MoneyCard("1M", icon, 1));
        }
        for (int i = 0; i < 5; i++) { // 初始化5张2$MoneyCard
            var icon = new ImageIcon("images/Card/2.pic.jpg");
            moneyCards.add(new MoneyCard("2M", icon, 2));
        }
        for (int i = 0; i < 4; i++) { // 初始化4张3$MoneyCard
            var icon = new ImageIcon("images/Card/3.pic.jpg");
            moneyCards.add(new MoneyCard("3M", icon, 3));
        }
        for (int i = 0; i < 3; i++) { // 初始化3张4$MoneyCard
            var icon = new ImageIcon("images/Card/4.pic.jpg");
            moneyCards.add(new MoneyCard("4M", icon, 4));
        }
        for (int i = 0; i < 2; i++) { // 初始化2张5$MoneyCard
            var icon = new ImageIcon("images/Card/5.pic.jpg");
            moneyCards.add(new MoneyCard("5M", icon, 5));
        }
        for (int i = 0; i < 1; i++) { // 初始化1张10$MoneyCard
            var icon = new ImageIcon("images/Card/10.pic.jpg");
            moneyCards.add(new MoneyCard("10M", icon, 10));
        }
        return moneyCards;
    }

    @Override
    public void play() {

    }

    @Override
    public void putBack() {

    }
}
