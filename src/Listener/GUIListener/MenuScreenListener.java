package Listener.GUIListener;// 导入所需的库

import GUI.ApplicationStart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


//this class concluds some class in order to handle the screen button
public class MenuScreenListener {

    // Inner class: Handles the event for the "Rules" button
public static class RulesButtonListener implements ActionListener {
    private ApplicationStart applicationStart;

    // Constructor: Receives an ApplicationStart object
    public RulesButtonListener(ApplicationStart applicationStart) {
        this.applicationStart = applicationStart;
    }

    // This method is called when the "Rules" button associated with this listener is triggered
    @Override
    public void actionPerformed(ActionEvent e) {
        applicationStart.showPanel("Rules"); // Call the showPanel method of the ApplicationStart object to switch to the "Rules" panel
    }
}

// Inner class: Handles the event for the "Settings" button
public static class SettingsButtonListener implements ActionListener {
    private ApplicationStart applicationStart;

    // Constructor: Receives an ApplicationStart object
    public SettingsButtonListener(ApplicationStart applicationStart) {
        this.applicationStart = applicationStart;
    }

    // This method is called when the "Settings" button associated with this listener is triggered
    @Override
    public void actionPerformed(ActionEvent e) {
        applicationStart.showPanel("Exit"); // Call the showPanel method of the ApplicationStart object to switch to the "Exit" panel
    }
}

// Inner class: Handles the event for the "Game" button
public static class GameButtonListener implements ActionListener {
    private ApplicationStart applicationStart;

    // Constructor: Receives an ApplicationStart object
    public GameButtonListener(ApplicationStart applicationStart) {
        this.applicationStart = applicationStart;
    }

    // This method is called when the "Game" button associated with this listener is triggered
    @Override
    public void actionPerformed(ActionEvent e) {
        applicationStart.showPanel("Game"); // Call the showPanel method of the ApplicationStart object to switch to the "Game" panel
    }
}

}
