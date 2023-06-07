package Listener.ModuleListener.CardsListener;

import Module.Cards.Card;
import Module.Cards.CardsEnum.RentCardType;
import Module.Cards.PropertyCard;
import Module.Cards.PropertyWildCard;
import Module.Cards.RentCard;
import Module.PlayerAndComponents.Player;
import Module.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardListener {
    public ActionListener playCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.play();
            }
        };
    }

    public ActionListener depositCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.deposit();
            }
        };
    }

    public ActionListener discardCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.discard();
            }
        };
    }

    public ActionListener moveCardButtonListener(Card card) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.move();
            }
        };
    }

    //用于RentCard被使用后挑选要收租金的房产
    public ActionListener chosenButtonListener(Player owner, Card propertyCard, RentCard rentCard, boolean isDoubleRent) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((propertyCard instanceof PropertyCard || propertyCard instanceof PropertyWildCard) && (rentCard.whetherRentCardCanBeUsed(propertyCard))) {
                    //为Property中的所有Card删去Choose Button:
                    owner.property.hideAndRemoveChooseButtons(owner);
                    //计算要收取的租金:
                    int totalRent = owner.property.calculatedRent(propertyCard, isDoubleRent);
                    //针对多色收租卡和双色收租卡分类讨论:
                    if (!rentCard.type.equals(RentCardType.WILD_RENT)) { //非多色收租卡:
                        for (int i = 0; i < Game.players.size(); i++) {
                            if (Game.players.get(i) != owner) {
                                owner.interactivePlayers.add(Game.players.get(i));
                            }
                        }
                        //退出房产界面:
                        owner.property.closeButton.setVisible(true);
                        owner.whetherViewComponent = false;
                        owner.property.setVisible(false); // 当玩家点击关闭按钮时，隐藏这个JPanel
                        owner.setIsInAction(false);
                        owner.interactivePlayers.get(0).setIsInAction(true);
                        for (Player player : Game.players) {
                            player.setVisible(true);
                            if (player.isPlayerTurn()) {
                                player.playerCardsPile.updateAndShowCards();
                            }
                        }
                        //让需要交租的玩家进行交互
                        owner.interactivePlayers.get(0).payForRent(owner, totalRent);

                    } else { //多色收租卡:
                        //退出房产界面:
                        owner.property.closeButton.setVisible(true);
                        owner.whetherViewComponent = false;
                        owner.property.setVisible(false); // 当玩家点击关闭按钮时，隐藏这个JPanel
                        for (Player player : Game.players) {
                            player.setVisible(true);
                            if (player.isPlayerTurn()) {
                                player.playerCardsPile.updateAndShowCards();
                            }
                        }
                        owner.addAndPaintRentChooseButtons(owner, totalRent);//给所有玩家(除了自己之外)都加上一个Choose按钮,将该按钮的事件应该包括:1.将玩家添加owner的interactivePlayers中; 2.让需要交租的玩家进行交互
                    }
                }
            }
        };
    }

}
