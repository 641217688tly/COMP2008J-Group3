package GUI;

import Module.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameScreen extends JPanel {
    private Image gameScreenBackground; // 存储背景图像的变量
    private Game game;

    public GameScreen() {
        loadAndSetBackgroundImage();
    }

    // 加载并设置背景图片
    private void loadAndSetBackgroundImage() {
        try {
            // 从文件中读取背景图片
            gameScreenBackground = ImageIO.read(new File("images/GameScreenBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void drawBackground(Graphics g) {
        if (gameScreenBackground != null) { // 如果背景图片已加载
            // 在面板上绘制背景图片，使其填充整个面板
            g.drawImage(gameScreenBackground, 0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight, this);
        }
    }

    // 重写paintComponent方法以在面板上绘制背景图片
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        drawBackground(g);
    }
}
