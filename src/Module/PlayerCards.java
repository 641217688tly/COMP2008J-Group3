package Module;

import Module.Cards.Card;

import javax.swing.*;
import java.util.ArrayList;

/*
属性：
cards（保存玩家手牌的列表，类型为List<Card>）
方法：
初始化方法，设置JDialog的布局，根据cards列表中的卡牌，创建卡片展示组件并添加到JDialog中
updateCards()（更新手牌列表）
*/
public class PlayerCards extends JDialog {
    //属性:
    public static ArrayList<Card> playerCards;
}
