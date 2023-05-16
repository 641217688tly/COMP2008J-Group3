/*
属性：
cards（保存玩家手牌的列表，类型为List<Card>）
方法：
初始化方法，设置JDialog的布局，根据cards列表中的卡牌，创建卡片展示组件并添加到JDialog中
updateCards()（更新手牌列表）
*/
package Module.PlayerAndComponents;

import GUI.ApplicationStart;
import Module.Cards.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerCardsPile extends JDialog {
    //属性:
    public static ArrayList<Card> playerCards;
    private Image playerCardsPileImage;

    public PlayerCardsPile(){
        loadAndSetPlayerCardsPileBackground();
    }

    private void loadAndSetPlayerCardsPileBackground() {
        try {
            // 从文件中读取背景图片
            playerCardsPileImage = ImageIO.read(new File("images/Module/PlayerAndComponents/PlayerCardsPileBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawPlayerCardsPile(Graphics g) { //TODO 不通过JPanel组件调用,而是通过传入PlayerCardsPile内的Image变量来绘制图片是不能一劳永逸的,必须进行更改
        if (playerCardsPileImage != null) {
            for (int i = 0; i < 10; i++) {
                g.drawImage(playerCardsPileImage, (ApplicationStart.screenWidth * i) / 12, (ApplicationStart.screenHeight * 4) / 5, Player.playerWidth, Player.playerHeight, null);
            }
        }
    }
}
