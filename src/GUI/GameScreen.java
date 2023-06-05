package GUI;

import Listener.GUIListener.GameScreenListener;
import Module.Game;
import Module.PlayerAndComponents.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import Module.GameEngine;

public class GameScreen extends JPanel {
    private Image gameScreenBackground; // 存储背景图像的变量
    private GameScreenListener gameScreenListener;
    private Game game;
    public GameEngine gameEngine;

    public GameScreen(Game game) {
        this.setLayout(null); // 需要手动设置每个组件的位置和大小
        setBounds(0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight); // 设置GameScreen的大小和位置

        loadAndSetBackgroundImage();
        setPreferredSize(new Dimension(ApplicationStart.screenWidth, ApplicationStart.screenHeight)); // 设置GameScreen的理想大小
        this.game = game;
        this.gameEngine = new GameEngine(game, this);
        this.gameScreenListener = new GameScreenListener(game);
        this.addKeyListener(gameScreenListener);  //添加键盘监听器到这个面板
        this.setFocusable(true); //设置面板可以获取焦点
        this.requestFocusInWindow(); //请求焦点
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
            Player player = Game.players.get(i);
            //将Player添加到JPanel中
            this.add(player);
            //将Player的PlayerCardsPile添加到JPanel中
            this.add(player.playerCardsPile);
            //将Player的Bank添加到JPanel中:
            this.add(player.bank);
            //将Player的Property添加到JPanel中:
            this.add(player.property);
            //将Player的HandCards添加到JPanel中:
            this.add(player.handCards);
        }
        //将中央的牌堆CardsPile添加到JPanel中
        this.add(Game.cardsPile);
    }

    private void drawBackground(Graphics g) {
        if (gameScreenBackground != null) { // 如果背景图片已加载
            g.drawImage(gameScreenBackground, 0, 0, ApplicationStart.screenWidth, ApplicationStart.screenHeight, null);
        }
    }

    private void drawPausePrompt(Graphics g) {
        if (game.getIsPaused()) {
            Font originalFont = g.getFont(); // 保存原始字体
            Color originalColor = g.getColor(); // 保存原始颜色

            g.setFont(new Font("Arial", Font.BOLD, 24)); // 设置新的字体
            g.setColor(Color.RED); // 设置新的颜色

            int x = (ApplicationStart.screenWidth / 12) * 5;
            int y = ApplicationStart.screenHeight / 5; // 对y坐标进行微调，以便字符串在指定的区域内垂直居中
            g.drawString("Press P to cancel the pause", x, y);

            g.setFont(originalFont); // 恢复原始字体
            g.setColor(originalColor); // 恢复原始颜色
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // 调用父类方法以确保正常绘制
        drawBackground(g);
        drawPausePrompt(g);
    }
}
