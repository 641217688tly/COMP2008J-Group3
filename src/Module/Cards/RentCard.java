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

    public static int obtainRentNumber(PropertyCardType type, int cardsNumber) {
        if (type.equals(PropertyCardType.BLUE)) {
            if (cardsNumber == 1) {
                return 3;
            } else if (cardsNumber >= 2) {
                return 8;
            }
        } else if (type.equals(PropertyCardType.BROWN)) {
            if (cardsNumber == 1) {
                return 1;
            } else if (cardsNumber >= 2) {
                return 2;
            }
        } else if (type.equals(PropertyCardType.GREEN)) {
            if (cardsNumber == 1) {
                return 2;
            } else if (cardsNumber == 2) {
                return 4;
            } else if (cardsNumber >= 3) {
                return 7;
            }
        } else if (type.equals(PropertyCardType.LIGHTBLUE)) {
            if (cardsNumber == 1) {
                return 1;
            } else if (cardsNumber == 2) {
                return 2;
            } else if (cardsNumber >= 3) {
                return 3;
            }
        } else if (type.equals(PropertyCardType.ORANGE)) {
            if (cardsNumber == 1) {
                return 1;
            } else if (cardsNumber == 2) {
                return 3;
            } else if (cardsNumber >= 3) {
                return 5;
            }
        } else if (type.equals(PropertyCardType.PINK)) {
            if (cardsNumber == 1) {
                return 1;
            } else if (cardsNumber == 2) {
                return 2;
            } else if (cardsNumber >= 3) {
                return 4;
            }
        } else if (type.equals(PropertyCardType.RAILROAD)) {
            if (cardsNumber == 1) {
                return 1;
            } else if (cardsNumber == 2) {
                return 2;
            } else if (cardsNumber == 3) {
                return 3;
            } else if (cardsNumber >= 4) {
                return 4;
            }
        } else if (type.equals(PropertyCardType.RED)) {
            if (cardsNumber == 1) {
                return 2;
            } else if (cardsNumber == 2) {
                return 3;
            } else if (cardsNumber >= 3) {
                return 6;
            }
        } else if (type.equals(PropertyCardType.YELLOW)) {
            if (cardsNumber == 1) {
                return 2;
            } else if (cardsNumber == 2) {
                return 4;
            } else if (cardsNumber >= 3) {
                return 6;
            }
        } else if (type.equals(PropertyCardType.UTILITY)) {
            if (cardsNumber == 1) {
                return 1;
            } else if (cardsNumber >= 2) {
                return 2;
            }
        }
        return 0;
    }

    public boolean whetherRentCardCanBeUsed(Card propertyCard) {
        boolean flag = false;
        PropertyCardType propertyCardType = null;
        if (propertyCard instanceof PropertyCard) {
            propertyCardType = ((PropertyCard) propertyCard).type;
        } else if (propertyCard instanceof PropertyWildCard) {
            propertyCardType = ((PropertyWildCard) propertyCard).currentType;
        } else {
            return flag;
        }
        if (propertyCardType.equals(PropertyCardType.BLUE)) {
            if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.BLUE_GREEN)) {
                flag = true;
            }
        } else if (propertyCardType.equals(PropertyCardType.BROWN)) {
            if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.LIGHTBLUE_BROWN)) {
                flag = true;
            }
        } else if (propertyCardType.equals(PropertyCardType.GREEN)) {
            if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.BLUE_GREEN)) {
                flag = true;
            }
        } else if (propertyCardType.equals(PropertyCardType.LIGHTBLUE)) {
            if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.LIGHTBLUE_BROWN)) {
                flag = true;
            }
        } else if (propertyCardType.equals(PropertyCardType.ORANGE)) {
            if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.PINK_ORANGE)) {
                flag = true;
            }
        } else if (propertyCardType.equals(PropertyCardType.PINK)) {
            if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.PINK_ORANGE)) {
                flag = true;
            }
        } else if (propertyCardType.equals(PropertyCardType.RAILROAD)) {
            if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RAILROAD_UTILITY)) {
                flag = true;
            }
        } else if (propertyCardType.equals(PropertyCardType.RED)) {
            if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RED_YELLOW)) {
                flag = true;
            }
        } else if (propertyCardType.equals(PropertyCardType.YELLOW)) {
            if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RED_YELLOW)) {
                flag = true;
            }
        } else if (propertyCardType.equals(PropertyCardType.UTILITY)) {
            if (this.type.equals(RentCardType.WILD_RENT) || this.type.equals(RentCardType.RAILROAD_UTILITY)) {
                flag = true;
            }
        }
        return flag;
    }

    public RentCard(RentCardType type, ImageIcon image, int value) {
        super(image, value);
        this.type = type;
    }

    @Override
    //多色卡是选择一个玩家;双色卡是选择所有玩家
    public void play() { //(被)使用
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.actionNumber > 0) {
                    if (owner.property.whetherRentCardCanPlay(this)) {
                        //检查有无双倍卡
                        Integer oldRentCardIndex = null;
                        Integer doubleCardIndex = null;
                        for (int i = 0; i < owner.cardsBuffer.size(); i++) {
                            if (owner.cardsBuffer.get(i) instanceof RentCard) {
                                oldRentCardIndex = i;
                            } else if (owner.cardsBuffer.get(i) instanceof ActionCard) {
                                if (((ActionCard) owner.cardsBuffer.get(i)).type.equals(ActionCardType.DOUBLE_RENT)) {
                                    doubleCardIndex = i;
                                }
                            }
                        }
                        //检查是否用了双倍卡,小心这种情况:双倍 租金卡 当前租金卡(双倍卡已经被用过了)和 双倍 其他卡 租金卡
                        boolean isDoubleRentUsed = (oldRentCardIndex == null && doubleCardIndex != null) || (doubleCardIndex != null && oldRentCardIndex != null && oldRentCardIndex < doubleCardIndex);
                        //将新的RentCard添加到cardsBuffer中
                        owner.cardsBuffer.add(this);
                        Player tempOwner = this.owner;
                        //从玩家的手牌中消除
                        discard();
                        //强制打开玩家的房产:
                        for (Player player : Game.players) {
                            //将所有玩家和PlayerCardsPile设置为不可视
                            player.setVisible(false);
                            if (player.isPlayerTurn()) {
                                player.playerCardsPile.setVisible(false);
                            }
                        }
                        tempOwner.whetherViewComponent = true;
                        for (Player player : Game.players) {
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
                        tempOwner.actionNumber = tempOwner.actionNumber - 1;
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
                    owner.cardsBuffer.add(this);
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
                    //将牌上的按钮全部隐藏:
                    this.playButtonSwitch = false;
                    this.depositButtonSwitch = false;
                    this.discardButtonSwitch = false;
                    this.moveButtonSwitch = false;
                    controlButtons();
                    //将牌存进银行并刷新银行的状态
                    owner.bank.saveMoneyAndShowCards(this);
                    owner.actionNumber = owner.actionNumber - 1;
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
                //将牌上的按钮全部隐藏:
                this.playButtonSwitch = false;
                this.depositButtonSwitch = false;
                this.discardButtonSwitch = false;
                this.moveButtonSwitch = false;
                controlButtons();
                //将牌扔进废牌堆
                this.owner = null;
                Game.cardsPile.recycleCardIntoDiscardPile(this); //把牌塞进牌堆的废牌区
            }
        }
    }

    @Override
    public void move() {
        //先判断自己所属的容器:
        if (owner != null) {
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
