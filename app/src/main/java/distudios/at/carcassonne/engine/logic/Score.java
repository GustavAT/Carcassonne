package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.List;

public class Score {
    private boolean closed=true;
    private CardSide base;
    private List<Integer> ppeepcount;
    private List<Card> cardlist;
    private List<Peep> peeplist;

    public Score(CardSide base, int players) {
        this.base = base;
        this.ppeepcount = new ArrayList<>();
        for (int i = 0; i < players; i++) {
            ppeepcount.add(0);
        }
        this.cardlist = new ArrayList<>();
        this.peeplist = new ArrayList<>();
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public List<Integer> getPpeepcount() {
        return ppeepcount;
    }

    public void setPeepCount(int player, int score) {
        this.ppeepcount.set(player,score);
    }

    public void addPeepCount(int player, int score) {
        this.ppeepcount.set(player,score+ ppeepcount.get(player));
    }

    public List<Card> getCardlist() {
        return cardlist;
    }

    public void setCardlist(List<Card> cardlist) {
        this.cardlist = cardlist;
    }

    public void addCardToList(Card card) {
        cardlist.add(card);
    }

    public CardSide getBase() {
        return base;
    }

    public List<Peep> getPeeplist() {
        return peeplist;
    }

    public void addToPeeplist(Peep peep) {
        this.peeplist.add(peep);
    }
}
