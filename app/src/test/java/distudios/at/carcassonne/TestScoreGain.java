package distudios.at.carcassonne;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.CardDataBase;
import distudios.at.carcassonne.engine.logic.GameEngine;
import distudios.at.carcassonne.engine.logic.GameState;
import distudios.at.carcassonne.engine.logic.Orientation;
import distudios.at.carcassonne.engine.logic.Peep;
import distudios.at.carcassonne.engine.logic.PeepPosition;
import distudios.at.carcassonne.engine.logic.Score;

public class TestScoreGain {
    GameEngine ge;
    GameState gs;
    CardDataBase cdb = CardDataBase.getInstance();
    Card c1 = new Card(4, -1, 0, Orientation.NORTH);
    Card c2 = new Card(13, -1, -1, Orientation.EAST);
    Card c3 = new Card(37, 0, -1, Orientation.WEST); //Grass/Grass/Burg/Burg
    Card c4 = new Card(46, 0, -1, Orientation.WEST); //Burg/Grass/Burg/Burg
    Card c5 = new Card(24, 0, -1, Orientation.SOUTH); //Burg/Grass/Burg/Grass mit Split

    @Before
    public void setUp() {


    }

    @Test
    public void testCardScores1() {
        ge = new GameEngine();
        ge.init(Orientation.NORTH);
        gs = ge.getGamestate();

        ge.placeCard(c1);
        ge.placeCard(c2);
        ge.placeCard(c3);
        gs.addPeep(new Peep(new Card(1, 0, 0, Orientation.NORTH), PeepPosition.Top, 0));
        gs.addPeep(new Peep(c2, PeepPosition.Right, 1));

        ArrayList<Score> sc = ge.getScoreChanges(c3);
        //printScores(sc);
        boolean[] b = {false, false, true, false};
        Assert.assertTrue(checkClosed(sc, b));
    }

    @Test
    public void testCardScores2() {
        ge = new GameEngine();
        ge.init(Orientation.NORTH);
        gs = ge.getGamestate();

        ge.placeCard(c1);
        ge.placeCard(c2);
        ge.placeCard(c4);
        gs.addPeep(new Peep(new Card(1, 0, 0, Orientation.NORTH), PeepPosition.Top, 0));
        gs.addPeep(new Peep(c2, PeepPosition.Right, 1));

        ArrayList<Score> sc = ge.getScoreChanges(c4);
        //printScores(sc);
        boolean[] b = {false, false, false, false};
        Assert.assertTrue(checkClosed(sc, b));
    }

    @Test
    public void testCardScores3() {
        ge = new GameEngine();
        ge.init(Orientation.NORTH);
        gs = ge.getGamestate();

        ge.placeCard(c1);
        ge.placeCard(c2);
        ge.placeCard(c4);
        gs.addPeep(new Peep(new Card(1, 0, 0, Orientation.NORTH), PeepPosition.Top, 0));
        gs.addPeep(new Peep(c2, PeepPosition.Right, 1));

        ArrayList<Score> sc = ge.getScoreChanges(c5);
        //printScores(sc);
        boolean[] b = {false, false, true, true};
        Assert.assertTrue(checkClosed(sc, b));
    }

    private void printScores(List<Score> sc) {
        for (int i = 0; i < 4; i++) {
            System.out.println("Felder: " + sc.get(i).getCardlist().size());
            for (int j = 0; j < 5; j++) {
                System.out.println("Peeps: " + sc.get(i).getPpeepcount().get(j) + " of Player: " + (j + 1));
            }
            System.out.println("Closed: " + sc.get(i).isClosed());
            System.out.println("Peepcount: " + sc.get(i).getPeeplist().size());
        }
    }

    private boolean checkClosed(List<Score> score, boolean[] info) {
        for (int i = 0; i < score.size(); i++) {
            if (score.get(i).isClosed() != info[i]) {
                return false;
            }
        }
        return true;
    }
}
