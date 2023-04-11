import GUI.GameScreen;
import GUI.MenuScreen;
import GUI.RulesScreen;
import GUI.SettingsScreen;

import javax.swing.*;
import java.awt.*;
import Module.Game;

public class ApplicationStart extends JFrame {
    private JPanel mainPanel;


    public ApplicationStart() {
        Game game = new Game();

        mainPanel = new JPanel(new CardLayout());// 初始化主面板，并设置布局管理器为CardLayout
        MenuScreen menuPanel = new MenuScreen(// 创建一个MenuScreen对象
                e -> showPanel("Rules"),// 当规则按钮被点击时，显示规则面板
                e -> showPanel("Settings"), // 当设置按钮被点击时，显示设置面板
                e -> showPanel("Game") // 当游戏按钮被点击时，显示游戏面板
        );
        RulesScreen rulesPanel = new RulesScreen(e -> showPanel("Menu"));// 创建一个RulesScreen对象，并设置返回按钮的事件监听器
        SettingsScreen settingsPanel = new SettingsScreen(e -> showPanel("Menu"), e -> showPanel("Game"),game);// 创建一个SettingsScreen对象，并设置返回按钮的事件监听器
        GameScreen gamePanel = new GameScreen(e -> showPanel("Menu"));// 创建一个GameScreen对象，并设置返回按钮的事件监听器

        mainPanel.add(menuPanel, "Menu"); // 将MenuScreen对象添加到主面板中
        mainPanel.add(rulesPanel, "Rules"); // 将RulesScreen对象添加到主面板中
        mainPanel.add(settingsPanel, "Settings"); // 将SettingsScreen对象添加到主面板中
        mainPanel.add(gamePanel, "Game"); // 将GameScreen对象添加到主面板中
        setContentPane(mainPanel); // 将主面板设置为JFrame的内容面板
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭窗口时的操作为退出程序
        setSize(800, 600); // 设置窗口的初始大小为800x600
        setVisible(true); // 设置窗口为可见
    }

    private void showPanel(String panelName) { // showPanel方法，用于切换面板
        CardLayout layout = (CardLayout) mainPanel.getLayout(); // 获取主面板的布局管理器
        layout.show(mainPanel, panelName); // 根据面板名显示对应的面板
    }

    public static void main(String[] args) { // main方法，程序的入口点
        new ApplicationStart(); // 创建一个ApplicationStart对象，启动程序
    }
}