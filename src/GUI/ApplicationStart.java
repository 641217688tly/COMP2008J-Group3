package GUI;

import Listener.GUIListener.ApplicationStartListener;
import Listener.GUIListener.MenuScreenListener;
import Listener.GUIListener.RulesScreenListener;
import Listener.GUIListener.SettingsScreenListener;
import Module.Game;
import Module.GameEngine;

import javax.swing.*;
import java.awt.*;

public class ApplicationStart extends JFrame {
    private JPanel mainPanel;
    private MenuScreen menuScreen;
    private RulesScreen rulesScreen;
    private SettingsScreen settingsScreen;
    private GameScreen gameScreen;

    public static int screenWidth; // 屏幕的宽度
    public static int screenHeight; // 屏幕的高度

    static {
        obtainScreenSize();
    }

    private static void obtainScreenSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.width;
        screenHeight = screenSize.height;
    }

    public ApplicationStart() {
        Game game = new Game();
        setupMainPanel(); // 主面板
        setupMenuScreen(); // 菜单栏
        setupRulesScreen(); // 规则面板
        setupGameScreen(game); // 游戏面板
        setupSettingsScreen(game, gameScreen); // 设置面板

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // 添加这一行来隐藏标题栏
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        device.setFullScreenWindow(this); // 添加这一行来设置全屏
    }

    // 设置主面板（使用CardLayout）
    private void setupMainPanel() {
        mainPanel = new JPanel(new CardLayout());
        setContentPane(mainPanel);
    }

    // 设置菜单屏幕
    private void setupMenuScreen() {
        menuScreen = new MenuScreen(
                new MenuScreenListener.RulesButtonListener(this),
                new MenuScreenListener.SettingsButtonListener(this),
                new MenuScreenListener.GameButtonListener(this)
        );
        mainPanel.add(menuScreen, "Menu");
    }

    // 设置规则屏幕
    private void setupRulesScreen() {
        rulesScreen = new RulesScreen(new RulesScreenListener.BackButtonListener(this));
        mainPanel.add(rulesScreen, "Rules");
    }

    // 设置设置屏幕
    private void setupSettingsScreen(Game game, GameScreen gameScreen) {
        settingsScreen = new SettingsScreen(
                new SettingsScreenListener.BackButtonListener(this),
                new ApplicationStartListener.ShowPanelActionListener(this, "Game"),
                game,
                gameScreen
        );
        mainPanel.add(settingsScreen, "Exit");
    }

    // 设置游戏屏幕
    private void setupGameScreen(Game game) {
        gameScreen = new GameScreen(game);
        gameScreen.setFocusable(true); // 设置GameScreen能够获得焦点
        mainPanel.add(gameScreen, "Game");
    }

    // 显示指定名称的面板
    public void showPanel(String panelName) {
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, panelName);
    }


    // 程序入口
    public static void main(String[] args) {
        ApplicationStart newGame = new ApplicationStart();
    }
}
