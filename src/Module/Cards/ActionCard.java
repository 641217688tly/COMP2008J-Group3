package Module.Cards;

import Module.Cards.CardsEnum.ActionCardType;

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
        actionCards.add(new ActionCard(ActionCardType.DOUBLE_RENT, new ImageIcon("images/Card/ActionCardType/ActionCardDoubleRent.jpg"), 1));
        actionCards.add(new ActionCard(ActionCardType.DOUBLE_RENT, new ImageIcon("images/Card/ActionCardType/ActionCardDoubleRent.jpg"), 1));
        actionCards.add(new ActionCard(ActionCardType.DEAL_BREAKER, new ImageIcon("images/Card/ActionCardType/ActionCardDealBreaker.jpg"), 5));
        actionCards.add(new ActionCard(ActionCardType.DEAL_BREAKER, new ImageIcon("images/Card/ActionCardType/ActionCardDealBreaker.jpg"), 5));
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.JUST_SAY_NO, new ImageIcon("images/Card/ActionCardType/ActionCardType/ActionCardSayNo.jpg"), 4));
        }
        for (int i = 0; i < 4; i++) {
            actionCards.add(new ActionCard(ActionCardType.FORCE_DEAL, new ImageIcon("images/Card/ActionCardType/ActionCardForcedDeal.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.DEBT_COLLECTOR, new ImageIcon("images/Card/ActionCardType/ActionCardDebtCollector.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.BIRTHDAY, new ImageIcon("images/Card/ActionCardType/ActionCardBirthday.jpg"), 2));
        }
        for (int i = 0; i < 10; i++) {
            actionCards.add(new ActionCard(ActionCardType.PASS_GO, new ImageIcon("images/Card/ActionCardType/ActionCardPassGo.jpg"), 1));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.HOUSE, new ImageIcon("images/Card/ActionCardType/ActionCardHouse.jpg"), 3));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.HOTEL, new ImageIcon("images/Card/ActionCardType/ActionCardHotel.jpg"), 4));
        }
        for (int i = 0; i < 3; i++) {
            actionCards.add(new ActionCard(ActionCardType.SLY_DEAL, new ImageIcon("images/Card/ActionCardType/ActionCardSlyDeal.jpg"), 3));
        }
        return actionCards;
    }


    @Override
    public void deposit() {

    }

    @Override
    public void play() {

    }

    @Override
    public void drawCard(Graphics g) {
        if (isDisplayable) {
            if (isCardFront) { //牌的正面
                g.drawImage(cardImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            } else {
                g.drawImage(cardBackImage.getImage(), 0, 0, cardWidth, cardHeight, null);
            }
        }
    }
}
