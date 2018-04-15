package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.Collections;

public class GameEngine implements IGameEngine {

    private GameState currentState;
    private final int STACK_SIZE=50;

    @Override
    public void init() {
        currentState = new GameState();
        shuffle();
        setInitialCard();
    }

    private void shuffle() {
        // Cardset
        ArrayList<Integer> stack=new ArrayList<>();
        for(int i=0;i<STACK_SIZE;i++){
            stack.add(i+1);
        }

        Collections.shuffle(stack);

        currentState.setStack(stack);
    }

    private void setInitialCard() {
        // todo: set card
    }
    public GameState getGamestate(){
        return currentState;
    }
}
