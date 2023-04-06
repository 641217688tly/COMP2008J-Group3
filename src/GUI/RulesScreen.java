package GUI;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionListener;

public class RulesScreen extends JPanel {
//    private void drawString(Graphics g, String text, Rectangle rect, int size) {
//        Graphics2D g2d = (Graphics2D) g.create();
//
//        Font font = new Font("Arial", Font.BOLD, size);
//        g2d.setFont(font);
//        FontMetrics metrics = g2d.getFontMetrics();
//        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
//        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
//
//        g2d.setColor(Color.GREEN);
//        g2d.drawString(text, x, y);
//
//    }
//    public void paintComponent(Graphics g) {
//        g.setColor(Color.BLACK);
//        g.fillRect(0, 0, this.getWidth(), this.getHeight());
//        drawString(g, "Welcome to Pacman!!!!", new Rectangle(this.getWidth() / 3, this.getHeight() / 32,
//                this.getWidth() / 3, this.getHeight() / 3), 36);
//        drawString(g, "Before the game starts:", new Rectangle(this.getWidth() / 3, this.getHeight() / 32,
//                this.getWidth() / 3, this.getHeight() / 3), 24);
//        drawString(g, "Shuffle the cards and assign 5 cards to each player, leaving the remaining cards in the center collar for collection", new Rectangle(this.getWidth() / 3, this.getHeight() / 32,
//                this.getWidth() / 3, this.getHeight() / 3), 20);
//        drawString(g, "Welcome to Pacman!!!!", new Rectangle(this.getWidth() / 3, this.getHeight() / 32,
//                this.getWidth() / 3, this.getHeight() / 3), 36);
//        drawString(g, "Welcome to Pacman!!!!", new Rectangle(this.getWidth() / 3, this.getHeight() / 32,
//                this.getWidth() / 3, this.getHeight() / 3), 36);
//
//    }
    public RulesScreen(ActionListener backListener) {
        setLayout(new BorderLayout()); // 设置布局管理器为BorderLayout

         //创建并设置文本区域以显示游戏规则
        JTextArea rulesTextArea = new JTextArea(); // 创建一个JTextArea对象
        rulesTextArea.setEditable(false);// 设置文本区域不可编辑
        rulesTextArea.setTabSize(24);
        Font x1 = new Font("Arial", 0, 24);
        rulesTextArea.setFont(x1);
        rulesTextArea.setText("Before the game starts:\n"+"Shuffle the cards and assign 5 cards to each player, leaving the remaining cards in the center collar for collection\n"+"\n"+
                "The game begins:\n" +
                "1. Draw 2 cards on your turn. If you don't have any cards in your hand, you pick up 5 cards on your next turn.\n" +
                "2, can play 1-3 cards.\n" +
                "3. You can deposit currency or action cards into your bank. Action cards can be used as currency. When an action card is placed in the bank, it can only be used as currency in the game. Even if it is transferred to another player, it can no longer be used as an action card.\n" +
                "4. Collect your property cards. Put the property cards in front of you and build the property. Each card shows the number of property cards required to complete the colour series.\n" +
                "5, the use of action card, put it in the central position according to the instructions on the action card.\n" +
                "6. Make sure you have no more than 7 cards in your hand after you play a card. Otherwise, selectively discard the card and place the discard card at the bottom of the deck until there are 7 cards left in your hand.\n" +"\n"+
                "How to win:\n" +
                "A complete set of 3 sets of property cards in different colors will win.");// 在文本区域中设置游戏规则文本
        SimpleAttributeSet green = new SimpleAttributeSet();
        StyleConstants.setBackground(green, Color.CYAN);
        rulesTextArea.setLineWrap(true); // 设置文本区域自动换行
        rulesTextArea.setWrapStyleWord(true);// 设置文本区域在单词边界处换行
        add(new JScrollPane(rulesTextArea), BorderLayout.CENTER); // 将文本区域添加到面板中间，并添加滚动条


        // 创建并设置用于返回主菜单的按钮
        JPanel buttonPanel = new JPanel(); // 创建一个JPanel对象
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS)); // 设置布局管理器为BoxLayout，水平排列

        buttonPanel.add(Box.createHorizontalGlue()); // 添加一个可调大小的间距组件，使得所有组件在水平方向上保持居中

        JButton backButton = new JButton("Back");// 创建一个名为"Back"的按钮
        backButton.setMaximumSize(new Dimension(120, 30));// 设置按钮的最大尺寸为120x30
        backButton.addActionListener(backListener);// 为按钮添加事件监听器
        buttonPanel.add(backButton); // 将按钮添加到面板中

        buttonPanel.add(Box.createHorizontalGlue()); // 添加一个可调大小的间距组件，使得所有组件在水平方向上保持居中

        add(buttonPanel, BorderLayout.SOUTH);// 将按钮面板添加到主面板的南（下）部
    }

}
