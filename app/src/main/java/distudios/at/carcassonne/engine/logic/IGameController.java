package distudios.at.carcassonne.engine.logic;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public interface IGameController {

    void init();

    void action();

    boolean actionCardPlacement(Card card);

    void dataReceived(Object data, int type);

    void setPoints(ArrayList<Integer> points);

    void checkPoints(Card card);

    GameState getGameState();
    void setState(GameState s);

    void startGame();
    void updateGameState();
    boolean isMyTurn();
    void endTurn();
    boolean hasPlacedCard();
    void placeCard(Card c);
    List<Pair<Integer, Integer>> getPossibleLocations(Card c);

    Card drawCard();
    Card getCurrentCard();
    void removeFromStack(Card c);

}
