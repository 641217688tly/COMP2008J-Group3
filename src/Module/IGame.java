package Module;

public interface IGame {
    // 游戏开始
    void startNewGame();

    // 游戏结束
    boolean isGameOver();

    // 游戏的更新操作
    void updateGame();

    // 进入下一个玩家的回合
    void nextPlayerTurn();
}
