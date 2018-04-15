package distudios.at.carcassonne;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.GameEngine;
import distudios.at.carcassonne.engine.logic.GameState;
import distudios.at.carcassonne.engine.logic.Orientation;


public class TestGameEngine {
    GameEngine ge;
    GameState gs;

    @Before
    public void setUp(){
        ge=new GameEngine();
        ge.init(Orientation.NORTH);
        gs=ge.getGamestate();
    }

    @Test
    public void shuffleWorks(){

        ArrayList<Integer> al=gs.getStack();
        for(int i=0;i<al.size();i++){
            System.out.println("Listelement "+i+" : "+al.get(i));
        }
    }

    @Test
    public void placeInitialCard(){
        ArrayList<Card> cardList = gs.getCards();

        for(int i=0;i<cardList.size();i++){
            System.out.println("Card "+i+" : "+cardList.get(i).toString());
        }

    }



}
