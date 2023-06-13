package Listener.ModuleListener.CardsListener;

import Module.Cards.PropertyWildCard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertyWildCardListener {

    public ActionListener reverseButtonListener(PropertyWildCard card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.switchCardColor();
                card.owner.property.updatePropertiesNumber(); //重新刷新玩家房产的数量信息,这样避免多色卡被转动后,玩家凑齐了三套房产但游戏不结束的bug
            }
        };
    }
}
