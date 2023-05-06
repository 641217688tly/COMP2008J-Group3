package Module;

import Listener.ModuleListener.PlayerListener;
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

/*
以下是对游戏过程的描述:
(1)在进入游戏后,玩家们被要求选择参与游戏的人数(2-5人),并输入自己的ID,程序会根据传入的玩家人数和游戏ID生成相应数量的Player对象(这一步已经完成);
(2)之后,玩家们会正式进入游戏界面,该界面的设计如下:
    1.整个游戏界面的背景为一张.png类型的图片,该游戏界面占据整个电脑屏幕(即全屏)
    2.由于游戏界面占据了整个电脑屏幕,因此它是一个长方形.根据玩家人数的不同,在长方形的四个边分别绘制玩家的形象(以五个玩家为例,在长方形的上面绘制两个玩家形象,在左右两边各绘制一个玩家形象,在长方形下面绘制一个玩家形象)
    3.每个玩家的形象是一个矩形,它在程序内对应了Player类.该矩形的背景为一张.png类型的图片,矩形内的最上方绘制了玩家的ID,ID下方分别有有以下三个按钮:
        按钮1:手牌按钮(对应PlayerCards类),点击该按钮可以在游戏界面内弹出该玩家所拥有的所有手牌
        按钮2:银行按钮(对应Bank类),点击该按钮可以在游戏界面内弹出该玩家所拥有的所有钱币
        按钮3:房产按钮(对应Property类),点击该按钮可以在游戏界面内弹出该玩家所拥有的所有房产
    4.游戏界面的正中央是两个矩形,左侧的矩形是废牌区,用于回收玩家已经打出的牌(有文字的那一面朝上,这样别的玩家可以看到别人刚刚打出了什么牌);右侧的矩形是抽牌区,玩家在这里抽取新的牌(有文字的那一面朝下,这样玩家就不会知道别人抽了什么牌),中央的这两个牌区统一由CardsPile类实现
    5.在多个场合中（比如中央牌区，点击玩家的手牌、银行和房产按钮后）卡牌会被呈现在GUI界面上,以下是卡牌被显示时的具体要求：
        1.卡牌为一个长方形，它的背景为一张.png类型的图片
        2.卡牌是可以被选中的，当玩家点击卡牌内的区域（也就是说鼠标与卡牌的碰撞面积相交互后），卡牌上会出现两个按钮：
            按钮1：打出，点击后卡牌被打出
            按钮2：放回，点击后卡牌被放回
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
