package distudios.at.carcassonne;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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

    @Before
    public void setUp() {
        ge = new GameEngine();
        ge.init(Orientation.NORTH);
        gs = ge.getGamestate();
        Card c1 = new Card(4, -1, 0, Orientation.NORTH);
        Card c2 = new Card(13, -1, -1, Orientation.EAST);
        Card c3 = new Card(37, 0, -1, Orientation.WEST); //Grass/Grass/Burg/Burg
        Card c4 = new Card(46, 0, -1, Orientation.WEST); //Burg/Grass/Burg/Burg
        Card c5 = new Card(24, 0, -1, Orientation.SOUTH); //Burg/Grass/Burg/Burg


        ge.placeCard(c1);
        ge.placeCard(c2);
        ge.placeCard(c5);
        gs.addPeep(new Peep(new Card(1, 0, 0, Orientation.NORTH), PeepPosition.Top, 0));
        gs.addPeep(new Peep(c2, PeepPosition.Right, 1));
        //gs.addPeep(new Peep(c1, PeepPosition.Top,0));

    }

    @Test
    public void testCardScores() {
        ArrayList<Score> sc = ge.getScoreChanges(new Card(37, 0, -1, Orientation.WEST));
        for (int i = 0; i < 4; i++) {
            System.out.println("Felder: " + sc.get(i).getCardlist().size());
            for (int j = 0; j < 5; j++) {
                System.out.println("Peeps: " + sc.get(i).getPpeepcount().get(j) + " of Player: " + (j + 1));
            }
            System.out.println("Closed: " + sc.get(i).isClosed());
            System.out.println("Peepcount: " + sc.get(i).getPeeplist().size());
        }

    }

    @Test
    public void testPlace() {

    }
}
