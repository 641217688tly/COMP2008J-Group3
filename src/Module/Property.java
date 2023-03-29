package Module;

import Module.Cards.Card;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/*
Property类将主要为玩家提供服务,它应该具有如下属性和功能:
属性:
房产库(HashMap?)(创建一个哈希Map,键为房产颜色,值为一个ArrayList<Card>)
完整的房产的数量
坐标(可以根据玩家的坐标来调整)
碰撞体积(当牌被拖进来后可以收纳牌)
主人

方法:
展示所有的房产卡
显示当前已经集齐的完整房产的数量
    (因此需要实现)统计完整房产数
自动交房产(实现某种算法,使得玩家在被要求收钱后如果长时间不行动且银行无力付款时,以最优的方式抵押房产)
更改房产颜色(对于万能卡或双色卡)
(被)交出房产
(被)安置房产
...

*/
public class Property {
    private Player owner; //主人
    private HashMap<Card, ArrayList<Card>> cardsWarehouse;
    private int x;
    private int y;
    private Rectangle area;
    private int wholePropertiesNumber; //完整的房产数


}
