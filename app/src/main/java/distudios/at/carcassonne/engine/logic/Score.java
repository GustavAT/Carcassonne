package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;

public class Score {
    private boolean closed=true;
    private CardSide base;
    private ArrayList<Integer> playerscore;
    private ArrayList<Card> cardlist;

    public Score(CardSide base, int players) {
        this.base = base;
        this.playerscore = new ArrayList<>(players);
        this.cardlist = new ArrayList<>();
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public ArrayList<Integer> getPlayerscore() {
        return playerscore;
    }

    public void setPlayerscore(int player, int score) {
        this.playerscore.set(player,score);
    }

    public void adcPlayerscore(int player, int score) {
        this.playerscore.set(player,score+playerscore.get(player));
    }

    public ArrayList<Card> getCardlist() {
        return cardlist;
    }

    public void setCardlist(ArrayList<Card> cardlist) {
        this.cardlist = cardlist;
    }

    public void addCardToList(Card card) {
        cardlist.add(card);
    }
}
