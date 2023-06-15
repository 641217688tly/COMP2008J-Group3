package Module;

public interface IGame {
    // game start
    void startNewGame();

    // game over
    boolean isGameOver();

    // Update operations
    void updateGame();

    // Go to the next player's turn
    void moveToNextPlayerTurn();
}
