package Module;

import GUI.ApplicationStart;
import Module.PlayerAndComponents.Player;

import java.util.ArrayList;
import java.util.List;

public class Game implements IGame {
    public static int[] playersJPanelXCoordinate = {(ApplicationStart.screenWidth * 11) / 12, 0, (ApplicationStart.screenWidth * 2) / 12, (ApplicationStart.screenWidth * 9) / 12, (ApplicationStart.screenWidth * 11) / 12};
    public static int[] playersJPanelYCoordinate = {(ApplicationStart.screenHeight * 4) / 5, (ApplicationStart.screenHeight * 2) / 5, 0, 0, (ApplicationStart.screenHeight * 2) / 5};
    public static CardsPile cardsPile = new CardsPile(); //中央牌区
    public static ArrayList<Player> players = new ArrayList<>(); //所有的Player对象(Player对象中包含有Bank,Property,PlayerCards以及PlayerCardsPile这些组件)
    private boolean isPaused = false;

    public Game() {

    }

    public void addPlayers(List<String> playerNames) { //用于在设置界面设置完玩家人数和姓名后创建所有的玩家对象并添加到Game类的players中
        Game.players.clear();
        for (int i = 0; i < playerNames.size(); i++) {
            Player player = new Player(playerNames.get(i), Player.images[i], Game.playersJPanelXCoordinate[i], Game.playersJPanelYCoordinate[i]);
            players.add(player);
        }
    }

    public void initPlayersCards() {
        for (int i = 0; i < Game.players.size(); i++) {
            Game.players.get(i).drawCards(cardsPile.drawCardFromDrawPile(5));
        }
    }

    @Override
    public void startNewGame() {
        Game.players.get(0).setPlayerTurn(true); //从一号玩家开始开启回合
        initPlayersCards();
    }

    @Override
    public void updateGame() {

    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public void nextPlayerTurn() {

    }

    @Override
    public boolean isGameOver() {
        //方法待实现
        return false;
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public boolean getIsPaused() {
        return this.isPaused;
    }
}
