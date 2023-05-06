package GUI;

import Listener.GUIListener.SettingsScreenListener;
import Module.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SettingsScreen extends JPanel {
    private JComboBox<Integer> playerCountComboBox; // 用于选择玩家数量的下拉框
    private JPanel playerNamePanel; // 用于输入玩家名称的面板
    private JButton startGameButton; // 开始游戏按钮
    private Image settingsScreenBackground; // 存储背景图像的变量
    private Game game; // 游戏对象

    // 构造函数，初始化SettingsScreen
    public SettingsScreen(ActionListener backButtonListener, ActionListener showPanelActionListener, Game game) {
        this.game = game;
        setLayout(new BorderLayout()); // 设置布局为边界布局
        loadAndSetBackgroundImage();
        setupPlayerCountPanel(); // 设置玩家数量选择面板
        setupPlayerNamePanel(); // 设置玩家名称输入面板
        setupButtonsPanel(backButtonListener, showPanelActionListener); // 设置按钮面板（开始游戏和返回按钮）
        createPlayerNameInputs(); // 在面板初始化时创建玩家姓名输入框
    }

    // 加载并设置背景图片
    private void loadAndSetBackgroundImage() {
        try {
            // 从文件中读取背景图片
            settingsScreenBackground = ImageIO.read(new File("images/SettingsScreenBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 设置玩家名称输入面板
    private void setupPlayerNamePanel() {
        playerNamePanel = new JPanel();
        playerNamePanel.setLayout(new BoxLayout(playerNamePanel, BoxLayout.Y_AXIS)); // 设置布局为垂直BoxLayout
        add(playerNamePanel, BorderLayout.CENTER); // 将面板添加到中间位置
    }

    // 设置按钮面板（开始游戏和返回按钮）
    private void setupButtonsPanel(ActionListener backListener, ActionListener gameListener) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // 创建按钮面板，使用流式布局，靠右对齐
        startGameButton = new JButton("Start Game"); // 创建开始游戏按钮
        startGameButton.addActionListener(new SettingsScreenListener.StartGameButtonListener(game, playerNamePanel, gameListener));
        startGameButton.setEnabled(false); // 初始时设置为禁用
        buttonPanel.add(startGameButton); // 将开始游戏按钮添加到按钮面板

        JButton backButton = new JButton("Back"); // 创建返回按钮
        backButton.addActionListener(backListener); // 添加监听器
        buttonPanel.add(backButton); // 将返回按钮添加到按钮面板

        add(buttonPanel, BorderLayout.SOUTH); // 将按钮面板添加到面板的南部（下方）
    }

    // 设置玩家数量选择面板
    private void setupPlayerCountPanel() {
        playerCountComboBox = new JComboBox<>(new Integer[]{2, 3, 4, 5}); // 创建玩家数量下拉框，允许选择2到5名玩家
        playerCountComboBox.setPreferredSize(new Dimension(100, 30)); // 设置下拉框的首选大小
        playerCountComboBox.setSelectedIndex(0); // 默认选择玩家人数为2，索引为0
        playerCountComboBox.addActionListener(new SettingsScreenListener.PlayerCountComboBoxListener(this)); // 添加监听器
        JPanel playerCountPanel = new JPanel(); // 创建玩家数量选择面板
        playerCountPanel.add(new JLabel("Player count: ")); // 添加标签
        playerCountPanel.add(playerCountComboBox); // 将下拉框添加到面板
        add(playerCountPanel, BorderLayout.NORTH); // 将玩家数量选择面板添加到面板的北部（上方）
    }


    // 创建玩家名称输入框
    public void createPlayerNameInputs() {
        playerNamePanel.removeAll(); // 清除面板上的所有组件

        int playerCount = (Integer) playerCountComboBox.getSelectedItem(); // 获取选中的玩家数量
        for (int i = 0; i < playerCount; i++) {
            JPanel inputPanel = new JPanel(); // 创建输入面板
            inputPanel.add(new JLabel("Player " + (i + 1) + " name: ")); // 添加玩家名称标签
            JTextField playerNameTextField = new JTextField(); // 创建文本输入框，用于输入玩家名称
            playerNameTextField.setPreferredSize(new Dimension(200, 30)); // 设置文本框的首选大小
            inputPanel.add(playerNameTextField); // 将文本输入框添加到输入面板
            playerNamePanel.add(inputPanel); // 将输入面板添加到玩家名称面板
        }
        playerNamePanel.revalidate(); // 重新验证面板，确保所有组件按照新布局正确排列
        playerNamePanel.repaint(); // 重新绘制面板，确保所有组件正确显示
        startGameButton.setEnabled(true); // 启用开始游戏按钮
    }

    private void drawBackground(Graphics g) {
        if (settingsScreenBackground != null) { // 如果背景图片已加载
            // 在面板上绘制背景图片，使其填充整个面板
            g.drawImage(settingsScreenBackground, 0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight, this);
        }
    }

    // 重写paintComponent方法以在面板上绘制背景图片
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        drawBackground(g); //TODO 无法正常加载背景图

    }
}
