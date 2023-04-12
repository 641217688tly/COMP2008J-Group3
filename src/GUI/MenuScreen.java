package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MenuScreen extends JPanel {
    private Image backgroundImage; // 用于存储背景图像

    public MenuScreen(ActionListener rulesListener, ActionListener settingsListener, ActionListener gameListener) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 设置布局管理器为BoxLayout，垂直排列

        try { // 加载并设置背景图像
            // 从指定路径加载背景图像
            backgroundImage = ImageIO.read(new File("images/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建并设置按钮
        JButton rulesButton = new JButton("Rules");// 创建一个名为"Rules"的按钮
        rulesButton.setMaximumSize(new Dimension(120, 30));// 设置按钮的最大尺寸为120x30
        rulesButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 设置按钮在水平方向上居中对齐
        rulesButton.addActionListener(rulesListener); // 为按钮添加事件监听器
        add(rulesButton);// 将按钮添加到面板中

        add(Box.createRigidArea(new Dimension(0, 10)));// 添加一个固定大小的间距组件，垂直间距为10
        JButton exitButton = new JButton("Exit");// 创建一个名为"Settings"的按钮
        exitButton.setMaximumSize(new Dimension(120, 30));// 设置按钮的最大尺寸为120x30
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);// 设置按钮在水平方向上居中对齐
        exitButton.addActionListener (new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                
            }
        });

        add(exitButton);// 将按钮添加到面板中

        add(Box.createRigidArea(new Dimension(0, 10)));// 添加一个固定大小的间距组件，垂直间距为10

        JButton gameButton = new JButton("Start Game");// 创建一个名为"Start Game"的按钮
        gameButton.setMaximumSize(new Dimension(120, 30)); // 设置按钮的最大尺寸为120x30
        gameButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 设置按钮在水平方向上居中对齐
        gameButton.addActionListener(gameListener);// 为按钮添加事件监听器
        add(gameButton);// 将按钮添加到面板中

        add(Box.createVerticalGlue()); // 添加一个可调大小的间距组件，使得所有组件在垂直方向上保持居中
    }
    public void actionPerformed(ActionEvent e) {
        System.exit(0);;
        
    }

    // 重写paintComponent方法以绘制背景图像
    @Override
    protected void paintComponent(Graphics g) {// 重写paintComponent方法以自定义绘图操作
        super.paintComponent(g);// 调用父类的paintComponent方法以确保正常绘制
        if (backgroundImage != null) {// 如果背景图像已加载
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);// 在面板上绘制背景图像，使其填充整个面板
        }
    }
}
