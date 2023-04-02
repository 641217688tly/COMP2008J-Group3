package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsScreen extends JPanel {
    public SettingsScreen(ActionListener backListener) {
        setLayout(new BorderLayout()); // 设置布局管理器为BorderLayout

        // 创建并设置用于返回主菜单的按钮
        JPanel buttonPanel = new JPanel(); // 创建一个JPanel对象
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS)); // 设置布局管理器为BoxLayout，水平排列

        buttonPanel.add(Box.createHorizontalGlue()); // 添加一个可调大小的间距组件，使得所有组件在水平方向上保持居中

        JButton backButton = new JButton("Back"); // 创建一个名为"Back"的按钮
        backButton.setMaximumSize(new Dimension(120, 30)); // 设置按钮的最大尺寸为120x30
        backButton.addActionListener(backListener); // 为按钮添加事件监听器
        buttonPanel.add(backButton); // 将按钮添加到面板中

        buttonPanel.add(Box.createHorizontalGlue()); // 添加一个可调大小的间距组件，使得所有组件在水平方向上保持居中

        add(buttonPanel, BorderLayout.SOUTH); // 将按钮面板添加到主面板的南（下）部

        // 在此处添加设置面板的其他组件（如调整游戏界面大小和玩家人数的选项）
    }
}
