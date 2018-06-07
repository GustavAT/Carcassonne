package distudios.at.carcassonne;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.CardDataBase;
import distudios.at.carcassonne.engine.logic.CardSide;
import distudios.at.carcassonne.engine.logic.GameEngine;
import distudios.at.carcassonne.engine.logic.GameState;
import distudios.at.carcassonne.engine.logic.Orientation;
import distudios.at.carcassonne.engine.logic.Peep;
import distudios.at.carcassonne.engine.logic.PeepPosition;

import static distudios.at.carcassonne.engine.logic.CardSide.CASTLE;
import static distudios.at.carcassonne.engine.logic.CardSide.GRASS;
import static distudios.at.carcassonne.engine.logic.CardSide.STREET;
import static distudios.at.carcassonne.engine.logic.Orientation.EAST;
import static distudios.at.carcassonne.engine.logic.Orientation.NORTH;
import static distudios.at.carcassonne.engine.logic.Orientation.SOUTH;
import static distudios.at.carcassonne.engine.logic.Orientation.WEST;

import static distudios.at.carcassonne.engine.logic.PeepPosition.Bottom;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Center;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Left;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Right;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Top;


public class TestGameEngine {
    GameEngine ge;
    GameState gs;
    CardDataBase cdb = CardDataBase.getInstance();

    @Before
    public void setUp() {
        ge = new GameEngine();
        ge.init(Orientation.NORTH);
        gs = ge.getGamestate();

        cdb.cardDB.get(19).setDown(CASTLE);
        cdb.cardDB.get(19).setTop(CardSide.GRASS);
        cdb.cardDB.get(19).setLeft(CardSide.GRASS);
        cdb.cardDB.get(19).setRight(CASTLE);   //Anpassung Neue Karte Check

        cdb.cardDB.get(20).setTop(CardSide.GRASS);
        cdb.cardDB.get(20).setDown(CardSide.GRASS);
        cdb.cardDB.get(20).setLeft(CardSide.GRASS);
        cdb.cardDB.get(20).setRight(CardSide.STREET);

        cdb.cardDB.get(21).setDown(CardSide.GRASS);     //Anpassung Neue Karte Check
        cdb.cardDB.get(21).setLeft(CASTLE);
        cdb.cardDB.get(21).setTop(CardSide.GRASS);
        cdb.cardDB.get(21).setRight(CASTLE);

        cdb.cardDB.get(22).setRight(CardSide.GRASS);
        cdb.cardDB.get(22).setDown(CardSide.STREET);
        cdb.cardDB.get(22).setTop(CardSide.GRASS);
        cdb.cardDB.get(22).setLeft(CardSide.STREET);

        cdb.cardDB.get(23).setTop(CardSide.STREET);
        cdb.cardDB.get(23).setRight(CardSide.STREET);
        cdb.cardDB.get(23).setLeft(CardSide.GRASS);
        cdb.cardDB.get(23).setDown(CardSide.GRASS);

        cdb.cardDB.get(25).setRight(CASTLE);
        cdb.cardDB.get(25).setLeft(CardSide.STREET);
        cdb.cardDB.get(25).setTop(CardSide.GRASS);

        cdb.cardDB.get(11).setRight(CardSide.STREET);
        cdb.cardDB.get(11).setTop(CardSide.STREET);
        cdb.cardDB.get(11).setDown(CardSide.GRASS);
        cdb.cardDB.get(11).setLeft(CardSide.GRASS);

        cdb.cardDB.get(26).setRight(CardSide.GRASS);
        cdb.cardDB.get(26).setTop(CardSide.STREET);
        cdb.cardDB.get(26).setDown(CardSide.STREET);
        cdb.cardDB.get(26).setLeft(CASTLE);

        cdb.cardDB.get(2).setRight(CardSide.CASTLE);
        cdb.cardDB.get(2).setTop(CASTLE);
        cdb.cardDB.get(2).setDown(CASTLE);
        cdb.cardDB.get(2).setLeft(GRASS);
        cdb.cardDB.get(2).setCathedral(false);

        cdb.cardDB.get(3).setRight(CASTLE);
        cdb.cardDB.get(3).setTop(CardSide.GRASS);
        cdb.cardDB.get(3).setDown(CASTLE);
        cdb.cardDB.get(3).setLeft(CASTLE);
    }

    @Test
    public void shuffleWorks() {
        ArrayList<Integer> al = gs.getStack();
        for (int i = 0; i < al.size(); i++) {
            System.out.println("Listelement " + i + " : " + al.get(i));
        }
    }

    @Test
    public void checkPlaceableMethod() {
        System.out.println("\nPlatziere 3 Karten und überprüfe auf Platzierbarkeit");
        Card card = new Card(20, 0, 1, Orientation.NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));     //Test Placeable
        ge.placeCard(card);
        Assert.assertFalse(ge.checkPlaceable(card));    //Test reflexive Placeable false
        card = new Card(21, 1, 0, Orientation.NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));     //Test placeable
        ge.placeCard(card);
        card = new Card(22, 1, 1, Orientation.NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));     //Test placeable with 2 Connection
        ge.placeCard(card);
        printCards(gs);
    }



    @Test
    public void testCheckBorder() {
        //Settings...
        Card card;
        ge.placeCard(card = new Card(20, 0, 1, NORTH));
        ge.placeCard(card = new Card(21, 1, 0, NORTH));
        //ge.placeCard(card = new Card(22, 1, 1, NORTH));
        Card testCard = new Card(22, 1, 1, NORTH);
        ArrayList<Card> field = gs.getCards();

        Boolean checkBorder = ge.checkBorder(testCard, field, NORTH);
        Assert.assertTrue(checkBorder == true);

    }

    @Test
    public void switchOrientation() {
        Orientation result = Card.getAbsoluteOrientation(Orientation.EAST, Orientation.SOUTH);
        Orientation compare = Card.getAbsoluteOrientation(Orientation.WEST, Orientation.NORTH);
        Assert.assertTrue(result == compare);
    }

    private void printCards(GameState pgs) {
        ArrayList<Card> cardList = pgs.getCards();

        for (int i = 0; i < cardList.size(); i++) {
            System.out.println("Card " + i + " : " + cardList.get(i).toString());
        }
    }

    //@Test
    public void checkGetMarkedBorders() {
        //Settings...
        Card cardI = new Card(40, 3, -1, WEST);
        Card cardII = new Card(35, 4, -1, NORTH);
        Card cardIII = new Card(24, 3, 0, WEST);
        Card cardIV = new Card(35, 4, 0, EAST);

        ge.placeCard(cardI);
        ge.placeCard(cardII);
        ge.placeCard(cardIV);
        //cardI.setMark(Right);
        cardI.setMark(Top);
        //ge.markCard(cardI,Top,CASTLE);
        ge.placeCard(cardIII);

        ArrayList<PeepPosition> markedBorders = ge.getMarkedBorders(cardIII, CASTLE);
        Assert.assertTrue(markedBorders.size()==1);
        Assert.assertTrue(markedBorders.contains(Bottom));
        //Assert.assertTrue(markedBorders.contains(Top));
    }



    @Test
    public void checkGetUnmarkedBuildingBorders() {
        //Settings...
        Card cardI = new Card(4, 1, 0, NORTH);
        Card cardIII = new Card(52, -1, 0, EAST);
        Card cardIV = new Card(30, -2, 0, EAST);
        Card cardV = new Card(21, 2, 0, WEST);
        Card cardVI = new Card(13, 3, 0, EAST);
        Card cardVII = new Card(18, -3, 0, NORTH);
        Card cardVIII = new Card(13, -4, 0, WEST);

        Card cardIX = new Card(18, 0, 1, EAST);
        Card cardX = new Card(35, 2, 1, SOUTH);
        Card cardXI = new Card(24, 3, 1, SOUTH);

        Card cardXII = new Card(24, 0, 2, SOUTH);

        Card cardXIII = new Card(60, 0, -1, SOUTH);
        Card cardXIV = new Card(52, 1, -1, EAST);
        Card cardXV = new Card(32, 2, -1, NORTH);
        Card cardXVI = new Card(2, 3, -1, WEST);

        Card cardXVII = new Card(24, 0, -2, WEST);
        Card cardXVIII = new Card(35, 1, -2, EAST);
        Card cardXXIV = new Card(52, 2, -2, NORTH);
        Card cardXIX = new Card(40, 0, -3, WEST);
        Card cardXX = new Card(35, 1, -3, NORTH);

        Card cardXXI = new Card(52, 0, -4, NORTH);

        Card cardXXII = new Card(28, 0, -5, NORTH);
        Card cardXXIII = new Card(69, 1, -5, SOUTH);

        ge.placeCard(cardI);
        ge.placeCard(cardIII);
        ge.placeCard(cardIV);
        ge.placeCard(cardV);
        ge.placeCard(cardVI);
        ge.placeCard(cardVII);
        ge.placeCard(cardVIII);
        ge.placeCard(cardIX);
        ge.placeCard(cardX);
        ge.placeCard(cardXI);
        ge.placeCard(cardXII);
        ge.placeCard(cardXIII);
        ge.placeCard(cardXIV);
        ge.placeCard(cardXV);
        ge.placeCard(cardXVI);
        ge.placeCard(cardXVII);
        ge.placeCard(cardXVIII);
        ge.placeCard(cardXIX);
        ge.placeCard(cardXX);
        ge.placeCard(cardXXI);
        ge.placeCard(cardXXII);
        ge.placeCard(cardXXIII);
        ge.placeCard(cardXXIV);

        //cardXVII.setMark(Top );
        //cardXVI.setMark(Left);
        int cardID = cardXV.getId();
        ArrayList<Orientation> buildingOs = cdb.getMatchingOrientations(cardID,STREET);

        ArrayList<PeepPosition> unmarkedBuildingBorders = new ArrayList<PeepPosition>();
        unmarkedBuildingBorders = ge.getUnmarkedBuildingBorders(cardXV, buildingOs, STREET);
        Assert.assertTrue(unmarkedBuildingBorders.size() == 3);
        Assert.assertTrue(unmarkedBuildingBorders.contains(Bottom));
        Assert.assertTrue(unmarkedBuildingBorders.contains(Right));
        Assert.assertTrue(unmarkedBuildingBorders.contains(Left));
        //Assert.assertTrue(unmarkedBuildingBorders.contains(Top));
    }

    @Test
    public void checkGetUnmarkedBorders() {
        //Settings...
        Card cardI = new Card(4, 1, 0, NORTH);
        Card cardIII = new Card(52, -1, 0, EAST);
        Card cardIV = new Card(30, -2, 0, EAST);
        Card cardV = new Card(22, 2, 0, WEST);
        Card cardVI = new Card(13, 3, 0, EAST);
        Card cardVII = new Card(8, -3, 0, NORTH);
        Card cardVIII = new Card(13, -4, 0, WEST);

        Card cardIX = new Card(18, 0, 1, EAST);
        Card cardX = new Card(35, 2, 1, SOUTH);
        Card cardXI = new Card(24, 3, 1, SOUTH);

        Card cardXII = new Card(24, 0, 2, SOUTH);

        Card cardXIII = new Card(60, 0, -1, SOUTH);
        Card cardXIV = new Card(52, 1, -1, EAST);
        Card cardXV = new Card(32, 2, -1, NORTH);
        Card cardXVI = new Card(2, 3, -1, WEST);

        Card cardXVII = new Card(24, 0, -2, WEST);
        Card cardXVIII = new Card(35, 1, -2, EAST);
        Card cardXXIV = new Card(52, 2, -2, NORTH);
        Card cardXIX = new Card(40, 0, -3, WEST);
        Card cardXX = new Card(35, 1, -3, NORTH);

        Card cardXXI = new Card(53, 0, -4, NORTH);

        Card cardXXII = new Card(29, 0, -5, NORTH);
        Card cardXXIII = new Card(69, 1, -5, SOUTH);

        ge.placeCard(cardI);
        ge.placeCard(cardIII);
        ge.placeCard(cardIV);
        ge.placeCard(cardV);
        ge.placeCard(cardVI);
        ge.placeCard(cardVII);
        ge.placeCard(cardVIII);
        ge.placeCard(cardIX);
        ge.placeCard(cardX);
        ge.placeCard(cardXI);
        ge.placeCard(cardXII);
        ge.placeCard(cardXIII);
        ge.placeCard(cardXIV);
        ge.placeCard(cardXV);
        ge.placeCard(cardXVI);
        ge.placeCard(cardXVII);
        ge.placeCard(cardXVIII);
        ge.placeCard(cardXIX);
        ge.placeCard(cardXX);
        ge.placeCard(cardXXI);
        //ge.placeCard(cardXXII);
        ge.placeCard(cardXXIII);
        ge.placeCard(cardXXIV);

        ge.markCard(cardXXI,Top,STREET);
        ge.markAllCards();
        ge.placeCard(cardXXII);
        ge.placePeep(cardVII,Center,3);
        //ge.markAllCards();
        //ge.placeCard(cardXV);
        //cardXVI.setMark(Left);
        //cardXIV.setMark(Right);

        ArrayList<PeepPosition> unmarkedBorders = new ArrayList<PeepPosition>();
        ArrayList<Peep> peeps = gs.getPeeps();
        unmarkedBorders = ge.getUnmarkedBorders(cardXXII, STREET);
        Assert.assertTrue(unmarkedBorders.size()==3);
        Assert.assertTrue(unmarkedBorders.contains(Bottom));
        Assert.assertTrue(unmarkedBorders.contains(Right));
        Assert.assertTrue(unmarkedBorders.contains(Left));
        //Assert.assertTrue(unmarkedBorders.contains(Top));
        //Assert.assertTrue(unmarkedBorders.contains(Center));
        //Assert.assertTrue(peeps.size()==1);
    }

    @Test
    public void checkGetALLFigurePos(){
        //Settings...
        Card cardXIII = new Card(60, 0, -1, SOUTH);
        Card cardXIV = new Card(52, 1, -1, EAST);
        Card cardXV = new Card(32, 2, -1, NORTH);
        Card cardIV = new Card(40, 3, -1, WEST);
        Card cardV = new Card(35, 4, -1, NORTH);
        Card cardVI = new Card(24, 3, 0, WEST);
        Card cardVII = new Card(35, 4, 0, EAST);


        ge.placeCard(cardXIII);
        //ge.placeCard(cardXIV);
        //ge.placeCard(cardXV);
        ge.placeCard(cardIV);
        ge.placeCard(cardV);
        ge.placeCard(cardVI);
        ge.placeCard(cardVII);


        ge.markCard(cardXIII,Right,STREET);
        ge.markAllCards();
        ge.placeCard(cardXIV);
        ge.markAllCards();
        ge.placeCard(cardXV);

        ArrayList<PeepPosition> unmarkedBorders = new ArrayList<PeepPosition>();
        unmarkedBorders = ge.getUnmarkedBorders(cardXV, STREET);

        ArrayList<PeepPosition> allUnmarkedBorders = new ArrayList<PeepPosition>();
        allUnmarkedBorders = ge.getALLFigurePos(cardXV);
        Assert.assertTrue(allUnmarkedBorders.size()==3);
        Assert.assertTrue(allUnmarkedBorders.contains(Bottom));
        Assert.assertTrue(allUnmarkedBorders.contains(Right));
        //Assert.assertTrue(allUnmarkedBorders.contains(Left));
        Assert.assertTrue(allUnmarkedBorders.contains(Top));
        //Assert.assertTrue(allUnmarkedBorders.contains(Center));
    }

    @Test
    public void checkMarkCards(){
        //Settings...
        Card cardI = new Card(4, 1, 0, NORTH);
        Card cardIII = new Card(52, -1, 0, EAST);
        Card cardIV = new Card(30, -2, 0, EAST);
        Card cardV = new Card(22, 2, 0, WEST);
        Card cardVI = new Card(13, 3, 0, EAST);
        Card cardVII = new Card(18, -3, 0, NORTH);
        Card cardVIII = new Card(13, -4, 0, WEST);

        Card cardIX = new Card(18, 0, 1, EAST);
        Card cardX = new Card(35, 2, 1, SOUTH);
        Card cardXI = new Card(24, 3, 1, SOUTH);

        Card cardXII = new Card(24, 0, 2, SOUTH);

        Card cardXIII = new Card(60, 0, -1, SOUTH);
        Card cardXIV = new Card(52, 1, -1, EAST);
        Card cardXV = new Card(32, 2, -1, NORTH);
        Card cardXVI = new Card(2, 3, -1, WEST);

        Card cardXVII = new Card(24, 0, -2, WEST);
        Card cardXVIII = new Card(35, 1, -2, EAST);
        Card cardXXIV = new Card(52, 2, -2, NORTH);
        Card cardXIX = new Card(40, 0, -3, WEST);
        Card cardXX = new Card(35, 1, -3, NORTH);

        Card cardXXI = new Card(53, 0, -4, NORTH);

        Card cardXXII = new Card(29, 0, -5, NORTH);
        Card cardXXIII = new Card(69, 1, -5, SOUTH);

        ge.placeCard(cardI);
        ge.placeCard(cardIII);
        ge.placeCard(cardIV);
        ge.placeCard(cardV);
        ge.placeCard(cardVI);
        ge.placeCard(cardVII);
        ge.placeCard(cardVIII);
        ge.placeCard(cardIX);
        ge.placeCard(cardX);
        ge.placeCard(cardXI);
        ge.placeCard(cardXII);
        ge.placeCard(cardXIII);
        ge.placeCard(cardXIV);
        ge.placeCard(cardXV);
        ge.placeCard(cardXVI);
        ge.placeCard(cardXVII);
        ge.placeCard(cardXVIII);
        ge.placeCard(cardXIX);
        ge.placeCard(cardXX);
        ge.placeCard(cardXXI);
        ge.placeCard(cardXXII);
        ge.placeCard(cardXXIII);
        ge.placeCard(cardXXIV);

        //cardXXI.setMark(Top);
        cardIX.setMark(Top);
        ge.markCard(cardXII,Left,CASTLE);
        ArrayList<PeepPosition> markedPos = cardXII.getMarks();

        Assert.assertTrue(markedPos.size()==1);
        Assert.assertTrue(markedPos.contains(Bottom));
        //Assert.assertTrue(markedPos.contains(Right));
        //Assert.assertTrue(markedPos.contains(Left));
        //Assert.assertTrue(markedPos.contains(Top));
        //Assert.assertTrue(markedPos.contains(Center));
    }

    @Test
    public void checkPlacePeep(){
        //Settings...
        Card cardI = new Card(4, 1, 0, NORTH);
        Card cardIII = new Card(52, -1, 0, EAST);
        Card cardIV = new Card(30, -2, 0, EAST);
        Card cardV = new Card(22, 2, 0, WEST);
        Card cardVI = new Card(13, 3, 0, EAST);
        Card cardVII = new Card(18, -3, 0, NORTH);
        Card cardVIII = new Card(13, -4, 0, WEST);

        Card cardIX = new Card(18, 0, 1, EAST);
        Card cardX = new Card(35, 2, 1, SOUTH);
        Card cardXI = new Card(24, 3, 1, SOUTH);

        Card cardXII = new Card(24, 0, 2, SOUTH);

        Card cardXIII = new Card(60, 0, -1, SOUTH);
        Card cardXIV = new Card(52, 1, -1, EAST);
        Card cardXV = new Card(32, 2, -1, NORTH);
        Card cardXVI = new Card(2, 3, -1, WEST);

        Card cardXVII = new Card(24, 0, -2, WEST);
        Card cardXVIII = new Card(35, 1, -2, EAST);
        Card cardXXIV = new Card(52, 2, -2, NORTH);
        Card cardXIX = new Card(40, 0, -3, WEST);
        Card cardXX = new Card(35, 1, -3, NORTH);

        Card cardXXI = new Card(53, 0, -4, NORTH);

        Card cardXXII = new Card(29, 0, -5, NORTH);
        Card cardXXIII = new Card(69, 1, -5, SOUTH);

        ge.placeCard(cardI);
        ge.placeCard(cardIII);
        ge.placeCard(cardIV);
        ge.placeCard(cardV);
        ge.placeCard(cardVI);
        ge.placeCard(cardVII);
        ge.placeCard(cardVIII);
        ge.placeCard(cardIX);
        ge.placeCard(cardX);
        ge.placeCard(cardXI);
        ge.placeCard(cardXII);
        ge.placeCard(cardXIII);
        ge.placeCard(cardXIV);
        ge.placeCard(cardXV);
        ge.placeCard(cardXVI);
        //ge.placeCard(cardXVII);
        ge.placeCard(cardXVIII);
        ge.placeCard(cardXIX);
        ge.placeCard(cardXX);
        ge.placeCard(cardXXI);
        ge.placeCard(cardXXII);
        ge.placeCard(cardXXIII);
        ge.placeCard(cardXXIV);

        //ge.markCard(cardXX,Left,CASTLE);
        //ge.markAllCards();
        ge.placeCard(cardXVII);

        ge.placePeep(cardXVII,Bottom,4);
        ArrayList<PeepPosition> marks = cardXVII.getMarks();
        ArrayList<Peep> peeps = gs.getPeeps();

        Assert.assertTrue(marks.size()==2);
        Assert.assertTrue(marks.contains(Bottom));
        Assert.assertTrue(marks.contains(Right));
        //Assert.assertTrue(marks.contains(Left));
        //Assert.assertTrue(marks.contains(Top));
        //Assert.assertTrue(marks.contains(Center));

        Assert.assertTrue(peeps.size() == 1);

        for (Peep peep:peeps) {
            System.out.println("Card: "+peep.getCard());
            System.out.println("Pos: "+peep.getPeepPosition());
            System.out.println("Player: "+peep.getPlayerID());
            System.out.println();
        }
    }

    @Test
    public void checkMarkAllCards(){
        //Settings...
        Card cardI = new Card(60, 0, -1, SOUTH);
        Card cardII = new Card(52, 1, -1, EAST);
        Card cardIII = new Card(32, 2, -1, NORTH);
        Card cardIV = new Card(40, 3, -1, WEST);
        Card cardV = new Card(35, 4, -1, NORTH);
        Card cardVI = new Card(24, 3, 0, WEST);
        Card cardVII = new Card(35, 4, 0, EAST);

        ge.placeCard(cardI);
        ge.placeCard(cardII);
        ge.placeCard(cardIII);
        ge.placeCard(cardIV);
        ge.placeCard(cardV);
        ge.placeCard(cardVI);
        ge.placeCard(cardVII);

        //cardI.setMark(Right);
        ge.markCard(cardI,Right,STREET);

        ge.markAllCards();
        ArrayList<PeepPosition> marksI = cardI.getMarks();
        ArrayList<PeepPosition> marksII = cardII.getMarks();
        ArrayList<PeepPosition> marksIII = cardIII.getMarks();

        Assert.assertTrue(marksI.size() == 2);
        //Assert.assertTrue(marksI.contains(Bottom));
        Assert.assertTrue(marksI.contains(Right));
        //Assert.assertTrue(marksI.contains(Left));
        Assert.assertTrue(marksI.contains(Top));
        //Assert.assertTrue(marksI.contains(Center));

        Assert.assertTrue(marksII.size() == 2);
        //Assert.assertTrue(marksII.contains(Bottom));
        Assert.assertTrue(marksII.contains(Right));
        Assert.assertTrue(marksII.contains(Left));
        //Assert.assertTrue(marksII.contains(Top));
        //Assert.assertTrue(marksII.contains(Center));

        Assert.assertTrue(marksIII.size() == 1);
        //Assert.assertTrue(marksIII.contains(Bottom));
        //Assert.assertTrue(marksIII.contains(Right));
        Assert.assertTrue(marksIII.contains(Left));
        //Assert.assertTrue(marksIII.contains(Top));
        //Assert.assertTrue(marksIII.contains(Center));

    }

}
