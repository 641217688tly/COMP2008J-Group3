package Listener.GUIListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GUI.ApplicationStart;

// This inner class implements the ActionListener interface to handle panel switching events
public class ApplicationStartListener {

    public static class ShowPanelActionListener implements ActionListener {
        private ApplicationStart applicationStart; // Reference to the ApplicationStart object to call the showPanel
                                                   // method
        private String panelName; // Name of the panel to be displayed

        // Constructor that receives an ApplicationStart object and the name of the
        // panel to be displayed
        public ShowPanelActionListener(ApplicationStart applicationStart, String panelName) {
            this.applicationStart = applicationStart; // Initialize the ApplicationStart object
            this.panelName = panelName; // Initialize the panel name
        }

        // This method is called when the component associated with this listener (e.g.,
        // a button) is triggered
        @Override
        public void actionPerformed(ActionEvent e) {
            applicationStart.showPanel(panelName); // Call the showPanel method of the ApplicationStart object to switch to the specified pane

        }
    }
}
