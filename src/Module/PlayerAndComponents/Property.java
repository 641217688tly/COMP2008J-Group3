/*
Property类将主要为玩家提供服务,它应该具有如下属性和功能:
属性:
房产库(HashMap?)(创建一个哈希Map,键为房产颜色,值为一个ArrayList<Card>)
完整的房产的数量
坐标(可以根据玩家的坐标来调整)
碰撞体积(当牌被拖进来后可以收纳牌)
主人

方法:
展示所有的房产卡
显示当前已经集齐的完整房产的数量
    (因此需要实现)统计完整房产数
自动交房产(实现某种算法,使得玩家在被要求收钱后如果长时间不行动且银行无力付款时,以最优的方式抵押房产)
更改房产颜色(对于万能卡或双色卡)
(被)交出房产
(被)安置房产
...
*/
package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Listener.ModuleListener.PlayerAndComponentsListener.PropertyListener;
import Module.Cards.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Property extends JPanel { //房产类
    public ArrayList<Card> cardsList;
    private Player owner;
    private JButton closeButton; // 新增一个关闭按钮
    private PropertyListener propertyListener;
    private Image propertyImage;

    public Property(Player owner) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        this.setBounds(0, ApplicationStart.screenHeight / 5 - (ApplicationStart.screenHeight / 25), ApplicationStart.screenWidth, 4 * ApplicationStart.screenHeight / 5 + (ApplicationStart.screenHeight / 25)); // 设置提示框的位置和大小
        this.setVisible(false); // 初始时设为不可见

        this.cardsList = new ArrayList<>();
        this.owner = owner;
        this.propertyListener = new PropertyListener();
        loadAndSetPlayerCardsPileBackground();
        initButtons();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // 从文件中读取背景图片
            propertyImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerComponentsBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
        closeButton = new JButton("Close");
        this.add(closeButton); // 将关闭按钮添加到这个JPanel
        closeButton.setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight / 25); // 这个值可能需要调整，以便将关闭按钮放在适当的位置
        closeButton.addActionListener(propertyListener.closeButtonListener(owner, this));
    }

    public void placePropertyCardAndShowCards(Card card) {
        this.cardsList.add(card);
        paintAllCardsFront();
        this.add(card);
    }

    private void setCardBounds(Card card, int x, int y, boolean isDisplayable, boolean isCardFront) {
        card.setCardJPanelBounds(x, y); //为Card重新分配它在该JPanel下的坐标
        card.setIsDisplayable(isDisplayable);
        card.setIsCardFront(isCardFront);
    }

    //-------绘制方法:

    public void paintAllCardsFront() { //TODO 排列展示房产的方法待优化
        for (int i = 0; i < cardsList.size(); i++) {
            if (i < 12) {
                setCardBounds(cardsList.get(i), (ApplicationStart.screenWidth / 12) * i, ApplicationStart.screenHeight / 25, true, true);
            } else if (i >= 12 && i < 24) {
                setCardBounds(cardsList.get(i), (ApplicationStart.screenWidth / 12) * (i - 12), ApplicationStart.screenHeight / 25 + 1 * ApplicationStart.screenHeight / 5, true, true);
            } else if (i >= 24 && i < 36) {
                setCardBounds(cardsList.get(i), (ApplicationStart.screenWidth / 12) * (i - 24), ApplicationStart.screenHeight / 25 + 2 * ApplicationStart.screenHeight / 5, true, true);
            } else if (i >= 36) {
                setCardBounds(cardsList.get(i), (ApplicationStart.screenWidth / 12) * (i - 36), ApplicationStart.screenHeight / 25 + 3 * ApplicationStart.screenHeight / 5, true, true);
            }
        }
    }

    private void paintPropertyCardsButton() {
        if (owner.isPlayerTurn()) { //如果处于玩家的回合:
            //TODO 将多色房产的旋转按钮给打开
        }
    }

    private void paintPropertyPile(Graphics g) {
        if (propertyImage != null) {
            for (int i = 0; i < 12; i++) {
                g.drawImage(propertyImage, (ApplicationStart.screenWidth / 12) * i, ApplicationStart.screenHeight / 25, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(propertyImage, (ApplicationStart.screenWidth / 12) * i, ApplicationStart.screenHeight / 25 + 1 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(propertyImage, (ApplicationStart.screenWidth / 12) * i, ApplicationStart.screenHeight / 25 + 2 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
                g.drawImage(propertyImage, (ApplicationStart.screenWidth / 12) * i, ApplicationStart.screenHeight / 25 + 3 * ApplicationStart.screenHeight / 5, ApplicationStart.screenWidth / 12, ApplicationStart.screenHeight / 5, null);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintPropertyPile(g);
        paintPropertyCardsButton();
    }


}
