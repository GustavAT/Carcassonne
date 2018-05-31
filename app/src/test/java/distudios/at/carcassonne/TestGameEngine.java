package distudios.at.carcassonne;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

       /* cdb.cardDB.get(19).setDown(CASTLE);
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

        cdb.cardDB.get(24).setRight(CardSide.STREET);
        cdb.cardDB.get(24).setLeft(CardSide.STREET);
        cdb.cardDB.get(24).setTop(CardSide.GRASS);
        cdb.cardDB.get(24).setDown(CardSide.STREET);

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
        cdb.cardDB.get(3).setLeft(CASTLE);*/
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
        card = new Card(23, -1, 0, NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));
        ge.placeCard(card);
        card = new Card(24, -1, -1, NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));
        ge.placeCard(card);
        card = new Card(25, 0, -1, NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));
        ge.placeCard(card);
        card = new Card(26, 1, -1, NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));
        ge.placeCard(card);
        printCards(gs);
    }


    @Test
    public void checkGetFollowedCard() {
        Card card;
        ge.placeCard(card = new Card(23, -1, 0, NORTH));
        ge.placeCard(card = new Card(24, -1, -1, NORTH));
        ArrayList<Card> field = gs.getCards();
        Card testCard = new Card(23, -1, 0, NORTH);

        Card result = ge.getFollowedCard(testCard, field, Orientation.WEST);
        Assert.assertTrue(result == null);

        Card exp = new Card(24, -1, -1, NORTH);
        result = ge.getFollowedCard(testCard, field, Orientation.SOUTH);
        Assert.assertTrue(result == exp);
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

    @Test
    public void checkGetMarkedBorders() {
        //Settings...
        Card cardI = new Card(4, 1, 0, EAST);
        Card cardII = new Card(3, 2, 0, WEST);
        Card cardIII = new Card(27, 3, 0, NORTH);
        ge.placeCard(cardI);
        ge.placeCard(cardII);
        ge.placeCard(cardIII);
        //cardI.setMark(Right);
        cardIII.setMark(Left);

        ArrayList<PeepPosition> markedBorders = ge.getMarkedBorders(cardII, CASTLE);
        Assert.assertTrue(markedBorders.size()==1);
        Assert.assertTrue(markedBorders.contains(Right));
        //Assert.assertTrue(markedBorders.contains(Left));
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
        ArrayList<Orientation> buildingOs = cdb.getMatchingOrientations(cardID, CASTLE);

        ArrayList<PeepPosition> unmarkedBuildingBorders = new ArrayList<PeepPosition>();
        unmarkedBuildingBorders = ge.getUnmarkedBuildingBorders(cardXV, buildingOs, CASTLE);
        Assert.assertTrue(unmarkedBuildingBorders.size() == 2);
        Assert.assertTrue(unmarkedBuildingBorders.contains(Bottom));
        Assert.assertTrue(unmarkedBuildingBorders.contains(Right));
        //Assert.assertTrue(unmarkedBuildingBorders.contains(Left));
        //Assert.assertTrue(unmarkedBuildingBorders.contains(Top));
    }

    @Test
    public void checkGetUnmarkedBorders() {
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

        cardXVI.setMark(Left);
        cardXIV.setMark(Right);

        ArrayList<PeepPosition> unmarkedBorders = new ArrayList<PeepPosition>();
        unmarkedBorders = ge.getUnmarkedBorders(cardXV, STREET);
        Assert.assertTrue(unmarkedBorders.size()==1);
        Assert.assertTrue(unmarkedBorders.contains(Bottom));
        //Assert.assertTrue(unmarkedBorders.contains(Right));
        //Assert.assertTrue(unmarkedBorders.contains(Left));
        //Assert.assertTrue(unmarkedBorders.contains(Top));
        //Assert.assertTrue(unmarkedBorders.contains(Center));
    }

    @Test
    public void checkGetALLFigurePos(){
        //Settings...
        Card cardXIV = new Card(52, 1, -1, EAST);
        Card cardXV = new Card(32, 2, -1, NORTH);
        Card cardXVI = new Card(2, 3, -1, WEST);

        ge.placeCard(cardXIV);
        ge.placeCard(cardXV);
        ge.placeCard(cardXVI);

        //cardXVI.setMark(Left);
        //cardXIV.setMark(Right);

        ArrayList<PeepPosition> allUnmarkedBorders = new ArrayList<PeepPosition>();
        allUnmarkedBorders = ge.getALLFigurePos(cardXV);
        Assert.assertTrue(allUnmarkedBorders.size()==4);
        Assert.assertTrue(allUnmarkedBorders.contains(Bottom));
        Assert.assertTrue(allUnmarkedBorders.contains(Right));
        Assert.assertTrue(allUnmarkedBorders.contains(Left));
        Assert.assertTrue(allUnmarkedBorders.contains(Top));
        //Assert.assertTrue(allUnmarkedBorders.contains(Center));
    }

    @Test
    public void checkMarkCards(){
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
        //cardIX.setMark(Top);
        ge.markCard(cardXII,Left,CASTLE);
        ArrayList<PeepPosition> markedPos = cardXII.getMarks();

        Assert.assertTrue(markedPos.size()==1);
        //Assert.assertTrue(markedPos.contains(Bottom));
        //Assert.assertTrue(markedPos.contains(Right));
        Assert.assertTrue(markedPos.contains(Left));
        //Assert.assertTrue(markedPos.contains(Top));
        //Assert.assertTrue(markedPos.contains(Center));
    }

}
/*__________________________________________________________________________________________________

@Test
    public void checkGetStreet() {
        //Settings...
        Card card;
        ge.placeCard(card = new Card(23, -1, 0, EAST));
        ge.placeCard(card = new Card(24, -1, -1, NORTH));
        ge.placeCard(card = new Card(25, 0, -1, NORTH));
        ge.placeCard(card = new Card(26, 1, -1, NORTH));
        Card testCard = new Card(23, -1, 0, EAST);

        ArrayList<Card> street = ge.getStreet(testCard, SOUTH);
        Assert.assertTrue(street.size() == 3);
    }

    @Test
    public void testCheckStreetComplete() {
        //Settings...
        Card card;
        ge.placeCard(card = new Card(23, -1, 0, EAST));
        ge.placeCard(card = new Card(24, -1, -1, NORTH));
        ge.placeCard(card = new Card(25, 0, -1, NORTH));
        ge.placeCard(card = new Card(26, 1, -1, NORTH));
        Card testCard = new Card(23, -1, 0, EAST);
        ArrayList<Card> street = ge.getStreet(testCard, SOUTH);

        Boolean complete = ge.checkStreetComplete(street);
        Assert.assertFalse(complete);
    }

    @Test
    public void checkGetPeepPositionsStreet() {
        //Settings...
        Card card;
        ge.placeCard(card = new Card(23, -1, 0, EAST));
        ge.placeCard(card = new Card(24, -1, -1, NORTH));
        ge.placeCard(card = new Card(25, 0, -1, NORTH));
        ge.placeCard(card = new Card(26, 1, -1, NORTH));
        Card testCard = new Card(23, -1, 0, EAST);

        ArrayList<PeepPosition> positions = ge.getPeepPositionsStreet(testCard);
        Assert.assertTrue(positions.size() == 3);
        Assert.assertTrue(positions.contains(Right));
        Assert.assertTrue(positions.contains(Bottom));
        Assert.assertTrue(positions.contains(Center));
    }


*/