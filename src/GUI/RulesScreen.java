package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class RulesScreen extends JPanel {
    private Image rulesScreenBackground; // Variable to store the background image

    // Constructor to initialize the RulesScreen
    public RulesScreen(ActionListener backButtonListener) {
        setLayout(new BorderLayout()); // Set the layout to BorderLayout
        loadAndSetBackgroundImage();// Load the background image
        setupRulesTextArea(); // Set up the rules text area
        setupBackButton(backButtonListener); // Set up the back button
    }

    // Load and set the background image
    private void loadAndSetBackgroundImage() {
        try {
            // Read the background image from a file
            rulesScreenBackground = ImageIO.read(new File("images/GUI/RulesScreenBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Set up the rules text area
    private void setupRulesTextArea() {
        JTextArea rulesTextArea = new JTextArea(); // Create a text area
        rulesTextArea.setEditable(false); // Set it to non-editable
        rulesTextArea.setTabSize(24); // Set the tab size
        Font font = new Font("Arial", Font.PLAIN, 24); // Create a font
        rulesTextArea.setFont(font); // Set the font
        rulesTextArea.setText("Before the game starts:\n"
                + "Shuffle the cards and assign 5 cards to each player, leaving the remaining cards in the center collar for collection\n"
                + "\n" +
                "The game begins:\n" +
                "1. Draw 2 cards on your turn. If you don't have any cards in your hand, you pick up 5 cards on your next turn.\n"
                +
                "2. Can play 1-3 cards.\n" +
                "3. You can deposit currency or action cards into your bank. Action cards can be used as currency. When an action card is placed in the bank, it can only be used as currency in the game. Even if it is transferred to another player, it can no longer be used as an action card.\n"
                +
                "4. Collect your property cards. Put the property cards in front of you and build the property. Each card shows the number of property cards required to complete the colour series.\n"
                +
                "5. the use of action card, put it in the central position according to the instructions on the action card.\n"
                +
                "6. Make sure you have no more than 7 cards in your hand after you play a card. Otherwise, selectively discard the card and place the discard card at the bottom of the deck until there are 7 cards left in your hand.\n"
                + "\n" +
                "How to win:\n" +
                "A complete set of 3 sets of property cards in different colors will win."); // Set the game rules text
                                                                                             // (omitted for brevity)
        SimpleAttributeSet green = new SimpleAttributeSet();
        StyleConstants.setBackground(green, Color.CYAN);
        rulesTextArea.setLineWrap(true); // Enable line wrapping
        rulesTextArea.setWrapStyleWord(true); // Wrap at word boundaries
        rulesTextArea.setOpaque(false); // Set the text area to be transparent

        // Add the text area to a scroll pane and add the scroll pane to the center of
        // the panel
        JScrollPane scrollPane = new JScrollPane(rulesTextArea);
        scrollPane.setOpaque(false); // Set the scroll pane to be transparent
        scrollPane.getViewport().setOpaque(false); // Set the viewport of the scroll pane to be transparent
        add(scrollPane, BorderLayout.CENTER);
    }

    // Set up the back button
    private void setupBackButton(ActionListener backListener) {
        JPanel buttonPanel = new JPanel(); // Create a button panel
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS)); // Set it to have a horizontal BoxLayout

        buttonPanel.add(Box.createHorizontalGlue()); // Add horizontally resizable glue

        // Create the back button
        JButton backButton = new JButton("Back");
        backButton.setMaximumSize(new Dimension(120, 30)); // Set the maximum size
        backButton.addActionListener(backListener); // Add the listener
        buttonPanel.add(backButton); // Add the button to the button panel

        buttonPanel.add(Box.createHorizontalGlue()); // Add horizontally resizable glue

        // Add the button panel to the south (bottom) of the panel
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void drawBackground(Graphics g) {
        if (rulesScreenBackground != null) { // If the background image is loaded
            // Draw the background image on the panel, filling the entire panel
            g.drawImage(rulesScreenBackground, 0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight, this);
        }
    }

    // Override the paintComponent method to draw the background image on the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the parent class method to ensure normal painting
        drawBackground(g);
    }

}
