package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.PlayerListener;
import Module.Cards.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends JPanel {
    public static int playerHeight = (ApplicationStart.screenHeight) / 5;
    public static int playerWidth = (ApplicationStart.screenWidth) / 12;
    public static Image[] images = new Image[5];

    public String name;
    public ArrayList<Card> cardsList;
    public int playerJPanelX;
    public int playerJPanelY;
    public boolean whetherViewComponent = false;
    public int actionNumber = 3; //行动次数
    public boolean isDisplayable = true;
    private boolean isPlayerTurn = false;

    //组件:
    private Image playerImage;
    public Bank bank; //玩家的银行
    public Property property; //玩家的房产区
    public HandCards handCards; //玩家的手牌
    public PlayerCardsPile playerCardsPile; //玩家左边的牌堆
    private PlayerListener playerListener;
    private JButton handCardsButton;
    private JButton bankButton;
    private JButton propertyButton;

    static {
        loadAndSetPlayerImage();
    }

    private static void loadAndSetPlayerImage() {
        for (int i = 0; i < 5; i++) {
            try {
                Player.images[i] = ImageIO.read(new File("images/Module/PlayerAndComponents/Player/player" + (i + 1) + ".jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Player(String name, Image playerImage, int playerJPanelX, int playerJPanelY) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        setOpaque(false);
        this.name = name;
        this.playerImage = playerImage;
        this.playerJPanelX = playerJPanelX;
        this.playerJPanelY = playerJPanelY;
        this.cardsList = new ArrayList<>();
        this.playerCardsPile = new PlayerCardsPile(this);
        this.handCards = new HandCards(this);
        this.bank = new Bank(this);
        this.property = new Property(this);
        this.playerListener = new PlayerListener();
        initButtons();

        this.setBounds(this.playerJPanelX, this.playerJPanelY, playerWidth, playerHeight); // 设置Player的大小和位置
    }

    private void initButtons() {
        handCardsButton = createButton("C", playerWidth * 2 / 3, 0, this.playerListener.handCardsButtonListener(this));
        bankButton = createButton("B", 0, playerHeight * 3 / 4, this.playerListener.bankButtonListener(this));
        propertyButton = createButton("P", playerWidth * 2 / 3, playerHeight * 3 / 4, this.playerListener.propertyButtonListener(this));
        this.add(handCardsButton);
        this.add(bankButton);
        this.add(propertyButton);
    }

    private JButton createButton(String text, int x, int y, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, playerWidth / 3, playerHeight / 4);

        Font buttonFont = new Font("Arial", Font.BOLD, 10); // 设置形状为粗体，大小为10的Arial字体
        button.setFont(buttonFont); // 设置按钮的字体和字体大小
        button.addActionListener(listener);
        return button;
    }

    public void drawCards(ArrayList<Card> cards) { //抽牌
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).owner = this;
            cards.get(i).setIsCardFront(true);
            cards.get(i).setIsDisplayable(false);
        }
        this.cardsList.addAll(cards);
    }

    public void moveToNextTurn() {
        //先把所有卡牌的按钮都隐藏:
        this.actionNumber = 3;
        if (isPlayerTurn) { //该玩家的回合
            //手牌的三个按钮都被允许使用
            for (Card card : cardsList) {
                card.openPlayButtonSwitch(true);
                card.openDepositButtonSwitch(true);
                card.openDiscardButtonSwitch(true);
            }
            for (Card propertyCard : property.cardsList) {
                // TODO 双色或多色的房产允许换色,换色的按钮需要被呈现

            }
            for (Card moneyCard : bank.cardsList) {
                moneyCard.openPlayButtonSwitch(true);
                moneyCard.openDepositButtonSwitch(true);
                moneyCard.openDiscardButtonSwitch(true);
            }
        } else {
            for (Card card : cardsList) {
                card.openPlayButtonSwitch(false);
                card.openDepositButtonSwitch(false);
                card.openDiscardButtonSwitch(false);
            }
            for (Card moneyCard : bank.cardsList) {
                moneyCard.openPlayButtonSwitch(false);
                moneyCard.openDepositButtonSwitch(false);
                moneyCard.openDiscardButtonSwitch(false);
            }
            for (Card propertyCard : property.cardsList) {
                propertyCard.openPlayButtonSwitch(false);
                propertyCard.openDepositButtonSwitch(false);
                propertyCard.openDiscardButtonSwitch(false);
            }
        }
        handCards.updateAndShowCards();
        if (this.isPlayerTurn) {
            playerCardsPile.updateAndShowCards();
        }
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void setPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    //-------绘制方法:

    private void paintPlayerImage(Graphics g) {
        if (playerImage != null) { // 如果背景图片已加载
            g.drawImage(playerImage, 0, 0, playerWidth, playerHeight, null);
        }
    }

    private void paintPlayerName(Graphics g) { //绘制玩家的名字
        if (playerImage != null) { // 如果背景图片已加载
            g.setColor(Color.BLACK); // 设置文本颜色
            g.setFont(new Font("Arial", Font.BOLD, 20)); // 设置字体和大小
            FontMetrics fm = g.getFontMetrics();
            int textHeight = fm.getAscent(); //获得Name的基线高度
            g.drawString(name, 0, textHeight); // 在图片内部的左上角绘制玩家名字
        }
    }

    private void paintChosenMark(Graphics g) { //被允许行动的玩家的头像上将会有标记
        if (isPlayerTurn) {
            g.setColor(Color.GREEN); // 设置文本颜色
            g.setFont(new Font("Arial", Font.BOLD, 20)); // 设置字体和大小
            g.drawString("Move", Player.playerWidth / 5 + Player.playerWidth / 10, 3 * Player.playerHeight / 8); // 在图片内部的左上角绘制玩家名字
        }
    }

    private void paintPlayerActionNumber(Graphics g) { //处于自己回合的玩家头像上将会有行动次数
        if (isPlayerTurn) {
            g.setColor(Color.RED); // 设置文本颜色
            g.setFont(new Font("Arial", Font.BOLD, 15)); // 设置字体和大小
            g.drawString("Remaining: " + this.actionNumber, Player.playerWidth / 10, 6 * Player.playerHeight / 8 - Player.playerHeight / 16); // 在图片内部的左上角绘制玩家名字
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isDisplayable) {
            super.paintComponent(g); // 调用父类方法以确保正常绘制
            paintPlayerImage(g);
            paintPlayerName(g);
            paintChosenMark(g);
            paintPlayerActionNumber(g);
        }
    }

}
