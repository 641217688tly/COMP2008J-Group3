/*
Bank类将主要为玩家提供服务(每个玩家都有自己的Bank),它应该具有如下属性和功能:
属性:
牌库(用于存钱卡和被当作钱存起来的手牌)
总存款
坐标

方法:
展示所有的钱卡
显示该银行的总存款
    (因此需要实现)统计总存款
自动交租金(实现某种算法,使得玩家在被要求收钱后如果长时间不行动,就自动从银行中以最优的方式扣款)
*/
package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.BankListener;
import Module.Cards.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Bank extends JPanel {
    public ArrayList<Card> cardsList;
    private Player owner;
    private JButton closeButton; // 新增一个关闭按钮
    private BankListener bankListener;
    private Image bankImage;

    public Bank(Player owner) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        this.setBounds(0, ApplicationStart.screenHeight / 5 - (ApplicationStart.screenHeight / 25), ApplicationStart.screenWidth, 3 * ApplicationStart.screenHeight / 5 + (ApplicationStart.screenHeight / 25)); // 设置提示框的位置和大小
        this.setVisible(false); // 初始时设为不可见

        this.cardsList = new ArrayList<>();
        this.owner = owner;
        this.bankListener = new BankListener();
        loadAndSetPlayerCardsPileBackground();
        initButtons();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // 从文件中读取背景图片
            bankImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerComponentsBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        closeButton = new JButton("Close");
        this.add(closeButton); // 将关闭按钮添加到这个JPanel
        closeButton.setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight / 25); // 这个值可能需要调整，以便将关闭按钮放在适当的位置
        closeButton.addActionListener(bankListener.closeButtonListener(owner, this));
    }

    public void saveMoneyAndShowCards(Card card) {//每次调用都会添加钱卡进银行并且刷新Card的呈现状态
        this.cardsList.add(card); //添加新牌
        paintAllCards(); //为所有牌重新分配坐标并设置状态
        this.add(card);
    }

    public void withdrawMoneyCard() {
        //TODO 从银行里取钱

        paintAllCards();
    }


    private void setCardBounds(Card card, int x, int y, boolean isDisplayable, boolean isCardFront) {
        card.setCardJPanelBounds(x, y); //为Card重新分配它在该JPanel下的坐标
        card.setIsDisplayable(isDisplayable);
        card.setIsCardFront(isCardFront);
    }

    //-------绘制方法:

    public void paintAllCards() {
        for (int i = 0; i < cardsList.size(); i++) {
            Card card = cardsList.get(i);
            if (i < 12) {
                setCardBounds(card, (ApplicationStart.screenWidth / 12) * i, ApplicationStart.screenHeight / 25, true, true);
            } else if (i >= 12 && i < 24) {
                setCardBounds(card, (ApplicationStart.screenWidth / 12) * (i - 12), ApplicationStart.screenHeight / 25 + 1 * ApplicationStart.screenHeight / 5, true, true);
            } else if (i >= 24 && i < 36) {
                setCardBounds(card, (ApplicationStart.screenWidth / 12) * (i - 24), ApplicationStart.screenHeight / 25 + 2 * ApplicationStart.screenHeight / 5, true, true);
            }
        }
    }

    private void paintBankPile(Graphics g) {
        if (bankImage != null) {
            for (int i = 0; i < 12; i++) {
                g.drawImage(bankImage, (ApplicationStart.screenWidth / 12) * i, ApplicationStart.screenHeight / 25, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(bankImage, (ApplicationStart.screenWidth / 12) * i, ApplicationStart.screenHeight / 25 + 1 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(bankImage, (ApplicationStart.screenWidth / 12) * i, ApplicationStart.screenHeight / 25 + 2 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintBankPile(g);
    }

}


