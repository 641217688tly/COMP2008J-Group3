package Module.Cards;

import Module.Cards.CardsEnum.PropertyCardType;
import Module.Game;
import Module.Cards.CardsEnum.ActionCardType;
import Module.PlayerAndComponents.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ActionCard extends Card {
    public ActionCardType type;

    public ActionCard(ActionCardType type, ImageIcon image, int value) {
        super(image, value);
        this.type = type;
    }

    public static ArrayList<Card> initializeCardsForCardsPile() {
        ArrayList<Card> actionCards = new ArrayList<>();
        actionCards.add(new ActionCard(ActionCardType.DOUBLE_RENT, new ImageIcon("images/Card/ActionCard/ActionCardDoubleRent.jpg"), 1));
        actionCards.add(new ActionCard(ActionCardType.DOUBLE_RENT, new ImageIcon("images/Card/ActionCard/ActionCardDoubleRent.jpg"), 1));
        actionCards.add(new ActionCard(ActionCardType.DEAL_BREAKER, new ImageIcon("images/Card/ActionCard/ActionCardDealBreaker.jpg"), 5));
        actionCards.add(new ActionCard(ActionCardType.DEAL_BREAKER, new ImageIcon("images/Card/ActionCard/ActionCardDealBreaker.jpg"), 5));
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.JUST_SAY_NO, new ImageIcon("images/Card/ActionCard/ActionCardSayNo.jpg"), 4));
        }
        for (int i = 0; i < 4; i++) {
            actionCards.add(new ActionCard(ActionCardType.FORCE_DEAL, new ImageIcon("images/Card/ActionCard/ActionCardForcedDeal.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.DEBT_COLLECTOR, new ImageIcon("images/Card/ActionCard/ActionCardDebtCollector.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.BIRTHDAY, new ImageIcon("images/Card/ActionCard/ActionCardBirthday.jpg"), 2));
        }
        for (int i = 0; i < 10; i++) {
            actionCards.add(new ActionCard(ActionCardType.PASS_GO, new ImageIcon("images/Card/ActionCard/ActionCardPassGo.jpg"), 1));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.HOUSE, new ImageIcon("images/Card/ActionCard/ActionCardHouse.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.HOTEL, new ImageIcon("images/Card/ActionCard/ActionCardHotel.jpg"), 4));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.SLY_DEAL, new ImageIcon("images/Card/ActionCard/ActionCardSlyDeal.jpg"), 3));
        }
        return actionCards;
    }

    private void updatePlayerInteractiveState() {
        //每次使用ActionCard后都代表玩家开启了一轮新的交互,因此需要确保上次交互中的数据被清除
        if (owner != null) {
            if (owner.isPlayerTurn()) {
                if (owner.isInAction()) {
                    owner.debt = 0;
                    owner.interactivePlayers.clear();
                    owner.singleActionCardsBuffer.clear();
                    owner.pledgeCardFromProperty.clear();
                    owner.pledgeCardFromBank.clear();
                }
            }
        }
    }

    private void playHouseOrHotel() {
        if (this.type.equals(ActionCardType.HOUSE) || this.type.equals(ActionCardType.HOTEL)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
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
                            //将牌存进房产中并刷新房产的状态
                            owner.property.placePropertyCardAndShowTable(this);
                            owner.actionNumber = owner.actionNumber - 1;
                        }
                    }
                }
            }
        }
    }

    private void playDoubleRent() {
        if (this.type.equals(ActionCardType.DOUBLE_RENT)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            owner.oneTurnCardsBuffer.add(this); //将RentCard加入到玩家的cardsBuffer中
                            owner.actionNumber = owner.actionNumber - 1;
                            discard();
                        }
                    }
                }
            }
        }
    }

    private void playPassGo() {
        if (this.type.equals(ActionCardType.PASS_GO)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            owner.drawCards(Game.cardsPile.drawCardFromDrawPile(2));
                            owner.actionNumber = owner.actionNumber - 1;
                            discard();
                        }
                    }
                }
            }
        }
    }

    private void playBirthday() {
        if (this.type.equals(ActionCardType.BIRTHDAY)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            updatePlayerInteractiveState();
                            //将所有玩家都加入交互队列中
                            for (int i = 0; i < Game.players.size(); i++) {
                                if (Game.players.get(i) != owner) {
                                    owner.interactivePlayers.add(Game.players.get(i));
                                }
                            }
                            //玩家的行动次数减少
                            owner.actionNumber = owner.actionNumber - 1;
                            owner.setIsInAction(false);
                            owner.interactivePlayers.get(0).setIsInAction(true);
                            for (Player player : Game.players) {
                                player.setVisible(true);
                                if (player.isPlayerTurn()) {
                                    player.playerCardsPile.updateAndShowCards();
                                }
                            }
                            //让需要交租的第一个玩家进行交互
                            owner.interactivePlayers.get(0).payForMoney(owner, 2);
                            discard();
                        }
                    }
                }
            }
        }
    }

    private void playDebtCollector() {
        if (this.type.equals(ActionCardType.DEBT_COLLECTOR)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            updatePlayerInteractiveState();
                            //将所有玩家都加入交互队列中
                            for (int i = 0; i < Game.players.size(); i++) {
                                if (Game.players.get(i) != owner) {
                                    owner.interactivePlayers.add(Game.players.get(i));
                                }
                            }
                            //玩家的行动次数减少
                            owner.actionNumber = owner.actionNumber - 1;
                            owner.setIsInAction(false);
                            owner.interactivePlayers.get(0).setIsInAction(true);
                            for (Player player : Game.players) {
                                player.setVisible(true);
                                if (player.isPlayerTurn()) {
                                    player.playerCardsPile.updateAndShowCards();
                                }
                            }
                            //让需要交租的第一个玩家进行交互
                            owner.interactivePlayers.get(0).payForMoney(owner, 5);
                            discard();
                        }
                    }
                }
            }
        }
    }

    private boolean whetherSlyDealOrForcedDealCardCanPlay() {
        for (Player player : Game.players) {
            if (player != owner) {
                for (PropertyCardType propertyType : PropertyCardType.values()) {
                    if ((player.property.propertyNumberMap.get(propertyType) > 0 && player.property.propertyNumberMap.get(propertyType) < PropertyCard.judgeCompleteSetNumber(propertyType)) || player.property.propertyNumberMap.get(propertyType) > PropertyCard.judgeCompleteSetNumber(propertyType)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void playForcedDeal() { //偷一张房产卡
        if (this.type.equals(ActionCardType.FORCE_DEAL)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            if (whetherSlyDealOrForcedDealCardCanPlay()) { //先判断场上的玩家是否有不成套的房产
                                //再判断自己是否有不成套的房产可以用于交易:
                                boolean flag = false;
                                for (PropertyCardType propertyType : PropertyCardType.values()) {
                                    if ((owner.property.propertyNumberMap.get(propertyType) > 0 && owner.property.propertyNumberMap.get(propertyType) < PropertyCard.judgeCompleteSetNumber(propertyType)) || owner.property.propertyNumberMap.get(propertyType) > PropertyCard.judgeCompleteSetNumber(propertyType)) {
                                        flag = true;
                                        break;
                                    }
                                }
                                if (flag) { //自己也有不成套的房产可以用于交换
                                    updatePlayerInteractiveState();
                                    //为除了卡牌使用者外的所有玩家加上用于被选择的按钮
                                    owner.addAndPaintForcedDealChooseButtons(owner);
                                    owner.oneTurnCardsBuffer.add(this);
                                    //为玩家的房产添加上选择按钮:
                                    owner.property.addAndPaintSwapPropertyButtons();
                                    //强制打开该玩家的房产,让他选择自己之后要用于交易的房产牌:
                                    for (Player player : Game.players) {
                                        player.setVisible(false);
                                        if (player.isPlayerTurn()) {
                                            player.playerCardsPile.setVisible(false);
                                        }
                                    }
                                    owner.whetherViewComponent = true;
                                    owner.property.setVisible(true);
                                    owner.property.reallocateAllCards();

                                    discard();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void playSlyDeal() { //偷一张房产卡
        if (this.type.equals(ActionCardType.SLY_DEAL)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            if (whetherSlyDealOrForcedDealCardCanPlay()) { //先判断场上的玩家是否有房产
                                updatePlayerInteractiveState();
                                //如果有,则为除了卡牌使用者外的所有玩家加上用于被选择的按钮
                                owner.addAndPaintSlyDealChooseButtons(owner);
                                owner.oneTurnCardsBuffer.add(this);
                                discard();
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean whetherDealBreakerCardCanPlay() { //判断场上的玩家是否有一整套房产
        for (Player player : Game.players) {
            if (player != owner) {
                for (PropertyCardType propertyType : PropertyCardType.values()) {
                    if (player.property.propertyNumberMap.get(propertyType) >= PropertyCard.judgeCompleteSetNumber(propertyType)) { //如果有一套完整的房产
                        return true;
                    }
                }
            }

        }
        return false;
    }

    //TODO 好像还会出现多色牌的PropertyType判定不正常的情况
    private void playDealBreaker() { //偷一整套房产
        if (this.type.equals(ActionCardType.DEAL_BREAKER)) {
            if (owner != null) {
                if (owner.isPlayerTurn()) {
                    if (owner.actionNumber > 0) {
                        if (owner.isInAction()) {
                            if (whetherDealBreakerCardCanPlay()) { //先判断场上的玩家是否有房产
                                updatePlayerInteractiveState();
                                //如果有,则为除了卡牌使用者外的所有玩家加上用于被选择的按钮
                                owner.addAndPaintDealBreakerChooseButtons(owner);
                                owner.oneTurnCardsBuffer.add(this);
                                discard();
                            }
                        }
                    }
                }
            }
        }
    }

    private void playJustSayNo() {
        if (this.type.equals(ActionCardType.JUST_SAY_NO)) {
            if (owner != null) {
                if (owner.isInAction()) { //只要玩家在行动中,他就有权使用SayNoCard
                    if (owner.isPlayerTurn()) { //使用SayNo牌的是处于自己回合的玩家
                        owner.oneTurnCardsBuffer.add(this);
                        owner.singleActionCardsBuffer.add(this);
                    } else {
                        if (owner.isInAction()) {//当前使用SayNo牌的是正在行动中的玩家
                            owner.singleActionCardsBuffer.add(this);
                        }
                    }
                    //关闭手牌框:
                    owner.whetherViewComponent = false;
                    owner.handCards.closeButton.setVisible(true);
                    owner.handCards.setVisible(false);
                    owner.handCards.removeAll(); //主要是防止JPanel组件上的冲突

                    Player tempOwner = owner;
                    discard(); //因为discard()会将卡牌的owner设置成null,因此必须用一个变量暂存owner
                    //让对手行动
                    tempOwner.interactivePlayers.get(0).setIsInAction(true);
                    tempOwner.setIsInAction(false); //暂时结束自己的行动
                    tempOwner.interactivePlayers.get(0).respondSayNoCard(); //将选择权交给对手

                    for (Player player : Game.players) {
                        player.setVisible(true);
                        if (player.isPlayerTurn()) {
                            player.playerCardsPile.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void play() { //(被)使用
        playHouseOrHotel();
        playDoubleRent();
        playPassGo();
        playJustSayNo();
        playBirthday();
        playDebtCollector();
        playSlyDeal();
        playDealBreaker();
        playForcedDeal();
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
    }

    @Override
    public void discard() { //(被)丢弃-仅供处于自己回合的玩家调用-需要更新玩家的HandCards或PlayerCardsPile的状态
        if (owner != null) {
            if (owner.isPlayerTurn() || owner.isInAction()) {
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
