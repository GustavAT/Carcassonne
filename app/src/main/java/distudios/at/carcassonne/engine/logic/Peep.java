package distudios.at.carcassonne.engine.logic;

/**
 * Created by Simon on 26.04.2018.
 */

public class Peep {

    //Card the peep is placed on
    private Card card;
    //Position of Peep on Card
    private PeepPosition peepPosition;
    private Color color; //Could also be a Player who has chosen a color and has a number of Peeps left to place

    public Peep(Card card, PeepPosition peepPosition, Color color) {
        this.peepPosition = peepPosition;
        this.card = card;
        this.color = color;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card Card) {this.card = card;}

    public PeepPosition getPeepPosition(){return peepPosition;}

    public void setPeepPosition(PeepPosition peepPosition){this.peepPosition = peepPosition;}

    public Color getColor(){return color;}

    public void setColor(Color color){this.color = color;}
}
