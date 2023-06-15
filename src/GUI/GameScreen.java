package GUI;

import Module.Game;
import Module.PlayerAndComponents.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import Module.GameEngine;

public class GameScreen extends JPanel {
    private Image gameScreenBackground; // Variable to store the background image
    private Game game;
    public GameEngine gameEngine;

    public GameScreen(Game game) {
        this.setLayout(null); // Need to manually set the position and size of each component
        setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight); // Set the size and position of
                                                                                      // the GameScreen

        loadAndSetBackgroundImage();
        setPreferredSize(new Dimension(ApplicationStart.screenWidth, ApplicationStart.screenHeight)); // Set the
                                                                                                      // preferred size
                                                                                                      // of the
                                                                                                      // GameScreen
        this.game = game;
        this.gameEngine = new GameEngine(game, this);
        this.setFocusable(true); // Set the panel to be focusable
        this.requestFocusInWindow(); // Request focus
    }

    // Load and set the background image
    private void loadAndSetBackgroundImage() {
        try {
            // Read the background image from a file
            gameScreenBackground = ImageIO.read(new File("images/GUI/GameScreenBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addComponentsIntoJPanel() {
        for (int i = 0; i < Game.players.size(); i++) {
            Player player = Game.players.get(i);
            // Add the Player to the JPanel
            this.add(player);
            // Add the Player's PlayerCardsPile to the JPanel
            this.add(player.playerCardsPile);
            // Add the Player's Bank to the JPanel:
            this.add(player.bank);
            // Add the Player's Property to the JPanel:
            this.add(player.property);
            // Add the Player's HandCards to the JPanel:
            this.add(player.handCards);
        }
        // Add the central CardsPile to the JPanel
        this.add(Game.cardsPile);
    }

    private void paintBackground(Graphics g) {
        if (gameScreenBackground != null) { // If the background image is loaded
            g.drawImage(gameScreenBackground, 0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight, null);
        }
    }

    private void paintDiscardReminder(Graphics g) { // If the number of cards in the player's hand is greater than 7
                                                    // before the end of the turn, remind the player to discard!
        if (Game.players.get(0).isPlayerTurn()) {
            if (Game.players.get(0).actionNumber <= 0) {
                if (Game.players.get(0).isInAction()) {
                    boolean flag = true;
                    for (Player player : Game.players) {
                        if (player.interactivePlayers.size() > 0) { // If the interaction between players is not
                                                                    // completed
                            flag = false;
                            break;
                        }
                    }
                    if (Game.players.get(0).numberOfHandCards() <= 7) { // If the number of cards is less than or equal
                                                                        // to 7
                        flag = false;
                    }
                    if (flag) {
                        g.setColor(Color.RED); // Set the text color
                        g.setFont(new Font("Arial", Font.BOLD, 30)); // Set the font and size
                        g.drawString("Discard to 7 remaining!",
                                5 * ApplicationStart.screenWidth / 12 - ApplicationStart.screenWidth / 40,
                                4 * ApplicationStart.screenHeight / 5 - ApplicationStart.screenHeight / 10);
                    }
                }
            }
        }
    }

    private void paintSwappedCard(Graphics g) {
        // TODO: Can the card be drawn on the desktop when swapping cards?
        g.drawImage(gameScreenBackground, 0, 0, 7 * ApplicationStart.screenWidth / 12,
                2 * ApplicationStart.screenHeight / 5, null);

    }

    private void paintMoveToNextTurnMessage(Graphics g) {
        if (game.isMoveToNextTurn) {
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("The Current Turn Ends! The Next Turn Is Player " + Game.players.get(0).name + "'s Turn!",
                    1 * ApplicationStart.screenWidth / 12 + ApplicationStart.screenWidth / 18,
                    1 * ApplicationStart.screenHeight / 5 + ApplicationStart.screenHeight / 10);
        }
    }

    private void paintGameOverMessage(Graphics g) {
        if (game.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", 5 * ApplicationStart.screenWidth / 12 + ApplicationStart.screenWidth / 100,
                    ApplicationStart.screenHeight / 5);
            g.drawString("The Winner Is: " + Game.players.get(0).name + "!",
                    5 * ApplicationStart.screenWidth / 12 - ApplicationStart.screenWidth / 48,
                    2 * ApplicationStart.screenHeight / 5 - ApplicationStart.screenHeight / 10);

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the parent class method to ensure normal painting
        paintBackground(g);
        paintDiscardReminder(g);
        paintGameOverMessage(g);
        paintMoveToNextTurnMessage(g);
    }

}
