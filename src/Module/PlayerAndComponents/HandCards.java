/*
属性：
cards（保存玩家手牌的列表，类型为List<Card>）
方法：
初始化方法，设置JDialog的布局，根据cards列表中的卡牌，创建卡片展示组件并添加到JDialog中
updateCards()（更新手牌列表）
*/
package Module.PlayerAndComponents;

import Module.Cards.Card;


import javax.swing.*;
import java.util.ArrayList;

public class HandCards extends JDialog { //该类为玩家边框上的按钮,用于查看玩家的手牌
    private ArrayList<Card> playerCards;
    private Player owner;

    public HandCards(Player owner) {
        this.playerCards = new ArrayList<>();
        this.owner = owner;
    }

    public void drawCardFromCardsPile(ArrayList<Card> cards) {
        playerCards.addAll(cards);
    }

}
