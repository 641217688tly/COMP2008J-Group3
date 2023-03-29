package Module;
/*
Player类应该主管玩家的一系列动作;同时应该具有如玩家的房产,玩家的银行,玩家的手牌等属性:
属性:
玩家名
玩家的手牌
玩家的房产
玩家的银行
玩家的行动次数(default=3) (待定)

方法:
切换到被选中的玩家(切人)
放置房产
存钱
交租
say no
pass(行动次数没使用完前可以跳过自己的回合)
打出手牌:
    收租
    收钱
    再抽三张
    改变房产颜色
    ....

*/
public class Player {
    private String name;
    private Bank bank;
    private Property property;
    private int actionNumber = 3;
    private int x;
    private int y;

    public Player(){

    }

}
