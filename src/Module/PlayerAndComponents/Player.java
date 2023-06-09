package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.PlayerListener;
import Module.Cards.*;
import Module.Cards.CardsEnum.ActionCardType;
import Module.Cards.CardsEnum.PropertyCardType;
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
    public ArrayList<Card> oneTurnCardsBuffer; //用于该回合下玩家每次行动中所用过的牌
    public ArrayList<Card> singleActionCardsBuffer; //用于存储玩家某次行动中所用过的牌,其中index=0处的牌是玩家在本次行动中最先使用的牌
    public ArrayList<Player> interactivePlayers;
    public ArrayList<Card> pledgeCardFromBank;
    public ArrayList<Card> pledgeCardFromProperty;
    public int playerJPanelX;
    public int playerJPanelY;
    public int actionNumber = 3; //行动次数
    public int debt = 0;
    public boolean whetherViewComponent = false;
    private boolean isInAction = false;
    private boolean isPlayerTurn = false;

    private Image playerImage;
    public Bank bank; //玩家的银行
    public Property property; //玩家的房产区
    public HandCards handCards; //玩家的手牌
    public PlayerCardsPile playerCardsPile; //玩家左边的牌堆
    private PlayerListener playerListener;
    public JButton handCardsButton;
    public JButton bankButton;
    public JButton propertyButton;
    public JButton skipButton;
    public JButton sayNoButton;
    public JButton abandonSayNoButton;


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
        this.oneTurnCardsBuffer = new ArrayList<>();
        this.singleActionCardsBuffer = new ArrayList<>();
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
        bankButton = createButton(10, "B", 0, playerHeight * 3 / 4, playerWidth / 3, playerHeight / 4, this.playerListener.bankButtonListener(this));
        handCardsButton = createButton(10, "C", playerWidth / 3, playerHeight * 3 / 4, playerWidth / 3, playerHeight / 4, this.playerListener.handCardsButtonListener(this));
        propertyButton = createButton(10, "P", playerWidth * 2 / 3, playerHeight * 3 / 4, playerWidth / 3, playerHeight / 4, this.playerListener.propertyButtonListener(this));
        skipButton = createButton(10, "S", playerWidth * 2 / 3, 0, playerWidth / 3, playerHeight / 4, this.playerListener.skipButtonListener(this));
        sayNoButton = createButton(8, "Say No", 0, playerHeight * 2 / 4, playerWidth / 2, playerHeight / 4, this.playerListener.sayNoButtonListener(this));
        abandonSayNoButton = createButton(8, "Waive", playerWidth / 2, playerHeight * 2 / 4, playerWidth / 2, playerHeight / 4, null);

        this.add(handCardsButton);
        this.add(bankButton);
        this.add(propertyButton);
        this.add(skipButton);
        this.add(sayNoButton);
        this.add(abandonSayNoButton);
        sayNoButton.setVisible(false);
        abandonSayNoButton.setVisible(false);
    }

    private JButton createButton(int fontSize, String text, int x, int y, int width, int height, ActionListener listener) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
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
        this.oneTurnCardsBuffer.clear();
        this.interactivePlayers.clear();
        this.singleActionCardsBuffer.clear();
        this.debt = 0;
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

    //TODO inTurnPlayer在使用sayNoCard的时候,手牌区中的其他牌仍然有Play,discard等按钮,而且依然能够被使用,这点需要优化
    //回应sayNoCard
    public void respondSayNoCard() {
        this.setIsInAction(true); //为要回应SayNoCard的玩家设置为可以自由行动
        //打开sayNo卡的开关:让玩家选择是否使用say No卡:
        this.sayNoButton.setVisible(true);
        this.abandonSayNoButton.setVisible(true);
        if (this.isPlayerTurn()) {
            //关闭玩家的牌堆和手牌,避免玩家直接使用sayNo牌出现BUG:
            this.playerCardsPile.setVisible(false);
            this.handCardsButton.setVisible(false);
            abandonSayNoButton.addActionListener(playerListener.inTurnPlayerAbandonSayNoButtonListener(this));
        } else { //如果是回应InTurnPlayer的普通玩家
            //关闭玩家的手牌,避免玩家直接使用sayNO牌出现BUG:
            this.handCardsButton.setVisible(false);
            //不需要干任何事,因为abandonSayNoButton上的Listener还未清除,因此此时单击abandonSayNoButton,会直接触发交租金/交房产等操作
        }
    }

    //为玩家的房产和银行中的卡牌都加上Use按钮,并且设置该玩家的债务为totalRent
    public void payForMoney(Player renter, int totalRent) { //renter是处于当前回合的玩家,也是收租人
        this.setIsInAction(true);
        if (this.bank.calculateTotalAssetsInBank() == 0 && this.property.calculateTotalAssetsInProperty() == 0) { //什么都没有,无法还债
            //从对手的交互队列中移除自己:
            renter.interactivePlayers.remove(0);
            this.setIsInAction(false);
            //判断对手接下来的行动
            if (renter.interactivePlayers.size() > 0) { //对手还要继续与别的玩家交互:
                renter.interactivePlayers.get(0).setIsInAction(true);
                if (renter.singleActionCardsBuffer.size() > 1) {
                    Card tempCard = renter.singleActionCardsBuffer.get(0);
                    renter.singleActionCardsBuffer.clear();
                    renter.singleActionCardsBuffer.add(tempCard);
                }
                renter.interactivePlayers.get(0).payForMoney(renter, totalRent);
            } else { //对手的本次action全部结束
                Timer timer = new Timer(3000, a -> {
                    //延迟两秒再呈现当前的玩家的卡牌
                    renter.setIsInAction(true);
                    renter.singleActionCardsBuffer.clear();
                    renter.interactivePlayers.clear();
                    renter.bank.paintAllCardsFront();
                    renter.property.reallocateAllCards();
                    renter.handCards.updateAndShowCards();
                    renter.playerCardsPile.updateAndShowCards();

                });
                timer.setRepeats(false); // make sure the timer only runs once
                timer.start(); // start the timer
            }

        } else { //玩家的银行或房产中还有牌可抵债
            this.interactivePlayers.add(renter);
            this.debt = totalRent;
            //隐藏手牌按钮的开关避免出现BUG(首次隐藏手牌):
            this.handCardsButton.setVisible(false);
            //打开sayNo按钮的开关:让玩家选择是否使用say No卡:
            this.sayNoButton.setVisible(true);
            this.abandonSayNoButton.setVisible(true);
            abandonSayNoButton.addActionListener(playerListener.abandonSayNoAndPayForMoneyButtonListener(this, totalRent));
        }
    }

    public void payForProperty(Player propertyThief) {
        this.setIsInAction(true);
        this.interactivePlayers.add(propertyThief);
        //隐藏手牌按钮的开关避免出现BUG(首次隐藏手牌):
        this.handCardsButton.setVisible(false);
        //打开sayNo按钮的开关:让玩家选择是否使用say No卡:
        this.sayNoButton.setVisible(true);
        this.abandonSayNoButton.setVisible(true);
        abandonSayNoButton.addActionListener(playerListener.abandonSayNoAndPayForSinglePropertyButtonListener(this));
    }

    public boolean whetherPlayerHasProperty() { //判断玩家是否拥有房产
        for (PropertyCardType propertyType : PropertyCardType.values()) {
            if (this.property.propertyNumberMap.get(propertyType) > 0) {
                return true;
            }
        }
        return false;
    }

    public void addAndPaintSlyDealChooseButtons(Player thief) { //当某些卡牌需要指定要作用的玩家时,为每个玩家都创建一个choose按钮
        ArrayList<Player> playersWhoHasTempButton = new ArrayList<>();
        for (Player player : Game.players) {
            if (player != thief) {
                if (player.whetherPlayerHasProperty()) { //如果玩家拥有房产
                    playersWhoHasTempButton.add(player);
                    JButton chosenButton = new JButton("↓");
                    chosenButton.setBounds(Player.playerWidth / 3, Player.playerHeight / 3, playerWidth / 3, playerHeight / 4);
                    Font buttonFont = new Font("Arial", Font.BOLD, 10);
                    chosenButton.setFont(buttonFont); // 设置按钮的字体和字体大小
                    chosenButton.addActionListener(playerListener.slyDealChooseButtonListener(thief, player, playersWhoHasTempButton));
                    player.add(chosenButton);
                    chosenButton.setVisible(true);
                }
            }
        }
    }

    public void hideAndRemoveSlyDealChooseButtons(ArrayList<Player> playersWhoHasTempButton) {
        for (Player player : playersWhoHasTempButton) {
            int lastIndex = player.getComponentCount() - 1;
            player.getComponent(lastIndex).setVisible(false);
            player.remove(lastIndex);
        }
    }

    public void addAndPaintRentChooseButtons(Player renter, int totalRent) { //当某些卡牌需要指定要作用的玩家时,为每个玩家都创建一个choose按钮
        for (int i = 0; i < Game.players.size(); i++) {
            if (!Game.players.get(i).isPlayerTurn()) { //对于那些不处于自己的回合内的Player
                JButton chosenButton = new JButton("↓");
                chosenButton.setBounds(Player.playerWidth / 3, Player.playerHeight / 3, playerWidth / 3, playerHeight / 4);
                Font buttonFont = new Font("Arial", Font.BOLD, 10);
                chosenButton.setFont(buttonFont); // 设置按钮的字体和字体大小
                chosenButton.addActionListener(playerListener.rentChooseButtonListener(renter, Game.players.get(i), totalRent));
                Game.players.get(i).add(chosenButton);
                chosenButton.setVisible(true);
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

    public int numberOfHandCards() {
        int counter = 0;
        for (int i = 0; i < cardsTable.length; i++) {
            if (cardsTable[i] != null) {
                counter++;
            }
        }
        return counter;
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
            g.drawString("Move", Player.playerWidth / 5 + Player.playerWidth / 15, 2 * Player.playerHeight / 8 + Player.playerHeight / 16);
        }
    }

    private void paintDebtNumber(Graphics g) { //被允许行动的玩家的头像上将会有标记
        if (isInAction) {
            if (this.debt > 0) {
                g.setColor(Color.RED); // 设置文本颜色
                g.setFont(new Font("Arial", Font.BOLD, 18)); // 设置字体和大小
                g.drawString("Debt: " + this.debt, Player.playerWidth / 5 + Player.playerWidth / 25, 3 * Player.playerHeight / 8 + Player.playerHeight / 10);
            }
        }
    }

    private void paintPlayerActionNumber(Graphics g) { //处于自己回合的玩家头像上将会有行动次数
        if (isPlayerTurn) {
            g.setColor(Color.GREEN); // 设置文本颜色
            g.setFont(new Font("Arial", Font.BOLD, 18)); // 设置字体和大小
            g.drawString("Actions: " + this.actionNumber, Player.playerWidth / 7, 5 * Player.playerHeight / 8 - Player.playerHeight / 16);
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
