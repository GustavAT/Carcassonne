package distudios.at.carcassonne;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import distudios.at.carcassonne.engine.logic.CardDataBase;
import distudios.at.carcassonne.engine.logic.DalekRace;
import distudios.at.carcassonne.engine.logic.ExtendedCard;
import distudios.at.carcassonne.engine.logic.FederationRace;
import distudios.at.carcassonne.engine.logic.GameEngine;
import distudios.at.carcassonne.engine.logic.GameState;
import distudios.at.carcassonne.engine.logic.GoaUldRace;
import distudios.at.carcassonne.engine.logic.KlingonRace;
import distudios.at.carcassonne.engine.logic.Orientation;
import distudios.at.carcassonne.engine.logic.Player;
import distudios.at.carcassonne.engine.logic.ProtossRace;
import distudios.at.carcassonne.engine.logic.ScientologyRace;
import distudios.at.carcassonne.engine.logic.SuperSaiyajinRace;
import distudios.at.carcassonne.engine.logic.ZergRace;


public class CardDBAndRaceTests {
    GameEngine ge;
    GameState gs;
    CardDataBase cdb = CardDataBase.getInstance();

    @Before
    public void setUp() {
        ge = new GameEngine();
        ge.init(Orientation.NORTH);
        gs = ge.getState();

    }

    @Test
    public void checkCardDB() {
        //Array filled with the number of Cards in each type A = 2x etc.
        ArrayList<Integer> cardTypeArray = new ArrayList<Integer>();
        //A x2
        cardTypeArray.add(2);
        //B x4
        cardTypeArray.add(4);
        //C x1
        cardTypeArray.add(1);
        //D x4
        cardTypeArray.add(4);
        //E x5
        cardTypeArray.add(5);
        //F x2
        cardTypeArray.add(2);
        //G x1
        cardTypeArray.add(1);
        //H 3x
        cardTypeArray.add(3);
        //I 2x
        cardTypeArray.add(2);
        //J 3x
        cardTypeArray.add(3);
        //START CARD
        cardTypeArray.add(1);
        //K 3x
        cardTypeArray.add(2);
        //L 3x
        cardTypeArray.add(3);
        //M 2x
        cardTypeArray.add(2);
        //N 3x
        cardTypeArray.add(3);
        //O 2x
        cardTypeArray.add(2);
        //P 3x
        cardTypeArray.add(3);
        //Q 1x
        cardTypeArray.add(1);
        //R 3x
        cardTypeArray.add(3);
        //S 2x
        cardTypeArray.add(2);
        //T 1x
        cardTypeArray.add(1);
        //U 8x
        cardTypeArray.add(8);
        //V 9x
        cardTypeArray.add(9);
        //W 4x
        cardTypeArray.add(4);
        //X 1x
        cardTypeArray.add(1);
        // COUNTER FOR FOR LOOPS
        int theAMAZINGCounter = 0;
        int theSystemCounter = 0;

        List<ExtendedCard> cardDB = cdb.getCardDb();

        for (int j = 0; j < cardTypeArray.size() - 1; j++) {
            if (cardTypeArray.get(j) > 1) {
                System.out.println("Folgende " + cardTypeArray.get(j) + " Karten sind ident:");
            }
            for (int i = 0; i < cardTypeArray.get(j) - 1; i++) {
                Assert.assertTrue(cardDB.get(i + theAMAZINGCounter).getTop().equals(cardDB.get(i + theAMAZINGCounter + 1).getTop()));
                Assert.assertTrue(cardDB.get(i + theAMAZINGCounter).getLeft().equals(cardDB.get(i + theAMAZINGCounter + 1).getLeft()));
                Assert.assertTrue(cardDB.get(i + theAMAZINGCounter).getRight().equals(cardDB.get(i + theAMAZINGCounter + 1).getRight()));
                Assert.assertTrue(cardDB.get(i + theAMAZINGCounter).getDown().equals(cardDB.get(i + theAMAZINGCounter + 1).getDown()));
                Assert.assertTrue(cardDB.get(i + theAMAZINGCounter).getTopLeftCorner().equals(cardDB.get(i + theAMAZINGCounter + 1).getTopLeftCorner()));
                Assert.assertTrue(cardDB.get(i + theAMAZINGCounter).getTopRightCorner().equals(cardDB.get(i + theAMAZINGCounter + 1).getTopRightCorner()));
                Assert.assertTrue(cardDB.get(i + theAMAZINGCounter).getBottomLeftCorner().equals(cardDB.get(i + theAMAZINGCounter + 1).getBottomLeftCorner()));
                Assert.assertTrue(cardDB.get(i + theAMAZINGCounter).getBottomRightCorner().equals(cardDB.get(i + theAMAZINGCounter + 1).getBottomRightCorner()));
                System.out.println("Card with ID: " + cardDB.get(i + theAMAZINGCounter).getId() + " is equal to CARD with ID: " + cardDB.get(i + theAMAZINGCounter + 1).getId());
            }
            theAMAZINGCounter = theAMAZINGCounter + cardTypeArray.get(j);
            System.out.println();
        }


    }

    @Test
    public void testGetPlayerRace() {
        Assert.assertTrue(Player.getRaceFromPlayer(-3, 1) instanceof Player);
        Assert.assertTrue(Player.getRaceFromPlayer(1, 1) instanceof Player);
        Assert.assertTrue(Player.getRaceFromPlayer(2, 1) instanceof ZergRace);
        Assert.assertTrue(Player.getRaceFromPlayer(3, 1) instanceof ProtossRace);
        Assert.assertTrue(Player.getRaceFromPlayer(4, 1) instanceof GoaUldRace);
        Assert.assertTrue(Player.getRaceFromPlayer(5, 1) instanceof DalekRace);
        Assert.assertTrue(Player.getRaceFromPlayer(6, 1) instanceof KlingonRace);
        Assert.assertTrue(Player.getRaceFromPlayer(7, 1) instanceof FederationRace);
        Assert.assertTrue(Player.getRaceFromPlayer(8, 1) instanceof ScientologyRace);
        Assert.assertTrue(Player.getRaceFromPlayer(9, 1) instanceof SuperSaiyajinRace);
        Assert.assertTrue(Player.getRaceFromPlayer(10130123, 31231) instanceof Player);

    }


}

