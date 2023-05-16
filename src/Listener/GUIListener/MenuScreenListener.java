package Listener.GUIListener;// 导入所需的库

import GUI.ApplicationStart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 这个类包含了一些内部类，用于处理菜单屏幕上按钮的事件
public class MenuScreenListener {

    // 内部类：处理"Rules"按钮的事件
    public static class RulesButtonListener implements ActionListener {
        private ApplicationStart applicationStart; // ApplicationStart对象的引用，用于调用showPanel方法

        // 构造方法，接收一个ApplicationStart对象
        public RulesButtonListener(ApplicationStart applicationStart) {
            this.applicationStart = applicationStart; // 初始化ApplicationStart对象
        }

        // 当与此监听器关联的"Rules"按钮被触发时，会调用此方法
        @Override
        public void actionPerformed(ActionEvent e) {
            applicationStart.showPanel("Rules"); // 调用ApplicationStart对象的showPanel方法，切换到"Rules"面板
        }
    }

    // 内部类：处理"Settings"按钮的事件
    public static class SettingsButtonListener implements ActionListener {
        private ApplicationStart applicationStart; // ApplicationStart对象的引用，用于调用showPanel方法

        // 构造方法，接收一个ApplicationStart对象
        public SettingsButtonListener(ApplicationStart applicationStart) {
            this.applicationStart = applicationStart; // 初始化ApplicationStart对象
        }

        // 当与此监听器关联的"Settings"按钮被触发时，会调用此方法
        @Override
        public void actionPerformed(ActionEvent e) {
            applicationStart.showPanel("Exit"); // 调用ApplicationStart对象的showPanel方法，切换到"Exit"面板
        }
    }

    // 内部类：处理"Game"按钮的事件
    public static class GameButtonListener implements ActionListener {
        private ApplicationStart applicationStart; // ApplicationStart对象的引用，用于调用showPanel方法

        // 构造方法，接收一个ApplicationStart对象
        public GameButtonListener(ApplicationStart applicationStart) {
            this.applicationStart = applicationStart; // 初始化ApplicationStart对象
        }

        // 当与此监听器关联的"Game"按钮被触发时，会调用此方法
        @Override
        public void actionPerformed(ActionEvent e) {
            applicationStart.showPanel("Game"); // 调用ApplicationStart对象的showPanel方法，切换到"Game"面板
        }
    }
}
