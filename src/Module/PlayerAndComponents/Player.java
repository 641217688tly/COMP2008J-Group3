package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.PlayerListener;
import Module.Cards.*;
import Module.Cards.CardsEnum.ActionCardType;
import Module.Game;

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
    public Card[] cardsTable;
    public ArrayList<Card> cardsBuffer; //用于存储玩家在一个回合内所出过的牌
    public ArrayList<Player> interactivePlayers;
    public ArrayList<Card> pledgeCardFromBank;
    public ArrayList<Card> pledgeCardFromProperty;
    public int playerJPanelX;
    public int playerJPanelY;
    public int actionNumber = 3; //行动次数
    public int debt = 0;
    public boolean whetherViewComponent = false;
    public boolean isInAction = false;
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
    private JButton skipButton;

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
        this.cardsTable = new Card[12];
        this.cardsBuffer = new ArrayList<>();
        this.pledgeCardFromBank = new ArrayList<>();
        this.pledgeCardFromProperty = new ArrayList<>();
        this.playerCardsPile = new PlayerCardsPile(this);
        this.handCards = new HandCards(this);
        this.bank = new Bank(this);
        this.property = new Property(this);
        this.interactivePlayers = new ArrayList<>();
        this.playerListener = new PlayerListener();
        initButtons();

        this.setBounds(this.playerJPanelX, this.playerJPanelY, playerWidth, playerHeight); // 设置Player的大小和位置
    }

    private void initButtons() {
        bankButton = createButton(10, "B", 0, playerHeight * 3 / 4, this.playerListener.bankButtonListener(this));
        handCardsButton = createButton(10, "C", playerWidth / 3, playerHeight * 3 / 4, this.playerListener.handCardsButtonListener(this));
        propertyButton = createButton(10, "P", playerWidth * 2 / 3, playerHeight * 3 / 4, this.playerListener.propertyButtonListener(this));
        skipButton = createButton(10, "S", playerWidth * 2 / 3, 0, this.playerListener.skipButtonListener(this));

        this.add(handCardsButton);
        this.add(bankButton);
        this.add(propertyButton);
        this.add(skipButton);
    }

    private JButton createButton(int fontSize, String text, int x, int y, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, playerWidth / 3, playerHeight / 4);
        Font buttonFont = new Font("Arial", Font.BOLD, fontSize); // 设置形状为粗体，大小为10的Arial字体
        button.setFont(buttonFont); // 设置按钮的字体和字体大小
        button.addActionListener(listener);
        return button;
    }

    public void drawCards(Card[] cards) { //抽牌
        for (int i = 0, j = 0; i < cardsTable.length; i++) {
            if (cardsTable[i] == null) {
                cards[j].owner = this;
                cardsTable[i] = cards[j];
                j = j + 1;
                if (j >= cards.length) {
                    break;
                }
            }
        }
    }

    public void moveToNextTurn() {
        this.cardsBuffer.clear();
        this.actionNumber = 3;
        handCards.updateAndShowCards();
        if (this.isPlayerTurn) {
            isInAction = true;
            playerCardsPile.updateAndShowCards();
        } else {
            isInAction = false;
        }
    }

    public boolean containsCard(Card card) {
        boolean flag = false;
        for (int column = 0; column < 12; column++) {
            if (cardsTable[column] == card) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    public void payForRent(Player interactivePlayer, int totalRent) { //interactivePlayer是处于当前回合的玩家,也是收租人
        this.debt = totalRent;
        Timer timer = new Timer(2000, e -> {
            if (whetherHasSayNoCard()) {
                //有sayNo牌,让玩家判断用不用
                //TODO 强制打开HandsCard,让玩家自己决定是否使用sayNo牌
            }
        });
        timer.setRepeats(false); // make sure the timer only runs once
        timer.start(); // start the timer

        ArrayList<Card> bankAssets = new ArrayList<>();
        ArrayList<Card> propertyAssets = new ArrayList<>();
        //不用sayNo牌或者玩家没有sayNo牌:
        if (this.bank.calculateTotalAssetsInBank() >= debt) {
            //情况1:玩家的银行有能力支付

        } else if ((this.bank.calculateTotalAssetsInBank() < debt) && (this.property.calculateTotalAssetsInProperty() + this.bank.calculateTotalAssetsInBank() >= debt)) {
            //情况2:玩家的银行无能力支付(银行可能为空),玩家的房产的价值有能力支付
            //TODO 从Bank和Property中选择Card加入pledgeCardFromProperty和pledgeCardFromBank

        } else if ((this.bank.calculateTotalAssetsInBank() < debt) && (this.property.calculateTotalAssetsInProperty() + this.bank.calculateTotalAssetsInBank() < debt)) {
            //情况3:玩家的银行(可能为空)和房产(可能为空)都不足以支付
            //TODO 将银行和房产全部打包送给对手

        }

        //最后不管还没还清债务,一律清零:
        this.debt = 0;
    }


    public boolean whetherHasSayNoCard() {
        for (int i = 0; i < cardsTable.length; i++) {
            if (cardsTable[i] != null) {
                if (cardsTable[i] instanceof ActionCard) {
                    if (((ActionCard) cardsTable[i]).type.equals(ActionCardType.JUST_SAY_NO)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void addAndPaintRentChooseButtons(Player inTurnPlayer, int totalRent) { //当某些卡牌需要指定要作用的玩家时,为每个玩家都创建一个choose按钮
        for (int i = 0; i < Game.players.size(); i++) {
            if (!Game.players.get(i).isPlayerTurn()) { //对于那些不处于自己的回合内的Player
                JButton chosenbutton = new JButton("Choose");
                chosenbutton.setBounds(Player.playerWidth / 3, Player.playerHeight / 3, playerWidth / 3, playerHeight / 4);
                Font buttonFont = new Font("Arial", Font.BOLD, 10);
                chosenbutton.setFont(buttonFont); // 设置按钮的字体和字体大小
                chosenbutton.addActionListener(playerListener.rentChooseButtonListener(inTurnPlayer, Game.players.get(i), totalRent));
                Game.players.get(i).add(chosenbutton);
                chosenbutton.setVisible(true);
            }
        }
    }

    public void hideAndRemoveRentChooseButtons() {
        for (int i = 0; i < Game.players.size(); i++) {
            if (!Game.players.get(i).isPlayerTurn()) { //对于那些不处于自己的回合内的Player
                int lastIndex = Game.players.get(i).getComponentCount() - 1;
                Game.players.get(i).getComponent(lastIndex).setVisible(false);
                Game.players.get(i).remove(lastIndex);
            }
        }
    }


    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void setPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    public boolean isInAction() {
        return isInAction;
    }

    public void setIsInAction(boolean inAction) {
        isInAction = inAction;
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
        if (isInAction) {
            g.setColor(Color.GREEN); // 设置文本颜色
            g.setFont(new Font("Arial", Font.BOLD, 20)); // 设置字体和大小
            g.drawString("Move", Player.playerWidth / 5 + Player.playerWidth / 10, 3 * Player.playerHeight / 8); // 在图片内部的左上角绘制玩家名字
        }
    }

    private void paintDebtNumber(Graphics g) { //被允许行动的玩家的头像上将会有标记
        if (isInAction) {
            if (this.debt > 0) {
                g.setColor(Color.RED); // 设置文本颜色
                g.setFont(new Font("Arial", Font.BOLD, 18)); // 设置字体和大小
                g.drawString("Debt: " + this.debt, Player.playerWidth / 5 + Player.playerWidth / 10, 4 * Player.playerHeight / 8 + Player.playerHeight / 16); // 在图片内部的左上角绘制玩家名字
            }
        }
    }

    private void paintPlayerActionNumber(Graphics g) { //处于自己回合的玩家头像上将会有行动次数
        if (isPlayerTurn) {
            g.setColor(Color.GREEN); // 设置文本颜色
            g.setFont(new Font("Arial", Font.BOLD, 15)); // 设置字体和大小
            g.drawString("Remaining: " + this.actionNumber, Player.playerWidth / 10, 6 * Player.playerHeight / 8 - Player.playerHeight / 16); // 在图片内部的左上角绘制玩家名字
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        paintPlayerImage(g);
        paintPlayerName(g);
        paintChosenMark(g);
        paintPlayerActionNumber(g);
        paintDebtNumber(g);

    }

}
