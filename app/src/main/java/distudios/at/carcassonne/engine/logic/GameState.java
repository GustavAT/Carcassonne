package distudios.at.carcassonne.engine.logic;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@JsonObject
public class GameState implements Serializable {

    @JsonField
    public ArrayList<Card> cards;
    @JsonField
    public ArrayList<Peep> peeps;
    @JsonField
    public ArrayList<Integer> stack;
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
        points = new ArrayList<>(5);
        maxPlayerCount = 0;
        currentPlayer = 0;
    }

    public void setStack(ArrayList<Integer> stack) {
        this.stack = stack;
    }

    public ArrayList<Integer> getStack() {
        return stack;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public ArrayList<Card> getCards() { return cards; }

    public void addPeep(Peep peep) { peeps.add(peep); }

    public ArrayList<Peep> getPeeps() {
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
        if (stack.size() > 0) {
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
        ArrayList<Card> listOfCards = new ArrayList<>();

            for(int i = 0;i<=Math.min(3,stack.size());i++){
                listOfCards.add(new Card(stack.get(i), -1, -1, Orientation.NORTH));
                //stack.remove(i);
            }
        return listOfCards;
    }

    /**
     * Remove given card from stack
     */
    public void removeFromStack(Card c) {
        stack.remove((Integer)c.getId());
    }

}
