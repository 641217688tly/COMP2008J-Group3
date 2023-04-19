package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/*
游戏的主界面
在游戏开始前,玩家应该被要求设置玩家的总人数以及每个玩家的名字
游戏开始后,游戏界面应该由一张桌子,n个玩家,玩家的牌堆和银行,中心发牌堆和弃牌堆组成

 */
public class GameScreen extends JPanel {
    public GameScreen(ActionListener backListener) {
        setLayout(new BorderLayout());// 设置布局管理器为BorderLayout
        setBackground(new Color(139, 69, 19));

        //创建并设置文本区域以显示游戏信息
        JPanel panel1=new JPanel();
        panel1.setLayout(null);
        panel1.setBackground(new Color(139, 69, 19));
       
        JButton bankButton = new JButton("Bank");// 创建一个名为"Start Game"的按钮
        
        bankButton.setBounds(150, 400, 100,50);
        
        panel1.add(bankButton);// 将按钮添加到面板中

         JButton cardsButton = new JButton("Cards");// 创建一个名为"Rules"的按钮
         cardsButton.setBounds(150, 460, 100,50);
         panel1.add(cardsButton);// 将按钮添加到面板中

        add(panel1);
    
        
        
        
        
        // 创建并设置用于返回主菜单的按钮
        
        JPanel buttonPanel = new JPanel(); // 创建一个JPanel对象
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));// 设置布局管理器为BoxLayout，水平排列

        JButton backButton = new JButton("Back"); // 创建一个名为"Back"的按钮
        backButton.setMaximumSize(new Dimension(120, 30));// 设置按钮的最大尺寸为120x30
        backButton.addActionListener(backListener);// 为按钮添加事件监听器
        buttonPanel.add(backButton); // 将按钮添加到面板中
        buttonPanel.add(Box.createHorizontalGlue());// 添加一个可调大小的间距组件，使得所有组件在水平方向上保持居中


        add(buttonPanel, BorderLayout.SOUTH);// 将按钮面板添加到主面板的南（下）部
    }

}
