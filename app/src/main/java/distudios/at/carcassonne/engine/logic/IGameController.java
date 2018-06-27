package distudios.at.carcassonne.engine.logic;

import android.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface IGameController {

    void setPoints(List<Integer> points, int multiplier);

    void checkPoints(Card card);

    GameState getGameState();
    void setState(GameState s);

    void startGame();
    void updateGameState();
    boolean isMyTurn();
    boolean hasPlacedCard();

    void initPlayerMappings();
    List<Pair<Integer, Integer>> getPossibleLocations(Card c);

    /**
     * Draw one card from stack (do not remove from stack -> call remove from stack)
     */
    Card drawCard();

    /**
     * Draw three cards from stack (do not remove from stack -> call remove from stack)
     * @return
     */
    List<Card> drawCards();


    boolean isCheating();
    void setCheating(boolean cheating);

    /**
     * Place single card on the field
     * @return false if position is invalid
     */
    boolean placeCard(Card card);

    /**
     * Place figure on the field
     * @return false if position is invalid
     */
    boolean placeFigure(Card card, PeepPosition position);

    List<Peep> getPlacedPeeps(Card c);

    boolean canPlacePeep();

    int peepsLeft();


    /**
     * Gets possible positions of a Peep on the current card
     * @param card the current placed card
     */
    List<PeepPosition> showPossibleFigurePos(Card card);

    /**
     * End turn of this player
     */
    void endTurn();

    /**
     * init new turn for this player
     */
    void initMyTurn();

    // card operations

    Card getCurrentCard();
    void setCurrentCard(Card c);
    void removeFromStack(Card c);

    CState getCState();

    boolean isDebug();
    void debug(boolean flag);

}
