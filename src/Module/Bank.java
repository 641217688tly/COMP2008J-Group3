package Module;

import Module.Cards.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/*
Bank类将主要为玩家提供服务(每个玩家都有自己的Bank),它应该具有如下属性和功能:
属性:
牌库(用于存钱卡和被当作钱存起来的手牌)
总存款
坐标
碰撞体积(当牌被拖进银行区后被收纳)

方法:
展示所有的钱卡
显示该银行的总存款
    (因此需要实现)统计总存款
自动交租金(实现某种算法,使得玩家在被要求收钱后如果长时间不行动,就自动从银行中以最优的方式扣款)
*/
public class Bank extends JDialog {
    public static ArrayList<Card> moneyCards;

}


