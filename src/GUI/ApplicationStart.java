package GUI;

import Listener.GUIListener.ApplicationStartListener;
import Listener.GUIListener.MenuScreenListener;
import Listener.GUIListener.RulesScreenListener;
import Listener.GUIListener.SettingsScreenListener;
import Module.Game;

import javax.swing.*;
import java.awt.*;

public class ApplicationStart extends JFrame {
    private JPanel mainPanel;

    public ApplicationStart() {
        Game game = new Game();
        setupMainPanel();
        setupMenuScreen();
        setupRulesScreen();
        setupSettingsScreen(game);
        setupGameScreen();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    // 设置主面板（使用CardLayout）
    private void setupMainPanel() {
        mainPanel = new JPanel(new CardLayout());
        setContentPane(mainPanel);
    }

    // 设置菜单屏幕
    private void setupMenuScreen() {
        MenuScreen menuPanel = new MenuScreen(
                new MenuScreenListener.RulesButtonListener(this),
                new MenuScreenListener.SettingsButtonListener(this),
                new MenuScreenListener.GameButtonListener(this)
        );
        mainPanel.add(menuPanel, "Menu");
    }

    // 设置规则屏幕
    private void setupRulesScreen() {
        RulesScreen rulesPanel = new RulesScreen(new RulesScreenListener.BackButtonListener(this));
        mainPanel.add(rulesPanel, "Rules");
    }

    // 设置设置屏幕
    private void setupSettingsScreen(Game game) {
        SettingsScreen settingsPanel = new SettingsScreen(
                new SettingsScreenListener.BackButtonListener(this),
                new ApplicationStartListener.ShowPanelActionListener(this, "Game"),
                game
        );
        mainPanel.add(settingsPanel, "Exit");
    }

    // 设置游戏屏幕
    private void setupGameScreen() {
        GameScreen gamePanel = new GameScreen(new ApplicationStartListener.ShowPanelActionListener(this, "Menu"));
        mainPanel.add(gamePanel, "Game");
    }

    // 显示指定名称的面板
    public void showPanel(String panelName) {
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, panelName);
    }

    // 程序入口
    public static void main(String[] args) {
        new ApplicationStart();
    }
}
