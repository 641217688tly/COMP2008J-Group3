package Module;

import Module.Cards.Card;

import java.awt.*;
import java.util.ArrayList;

/*
Bank类将主要为玩家提供服务(每个玩家都有自己的Bank),它应该具有如下属性和功能:
属性:
牌库(用于存钱卡和被当作钱存起来的手牌)
总存款
坐标
碰撞体积(当牌被拖进银行区后被收纳)
拥有者

方法:
展示所有的钱卡
显示该银行的总存款
    (因此需要实现)统计总存款
自动交租金(实现某种算法,使得玩家在被要求收钱后如果长时间不行动,就自动从银行中以最优的方式扣款)



*/
/* 
public class Bank {
    private Player owner; //主人
    ArrayList<Card> cardWarehouse;
    private int sumMoney;
    private int x;
    private int y;
    private Rectangle area;


    public Bank(){

    }
}
*/

  public class Bank {
    private ArrayList<Card> cardDeck;
    private int totalSavings;
    private int x;
    private int y;
    private int collisionVolume;
    private Player owner;

    public Bank(Player owner, int x, int y, int collisionVolume) {
        this.owner = owner;
        this.cardDeck = new ArrayList<Card>();
        this.totalSavings = 0;
        this.x = x;
        this.y = y;
        this.collisionVolume = collisionVolume;
    }

    public List<Card> getCardDeck() {
        return this.cardDeck;
    }

    public int getTotalSavings() {
        return this.totalSavings;
    }

    public void addSavings(int amount) {
        this.totalSavings += amount;
    }

    public void subtractSavings(int amount) {
        this.totalSavings -= amount;
    }

    public void addCard(Card card) {
        this.cardDeck.add(card);
    }

    public void removeCard(Card card) {
        this.cardDeck.remove(card);
    }

    public void displayMoneyCards() {
        for (Card card : this.cardDeck) {
            if (card instanceof MoneyCard) {
                System.out.println(card.getName());
            }
        }
    }

    public void displayTotalSavings() {
        System.out.println("Total savings: " + this.totalSavings);
    }

    public void autoPayRent() {
        // Implement algorithm to automatically pay rent in the most optimal way
    }
}
 */
/*public class RankedCard extends Card {
    private int rank;

    public RankedCard(String name, CardType cardType, int rank, int collisionVolume, int x, int y) {
        super(name, cardType, rank, collisionVolume, x, y);
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    @Override
    public void playCard(Player player, Player targetPlayer, Card targetCard) {
        player.addMoney(this.rank);
    }
} */