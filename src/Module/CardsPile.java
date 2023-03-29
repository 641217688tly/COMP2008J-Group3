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

import Module.Cards.Card;

import java.util.ArrayList;

public class CardsPile {
    private ArrayList<Card> cardsPile;


}
