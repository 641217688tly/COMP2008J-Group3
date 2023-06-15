package Listener.GUIListener;// 导入所需的库
import GUI.ApplicationStart;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 这个类包含了一些内部类，用于处理规则屏幕上按钮的事件
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
