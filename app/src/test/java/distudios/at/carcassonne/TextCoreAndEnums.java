package distudios.at.carcassonne;

import org.junit.Assert;
import org.junit.Test;

import distudios.at.carcassonne.engine.logic.CState;

public class TextCoreAndEnums {


    @Test
    public void testCarcassonneState() {
        CState state = CState.DRAW_CARD;

        Assert.assertEquals(state.getValue(), CState.DRAW_CARD.getValue());
    }


    @Test
    public void testCarcassonneState2() {
        CState state = CState.valueOf(0);

        Assert.assertEquals(state.getValue(), 0);
        Assert.assertEquals(state.getValue(), CState.WAITING.getValue());
    }
}
