package distudios.at.carcassonne.engine.logic;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public interface IGameController {

    void setPoints(ArrayList<Integer> points);

    void checkPoints(Card card);

    GameState getGameState();
    void setState(GameState s);

    void startGame();
    void updateGameState();
    boolean isMyTurn();
    boolean hasPlacedCard();
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

    /**
     * Place single card on the field
     * @return false if position is invalid
     */
    boolean placeCard(Card card);

    /**
     * Place figure on the field
     * @return false if position is invalid
     */
    boolean placeFigure(PeepPosition position, Card nextCard);


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

}
