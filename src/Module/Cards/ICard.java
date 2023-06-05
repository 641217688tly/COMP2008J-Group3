package Module.Cards;

import Module.PlayerAndComponents.Player;

public interface ICard {

    void deposit(); //把牌当作Money存进银行

    void play();//打牌

    void discard();//弃牌
}
