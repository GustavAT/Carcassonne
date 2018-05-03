package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;

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

}
