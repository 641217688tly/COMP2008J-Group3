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
    public int whichPlayerTurn = 0;
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

    @Override
    public void startNewGame() {
        Game.players.get(whichPlayerTurn).setPlayerTurn(true); //从一号玩家开始开启回合
        for (Player player : Game.players) {
            player.drawCards(Game.cardsPile.drawCardFromDrawPile(5)); //为玩家加牌
            player.moveToNextTurn(); //设置回合次数,打开或关闭卡牌的按钮
        }
    }

    @Override
    public void updateGame() {
        nextPlayerTurn();
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public void nextPlayerTurn() {
        if (Game.players.get(whichPlayerTurn).actionNumber == 0) { //玩家的行动次数为0
            if (false) { //TODO 如果玩家间的互动也已经结束,待完成
                Game.players.get(whichPlayerTurn).setPlayerTurn(false);
                whichPlayerTurn = (whichPlayerTurn + 1) % Game.players.size();
                Game.players.get(whichPlayerTurn).setPlayerTurn(true);
                for (Player player : Game.players) {
                    player.moveToNextTurn();
                }
            }
        }
        //TODO 移动玩家的位置
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
