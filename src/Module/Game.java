package Module;

import Listener.PlayerListener;
import Module.Cards.Card;

import java.util.ArrayList;
/*
该类应该负责管理整个游戏
属性:
玩家集合
中央牌区
playerListener(用于监控玩家鼠标或键盘的行为以实现拖拽牌等行为)

方法:
提醒该哪位玩家开始行动
切换到被选中的玩家(切人)
牌不够时命令中央牌区洗牌
判断是否有Winner
...

 */


public class Game {
    public CardsPile cardsPile; //中央牌区
    public static ArrayList<Card> cardsWarehouse = new ArrayList<>(); //所有的玩家
    public PlayerListener playerListener;





}
