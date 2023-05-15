/*
Cards:
    Properties (28):
        2蓝色，2棕色，2实用，3绿色，3黄色，3红色，3橙色，3粉色，3
        浅蓝色，4号铁路

    Property Wildcards (11):
        1个深蓝色/绿色，1个绿色/铁路，1个公用事业/铁路，1个灯光
        蓝色/铁路，1浅蓝色/棕色，2粉色/橙色，2红色/黄色，2多色属性
        通配符。

    Action Cards(34):
        2交易破坏，3只是说不，3狡猾的交易，4强迫交易，3讨债，3这是我的生日，
        10 Pass Go, 3房子，3酒店，2双倍租金卡

    Rent Cards(13):
        2张深蓝色/绿色，2张红色/黄色，2张粉红色/橙色，2张浅蓝色/棕色，
        2张铁路/公用事业，3张Wild出租卡

    Money Cards(20):
        6 cards of 1M, 5 cards of 2M, 3 cards of 3M,
        3 cards of 4M, 2 cards of 5M, 1 card of 10M.
*/
package Module.Cards;

import GUI.ApplicationStart;

import javax.swing.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public abstract class Card extends JPanel {
    public static int cardHeight = (ApplicationStart.screenHeight) / 5;
    public static int cardWidth = (ApplicationStart.screenWidth) / 12;
    public int cardX;
    public int cardY;
    private String name;
    private ImageIcon cardImage;
    private JLabel cardLabel;
    private JButton playButton;
    private JButton putBackButton;


    public Card(String name, ImageIcon cardImage) {
        this.name = name;
        this.cardImage = cardImage;

        // 初始化 cardLabel
        cardLabel = new JLabel(cardImage);
        cardLabel.setBounds(0, 0, cardWidth, cardHeight);
        add(cardLabel);

        // 初始化 playButton
        playButton = new JButton("Play");
        playButton.setBounds(0, cardHeight, cardWidth / 2, cardHeight / 5);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });
        add(playButton);

        // 初始化 putBackButton
        putBackButton = new JButton("Put Back");
        putBackButton.setBounds(cardWidth / 2, cardHeight, cardWidth / 2, cardHeight / 5);
        putBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                putBack();
            }
        });
        add(putBackButton);
    }
    public abstract void play();

    public abstract void putBack();
}
//将这张卡牌放回牌堆的操作

//初始化了 cardLabel、playButton 和 putBackButton，并为 playButton 和 putBackButton 添加了监听器




