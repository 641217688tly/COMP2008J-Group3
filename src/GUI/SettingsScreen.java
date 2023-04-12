package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import Module.Game;

public class SettingsScreen extends JPanel {
    private JComboBox<Integer> playerCountComboBox;
    private JPanel playerNamePanel;
    private JButton startGameButton;
    private ActionListener gameListener;
    private Game game;

    public SettingsScreen(ActionListener backListener, ActionListener gameListener, Game game) {
        this.gameListener = gameListener;
        this.game = game;
        setLayout(new BorderLayout());

        playerCountComboBox = new JComboBox<>(new Integer[]{2, 3, 4, 5});
        playerCountComboBox.addActionListener(e -> createPlayerNameInputs());
        JPanel playerCountPanel = new JPanel();
        playerCountPanel.add(new JLabel("Player count: "));
        playerCountPanel.add(playerCountComboBox);
        add(playerCountPanel, BorderLayout.NORTH);

        playerNamePanel = new JPanel();
        playerNamePanel.setLayout(new BoxLayout(playerNamePanel, BoxLayout.Y_AXIS));
        add(playerNamePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        startGameButton = new JButton("Start Game");
        StartGameButtonListener startGameButtonListener = new StartGameButtonListener(game, playerNamePanel, gameListener);
        startGameButton.addActionListener(startGameButtonListener);
        startGameButton.setEnabled(false);
        buttonPanel.add(startGameButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(backListener);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createPlayerNameInputs() {
        playerNamePanel.removeAll();

        int playerCount = (Integer) playerCountComboBox.getSelectedItem();
        for (int i = 0; i < playerCount; i++) {
            JPanel inputPanel = new JPanel();
            inputPanel.add(new JLabel("Player " + (i + 1) + " name: "));
            inputPanel.add(new JTextField(10));
            playerNamePanel.add(inputPanel);
        }
        playerNamePanel.revalidate();
        playerNamePanel.repaint();

        startGameButton.setEnabled(true);
    }

    // StartGameButtonListener 内部类,用于读取玩家输入的玩家名
    class StartGameButtonListener implements ActionListener {
        private Game game;
        private JPanel playerNamePanel;
        private ActionListener gameListener;

        public StartGameButtonListener(Game game, JPanel playerNamePanel, ActionListener gameListener) {
            this.game = game;
            this.playerNamePanel = playerNamePanel;
            this.gameListener = gameListener;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> playerNames = new ArrayList<>();
            for (Component component : playerNamePanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel inputPanel = (JPanel) component;
                    for (Component inputComponent : inputPanel.getComponents()) {
                        if (inputComponent instanceof JTextField) {
                            JTextField textField = (JTextField) inputComponent;
                            playerNames.add(textField.getText());
                        }
                    }
                }
            }
            game.addPlayers(playerNames);
            gameListener.actionPerformed(e);
        }
    }
}
