package distudios.at.carcassonne;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.CardDataBase;
import distudios.at.carcassonne.engine.logic.GameEngine;
import distudios.at.carcassonne.engine.logic.GameState;
import distudios.at.carcassonne.engine.logic.Orientation;
import distudios.at.carcassonne.engine.logic.Peep;
import distudios.at.carcassonne.engine.logic.PeepPosition;

import static distudios.at.carcassonne.engine.logic.CardSide.CASTLE;
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


public class TestPlacePeeps {
    GameEngine ge;
    GameState gs;
    CardDataBase cdb = CardDataBase.getInstance();

    @Before
    public void setUp() {
        ge = new GameEngine();
        ge.init(Orientation.NORTH);
        gs = ge.getGamestate();

        //Settings...
       /* Card cardI = new Card(4, 1, 0, NORTH);
        Card cardII = new Card(21, 2, 0, WEST);
        Card cardIII = new Card(13, 3, 0, NORTH);

        Card cardIV = new Card(52, -1, 0, EAST);
        Card cardV = new Card(30, -2, 0, EAST);
        Card cardVI = new Card(18, -3, 0, NORTH);
        Card cardVII = new Card(13, -4, 0, WEST);

        Card cardVIII = new Card(18, 0, 1, EAST);
        Card cardIX = new Card(35, 2, 1, WEST);
        Card cardX = new Card(24, 3, 1, SOUTH);

        Card cardXI = new Card(24, 0, 2, SOUTH);

        Card cardXII = new Card(60, 0, -1, SOUTH);
        Card cardXIII = new Card(52, 1, -1, EAST);
        Card cardXIV = new Card(32, 2, -1, NORTH);
        Card cardXV = new Card(2, 3, -1, WEST);

        Card cardXVI = new Card(24, 0, -2, WEST);
        Card cardXVII = new Card(35, 1, -2, EAST);
        Card cardXVIII = new Card(52, 2, -2, NORTH);

        Card cardXIX = new Card(40, 0, -3, WEST);
        Card cardXX = new Card(35, 1, -3, NORTH);

        Card cardXXI = new Card(52, 0, -4, NORTH);

        Card cardXXII = new Card(29, 0, -5, NORTH);
        Card cardXXIII = new Card(69, 1, -5, SOUTH);


        ge.placeCard(cardI);
        ge.placeCard(cardII);
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
        ge.placeCard(cardXXIII);*/
    }

    @Test
    @Ignore
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
        Card cardVI = new Card(13, 3, 0, NORTH);
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
    @Ignore
    public void checkGetUnmarkedBorders() {
        //Settings...
        Card cardI = new Card(4, 1, 0, NORTH);
        Card cardIII = new Card(52, -1, 0, EAST);
        Card cardIV = new Card(30, -2, 0, EAST);
        Card cardV = new Card(22, 2, 0, WEST);
        Card cardVI = new Card(13, 3, 0, NORTH);
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
        //ge.placeCard(cardXIV);
        //ge.placeCard(cardXV);
        ge.placeCard(cardXVI);
        ge.placeCard(cardXVII);
        ge.placeCard(cardXVIII);
        ge.placeCard(cardXIX);
        ge.placeCard(cardXX);
        ge.placeCard(cardXXI);
        ge.placeCard(cardXXII);
        ge.placeCard(cardXXIII);
        ge.placeCard(cardXXIV);

        ge.markCard(cardXIII, Top, STREET);
        ge.markAllCards();
        ge.placeCard(cardXIV);
        ge.markAllCards();
        ge.placeCard(cardXV);
        //ge.markAllCards();
        //ge.placeCard(cardXV);
        //cardXVI.setMark(Left);
        //cardXIV.setMark(Right);

        ArrayList<PeepPosition> unmarkedBorders = new ArrayList<PeepPosition>();
        unmarkedBorders = ge.getUnmarkedBorders(cardXV, STREET);
        Assert.assertTrue(unmarkedBorders.size() == 2);
        Assert.assertTrue(unmarkedBorders.contains(Bottom));
        Assert.assertTrue(unmarkedBorders.contains(Right));
        //Assert.assertTrue(unmarkedBorders.contains(Left));
        //Assert.assertTrue(unmarkedBorders.contains(Top));
        //Assert.assertTrue(unmarkedBorders.contains(Center));
        //Assert.assertTrue(peeps.size()==1);
    }

    @Test
    public void checkGetALLFigurePos(){
        //Settings...
        Card cardI = new Card(5, 1, 0, NORTH);
        Card cardXIII = new Card(60, 0, -1, SOUTH);
        Card cardXIV = new Card(52, 1, -1, EAST);
        Card cardXV = new Card(32, 2, -1, NORTH);
        Card cardIV = new Card(40, 3, -1, WEST);
        Card cardV = new Card(35, 4, -1, NORTH);
        Card cardVI = new Card(24, 3, 0, WEST);
        Card cardVII = new Card(35, 4, 0, EAST);
        Card cardXVI = new Card(2, -1, 0, EAST);


        ge.placeCard(cardI);
        ge.placeCard(cardXIII);
        //ge.placeCard(cardXIV);
        //ge.placeCard(cardXV);
        ge.placeCard(cardIV);
        ge.placeCard(cardV);
        ge.placeCard(cardVI);
        ge.placeCard(cardVII);
        ge.placeCard(cardXVI);


        //ge.markCard(cardXIII,Right,STREET);
        ge.markAllCards();
        ge.placeCard(cardXIV);
        ge.markAllCards();
        ge.placeCard(cardXV);

        ArrayList<PeepPosition> unmarkedBorders = new ArrayList<PeepPosition>();
        unmarkedBorders = ge.getUnmarkedBorders(cardI, STREET);

        ArrayList<PeepPosition> allUnmarkedBorders = new ArrayList<PeepPosition>();
        allUnmarkedBorders = ge.getALLFigurePos(cardI);
        Assert.assertTrue(allUnmarkedBorders.size()==1);
        //Assert.assertTrue(allUnmarkedBorders.contains(Bottom));
        //Assert.assertTrue(allUnmarkedBorders.contains(Right));
        //Assert.assertTrue(allUnmarkedBorders.contains(Left));
        //Assert.assertTrue(allUnmarkedBorders.contains(Top));
        Assert.assertTrue(allUnmarkedBorders.contains(Center));
    }

    @Test
    @Ignore
    public void checkMarkCards(){
        //Settings...
        Card cardI = new Card(4, 1, 0, NORTH);
        Card cardIII = new Card(52, -1, 0, EAST);
        Card cardIV = new Card(30, -2, 0, EAST);
        Card cardV = new Card(22, 2, 0, WEST);
        Card cardVI = new Card(13, 3, 0, NORTH);
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

        cardIX.setMark(Top);
        ge.markCard(cardXII,Left,CASTLE);
        ArrayList<Integer> markedPos = cardXII.getMarks();

        Assert.assertTrue(markedPos.size()==2);
        Assert.assertTrue(markedPos.contains(PeepPosition.fromPosition(Bottom)));
        //Assert.assertTrue(markedPos.contains(Right));
        Assert.assertTrue(markedPos.contains(PeepPosition.fromPosition(Left)));
        //Assert.assertTrue(markedPos.contains(Top));
        //Assert.assertTrue(markedPos.contains(Center));
    }

    @Test
    public void checkPlacePeep(){
        //Settings...
        Card cardI = new Card(4, 1, 0, NORTH);
        Card cardII = new Card(52, -1, 0, EAST);
        Card cardIII = new Card(30, -2, 0, EAST);
        Card cardIV = new Card(22, 2, 0, WEST);
        Card cardV = new Card(13, 3, 0, NORTH);
        Card cardVI = new Card(18, -3, 0, NORTH);
        Card cardVII = new Card(13, -4, 0, WEST);

        Card cardVIII = new Card(18, 0, 1, EAST);
        Card cardIX = new Card(35, 2, 1, SOUTH);
        Card cardX = new Card(24, 3, 1, SOUTH);

        Card cardXI = new Card(24, 0, 2, SOUTH);

        Card cardXII = new Card(60, 0, -1, SOUTH);
        Card cardXIII = new Card(52, 1, -1, EAST);
        Card cardXIV = new Card(32, 2, -1, NORTH);
        Card cardXV = new Card(2, 3, -1, WEST);

        Card cardXVI = new Card(24, 0, -2, WEST);
        Card cardXVII = new Card(35, 1, -2, EAST);
        Card cardXVIII = new Card(52, 2, -2, NORTH);
        Card cardXIX = new Card(40, 0, -3, WEST);
        Card cardXX = new Card(35, 1, -3, NORTH);

        Card cardXXI = new Card(53, 0, -4, NORTH);

        Card cardXXII = new Card(29, 0, -5, NORTH);
        Card cardXXIII = new Card(69, 1, -5, SOUTH);

        ge.placeCard(cardI);
        ge.placeCard(cardII);
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

        //ge.markCard(cardXX,Left,CASTLE);
        //ge.markAllCards();
        ge.placeCard(cardXVII);

        ge.placePeep(cardXVII,Bottom,4);
        ArrayList<Integer> marks = cardXVII.getMarks();
        ArrayList<Peep> peeps = gs.getPeeps();

        Assert.assertTrue(marks.size()==2);
        Assert.assertTrue(marks.contains(PeepPosition.fromPosition(Bottom)));
        Assert.assertTrue(marks.contains(PeepPosition.fromPosition(Left)));
        //Assert.assertTrue(marks.contains(Left));
        //Assert.assertTrue(marks.contains(Top));
        //Assert.assertTrue(marks.contains(Center));

        Assert.assertTrue(peeps.size() == 1);

        for (Peep peep:peeps) {
            System.out.println("Card: "+peep.getCardId());
            System.out.println("Pos: "+peep.getPeepPosition());
            System.out.println("Player: "+peep.getPlayerID());
            System.out.println();
        }
    }

    @Test
    @Ignore
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
        ArrayList<Integer> marksI = cardI.getMarks();
        ArrayList<Integer> marksII = cardII.getMarks();
        ArrayList<Integer> marksIII = cardIII.getMarks();

        Assert.assertTrue(marksI.size() == 2);
        //Assert.assertTrue(marksI.contains(Bottom));
        Assert.assertTrue(marksI.contains(PeepPosition.fromPosition(Right)));
        //Assert.assertTrue(marksI.contains(Left));
        Assert.assertTrue(marksI.contains(PeepPosition.fromPosition(Top)));
        //Assert.assertTrue(marksI.contains(Center));

        Assert.assertTrue(marksII.size() == 2);
        //Assert.assertTrue(marksII.contains(Bottom));
        Assert.assertTrue(marksII.contains(PeepPosition.fromPosition(Right)));
        Assert.assertTrue(marksII.contains(PeepPosition.fromPosition(Left)));
        //Assert.assertTrue(marksII.contains(Top));
        //Assert.assertTrue(marksII.contains(Center));

        Assert.assertTrue(marksIII.size() == 1);
        //Assert.assertTrue(marksIII.contains(Bottom));
        //Assert.assertTrue(marksIII.contains(Right));
        Assert.assertTrue(marksIII.contains(PeepPosition.fromPosition(Left)));
        //Assert.assertTrue(marksIII.contains(Top));
        //Assert.assertTrue(marksIII.contains(Center));

    }

    @Test
    public void checkDoubleStreet() {
        Card cardI = new Card(57, -3, 1, NORTH);
        Card cardII = new Card(54, -3, 2, NORTH);

        ge.placeCard(cardII);
        ge.markCard(cardII, Top, STREET);
        ge.markAllCards();
        ge.placeCard(cardI);
        ArrayList<PeepPosition> marks = ge.getALLFigurePos(cardI);
        Assert.assertTrue(marks.size() == 0);
    }

    @Test
    public void test() {
        Card cardI = new Card(4, -3, 1, NORTH);
        Card cardII = new Card(21, -3, 2, NORTH);

        ge.placeCard(cardII);
        //ge.markCard(cardII, Top,STREET);
        //ge.markAllCards();
        ge.placeCard(cardI);
        ArrayList<PeepPosition> pos = ge.getALLFigurePos(cardI);
        Assert.assertTrue(pos.size() == 1);
        Assert.assertTrue(pos.contains(Center));
        ge.placePeep(cardI, Center, 2);
        /*ArrayList<PeepPosition> marks = cardI.getMarks();
        Assert.assertTrue(marks.size() == 1);
        Assert.assertTrue(marks.contains(Center));
        ArrayList<Peep> peeps = gs.getPeeps();*/
    }
}
