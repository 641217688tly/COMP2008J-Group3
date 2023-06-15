package Module.Cards;

import Module.Cards.CardsEnum.ActionCardType;
import Module.Cards.CardsEnum.PropertyCardType;
import Module.Game;
import Module.Cards.CardsEnum.RentCardType;
import Module.PlayerAndComponents.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RentCard extends Card {
    public RentCardType type;
    public int totalRent = 0;

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> rentCards = new ArrayList<>();

        rentCards.add(new RentCard(RentCardType.BLUE_GREEN, new ImageIcon("images/Card/RentCard/RentGreenBlue.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.BLUE_GREEN, new ImageIcon("images/Card/RentCard/RentGreenBlue.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.RED_YELLOW, new ImageIcon("images/Card/RentCard/RentRedYellow.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.RED_YELLOW, new ImageIcon("images/Card/RentCard/RentRedYellow.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.PINK_ORANGE, new ImageIcon("images/Card/RentCard/RentPinkOrange.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.PINK_ORANGE, new ImageIcon("images/Card/RentCard/RentPinkOrange.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.LIGHTBLUE_BROWN, new ImageIcon("images/Card/RentCard/RentLightBlueBrown.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.LIGHTBLUE_BROWN, new ImageIcon("images/Card/RentCard/RentLightBlueBrown.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.RAILROAD_UTILITY, new ImageIcon("images/Card/RentCard/RentRailroadUtility.jpg"), 1));
        rentCards.add(new RentCard(RentCardType.RAILROAD_UTILITY, new ImageIcon("images/Card/RentCard/RentRailroadUtility.jpg"), 1));
        for (int i = 0; i < 3; i++) {
            rentCards.add(new RentCard(RentCardType.WILD_RENT, new ImageIcon("images/Card/RentCard/RentAllColor.jpg"), 3));
        }
        return rentCards;
    }

    public RentCard(RentCardType type, ImageIcon image, int value) {
        super(image, value);
        this.type = type;
    }

    public static int obtainRentNumber(PropertyCardType type, int cardsNumber) {
        switch (type) {
            case BLUE:
                if (cardsNumber == 1) {
                    return 3;
                } else if (cardsNumber >= 2) {
                    return 8;
                }
                break;
            case BROWN:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber >= 2) {
                    return 2;
                }
                break;
            case GREEN:
                if (cardsNumber == 1) {
                    return 2;
                } else if (cardsNumber == 2) {
                    return 4;
                } else if (cardsNumber >= 3) {
                    return 7;
                }
                break;
            case LIGHTBLUE:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber == 2) {
                    return 2;
                } else if (cardsNumber >= 3) {
                    return 3;
                }
                break;
            case ORANGE:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber == 2) {
                    return 3;
                } else if (cardsNumber >= 3) {
                    return 5;
                }
                break;
            case PINK:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber == 2) {
                    return 2;
                } else if (cardsNumber >= 3) {
                    return 4;
                }
                break;
            case RAILROAD:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber == 2) {
                    return 2;
                } else if (cardsNumber == 3) {
                    return 3;
                } else if (cardsNumber >= 4) {
                    return 4;
                }
                break;
            case RED:
                if (cardsNumber == 1) {
                    return 2;
                } else if (cardsNumber == 2) {
                    return 3;
                } else if (cardsNumber >= 3) {
                    return 6;
                }
                break;
            case YELLOW:
                if (cardsNumber == 1) {
                    return 2;
                } else if (cardsNumber == 2) {
                    return 4;
                } else if (cardsNumber >= 3) {
                    return 6;
                }
                break;
            case UTILITY:
                if (cardsNumber == 1) {
                    return 1;
                } else if (cardsNumber >= 2) {
                    return 2;
                }
                break;
        }
        return 0;
    }

    public boolean whetherRentCardCanBeUsed(Card propertyCard) {
        PropertyCardType propertyCardType = null;
        if (propertyCard instanceof PropertyCard) {
            propertyCardType = ((PropertyCard) propertyCard).type;
        } else if (propertyCard instanceof PropertyWildCard) {
            propertyCardType = ((PropertyWildCard) propertyCard).currentType;
        } else {
            return false;
        }
        switch (propertyCardType) {
            case BLUE:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.BLUE_GREEN)) {
                    return true;
                }
                break;
            case BROWN:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.LIGHTBLUE_BROWN)) {
                    return true;
                }
                break;
            case GREEN:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.BLUE_GREEN)) {
                    return true;
                }
                break;
            case LIGHTBLUE:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.LIGHTBLUE_BROWN)) {
                    return true;
                }
                break;
            case ORANGE:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.PINK_ORANGE)) {
                    return true;
                }
                break;
            case PINK:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.PINK_ORANGE)) {
                    return true;
                }
                break;
            case RAILROAD:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RAILROAD_UTILITY)) {
                    return true;
                }
                break;
            case RED:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RED_YELLOW)) {
                    return true;
                }
                break;
            case YELLOW:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RED_YELLOW)) {
                    return true;
                }
                break;
            case UTILITY:
                if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RAILROAD_UTILITY)) {
                    return true;
                }
                break;
        }
        return false;
    }

    private void updatePlayerInteractiveState() {
        //每次使用RentCard后都代表玩家开启了一轮新的交互,因此需要确保上次交互中的数据被清除
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.isInAction()) {
                    owner.interactivePlayers.clear();
                    owner.singleActionCardsBuffer.clear();
                    owner.debt = 0;
                    owner.pledgeCardFromProperty.clear();
                    owner.pledgeCardFromBank.clear();
                }
            }
        }
    }

    //多色卡是选择一个玩家;双色卡是选择所有玩家
    @Override
    public void play() { //(被)使用
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) {
                    if (owner.isInAction()) {
                        if (owner.property.whetherHasPropertyCards(this)) {
                            updatePlayerInteractiveState();
                            //检查有无双倍卡
                            Integer oldRentCardIndex = null;
                            Integer doubleCardIndex = null;
                            for (int i = 0; i < owner.oneTurnCardsBuffer.size(); i++) {
                                if (owner.oneTurnCardsBuffer.get(i) instanceof RentCard) {
                                    oldRentCardIndex = i;
                                } else if (owner.oneTurnCardsBuffer.get(i) instanceof ActionCard) {
                                    if (((ActionCard) owner.oneTurnCardsBuffer.get(i)).type.equals(ActionCardType.DOUBLE_RENT)) {
                                        doubleCardIndex = i;
                                    }
                                }
                            }
                            //检查是否用了双倍卡,小心这种情况:双倍 租金卡 当前租金卡(双倍卡已经被用过了)和 双倍 其他卡 租金卡
                            boolean isDoubleRentUsed = ((oldRentCardIndex == null && doubleCardIndex != null) || (doubleCardIndex != null && oldRentCardIndex != null && oldRentCardIndex < doubleCardIndex));
                            Player tempOwner = this.owner;
                            //从玩家的手牌中消除
                            discard();
                            //强制打开玩家的房产:
                            tempOwner.whetherViewComponent = true;
                            for (Player player : Game.players) {
                                //将所有玩家和PlayerCardsPile设置为不可视
                                player.setVisible(false);
                                if (player.isPlayerTurn()) {
                                    player.playerCardsPile.setVisible(false);
                                }
                            }
                            tempOwner.property.setVisible(true);
                            tempOwner.property.reallocateAllCards();
                            tempOwner.property.closeButton.setVisible(false); //将关闭按钮隐藏,直到玩家选择完房产
                            //为使用RentCard的玩家的所有房产牌上增加临时的"choose"按钮
                            tempOwner.property.addAndPaintChooseButtons(this, isDoubleRentUsed);
                            //为choose按钮添加监听事件,获得:1.所选择的卡牌的种类 2.统计这种卡牌一共多少张 3.计算要支付的钱 4.查看玩家是否之前使用了翻倍租金卡,如果有,计算结果翻倍

                            //inTurnPlayer.actionNumber = inTurnPlayer.actionNumber - 1;
                            //上述扣除玩家行动次数的语句不能在这里写!必须要等到玩家在interactivePlayers中添加互动对象后才能扣除行动次数
                            //不然游戏会直接进入下一个回合
                        }
                    }
                }
            }
        }
    }

    @Override
    public void deposit() { //(被)储蓄-需要更新银行
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) {
                    if (owner.isInAction()) {
                        owner.oneTurnCardsBuffer.add(this);
                        for (int i = 0; i < owner.cardsTable.length; i++) { //把牌从玩家上手清除
                            if (owner.cardsTable[i] == this) {
                                owner.cardsTable[i] = null;
                                break;
                            }
                        }
                        if (!owner.whetherViewComponent) { //如果被调用的时候玩家正在看的是PlayerCardsPile
                            owner.playerCardsPile.updateAndShowCards(); //直接更新PlayerCardsPile
                        } else { //如果被调用的时候玩家正在看的是组件
                            owner.handCards.updateAndShowCards(); //直接更新HandCards
                        }
                        //将牌存进银行并刷新银行的状态
                        owner.bank.saveMoneyAndShowCards(this);
                        owner.actionNumber = owner.actionNumber - 1;
                    }
                }
            }
        }
    }

    @Override
    public void discard() { //(被)丢弃-仅供处于自己回合的玩家调用-需要更新玩家的HandCards或PlayerCardsPile的状态
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                for (int i = 0; i < owner.cardsTable.length; i++) { //把牌从玩家上手清除
                    if (owner.cardsTable[i] == this) {
                        owner.cardsTable[i] = null;
                        break;
                    }
                }
                if (!owner.whetherViewComponent) { //如果被调用的时候玩家正在看的是PlayerCardsPile
                    owner.playerCardsPile.updateAndShowCards(); //直接更新PlayerCardsPile
                } else { //如果被调用的时候玩家正在看的是组件
                    owner.handCards.updateAndShowCards(); //直接更新HandCards
                }
                Game.cardsPile.recycleCardIntoDiscardPile(this); //把牌塞进牌堆的废牌区
            }
        }
    }

    @Override
    public void move() {
        //先判断自己所属的容器:
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.containsCard(this)) {
                    if (owner.whetherViewComponent) { //玩家正在看HandCards
                        //给HandCards内的空位置加上按钮
                        owner.handCards.addAndPaintHereButtons(this);
                    } else { //玩家正在看PlayerCardsPile
                        //给PlayerCardsPile内的空位置加上按钮
                        owner.playerCardsPile.addAndPaintHereButtons(this);
                    }
                } else if (owner.bank.containsCard(this)) {
                    owner.bank.addAndPaintHereButtons(this);
                } else if (owner.property.containsCard(this)) {
                    owner.property.addAndPaintHereButtons(this);
                }
            }
        }
    }

    //-------绘制方法:

    @Override
    public void paintCard(Graphics g) {
        if (isDisplayable) {
            if (isCardFront) { //牌的正面
                g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            } else {
                g.drawImage(cardBackImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            }
        }
    }
}
