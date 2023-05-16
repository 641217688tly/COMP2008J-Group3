package GUI;

import Module.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameScreen extends JPanel {
    private Image gameScreenBackground; // 存储背景图像的变量

    public GameScreen() {
        setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight); // 设置GameScreen的大小和位置
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        loadAndSetBackgroundImage();
        setPreferredSize(new Dimension(ApplicationStart.screenWidth, ApplicationStart.screenHeight)); // 设置GameScreen的理想大小
    }

    // 加载并设置背景图片
    private void loadAndSetBackgroundImage() {
        try {
            // 从文件中读取背景图片
            gameScreenBackground = ImageIO.read(new File("images/GUI/GameScreenBackground.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addComponentsIntoJPanel() {
        for (int i = 0; i < Game.players.size(); i++) {
            this.add(Game.players.get(i));
        }
        this.add(Game.cardsPile);
    }

    private void drawBackground(Graphics g) {
        if (gameScreenBackground != null) { // 如果背景图片已加载
            g.drawImage(gameScreenBackground, 0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight, null);
        }
    }

    private void drawPlayerCardsPile(Graphics g) { //TODO 不通过JPanel组件调用,而是通过传入PlayerCardsPile内的Image变量来绘制图片是不能一劳永逸的,必须进行更改
        for (int i = 0; i < Game.players.size(); i++) {
            Game.players.get(i).drawPlayerCardsPile(g);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        drawBackground(g);
        drawPlayerCardsPile(g);//TODO 不通过JPanel组件调用,而是通过传入PlayerCardsPile内的Image变量来绘制图片是不能一劳永逸的,必须进行更改
    }
}
