package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.HandCardsListener;
import Module.Cards.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HandCards extends JPanel { //该类为玩家边框上的按钮,用于查看玩家的手牌
    public ArrayList<Card> cardsList;
    private Player owner;
    private JButton closeButton; // 新增一个关闭按钮
    private HandCardsListener handCardsListener;
    private Image handCardsImage;

    public HandCards(Player owner) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        this.setBounds(0, 3 * ApplicationStart.screenHeight / 5 - (ApplicationStart.screenHeight / 25), ApplicationStart.screenWidth, ApplicationStart.screenHeight / 5 + (ApplicationStart.screenHeight / 25)); // 设置提示框的位置和大小
        this.setVisible(false); // 初始时设为不可见

        this.cardsList = new ArrayList<>();
        this.owner = owner;
        this.handCardsListener = new HandCardsListener();
        loadAndSetPlayerCardsPileBackground();
        initButtons();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // 从文件中读取背景图片
            handCardsImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerComponentsBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        closeButton = new JButton("Close");
        this.add(closeButton); // 将关闭按钮添加到这个JPanel
        closeButton.setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight / 25); // 这个值可能需要调整，以便将关闭按钮放在适当的位置
        closeButton.addActionListener(handCardsListener.closeButtonListener(owner, this));
    }

    public void updateAndShowCards() { //每次调用都需要清除列表中已有的牌并移除JPanel中牌对应的组件,相当于HandCards的刷新方法
        this.cardsList.clear(); //将旧牌全部丢弃
        this.removeAll(); //将旧牌(组件)全部丢弃,但这会导致按钮也丢失
        this.add(closeButton); //将被丢失的按钮加上
        this.cardsList.addAll(owner.cardsList);
        for (int i = 0; i < cardsList.size(); i++) {
            cardsList.get(i).setIsDisplayable(false);
            if (owner.isPlayerTurn()) {
                cardsList.get(i).setIsCardFront(true);
            } else {
                cardsList.get(i).setIsCardFront(false);
            }
        }
        paintAllCardsFront();
    }


    //-------绘制方法:

    private void paintAllCardsFront() {
        for (int i = 0; i < cardsList.size(); i++) {
            if (i < 12) {
                Card card = cardsList.get(i);
                this.add(card);
                card.setCardJPanelBounds((ApplicationStart.screenWidth / 12) * i, ApplicationStart.screenHeight / 25); //为Card重新分配它在该JPanel下的坐标
                card.setIsDisplayable(true);
            }
        }
    }

    private void paintHandCardsPile(Graphics g) {
        if (handCardsImage != null) {
            for (int i = 0; i < 12; i++) {
                g.drawImage(handCardsImage, (ApplicationStart.screenWidth / 12) * i, ApplicationStart.screenHeight / 25, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintHandCardsPile(g);
    }

}
