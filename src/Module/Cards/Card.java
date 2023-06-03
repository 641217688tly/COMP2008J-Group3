/*
HandCards:
    Properties (28):
    2 Blue, 2 Brown, 2 Utility, 3 Green, 3 Yellow, 3 Red, 3 Orange, 3 Pink,
    3 Light Blue, 4 Railroad.

    Property Wildcards (11):
    1 Dark Blue/Green, 1 Green/Railroad, 1 Utility/Railroad,1 Light Blue/Railroad,
    1 Light Blue/Brown,2 Pink/Orange, 2 Red/Yellow, 2 multi-colour Property Wildcards.

    Action HandCards(34):
    2 Deal Breaker, 3 Just Say No, 3 Sly Deal, 4 Force Deal,
    3 Debt Collector, 3 It’s My Birthday, 10 Pass Go, 3 House, 3 Hotel,
    2 Double The Rent HandCards

    Rent HandCards(13):
    2 Dark Blue/Green, 2 Red/Yellow, 2 Pink/Orange, 2 Light Blue/Brown,
    2 Railroad/Utility, 3 Wild Rent

    Money HandCards(20):
    6 cards of 1M, 5 cards of 2M, 3 cards of 3M,
    3 cards of 4M, 2 cards of 5M, 1 card of 10M.
*/
package Module.Cards;

import GUI.ApplicationStart;

import javax.swing.*;
import java.awt.*;


public abstract class Card extends JPanel implements ICard {
    public static int cardHeight = (ApplicationStart.screenHeight) / 5;
    public static int cardWidth = (ApplicationStart.screenWidth) / 12;
    protected int cardJPanelX;
    protected int cardJPanelY;
    protected int value;
    protected boolean isCardFront = false; //默认卡牌是背面的
    protected boolean isDisplayable = false; //默认卡牌时不被绘制的
    protected final ImageIcon cardBackImage = new ImageIcon("images/Card/CardsBack.jpg"); //卡牌的背面
    protected ImageIcon cardImage; //卡牌的图片

    protected JLabel cardLabel;
    protected JButton useButton;
    protected JButton depositButton;//当做钱存进银行


    public Card(ImageIcon image, int value) {
        this.cardImage = image;
        this.value = value;
    }

    public void setCardJPanelBounds(int cardJPanelX, int cardJPanelY) {
        this.cardJPanelX = cardJPanelX;
        this.cardJPanelY = cardJPanelY;
        this.setBounds(cardJPanelX, cardJPanelY, cardWidth, cardHeight);
    }

    protected abstract void drawCard(Graphics g);

    @Override
    protected void paintComponent(Graphics g) {
        if (isDisplayable) {
            drawCard(g);
        }
    }

    public void setIsDisplayable(boolean isDisplayable) {
        this.isDisplayable = isDisplayable;
    }

    public void setIsCardFront(boolean isCardFront) {
        this.isCardFront = isCardFront;
    }
}


