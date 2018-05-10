package distudios.at.carcassonne;

import android.util.Log;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.CardDataBase;
import distudios.at.carcassonne.engine.logic.CardSide;
import distudios.at.carcassonne.engine.logic.Color;
import distudios.at.carcassonne.engine.logic.GameEngine;
import distudios.at.carcassonne.engine.logic.GameState;
import distudios.at.carcassonne.engine.logic.Orientation;
import distudios.at.carcassonne.engine.logic.Peep;
import distudios.at.carcassonne.engine.logic.PeepPosition;

import static distudios.at.carcassonne.engine.logic.Orientation.EAST;
import static distudios.at.carcassonne.engine.logic.Orientation.NORTH;
import static distudios.at.carcassonne.engine.logic.Orientation.SOUTH;
import static distudios.at.carcassonne.engine.logic.Orientation.WEST;

import static distudios.at.carcassonne.engine.logic.Orientation.EAST;
import static distudios.at.carcassonne.engine.logic.Orientation.NORTH;
import static distudios.at.carcassonne.engine.logic.Orientation.SOUTH;
import static distudios.at.carcassonne.engine.logic.Orientation.WEST;



public class TestGameEngine {
    GameEngine ge;
    GameState gs;
    CardDataBase cdb=CardDataBase.getInstance();

    @Before
    public void setUp(){
        ge=new GameEngine();
        ge.init(Orientation.NORTH);
        gs=ge.getGamestate();
        cdb.cardDB.get(19).setDown(CardSide.CASTLE);    //Anpassung Startkarte
        cdb.cardDB.get(20).setLeft(CardSide.GRASS);

        cdb.cardDB.get(19).setRight(CardSide.CASTLE);   //Anpassung Neue Karte Check
        cdb.cardDB.get(20).setTop(CardSide.GRASS);
        cdb.cardDB.get(20).setDown(CardSide.GRASS);

        cdb.cardDB.get(21).setDown(CardSide.GRASS);     //Anpassung Neue Karte Check
        cdb.cardDB.get(21).setLeft(CardSide.CASTLE);

        cdb.cardDB.get(22).setRight(CardSide.STREET);
        cdb.cardDB.get(22).setDown(CardSide.STREET);
        cdb.cardDB.get(22).setTop(CardSide.GRASS);
        cdb.cardDB.get(22).setLeft(CardSide.GRASS);

        cdb.cardDB.get(23).setTop(CardSide.STREET);
        cdb.cardDB.get(23).setRight(CardSide.STREET);
        cdb.cardDB.get(23).setLeft(CardSide.GRASS);
        cdb.cardDB.get(23).setDown(CardSide.GRASS);


        cdb.cardDB.get(24).setRight(CardSide.STREET);
        cdb.cardDB.get(24).setLeft(CardSide.STREET);
        cdb.cardDB.get(24).setTop(CardSide.GRASS);
        cdb.cardDB.get(24).setDown(CardSide.STREET);

        cdb.cardDB.get(25).setRight(CardSide.CASTLE);
        cdb.cardDB.get(25).setLeft(CardSide.STREET);
        cdb.cardDB.get(25).setTop(CardSide.GRASS);

        cdb.cardDB.get(11).setRight(CardSide.STREET);
        cdb.cardDB.get(11).setTop(CardSide.STREET);
        cdb.cardDB.get(11).setDown(CardSide.GRASS);
        cdb.cardDB.get(11).setLeft(CardSide.GRASS);
    }

    @Test
    public void shuffleWorks(){
        ArrayList<Integer> al=gs.getStack();
        for(int i=0;i<al.size();i++){
            System.out.println("Listelement "+i+" : "+al.get(i));
        }
    }

    @Test
    public void checkPlaceableMethod(){
        System.out.println("\nPlatziere 3 Karten und überprüfe auf Platzierbarkeit");
        Card card=new Card(20,0,1,Orientation.NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));     //Test Placeable
        ge.placeCard(card);
        Assert.assertFalse(ge.checkPlaceable(card));    //Test reflexive Placeable false
        card=new Card(21,1,0,Orientation.NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));     //Test placeable
        ge.placeCard(card);
        card=new Card(22,1,1,Orientation.NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));     //Test placeable with 2 Connection
        ge.placeCard(card);
        card=new Card(23,-1,0, NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));
        ge.placeCard(card);
        card=new Card(24,-1,-1, NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));
        ge.placeCard(card);
        card=new Card(25,0,-1, NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));
        ge.placeCard(card);
        card=new Card(26,1,-1, NORTH);
        Assert.assertTrue(ge.checkPlaceable(card));
        ge.placeCard(card);
        printCards(gs);
    }

    @Test
    public void checkGetPossibilities(){
        //Settings...
        Card card;
        ge.placeCard(card = new Card(23, -1, 0, NORTH));
        ge.placeCard(card = new Card(24, -1, -1, NORTH));
        ge.placeCard(card = new Card(25, 0, -1, NORTH));


        Card testCard = new Card(12,2,2,NORTH);
        ArrayList test = ge.getPossibilities(testCard);
        Assert.assertTrue(test.size()==3);
    }

    @Test
    public void checkGetFollowedCard(){
        Card card;
        ge.placeCard(card = new Card(23, -1, 0, NORTH));
        ge.placeCard(card = new Card(24, -1, -1, NORTH));
        ArrayList<Card> field = gs.getCards();
        Card testCard = new Card(23, -1, 0, NORTH);

        Card result = ge.getFollowedCard(testCard, field, Orientation.WEST );
        Assert.assertTrue(result==null);

        Card exp = new Card(24, -1, -1, NORTH);
        result = ge.getFollowedCard(testCard, field, Orientation.SOUTH);
        Assert.assertTrue(result==exp);
    }

    @Test
    public void checkGetStreet(){
        //Settings...
        Card card;
        ge.placeCard(card = new Card(23, -1, 0, NORTH));
        ge.placeCard(card = new Card(24, -1, -1, NORTH));
        ge.placeCard(card = new Card(25, 0, -1, NORTH));
        ge.placeCard(card = new Card(26, 1, -1, NORTH));
        Card testCard = new Card(23,-1,0,NORTH);

        ArrayList<Card> street = ge.getStreet(testCard, SOUTH);
        Assert.assertTrue(street.size() == 3);
    }

    @Test
    public void testCheckStreetComplete(){
        //Settings...
        Card card;
        ge.placeCard(card = new Card(23, -1, 0, NORTH));
        ge.placeCard(card = new Card(24, -1, -1, NORTH));
        ge.placeCard(card = new Card(25, 0, -1, NORTH));
        ge.placeCard(card = new Card(26, 1, -1, NORTH));
        Card testCard = new Card(23,-1,0,NORTH);
        ArrayList<Card> street = ge.getStreet(testCard, SOUTH);

        Boolean complete = ge.checkStreetComplete(street);
        Assert.assertFalse(complete);
    }

    @Test
    public void checkPlacePeep(){
        Card card = new Card(25,0,-1,NORTH);
        Peep nextPeep = new Peep(card, PeepPosition.RightMid, Color.YELLOW);

        Assert.assertTrue(ge.checkPeepPlaceable(nextPeep,card));
        ge.placePeep(nextPeep);
        Assert.assertTrue(gs.getPeeps().size()==1);
        ArrayList<Peep> peeps = gs.getPeeps();
        for (Peep peep:peeps) {
            System.out.println("Color: "+peep.getColor());
            System.out.println("Position: "+peep.getPeepPosition());
            System.out.println("Card: "+peep.getCard());
        }
    }

    @Test
    public void switchOrientation(){
        Orientation result=Card.getAbsoluteOrientation(Orientation.EAST, Orientation.SOUTH);
        Orientation compare=Card.getAbsoluteOrientation(Orientation.WEST, Orientation.NORTH);
        Assert.assertTrue(result==compare);
    }

    private void printCards(GameState pgs){
        ArrayList<Card> cardList = pgs.getCards();

        for(int i=0;i<cardList.size();i++){
            System.out.println("Card "+i+" : "+cardList.get(i).toString());
        }
    }
}
