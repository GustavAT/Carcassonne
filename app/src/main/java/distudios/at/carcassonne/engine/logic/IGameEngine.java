package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.List;

public interface IGameEngine {

    GameState getState();
    void setState(GameState s);

    void init(Orientation orientation);

    void placeCard(Card card);

    boolean checkPlaceable(Card card);

    void addScore(int point, int player);

    ArrayList<Integer> getScoreChanges(Card card);

    List<PeepPosition> getMarkedBorders(Card card, CardSide cardSide);

    List<PeepPosition> getUnmarkedBorders(Card card, CardSide cardSide);

    boolean markCard(Card card, PeepPosition mark, CardSide cardSide);

    boolean placePeep(Card card, PeepPosition mark, int playerID );

    ArrayList<PeepPosition> getALLFigurePos(Card card);

    boolean markAllCards();

    int getPlayerPeeps(int playerID);

}
