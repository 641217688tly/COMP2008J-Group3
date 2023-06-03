package Listener.GUIListener;

import GUI.ApplicationStart;
import GUI.GameScreen;
import GUI.SettingsScreen;
import Module.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// 这个类包含了处理设置屏幕上各种按钮事件的内部类
public class SettingsScreenListener {

    // 内部类：处理玩家数量下拉框的事件
    public static class PlayerCountComboBoxListener implements ActionListener {
        private SettingsScreen settingsScreen; // SettingsScreen对象的引用

        // 构造方法，接收一个SettingsScreen对象
        public PlayerCountComboBoxListener(SettingsScreen settingsScreen) {
            this.settingsScreen = settingsScreen;
        }

        // 当与此监听器关联的下拉框的选项被改变时，会调用此方法
        @Override
        public void actionPerformed(ActionEvent e) {
            settingsScreen.createPlayerNameInputs(); // 调用SettingsScreen对象的createPlayerNameInputs方法，创建玩家名字输入框
        }
    }

    // 内部类：处理"Back"按钮的事件
    public static class BackButtonListener implements ActionListener {
        private ApplicationStart applicationStart; // ApplicationStart对象的引用，用于调用showPanel方法

        // 构造方法，接收一个ApplicationStart对象
        public BackButtonListener(ApplicationStart applicationStart) {
            this.applicationStart = applicationStart;
        }

        // 当与此监听器关联的"Back"按钮被触发时，会调用此方法
        @Override
        public void actionPerformed(ActionEvent e) {
            applicationStart.showPanel("Menu"); // 调用ApplicationStart对象的showPanel方法，切换到"Menu"面板
        }
    }

    // 内部类：处理"Start Game"按钮的事件
    public static class StartGameButtonListener implements ActionListener {
        private Game game; // Game对象的引用，用于添加玩家
        private GameScreen gameScreen;
        private JPanel playerNamePanel; // 包含玩家名字输入框的面板
        private ActionListener gameListener; // 处理游戏开始后的监听器

        // 构造方法，接收Game对象、玩家名字输入框所在面板和游戏开始后的监听器
        public StartGameButtonListener(Game game, GameScreen gameScreen, JPanel playerNamePanel, ActionListener gameListener) {
            this.game = game;
            this.gameScreen = gameScreen;
            this.playerNamePanel = playerNamePanel;
            this.gameListener = gameListener;
        }

        // 当与此监听器关联的"Start Game"按钮被触发时，会调用此方法
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> playerNames = new ArrayList<String>(); // 创建一个ArrayList，用于存储玩家名字
            // 遍历playerNamePanel中的组件
            for (Component component : playerNamePanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel inputPanel = (JPanel) component;
                    // 遍历inputPanel中的组件
                    for (Component inputComponent : inputPanel.getComponents()) {
                        if (inputComponent instanceof JTextField) {
                            JTextField textField = (JTextField) inputComponent;
                            playerNames.add(textField.getText()); // 将输入框中的文本添加到玩家名字列表中
                        }
                    }
                }
            }
            game.addPlayers(playerNames); // 将玩家名字列表传递给Game对象，添加玩家
            gameScreen.addComponentsIntoJPanel();
            gameListener.actionPerformed(e); // 调用游戏开始后的监听器，处理游戏开始后的动作
            ApplicationStart.setIsGameStart(true);
        }
    }
}
