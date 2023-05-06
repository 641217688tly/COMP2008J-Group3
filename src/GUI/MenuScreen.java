package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuScreen extends JPanel {
    private Image menuScreenBackground; // 存储背景图像的变量

    // 构造函数，初始化MenuScreen
    public MenuScreen(ActionListener rulesButtonListener, ActionListener settingsButtonListener, ActionListener gameButtonListener) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 设置布局为垂直排列的BoxLayout
        loadAndSetBackgroundImage(); // 加载并设置背景图片
        createAndAddButtons(rulesButtonListener, settingsButtonListener, gameButtonListener); // 创建并添加按钮
    }

    // 加载并设置背景图片
    private void loadAndSetBackgroundImage() {
        try {
            // 从文件中读取背景图片
            menuScreenBackground = ImageIO.read(new File("images/MenuScreenBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 创建并添加按钮
    private void createAndAddButtons(ActionListener rulesListener, ActionListener settingsListener, ActionListener gameListener) {
        // 添加间距
        add(Box.createRigidArea(new Dimension(0, 100)));
        // 创建并添加开始游戏按钮
        createAndAddStartGameButton(settingsListener);
        // 添加间距
        add(Box.createRigidArea(new Dimension(0, 100)));
        // 创建并添加规则按钮
        createAndAddRulesButton(rulesListener);
        // 添加间距
        add(Box.createRigidArea(new Dimension(0, 100)));
        // 创建并添加退出按钮
        createAndAddExitButton();
        // 添加可调整大小的垂直间距，使组件保持居中
        add(Box.createVerticalGlue());
    }

    // 创建并添加开始游戏按钮
    private void createAndAddStartGameButton(ActionListener settingsListener) {
        JButton gameButton = new JButton("Start Game");
        gameButton.setMaximumSize(new Dimension(200, 150));
        gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameButton.addActionListener(settingsListener);
        add(gameButton);
    }

    // 创建并添加规则按钮
    private void createAndAddRulesButton(ActionListener rulesListener) {
        JButton rulesButton = new JButton("Rules");
        rulesButton.setMaximumSize(new Dimension(200, 150));
        rulesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rulesButton.addActionListener(rulesListener);
        add(rulesButton);
    }

    // 创建并添加退出按钮
    private void createAndAddExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.setMaximumSize(new Dimension(200, 150));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    private void drawBackground(Graphics g) {
        if (menuScreenBackground != null) { // 如果背景图片已加载
            // 在面板上绘制背景图片，使其填充整个面板
            g.drawImage(menuScreenBackground, 0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight, this);
        }
    }

    // 重写paintComponent方法以在面板上绘制背景图片
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        drawBackground(g);
    }
}
