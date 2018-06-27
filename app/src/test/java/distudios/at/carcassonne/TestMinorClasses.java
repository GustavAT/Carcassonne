package distudios.at.carcassonne;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.CardSide;
import distudios.at.carcassonne.engine.logic.Orientation;
import distudios.at.carcassonne.engine.logic.Score;

public class TestMinorClasses {
    Card c;
    Orientation o;
    Score sc;

    @Before
    public void setUp() {
        c = new Card(1, 0, 0, Orientation.NORTH);
        o = Orientation.NORTH;
        sc = new Score(CardSide.CASTLE, 5);
    }

    @Test
    public void testSetID() {
        c.setId(2);
        Assert.assertTrue(c.getId() == 2);
        c.setId(1);
    }

    @Test
    public void testSetOrientation() {
        c.setOrientation(Orientation.SOUTH);
        Assert.assertTrue(c.getOrientation() == Orientation.SOUTH);
        c.setOrientation(Orientation.NORTH);
    }

    @Test
    public void testSetX() {
        c.setxCoordinate(2);
        Assert.assertTrue(c.getxCoordinate() == 2);
        c.setxCoordinate(1);
    }

    @Test
    public void testSetY() {
        c.setyCoordinate(2);
        Assert.assertTrue(c.getyCoordinate() == 2);
        c.setyCoordinate(1);
    }

    @Test
    public void testRotate() {
        for (int i = 0; i < 4; i++) {
            Assert.assertTrue(c.getOrientation() == Orientation.valueOf(i));
            c.rotate();
        }
        Assert.assertTrue(c.getOrientation() == Orientation.valueOf(0));
    }

    @Test
    public void testOrientationInit() {
        for (int i = 0; i < 4; i++) {
            Assert.assertTrue(o.getValue() == i);
            o = Card.getAbsoluteOrientation(o, Orientation.WEST);
        }
        Assert.assertTrue(o.getValue() == 0);
    }

    @Test
    public void testSwitchOrientation() {
        int act = 0;
        Assert.assertTrue(o.getValue() == act);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                o = Card.getAbsoluteOrientation(o, Orientation.valueOf(i));
                act = (act - i + 4) % 4;
                Assert.assertTrue(o.getValue() == act);
            }
        }
    }

    @Test
    public void testScore() {
        for (int i = 0; i < 5; i++) {
            sc.setPeepCount(i, 5 - i);
            Assert.assertTrue(sc.getPpeepcount().get(i) == (5 - i));
        }
        sc.addCardToList(c);
        Assert.assertTrue(sc.getCardlist().size() == 1);
    }
}
