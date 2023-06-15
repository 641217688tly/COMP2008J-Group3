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

    public static int screenWidth; // Screen width
    public static int screenHeight; // Screen height

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
        setupMainPanel();
        setupMenuScreen();
        setupRulesScreen();
        setupGameScreen(game);
        setupSettingsScreen(game, gameScreen);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Add this line to hide the title bar
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        device.setFullScreenWindow(this); // Add this line to set the application to full screen
    }

    // Set up the main panel using CardLayout
    private void setupMainPanel() {
        mainPanel = new JPanel(new CardLayout());
        setContentPane(mainPanel);
    }

    // Set up the menu screen
    private void setupMenuScreen() {
        menuScreen = new MenuScreen(
                new MenuScreenListener.RulesButtonListener(this),
                new MenuScreenListener.SettingsButtonListener(this),
                new MenuScreenListener.GameButtonListener(this));
        mainPanel.add(menuScreen, "Menu");
    }

    // Set up the rules screen
    private void setupRulesScreen() {
        rulesScreen = new RulesScreen(new RulesScreenListener.BackButtonListener(this));
        mainPanel.add(rulesScreen, "Rules");
    }

    // Set up the settings screen
    private void setupSettingsScreen(Game game, GameScreen gameScreen) {
        settingsScreen = new SettingsScreen(
                new SettingsScreenListener.BackButtonListener(this),
                new ApplicationStartListener.ShowPanelActionListener(this, "Game"),
                game,
                gameScreen);
        mainPanel.add(settingsScreen, "Exit");
    }

    // Set up the game screen
    private void setupGameScreen(Game game) {
        gameScreen = new GameScreen(game);
        gameScreen.setFocusable(true); // Set the game screen to be focusable
        mainPanel.add(gameScreen, "Game");
    }

    // Show the panel with the specified name
    public void showPanel(String panelName) {
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, panelName);
    }

    // Entry point of the program
    public static void main(String[] args) {
        ApplicationStart newGame = new ApplicationStart();
    }

}
