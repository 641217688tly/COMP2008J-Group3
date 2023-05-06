package Listener.GUIListener;// 导入所需的库
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GUI.ApplicationStart;

// 这个类包含了一个内部类，处理显示不同面板的事件
public class ApplicationStartListener {

    // 这个内部类实现了ActionListener接口，用于处理切换面板的操作
    public static class ShowPanelActionListener implements ActionListener {
        private ApplicationStart applicationStart; // ApplicationStart对象的引用，用于调用showPanel方法
        private String panelName; // 要显示的面板的名称

        // 构造方法，接收一个ApplicationStart对象和要显示的面板的名称
        public ShowPanelActionListener(ApplicationStart applicationStart, String panelName) {
            this.applicationStart = applicationStart; // 初始化ApplicationStart对象
            this.panelName = panelName; // 初始化面板名称
        }

        // 当与此监听器关联的组件（例如按钮）被触发时，会调用此方法
        @Override
        public void actionPerformed(ActionEvent e) {
            applicationStart.showPanel(panelName); // 调用ApplicationStart对象的showPanel方法，切换到指定的面板
        }
    }
}
