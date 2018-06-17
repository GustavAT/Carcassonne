package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;

public interface IGameEngine {

    GameState getState();
    void setState(GameState s);

    void init(Orientation orientation);

    void placeCard(Card card);

    boolean checkPlaceable(Card card);

    void addScore(int point, int player);

    ArrayList<Score> getScoreChanges(Card card);

    ArrayList<PeepPosition> getMarkedBorders(Card card, CardSide cardSide);

    ArrayList<PeepPosition> getUnmarkedBorders(Card card, CardSide cardSide);

    boolean markCard(Card card, PeepPosition mark, CardSide cardSide);

    boolean placePeep(Card card, PeepPosition mark, int playerID );

    ArrayList<PeepPosition> getALLFigurePos(Card card);

    boolean markAllCards();

    int getPlayerPeeps(int playerID);

}
