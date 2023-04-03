package Module.Cards;

import java.awt.*;

/*
Cards:
    Properties (28):
        2蓝色，2棕色，2实用，3绿色，3黄色，3红色，3橙色，3粉色，3
        浅蓝色，4号铁路

    Property Wildcards (11):
        1个深蓝色/绿色，1个绿色/铁路，1个公用事业/铁路，1个灯光
        蓝色/铁路，1浅蓝色/棕色，2粉色/橙色，2红色/黄色，2多色属性
        通配符。

    Action Cards(34):
        2交易破坏，3只是说不，3狡猾的交易，4强迫交易，3讨债，3这是我的生日，
        10 Pass Go, 3房子，3酒店，2双倍租金卡

    Rent Cards(13):
        2张深蓝色/绿色，2张红色/黄色，2张粉红色/橙色，2张浅蓝色/棕色，
        2张铁路/公用事业，3张Wild出租卡

    Money Cards(20):
        6 cards of 1M, 5 cards of 2M, 3 cards of 3M,
        3 cards of 4M, 2 cards of 5M, 1 card of 10M.

属性:
x,y坐标(用于被展示)
碰撞体积
价值

方法:
用按钮的方式实现打出牌
先不做被拖动(被鼠标选中后可以拖动到桌面上)



*/
public class Card { //所有Card的顶级父类

    private int x;
    private int y;
    private Rectangle area;
    private int value;

}
/*public Card(String name, String type, String color, int value, int x, int y, int width, int height) {
    this.name = name;
    this.type = type;
    this.color = color;
    this.value = value;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
}

public String getName() {
    return name;
}

public String getType() {
    return type;
}

public String getColor() {
    return color;
}

public int getValue() {
    return value;
}

public int getX() {
    return x;
}

public int getY() {
    return y;
}

public int getWidth() {
    return width;
}

public int getHeight() {
    return height;
}

public void setX(int x) {
    this.x = x;
}

public void setY(int y) {
    this.y = y;
}

//实现打出牌的方法
public void playCard() {
    //TODO
}
} */
