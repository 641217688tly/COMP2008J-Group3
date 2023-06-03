/*
Player类应该主管玩家的一系列动作;同时应该具有如玩家的房产,玩家的银行,玩家的手牌等属性:
属性:
玩家名
玩家的手牌
玩家的房产
玩家的银行
玩家的行动次数(default=3) (待定)
倒计时(玩家需要在指定的时间内打出手牌,否则将自动pass或者自动交租/交房产)

方法:
放置房产
存钱
交租
pass(行动次数没使用完前可以跳过自己的回合)
改变房产颜色
打出手牌:
    收租
    收钱
    再抽三张
    say no(不消耗行动次数)
    ....
*/
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
    public int playerJPanelX;
    public int playerJPanelY;
    private final int actionNumbers = 3; //行动次数
    private final int countDown = 30; //倒计时
    private boolean isPlayerTurn = false;
    private Image playerImage;
    private ArrayList<Card> cardsList;
    //组件:
    public Bank bank; //玩家的银行
    public Property property; //玩家的房产区
    public HandCards handCards; //玩家的手牌
    private PlayerCardsPile playerCardsPile; //玩家的手牌区
    private PlayerListener playerListener;
    private JButton playerCardsButton;
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
        this.handCards = new HandCards(this);
        this.bank = new Bank(this);
        this.property = new Property(this);
        this.playerCardsPile = new PlayerCardsPile(this);
        this.playerListener = new PlayerListener();

        this.setBounds(this.playerJPanelX, this.playerJPanelY, playerWidth, playerHeight); // 设置Player的大小和位置
        initButtons();
    }


    private void initButtons() {
        playerCardsButton = createButton("C", playerWidth * 2 / 3, 0, this.playerListener.playerCardsButtonListener(this.playerCardsPile));
        bankButton = createButton("B", 0, playerHeight * 3 / 4, this.playerListener.bankButtonListener(this.bank));
        propertyButton = createButton("P", playerWidth * 2 / 3, playerHeight * 3 / 4, this.playerListener.propertyButtonListener(this.property));
        this.add(playerCardsButton);
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

    private void drawPlayerImage(Graphics g) {
        if (playerImage != null) { // 如果背景图片已加载
            g.drawImage(playerImage, 0, 0, playerWidth, playerHeight, null);
        }
    }

    private void drawPlayerName(Graphics g) {
        if (playerImage != null) { // 如果背景图片已加载
            g.setColor(Color.BLACK); // 设置文本颜色
            g.setFont(new Font("Arial", Font.BOLD, 20)); // 设置字体和大小
            FontMetrics fm = g.getFontMetrics();
            int textHeight = fm.getAscent(); //获得Name的基线高度
            g.drawString(name, 0, textHeight); // 在图片内部的左上角绘制玩家名字
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        drawPlayerImage(g);
        drawPlayerName(g);
    }

    public PlayerCardsPile getPlayerCardsPile() {
        return this.playerCardsPile;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void setPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    public void drawCards(ArrayList<Card> cards) {
        this.cardsList.addAll(cards);
        handCards.drawCardFromCardsPile(cards);
        playerCardsPile.drawCardFromCardsPile(cards);
    }
}
