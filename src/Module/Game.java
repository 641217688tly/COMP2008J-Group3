package Module;

import GUI.ApplicationStart;
import Module.Cards.Card;
import Module.PlayerAndComponents.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements IGame {
    public static int[] playersJPanelXCoordinate = {(ApplicationStart.screenWidth * 11) / 12, 0, (ApplicationStart.screenWidth * 2) / 12, (ApplicationStart.screenWidth * 9) / 12, (ApplicationStart.screenWidth * 11) / 12};
    public static int[] playersJPanelYCoordinate = {(ApplicationStart.screenHeight * 4) / 5, (ApplicationStart.screenHeight * 2) / 5, 0, 0, (ApplicationStart.screenHeight * 2) / 5};
    public static CardsPile cardsPile = new CardsPile(); //中央牌区
    public static ArrayList<Player> players = new ArrayList<>(); //所有的Player对象(Player对象中包含有Bank,Property,PlayerCards以及PlayerCardsPile这些组件)
    private int counter = 1;

    public void addPlayers(List<String> playerNames) { //用于在设置界面设置完玩家人数和姓名后创建所有的玩家对象并添加到Game类的players中
        Game.players.clear();
        for (int i = 0; i < playerNames.size(); i++) {
            Player player = new Player(playerNames.get(i), Player.images[i], Game.playersJPanelXCoordinate[i], Game.playersJPanelYCoordinate[i]);
            players.add(player);
        }
    }

    public Game() {

    }

    @Override
    public void startNewGame() {
        Game.players.get(0).setPlayerTurn(true); //从一号玩家开始开启回合
        for (Player player : Game.players) {
            player.drawCards(Game.cardsPile.drawCardFromDrawPile(5)); //回合开始时每个玩家都先领取五张牌
            player.moveToNextTurn(); //设置回合次数,打开或关闭卡牌的按钮
        }
    }

    @Override
    public void updateGame() {
        nextPlayerTurn();
    }

    @Override
    public void nextPlayerTurn() {
        if (Game.players.get(0).actionNumber == 0) { //玩家的行动次数为0
            if (true) { //TODO 如果玩家间的互动也已经结束,待完成
                Game.players.get(0).setPlayerTurn(false);
                reDistributePlayersLocation(); //将会转动玩家以及玩家的数组
                Game.players.get(0).setPlayerTurn(true); //为下一个玩家设置成是他的回合

                //补牌:
                int cardsCount = 0;
                for (Card card : Game.players.get(0).cardsTable) {
                    if (card != null) {
                        cardsCount++;
                    }
                }
                if (counter >= Game.players.size()) {
                    if (cardsCount <= 7) {
                        if (cardsCount == 0) {
                            Game.players.get(0).drawCards(Game.cardsPile.drawCardFromDrawPile(5));
                        } else if (cardsCount == 7) {
                            Game.players.get(0).drawCards(Game.cardsPile.drawCardFromDrawPile(2));
                        } else {
                            Game.players.get(0).drawCards(Game.cardsPile.drawCardFromDrawPile(3));
                        }
                    }
                } else {
                    counter++;
                }
                for (Player player : Game.players) {
                    player.moveToNextTurn();
                }
            }
        }
    }

    private void reDistributePlayersLocation() {
        // Rotate the players list counter-clockwise by 1
        Collections.rotate(Game.players, -1);
        // Update the players' coordinates according to their new order
        for (int i = 0; i < Game.players.size(); i++) {
            Player player = Game.players.get(i);
            player.playerJPanelX = playersJPanelXCoordinate[i];
            player.playerJPanelY = playersJPanelYCoordinate[i];
            player.setBounds(player.playerJPanelX, player.playerJPanelY, Player.playerWidth, Player.playerHeight);
        }
    }

    @Override
    public boolean isGameOver() {
        //方法待实现
        return false;
    }

}
