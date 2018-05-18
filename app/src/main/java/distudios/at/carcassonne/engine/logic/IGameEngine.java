package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;

public interface IGameEngine {

    GameState getState();
    void setState(GameState s);

    void init(Orientation orientation);

    void placeCard(Card card);

    boolean checkPlaceable(Card card);

    void addScore(int point, int player);

    ArrayList<Integer> getScoreChanges(Card card);

    void placePeep(Peep peep);

    boolean checkPeepPlaceable(Card nextCard);

}
