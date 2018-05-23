package distudios.at.carcassonne.engine.logic;

/**
 * Created by Simon on 26.04.2018.
 */

public class Peep {

    //Card the peep is placed on
    private Card card;
    //Position of Peep on Card
    private PeepPosition peepPosition;
    private int playerID;

    public Peep(Card card, PeepPosition peepPosition, int playerID) {
        this.peepPosition = peepPosition;
        this.card = card;
        this.playerID = playerID;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card Card) {this.card = card;}

    public PeepPosition getPeepPosition(){return peepPosition;}

    public void setPeepPosition(PeepPosition peepPosition){this.peepPosition = peepPosition;}

    public int getPlayerID(){return playerID;}

    public void setPlayerID(int playerID){this.playerID = playerID;}
}
