package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;

public class Score {
    private boolean closed=true;
    private CardSide base;
    private ArrayList<Integer> ppeepcount;
    private ArrayList<Card> cardlist;

    public Score(CardSide base, int players) {
        this.base = base;
        this.ppeepcount = new ArrayList<>(players);
        for(int i = 0; i< ppeepcount.size(); i++){
            ppeepcount.set(i,0);
        }
        this.cardlist = new ArrayList<>();
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public ArrayList<Integer> getPpeepcount() {
        return ppeepcount;
    }

    public void setPeepCount(int player, int score) {
        this.ppeepcount.set(player,score);
    }

    public void addPeepCount(int player, int score) {
        this.ppeepcount.set(player,score+ ppeepcount.get(player));
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
