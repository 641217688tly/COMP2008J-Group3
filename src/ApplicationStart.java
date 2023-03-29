public class ApplicationStart {
    /*
    app的启动类
    该类仅需要一个main方法
    main方法中应该创建多个JFrame,Listener,Screen类的对象
     */
    public static void main(String[] args) {
        /*
        PACMAN EXAMPLE:
        JFrame mainWindow = new JFrame();
        mainWindow.setVisible(true);
        Insets insets = mainWindow.getInsets();
        mainWindow.setSize(PACMAN_Game.SCREEN_WIDTH + insets.left + insets.right, PACMAN_Game.SCREEN_HEIGHT + insets.top + insets.bottom);
        mainWindow.setTitle("PAC-MAN(Name:LiYanTao)");
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);

        PlayerListener playerListener = new PlayerListener();
        mainWindow.addKeyListener(playerListener);
        MenuListener menuListener = new MenuListener();
        mainWindow.addKeyListener(menuListener);
        PACMAN_Game game = new PACMAN_Game(playerListener);
        GameScreen gameScreen = new GameScreen(game);
        MenuScreen menuScreen = new MenuScreen();
        ScoreKeeper scoreKeeper = new ScoreKeeper("scores.txt");
        GameManager mmm = new GameManager(game, mainWindow, menuListener, menuScreen, new AboutScreen(), new ScoreScreen(scoreKeeper), gameScreen, scoreKeeper);
        mainWindow.setVisible(true);
        mmm.run();
         */
    }
}
