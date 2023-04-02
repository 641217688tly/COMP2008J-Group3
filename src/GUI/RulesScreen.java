package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RulesScreen extends JPanel {
    public RulesScreen(ActionListener backListener) {
        setLayout(new BorderLayout()); // 设置布局管理器为BorderLayout

        // 创建并设置文本区域以显示游戏规则
        JTextArea rulesTextArea = new JTextArea(); // 创建一个JTextArea对象
        rulesTextArea.setEditable(false);// 设置文本区域不可编辑
        rulesTextArea.setText("游戏规则说明...");// 在文本区域中设置游戏规则文本
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
