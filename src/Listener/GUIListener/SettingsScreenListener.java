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


public class SettingsScreenListener {

    // Inner class: Handles the event for the player count combo box
    public static class PlayerCountComboBoxListener implements ActionListener {
        private SettingsScreen settingsScreen;

        // Constructor: Receives a SettingsScreen object
        public PlayerCountComboBoxListener(SettingsScreen settingsScreen) {
            this.settingsScreen = settingsScreen;
        }

        // This method is called when the selected option in the associated combo box is
        // changed
        @Override
        public void actionPerformed(ActionEvent e) {
            settingsScreen.createPlayerNameInputs(); // Call the createPlayerNameInputs method of the SettingsScreen
                                                     // object to create player name inputs
        }
    }

    // Inner class: Handles the event for the "Back" button
    public static class BackButtonListener implements ActionListener {
        private ApplicationStart applicationStart;

        // Constructor: Receives an ApplicationStart object
        public BackButtonListener(ApplicationStart applicationStart) {
            this.applicationStart = applicationStart;
        }

        // This method is called when the "Back" button associated with this listener is
        // triggered
        @Override
        public void actionPerformed(ActionEvent e) {
            applicationStart.showPanel("Menu"); // Call the showPanel method of the ApplicationStart object to switch to
                                                // the "Menu" panel
        }
    }

    // Inner class: Handles the event for the "Start Game" button
    public static class StartGameButtonListener implements ActionListener {
        private Game game;
        private GameScreen gameScreen;
        private JPanel playerNamePanel;
        private ActionListener gameListener;

        // Constructor: Receives a Game object, the player name input panel, and the
        // game listener
        public StartGameButtonListener(Game game, GameScreen gameScreen, JPanel playerNamePanel,
                ActionListener gameListener) {
            this.game = game;
            this.gameScreen = gameScreen;
            this.playerNamePanel = playerNamePanel;
            this.gameListener = gameListener;
        }

        // This method is called when the "Start Game" button associated with this
        // listener is triggered
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> playerNames = new ArrayList<String>(); // Create an ArrayList to store player names
            // Traverse the components in the playerNamePanel
            for (Component component : playerNamePanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel inputPanel = (JPanel) component;
                    // Traverse the components in the inputPanel
                    for (Component inputComponent : inputPanel.getComponents()) {
                        if (inputComponent instanceof JTextField) {
                            JTextField textField = (JTextField) inputComponent;
                            playerNames.add(textField.getText()); // Add the text from the input field to the player
                                                                  // name list
                        }
                    }
                }
            }
            game.addPlayers(playerNames); // Pass the player name list to the Game object to add players
            gameScreen.addComponentsIntoJPanel();
            gameListener.actionPerformed(e); // Call the game listener to handle actions after the game starts
            gameScreen.gameEngine.run();
        }
    }

}
