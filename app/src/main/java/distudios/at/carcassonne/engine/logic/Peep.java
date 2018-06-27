package distudios.at.carcassonne.engine.logic;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import distudios.at.carcassonne.networking.connection.PeepTypeConverter;

/**
 * Created by Simon on 26.04.2018.
 */

@JsonObject
public class Peep {

    //Card the peep is placed on
    @JsonField
    private int cardId;
    //Position of Peep on Card
    @JsonField(typeConverter = PeepTypeConverter.class)
    private PeepPosition peepPosition;
    @JsonField
    private int playerID;

    public Peep() {
    }

    public Peep(Card card, PeepPosition peepPosition, int playerID) {
        this.peepPosition = peepPosition;
        this.cardId = card.getId();
        this.playerID = playerID;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public PeepPosition getPeepPosition(){return peepPosition;}

    public void setPeepPosition(PeepPosition peepPosition){this.peepPosition = peepPosition;}

    public int getPlayerID(){return playerID;}

    public void setPlayerID(int playerID){this.playerID = playerID;}
}
