package distudios.at.carcassonne.engine.logic;

public class GameEngine implements IGameEngine {

    private GameState currentState;

    @Override
    public void init() {
        currentState = new GameState();
        shuffle();
        setInitialCard();
    }

    private void shuffle() {
        // todo: shuffle

        currentState.setStack(null);
    }

    private void setInitialCard() {
        // todo: set card
    }
}
