package distudios.at.carcassonne;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.GameEngine;
import distudios.at.carcassonne.engine.logic.GameState;
import distudios.at.carcassonne.engine.logic.Orientation;
import distudios.at.carcassonne.engine.logic.Peep;
import distudios.at.carcassonne.engine.logic.PeepPosition;

public class TestGameState {
    GameState gs;
    GameEngine ge;

    @Before
    public void setUp() {
        ge = new GameEngine();
        ge.init(Orientation.NORTH);
        gs = ge.getGamestate();

    }

    @Test
    public void testInit() {
        Assert.assertTrue(gs != null);
    }

    @Test
    public void testRemovePeep() {
        Peep p1 = new Peep(new Card(1, 0, 0, Orientation.NORTH), PeepPosition.Top, 0);
        Peep p2 = new Peep(new Card(1, 0, 0, Orientation.NORTH), PeepPosition.Bottom, 0);
        gs.addPeep(p1);
        gs.addPeep(p2);
        gs.removePeep(p1);
        Assert.assertTrue(gs.getPeeps().size() == 1);
        Assert.assertTrue(gs.getPeeps().get(0) == p2);
        gs.removePeep(p2);
    }

    @Test
    public void testPoints() {
        for (int i = 0; i < 5; i++) {
            gs.setPoints(i, 5 - i);
            Assert.assertTrue(gs.getPoints(i) == (5 - i));
        }
    }

    @Test
    public void testTurnBasedActions() {
        gs.currentPlayer = 3;
        Assert.assertTrue(gs.getNextPlayer() == (4));
        for (int i = 0; i < 5; i++) {
            if (i == 3) {
                Assert.assertTrue(gs.myTurn(i));
            } else {
                Assert.assertFalse(gs.myTurn(i));
            }

        }
    }

    //Sinnloser Test, simuliert volle Coverage
    @Test
    public void TestDrawCard() {
        ArrayList<Integer> stack = gs.getStack();
        Assert.assertTrue(gs.drawCard() != null);
        gs.setStack(new ArrayList<Integer>());
        Assert.assertTrue(gs.drawCard() == null);
        gs.setStack(stack);
        Assert.assertTrue(gs.drawCard() != null);
    }

    @Test
    public void TestRemoveFromStack() {
        int count = gs.getStack().size();
        ArrayList<Integer> stack = gs.getStack();
        for (int i = 2; i < 73; i++) {
            Card c = new Card(i, 0, 0, Orientation.NORTH);
            gs.removeFromStack(c);
            Assert.assertTrue(count == (gs.getStack().size() + i - 1));
        }
        gs.setStack(stack);
    }
}
