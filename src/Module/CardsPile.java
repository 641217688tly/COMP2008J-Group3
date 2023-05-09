package Module;
/*
CardsPile类应该负责发牌功能,所有未发的牌和已经打出来的牌都应该放在牌堆中
属性:
牌库1:装载尚未被抽取的牌
牌库2:回收玩家打出的牌,如果牌库1被用完,那么就将牌库2中的牌洗牌后装载回牌库1
坐标
碰撞体积(当牌被拖进牌堆,牌堆应该收容它)


方法:
为玩家发牌
回收打出的牌
牌不够时洗牌库2装牌库1

 */

import GUI.ApplicationStart;
import Module.Cards.Card;

import javax.swing.*;
import java.util.Stack;

public class CardsPile extends JPanel {
    public Stack<Card> drawPile = new Stack<>(); // 抽牌堆
    public Stack<Card> discardPile = new Stack<>(); // 废牌堆
    public int drawPileX = ApplicationStart.screenWidth;
    public int drawPileY;
    public int discardPileX;
    public int discardPileY;

    public CardsPile() {

    }

    public void addToDrawPile(Card card) {
        drawPile.push(card);
    }

    public Card drawCard() {
        if (drawPile.isEmpty()) {
            // 如果抽牌堆为空，需要将废牌堆的牌洗一次并转移到抽牌堆
            shuffleDiscardPile();
        }
        return drawPile.pop();
    }

    public void addToDiscardPile(Card card) {
        discardPile.push(card);
    }

    private void shuffleDiscardPile() {
        // 将废牌堆洗牌，并将它们转移到抽牌堆
        // 您可以使用您喜欢的洗牌算法
    }
}

