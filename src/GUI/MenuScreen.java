package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MenuScreen extends JPanel {
    private Image menuScreenBackground; // Variable to store the background image

    // Constructor to initialize the MenuScreen
    public MenuScreen(ActionListener rulesButtonListener, ActionListener settingsButtonListener,
            ActionListener gameButtonListener) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set the layout to a vertical BoxLayout
        loadAndSetBackgroundImage(); // Load and set the background image
        createAndAddButtons(rulesButtonListener, settingsButtonListener, gameButtonListener); // Create and add buttons
    }

    // Load and set the background image
    private void loadAndSetBackgroundImage() {
        try {
            // Read the background image from a file
            menuScreenBackground = ImageIO.read(new File("images/GUI/MenuScreenBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create and add buttons
    private void createAndAddButtons(ActionListener rulesListener, ActionListener settingsListener,
            ActionListener gameListener) {
        // Add spacing
        add(Box.createRigidArea(new Dimension(0, 100)));
        // Create and add the Start Game button
        createAndAddStartGameButton(settingsListener);
        // Add spacing
        add(Box.createRigidArea(new Dimension(0, 100)));
        // Create and add the Rules button
        createAndAddRulesButton(rulesListener);
        // Add spacing
        add(Box.createRigidArea(new Dimension(0, 100)));
        // Create and add the Exit button
        createAndAddExitButton();
        // Add vertically resizable glue to keep the components centered
        add(Box.createVerticalGlue());
    }

    // Create and add the Start Game button
    private void createAndAddStartGameButton(ActionListener settingsListener) {
        JButton gameButton = new JButton("Start Game");
        gameButton.setMaximumSize(new Dimension(300, 500));
        gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameButton.addActionListener(settingsListener);
        add(gameButton);
    }

    // Create and add the Rules button
    private void createAndAddRulesButton(ActionListener rulesListener) {
        JButton rulesButton = new JButton("Rules");
        rulesButton.setMaximumSize(new Dimension(300, 500));
        rulesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rulesButton.addActionListener(rulesListener);
        add(rulesButton);
    }

    // Create and add the Exit button
    private void createAndAddExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.setMaximumSize(new Dimension(300, 500));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    private void drawBackground(Graphics g) {
        if (menuScreenBackground != null) { // If the background image is loaded
            // Draw the background image on the panel, filling the entire panel
            g.drawImage(menuScreenBackground, 0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight, this);
        }
    }

    // Override the paintComponent method to draw the background image on the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the parent class method to ensure normal painting
        drawBackground(g);
    }

}
