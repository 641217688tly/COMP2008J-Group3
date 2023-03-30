package Module;

import Module.Cards.Card;

import java.util.ArrayList;

/*
Player类应该主管玩家的一系列动作;同时应该具有如玩家的房产,玩家的银行,玩家的手牌等属性:
属性:
玩家名
玩家的手牌
玩家的房产
玩家的银行
玩家的行动次数(default=3) (待定)
倒计时(玩家需要在指定的时间内打出手牌,否则将自动pass或者自动交租/交房产)

方法:
放置房产
存钱
交租
pass(行动次数没使用完前可以跳过自己的回合)
改变房产颜色
打出手牌:
    收租
    收钱
    再抽三张
    say no(不消耗行动次数)
    ....

*/
public class Player {
    private String name;
    private int x;
    private int y;
    private Bank bank; //玩家的银行
    private Property property; //玩家的房产区
    private ArrayList<Card> cardsWarehouse; //玩家的手牌堆
    private int actionsNumber = 3; //行动次数
    private int countDown = 180; //倒计时


    public Player(){

    }

}