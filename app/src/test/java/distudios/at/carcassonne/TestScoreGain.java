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

        ge.placeCard(new Card(4,-1,0,Orientation.NORTH));
        ge.placeCard(new Card(13,-1,1,Orientation.EAST));
        ge.placeCard(new Card(37,0,1,Orientation.WEST));

    }

    @Test
    public void test2Sides() {
        ArrayList<Score> sc=ge.getScoreChanges(new Card(37,0,1,Orientation.WEST));
        for(int i=0;i<4;i++){
            System.out.println(sc.get(i).getCardlist().size());
        }

    }

}
