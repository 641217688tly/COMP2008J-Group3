package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class RulesScreen extends JPanel {
    private Image rulesScreenBackground; // 存储背景图像的变量

    // 构造函数，初始化RulesScreen
    public RulesScreen(ActionListener backButtonListener) {
        setLayout(new BorderLayout()); // 设置布局为边界布局
        loadAndSetBackgroundImage();// 加载背景图
        setupRulesTextArea(); // 设置规则文本区域
        setupBackButton(backButtonListener); // 设置返回按钮
    }

    // 加载并设置背景图片
    private void loadAndSetBackgroundImage() {
        try {
            // 从文件中读取背景图片
            rulesScreenBackground = ImageIO.read(new File("images/RulesScreenBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 设置规则文本区域
    private void setupRulesTextArea() {
        JTextArea rulesTextArea = new JTextArea(); // 创建文本区域
        rulesTextArea.setEditable(false); // 设置为不可编辑
        rulesTextArea.setTabSize(24); // 设置制表符大小
        Font x1 = new Font("Arial", 0, 24); // 创建字体
        rulesTextArea.setFont(x1); // 设置字体
        rulesTextArea.setText("Before the game starts:\n" + "Shuffle the cards and assign 5 cards to each player, leaving the remaining cards in the center collar for collection\n" + "\n" +
                "The game begins:\n" +
                "1. Draw 2 cards on your turn. If you don't have any cards in your hand, you pick up 5 cards on your next turn.\n" +
                "2, can play 1-3 cards.\n" +
                "3. You can deposit currency or action cards into your bank. Action cards can be used as currency. When an action card is placed in the bank, it can only be used as currency in the game. Even if it is transferred to another player, it can no longer be used as an action card.\n" +
                "4. Collect your property cards. Put the property cards in front of you and build the property. Each card shows the number of property cards required to complete the colour series.\n" +
                "5, the use of action card, put it in the central position according to the instructions on the action card.\n" +
                "6. Make sure you have no more than 7 cards in your hand after you play a card. Otherwise, selectively discard the card and place the discard card at the bottom of the deck until there are 7 cards left in your hand.\n" + "\n" +
                "How to win:\n" +
                "A complete set of 3 sets of property cards in different colors will win."); // 设置游戏规则文本，已省略以节省空间
        SimpleAttributeSet green = new SimpleAttributeSet();
        StyleConstants.setBackground(green, Color.CYAN);
        rulesTextArea.setLineWrap(true); // 设置自动换行
        rulesTextArea.setWrapStyleWord(true); // 设置按单词换行
        rulesTextArea.setOpaque(false); // 设置文本区域为透明

        // 将文本区域添加到滚动面板，并将滚动面板添加到面板的中间位置
        JScrollPane scrollPane = new JScrollPane(rulesTextArea);
        scrollPane.setOpaque(false); // 设置滚动面板为透明
        scrollPane.getViewport().setOpaque(false); // 设置滚动面板的视口为透明
        add(scrollPane, BorderLayout.CENTER);
    }

    // 设置返回按钮
    private void setupBackButton(ActionListener backListener) {
        JPanel buttonPanel = new JPanel(); // 创建按钮面板
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS)); // 设置为水平BoxLayout布局

        buttonPanel.add(Box.createHorizontalGlue()); // 添加可调整大小的水平间距

        // 创建返回按钮
        JButton backButton = new JButton("Back");
        backButton.setMaximumSize(new Dimension(120, 30)); // 设置最大尺寸
        backButton.addActionListener(backListener); // 添加监听器
        buttonPanel.add(backButton); // 将按钮添加到按钮面板

        buttonPanel.add(Box.createHorizontalGlue()); // 添加可调整大小的水平间距

        // 将按钮面板添加到面板的南部（下方）
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void drawBackground(Graphics g) {
        if (rulesScreenBackground != null) { // 如果背景图片已加载
            // 在面板上绘制背景图片，使其填充整个面板
            g.drawImage(rulesScreenBackground, 0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight, this);
        }
    }

    // 重写paintComponent方法以在面板上绘制背景图片
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        drawBackground(g);
    }
}


