package Listener.GUIListener;// 导入所需的库
import GUI.ApplicationStart;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 这个类包含了一些内部类，用于处理规则屏幕上按钮的事件
public class RulesScreenListener {

    // 内部类：处理"Back"按钮的事件
    public static class BackButtonListener implements ActionListener {
        private ApplicationStart applicationStart; // ApplicationStart对象的引用，用于调用showPanel方法

        // 构造方法，接收一个ApplicationStart对象
        public BackButtonListener(ApplicationStart applicationStart) {
            this.applicationStart = applicationStart; // 初始化ApplicationStart对象
        }

        // 当与此监听器关联的"Back"按钮被触发时，会调用此方法
        @Override
        public void actionPerformed(ActionEvent e) {
            applicationStart.showPanel("Menu"); // 调用ApplicationStart对象的showPanel方法，切换到"Menu"面板
        }
    }
}
