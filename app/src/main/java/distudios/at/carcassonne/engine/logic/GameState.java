package distudios.at.carcassonne.engine.logic;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonObject
public class GameState implements Serializable {

    @JsonField
    public List<Card> cards;
    @JsonField
    public List<Peep> peeps;
    @JsonField
    public List<Integer> stack;
    @JsonField
    public List<Integer> points;
    @JsonField
    public int maxPlayerCount;
    @JsonField
    public int currentPlayer;

    public GameState() {
        cards = new ArrayList<>();
        peeps = new ArrayList<>();
        stack = new ArrayList<>();
        points = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            points.add(0);
        }
        maxPlayerCount = 5;
        currentPlayer = 0;
    }

    public List<Integer> getStack() {
        return stack;
    }

    public void setStack(List<Integer> stack) {
        this.stack = stack;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addPeep(Peep peep) { peeps.add(peep); }

    public List<Peep> getPeeps() {
        return peeps;
    }

    public void removePeep(Object peep) {
        peeps.remove(peep);
    }

    public int getPoints(int player) {
        return points.get(player);
    }

    public void setPoints(int player, int newPoints) {
        points.set(player, newPoints);
    }

    /**
     * Get the player number for the next turn
     * @return
     */
    public int getNextPlayer() {
        return (currentPlayer + 1) % maxPlayerCount;
    }

    /**
     * Check if its my turn
     * @param playerNumber my player number
     * @return
     */
    public boolean myTurn(int playerNumber) {
        return currentPlayer == playerNumber;
    }

    /**
     * Draw card from stack without removing it
     * @return
     */
    public Card drawCard() {
        if (!stack.isEmpty()) {
            return new Card(stack.get(0), -1, -1, Orientation.NORTH);
        } else {
            return null;
        }
    }

    /**
     * Draw cards from stack without removing it
     * @return
     */
    public List<Card> drawCards() {
        return new ArrayList<>();
    }

    /**
     * Remove given card from stack
     */
    public void removeFromStack(Card c) {
        stack.remove((Integer)c.getId());
    }

}
