package distudios.at.carcassonne;

import android.util.Log;

import org.junit.Test;

import java.util.ArrayList;

import distudios.at.carcassonne.engine.logic.GameEngine;
import distudios.at.carcassonne.engine.logic.GameState;


public class TestGameEngine {
    @Test
    public void shuffleWorks(){
        GameEngine ge=new GameEngine();
        ge.init();
        GameState gs=ge.getGamestate();
        ArrayList<Integer> al=gs.getStack();
        for(int i=0;i<al.size();i++){
            System.out.println("Listelement "+i+" : "+al.get(i));
        }
    }



}
