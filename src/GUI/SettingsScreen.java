package GUI;

import Listener.GUIListener.SettingsScreenListener;
import Module.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsScreen extends JPanel {
    private JComboBox<Integer> playerCountComboBox; // 用于选择玩家数量的下拉框
    private JPanel playerNamePanel; // 用于输入玩家名称的面板
    private JButton startGameButton; // 开始游戏按钮
    private Game game; // 游戏对象

    // 构造函数，初始化SettingsScreen
    public SettingsScreen(ActionListener backButtonListener, ActionListener showPanelActionListener, Game game) {
        this.game = game;
        setLayout(new BorderLayout()); // 设置布局为边界布局

        setupPlayerCountPanel(); // 设置玩家数量选择面板
        setupPlayerNamePanel(); // 设置玩家名称输入面板
        setupButtonsPanel(backButtonListener, showPanelActionListener); // 设置按钮面板（开始游戏和返回按钮）
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
            inputPanel.add(new JTextField(10)); // 添加文本输入框，用于输入玩家名称
            playerNamePanel.add(inputPanel); // 将输入面板添加到玩家名称面板
        }
        playerNamePanel.revalidate(); // 重新验证面板，确保所有组件按照新布局正确排列
        playerNamePanel.repaint(); // 重新绘制面板，确保所有组件正确显示
        startGameButton.setEnabled(true); // 启用开始游戏按钮
    }
}
