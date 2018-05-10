package distudios.at.carcassonne.engine.logic;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.networking.INetworkController;
import distudios.at.carcassonne.networking.connection.CarcassonneMessage;

public class GameController implements IGameController {

    private IGameEngine gameEngine;

    /**
     * True if the player has placed his card in this turn
     */
    private boolean cardPlaced = false;

    /**
     * Game state
     *
     * If its not this players turn: WAITING (do nothing)
     * else:
     * - DRAW_CARD (waiting for card drawing)
     * - PLACE_CARD (waiting for card placement)
     * - PLACE_FIGURE (waiting for figure placement)
     * - END_TURN (waiting for player to end turn)
     */
    private CState cState;
    private Card currentCard;
    private boolean isCheating = false;

    public GameController() {
        this.init();
    }

    private void init() {
        // init game engine;
        gameEngine = new GameEngine();
        gameEngine.init(Orientation.NORTH);
        cState = CState.WAITING;
    }

    @Override
    public void removeFromStack(Card c) {
        getGameState().removeFromStack(c);
    }

    @Override
    public Card drawCard() {
        if (cState == CState.DRAW_CARD) {
            cState = CState.PLACE_CARD;
            return getGameState().drawCard();
        }
        return null;
    }

    @Override
    public List<Card> drawCards() {
        if (cState == CState.DRAW_CARD) {
            cState = CState.PLACE_CARD;
            isCheating = true;
            return getGameState().drawCards();
        }
        return null;
    }


    //todo: Nachdem Connection auf ExtendedCard gecoded wurden-->implementiere einen Situationellen Punktezähler anhand einer Id oder Koordinate

    @Override
    public boolean placeCard(Card card) {
        if (cState != CState.PLACE_CARD) return false;

        if(gameEngine.checkPlaceable(card)){
            gameEngine.placeCard(card);
            removeFromStack(card);
            // todo change to PLACE_FIGURE
            cState = CState.END_TURN;
            return true;
        }
        return false;
    }

    @Override
    public boolean placeFigure(Peep nextPeep, Card currentCard) {
        if (cState != CState.PLACE_FIGURE) return false;

        cState = CState.END_TURN;

        if(gameEngine.checkPeepPlaceable(nextPeep, currentCard)){
            gameEngine.placePeep(nextPeep);
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public void endTurn() {
        if (cState != CState.END_TURN || !isMyTurn()) return;

        CarcassonneMessage message = new CarcassonneMessage(CarcassonneMessage.END_TURN);
        GameState state = getGameState();
        state.currentPlayer = state.getNextPlayer();
        message.state = state;
        Log.d("CARDS", "send: " + state.cards.size());

        cState = CState.WAITING;
        isCheating = false;
        currentCard = null;
        CarcassonneApp.getNetworkController().sendMessage(message);
    }

    @Override
    public void initMyTurn() {
        if (cState != CState.WAITING) return;

        cState = CState.DRAW_CARD;
        isCheating = false;
        currentCard = null;
    }

    /**
     * Get possible locations for given card
     * @param c
     */
    public List<Pair<Integer, Integer>> getPossibleLocations(Card c) {
        List<Pair<Integer, Integer>> locations = new ArrayList<>();

        List<Card> cards = getGameState().cards;

        int originalX = c.getxCoordinate();
        int originalY = c.getyCoordinate();

        for (Card temp : cards) {
            int x = temp.getxCoordinate();
            int y = temp.getyCoordinate();

            c.setxCoordinate(x + 1);
            c.setyCoordinate(y);
            if (checkPosition(cards, c) && gameEngine.checkPlaceable(c)) {
                locations.add(new Pair<>(x + 1, y));
            }
            c.setxCoordinate(x - 1);
            c.setyCoordinate(y);
            if (checkPosition(cards, c) && gameEngine.checkPlaceable(c)) {
                locations.add(new Pair<>(x - 1, y));
            }
            c.setxCoordinate(x);
            c.setyCoordinate(y + 1);
            if (checkPosition(cards, c) && gameEngine.checkPlaceable(c)) {
                locations.add(new Pair<>(x, y + 1));
            }
            c.setxCoordinate(x);
            c.setyCoordinate(y - 1);
            if (checkPosition(cards, c) && gameEngine.checkPlaceable(c)) {
                locations.add(new Pair<>(x, y - 1));
            }
        }

        c.setxCoordinate(originalX);
        c.setyCoordinate(originalY);

        return locations;
    }


    @Override
    public CState getCState() {
        return cState;
    }

    @Override
    public Card getCurrentCard() {
        return currentCard;
    }

    @Override
    public void setCurrentCard(Card c) {
        currentCard = c;
    }

    /**
     * Check the position of given card is already set
     * @param cards
     * @param c
     */
    private boolean checkPosition(List<Card> cards, Card c) {
        boolean free = true;
        for (Card temp :
                cards) {
            if (temp.getxCoordinate() == c.getxCoordinate() &&
                    temp.getyCoordinate() == c.getyCoordinate()) {
                free = false;
                break;
            }
        }

        return free;
    }

    @Override
    public void setPoints(ArrayList<Integer> points) {
        for(int i=0;i<points.size();i++){
            gameEngine.addScore(points.get(i),i);
        }
    }

    @Override
    public void checkPoints(Card card) {
        ArrayList<Integer> cardscore= gameEngine.getScoreChanges(card);
        //todo: Weise scores den Objekten zu
        //todo: Weise objektscores den Peeps zu
        //setPoints(pointchanges);
    }

    @Override
    public GameState getGameState() {
        return gameEngine.getState();
    }

    @Override
    public void setState(GameState s) {
        gameEngine.setState(s);
    }

    /**
     * Start the game.
     * Host owner must call this method
     */
    @Override
    public void startGame() {

        gameEngine = new GameEngine();
        gameEngine.init(Orientation.NORTH);
        cState = CState.WAITING;

        INetworkController controller = CarcassonneApp.getNetworkController();

        CarcassonneMessage message = new CarcassonneMessage(CarcassonneMessage.HOST_START_GAME);
        message.playerMappings = controller.createPlayerMappings();
        GameState state = getGameState();
        state.currentPlayer = 0;
        state.maxPlayerCount = controller.getDeviceCount();
        message.state = state;

        cState = CState.DRAW_CARD;
        controller.sendToAllDevices(message);
    }

    @Override
    public void updateGameState() {
        INetworkController controller = CarcassonneApp.getNetworkController();
        CarcassonneMessage message = new CarcassonneMessage();
        message.type = CarcassonneMessage.GAME_STATE_UPDATE;
        message.state = getGameState();
        if (controller.isHost()) {
            controller.sendToAllDevices(message);
        } else if (controller.isClient()) {
            controller.sendToHost(message);
        }
    }

    @Override
    public boolean isMyTurn() {
        return getGameState().myTurn(CarcassonneApp.getNetworkController().getDevicePlayerNumber());
    }



    @Override
    public boolean hasPlacedCard() {
        return cardPlaced;
    }
}
