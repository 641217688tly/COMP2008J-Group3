package Module;

import Listener.PlayerListener;
import Module.Cards.Card;

import java.util.ArrayList;
import java.util.List;
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
    public static CardsPile cardsPile; //中央牌区
    public static ArrayList<Card> cardsWarehouse = new ArrayList<>();
    public static ArrayList<Player> players = new ArrayList<>();
    public PlayerListener playerListener;


    public Game(){

    }

    public void addPlayers(List<String> playerNames) { //用于在设置界面设置完玩家人数和姓名后创建所有的玩家对象并添加到Game类的players中
        players.clear();
        for (String playerName : playerNames) {
            Player player = new Player(playerName);
            players.add(player);
        }
    }


}
