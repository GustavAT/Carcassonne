package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.Collections;

public class GameEngine implements IGameEngine {

    private GameState currentState;
    private final int STACK_SIZE=50;
    private Orientation orientation;

    @Override
    public void init(Orientation orientation) {
        currentState = new GameState();
        shuffle();
        setInitialCard();
    }

    private void shuffle() {
        // Cardset
        ArrayList<Integer> stack=new ArrayList<>();
        for(int i=0;i<STACK_SIZE;i++){
            stack.add(i+2);
            // Card Stack IDs go from 2 to 51. ID 1 is always start card
        }

        Collections.shuffle(stack);

        currentState.setStack(stack);
    }

    private void setInitialCard() {
        currentState.addCard(new Card(1, orientation, 0,0 ));
    }

    public GameState getGamestate(){
        return currentState;
    }
}
