package Listener.GUIListener;
import GUI.ApplicationStart;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RulesScreenListener {

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

}
