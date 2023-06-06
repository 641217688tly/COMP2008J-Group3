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
import Listener.ModuleListener.CardsListener.CardListener;
import Module.PlayerAndComponents.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/*
不同的回合中,卡牌的如下属性可能发生变化:
1.位置: this.setBounds(cardJPanelX, cardJPanelY, cardWidth, cardHeight);
2.正反面:isCardFront
3.按钮的开关: 1.使用按钮的开关 2.储蓄按钮的开关 3.丢弃按钮的开关 (多色卡: 4.换色按钮的开关)
4.是否可视: isDisplayable
5.主人: owner
*/

public abstract class Card extends JPanel implements ICard {
    public static int cardHeight = (ApplicationStart.screenHeight) / 5;
    public static int cardWidth = (ApplicationStart.screenWidth) / 12;
    public Player owner = null;
    protected int cardJPanelX;
    protected int cardJPanelY;
    protected int value;
    protected boolean isCardFront = false; //默认卡牌是背面的
    protected boolean isDisplayable = false; //默认卡牌时不被绘制的
    protected final ImageIcon cardBackImage = new ImageIcon("images/Card/CardsBack.jpg"); //卡牌的背面
    protected ImageIcon cardImage; //卡牌的图片

    protected CardListener cardListener;
    protected JButton playButton; //使用按钮
    protected JButton depositButton;//当做钱存进银行
    protected JButton discardButton;//丢弃按钮
    protected JButton moveButton; //移动卡牌的按钮
    protected boolean playButtonSwitch = false;
    protected boolean depositButtonSwitch = false;
    protected boolean discardButtonSwitch = false;
    protected boolean moveButtonSwitch = false;

    public Card(ImageIcon image, int value) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        setOpaque(false);
        this.cardImage = image;
        this.value = value;
        this.cardListener = new CardListener();
        initButtons();
    }

    private void initButtons() {
        this.playButton = createButton("Play", cardWidth / 5, 0, 3 * cardWidth / 5, cardHeight / 10, this.cardListener.playCardButtonListener(this));
        this.depositButton = createButton("S", 2 * cardWidth / 3, 7 * cardHeight / 8, cardWidth / 3, cardHeight / 8, this.cardListener.depositCardButtonListener(this));
        this.moveButton = createButton("M", 1 * cardWidth / 3, 7 * cardHeight / 8, cardWidth / 3, cardHeight / 8, this.cardListener.moveCardButtonListener(this));
        this.discardButton = createButton("T", 0, 7 * cardHeight / 8, cardWidth / 3, cardHeight / 8, this.cardListener.discardCardButtonListener(this));
        this.add(this.playButton);
        this.add(this.depositButton);
        this.add(this.discardButton);
        this.add(this.moveButton);
    }

    private JButton createButton(String text, int x, int y, int buttonWidth, int buttonHeight, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, buttonWidth, buttonHeight);
        Font buttonFont = new Font("Arial", Font.BOLD, 7);
        button.setFont(buttonFont); // 设置按钮的字体和字体大小
        button.addActionListener(listener);
        return button;
    }

    public void setCardJPanelBounds(int cardJPanelX, int cardJPanelY) {
        this.cardJPanelX = cardJPanelX;
        this.cardJPanelY = cardJPanelY;
        this.setBounds(cardJPanelX, cardJPanelY, cardWidth, cardHeight);
    }

    public void setIsDisplayable(boolean isDisplayable) {
        this.isDisplayable = isDisplayable;
    }

    public void setIsCardFront(boolean isCardFront) {
        this.isCardFront = isCardFront;
    }

    public void openPlayButtonSwitch(boolean playButtonSwitch) {
        this.playButtonSwitch = playButtonSwitch;
    }

    public void openDepositButtonSwitch(boolean depositButtonSwitch) {
        this.depositButtonSwitch = depositButtonSwitch;
    }

    public void openDiscardButtonSwitch(boolean discardButtonSwitch) {
        this.discardButtonSwitch = discardButtonSwitch;
    }

    public void openMoveButtonSwitch(boolean moveButtonSwitch) {
        this.moveButtonSwitch = moveButtonSwitch;
    }

    //-------绘制方法:

    protected abstract void paintCard(Graphics g);

    protected void controlButtons() {
        if (playButtonSwitch) {
            playButton.setVisible(true);
        } else {
            playButton.setVisible(false);
        }
        if (depositButtonSwitch) {
            depositButton.setVisible(true);
        } else {
            depositButton.setVisible(false);
        }
        if (discardButtonSwitch) {
            discardButton.setVisible(true);
        } else {
            discardButton.setVisible(false);
        }
        if (moveButtonSwitch) {
            moveButton.setVisible(true);
        } else {
            moveButton.setVisible(false);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isDisplayable) {
            this.setVisible(true);
        } else {
            this.setVisible(false);
        }
        controlButtons(); //不断刷新着纸牌的按钮的状态
        paintCard(g);
    }
}


