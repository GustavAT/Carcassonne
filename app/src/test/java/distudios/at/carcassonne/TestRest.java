package distudios.at.carcassonne;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.IGameController;
import distudios.at.carcassonne.engine.logic.Peep;
import distudios.at.carcassonne.engine.logic.PeepPosition;

import static distudios.at.carcassonne.engine.logic.Orientation.SOUTH;

public class TestRest {
    Peep peep;
    IGameController controller;

    @Before
    public void init() {
        peep = new Peep();
        controller = CarcassonneApp.getGameController();
    }

    @Test
    public void testSetter() {
        peep.setCardId(10);
        Assert.assertEquals(peep.getCardId(), 10);
    }


    @Test
    public void textNullPointerExp() {
        try {
            Card card = new Card(10, 0, -1, SOUTH);
            controller.drawCards().add(card);
        } catch (NullPointerException e) {
            e.getStackTrace().toString();
        }
    }

    @Test
    public void testFromPositionFromPeepPosition() {
        Assert.assertEquals(0, PeepPosition.fromPosition(PeepPosition.getPeepPositions().get(0)));
        Assert.assertEquals(2, PeepPosition.fromPosition(PeepPosition.getPeepPositions().get(2)));
        Assert.assertEquals(5, PeepPosition.fromPosition(PeepPosition.getPeepPositions().get(5)));
        Assert.assertEquals(6, PeepPosition.fromPosition(PeepPosition.getPeepPositions().get(6)));
        Assert.assertEquals(8, PeepPosition.fromPosition(PeepPosition.getPeepPositions().get(8)));
    }

    @Test
    public void testFromIntFromPeepPosition() {
        Assert.assertEquals(PeepPosition.TopLeft, PeepPosition.fromInt(0));
        Assert.assertEquals(PeepPosition.TopRight, PeepPosition.fromInt(2));
        Assert.assertEquals(PeepPosition.Center, PeepPosition.fromInt(5));
        Assert.assertEquals(PeepPosition.BottomLeft, PeepPosition.fromInt(6));
        Assert.assertEquals(PeepPosition.BottomRight, PeepPosition.fromInt(20));

    }
}