package Module.Cards;

import javax.swing.*;
import java.util.ArrayList;

public class MoneyCard extends Card {


    public MoneyCard(ImageIcon cardImage, int value) {
        super(cardImage, value);
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> moneyCards = new ArrayList<>();
        for (int i = 0; i < 6; i++) { // 初始化6张1$MoneyCard
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/1.jpg"), 1));
        }
        for (int i = 0; i < 5; i++) { // 初始化5张2$MoneyCar
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/2.jpg"), 2));
        }
        for (int i = 0; i < 3; i++) { // 初始化3张3$MoneyCard
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/3.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) { // 初始化3张4$MoneyCard
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/4.jpg"), 4));
        }
        for (int i = 0; i < 2; i++) { // 初始化2张5$MoneyCard
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/5.jpg"), 5));
        }
        for (int i = 0; i < 1; i++) { // 初始化1张10$MoneyCard
            moneyCards.add(new MoneyCard(new ImageIcon("images/Card/MoneyCard/10.jpg"), 10));
        }
        return moneyCards;
    }


    @Override
    public void deposit() {

    }

    @Override
    public void play() {

    }
}
