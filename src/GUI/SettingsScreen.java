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
    private JComboBox<Integer> playerCountComboBox; // Combo box for selecting the number of players
    private JPanel playerNamePanel; // Panel for entering player names
    private JButton startGameButton; // Start game button
    private JButton backButton;
    private Image settingsScreenBackground; // Variable to store the background image
    private Game game; // Game object
    private GameScreen gameScreen; // GameScreen object, added for later adding Player and CardsPile objects to the
                                   // GameScreen JPanel

    public SettingsScreen(ActionListener backButtonListener, ActionListener showPanelActionListener, Game game,
            GameScreen gameScreen) {
        setLayout(new BorderLayout()); // Set the layout to BorderLayout

        this.game = game;
        this.gameScreen = gameScreen;
        loadAndSetBackgroundImage();
        setupPlayerCountPanel(); // Set up the player count selection panel
        setupPlayerNamePanel(); // Set up the player name input panel
        setupButtonsPanel(backButtonListener, showPanelActionListener); // Set up the buttons panel (start game and back
                                                                        // buttons)
        createPlayerNameInputs(); // Create player name inputs when the panel is initialized
    }

    // Load and set the background image
    private void loadAndSetBackgroundImage() {
        try {
            // Read the background image from a file
            settingsScreenBackground = ImageIO.read(new File("images/GUI/SettingsScreenBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Set up the player name input panel
    private void setupPlayerNamePanel() {
        playerNamePanel = new JPanel();
        playerNamePanel.setLayout(new BoxLayout(playerNamePanel, BoxLayout.Y_AXIS)); // Set the layout to vertical
                                                                                     // BoxLayout
        add(playerNamePanel, BorderLayout.CENTER); // Add the panel to the center position
    }

    // Set up the buttons panel (start game and back buttons)
    private void setupButtonsPanel(ActionListener backListener, ActionListener gameListener) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Create a button panel using flow layout,
                                                                           // aligned to the right

        startGameButton = new JButton("Start Game"); // Create the start game button
        startGameButton.addActionListener(
                new SettingsScreenListener.StartGameButtonListener(game, gameScreen, playerNamePanel, gameListener));
        startGameButton.setEnabled(false); // Set it initially disabled
        buttonPanel.add(startGameButton); // Add the start game button to the button panel

        backButton = new JButton("Back"); // Create the back button
        backButton.addActionListener(backListener); // Add the listener
        buttonPanel.add(backButton); // Add the back button to the button panel

        add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the south position (bottom)
    }

    // Set up the player count selection panel
    private void setupPlayerCountPanel() {
        playerCountComboBox = new JComboBox<>(new Integer[] { 2, 3, 4, 5 }); // Create a combo box for selecting the
                                                                             // number of players (2-5)
        playerCountComboBox.setPreferredSize(new Dimension(100, 30)); // Set the preferred size of the combo box
        playerCountComboBox.setSelectedIndex(0); // Default selection is 2 players, index 0
        playerCountComboBox.addActionListener(new SettingsScreenListener.PlayerCountComboBoxListener(this)); // Add the
                                                                                                             // listener
        JPanel playerCountPanel = new JPanel(); // Create the player count selection panel
        playerCountPanel.add(new JLabel("Player count: ")); // Add a label
        playerCountPanel.add(playerCountComboBox); // Add the combo box to the panel
        add(playerCountPanel, BorderLayout.NORTH); // Add the player count panel to the north position (top)
    }

    // Create player name inputs
    public void createPlayerNameInputs() {
        playerNamePanel.removeAll(); // Clear all components from the panel

        int playerCount = (Integer) playerCountComboBox.getSelectedItem(); // Get the selected player count
        for (int i = 0; i < playerCount; i++) {
            JPanel inputPanel = new JPanel(); // Create an input panel
            inputPanel.add(new JLabel("Player " + (i + 1) + " name: ")); // Add the player name label
            JTextField playerNameTextField = new JTextField(); // Create a text field for entering player names
            playerNameTextField.setPreferredSize(new Dimension(200, 30)); // Set the preferred size of the text field
            inputPanel.add(playerNameTextField); // Add the text field to the input panel
            playerNamePanel.add(inputPanel); // Add the input panel to the player name panel
        }
        playerNamePanel.revalidate(); // Revalidate the panel to ensure correct layout of all components
        playerNamePanel.repaint(); // Repaint the panel to ensure correct display of all components
        startGameButton.setEnabled(true); // Enable the start game button
    }

    private void drawBackground(Graphics g) {
        if (settingsScreenBackground != null) { // If the background image is loaded
            // Draw the background image on the panel, filling the entire panel
            g.drawImage(settingsScreenBackground, 0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight,
                    this);
        }
    }

    // Override the paintComponent method to draw the background image on the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the superclass method to ensure proper painting
        drawBackground(g); // TODO Unable to load the background image correctly
    }
}
