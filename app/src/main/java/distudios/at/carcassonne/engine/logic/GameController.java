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

    private Card currentCard;

    public GameController() {
        this.init();
    }

    @Override
    public void init() {
        // init game engine;
        gameEngine = new GameEngine();
        gameEngine.init(Orientation.NORTH);
    }

    @Override
    public void action() {

        //choose card

        Card nextCard=null;
        actionCardPlacement(nextCard);

        //Überprüfe Peep

        //Platziere Peep

        //Überprüfe Punkte

        //Setze Punkte

        //Entferne Peep

        update();
    }

    //todo: Nachdem Connection auf ExtendedCard gecoded wurden-->implementiere einen Situationellen Punktezähler anhand einer Id oder Koordinate

    @Override
    public boolean actionCardPlacement(Card nextCard) {
        if(gameEngine.checkPlaceable(nextCard)){
            gameEngine.placeCard(nextCard);
            cardPlaced = true;
            currentCard = null;
            return true;
        }
        else{
            //Melde "Nicht platzierbare Karte"
            System.out.println("Nicht platzierbare Karte");
            return false;
        }
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
    public Card drawCard() {
        currentCard = getGameState().drawCard();
        return currentCard;
    }

    @Override
    public void removeFromStack(Card c) {
        getGameState().removeFromStack(c);
    }

    @Override
    public Card getCurrentCard() {
        return currentCard;
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

    private void update() {
        CarcassonneApp.getGraphicsController().drawField(null);
    }

    @Override
    public void dataReceived(Object data, int type) {

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
     * Start the actual game.
     * This method should be called from the host owner
     */
    @Override
    public void startGame() {
        INetworkController controller = CarcassonneApp.getNetworkController();
        controller.createPlayerMappings();
        CarcassonneMessage message = new CarcassonneMessage();
        message.type = CarcassonneMessage.HOST_START_GAME;
        message.playerMappings = controller.getPlayerMappings();
        GameState state = getGameState();
        state.currentPlayer = 0;
        state.maxPlayerCount = controller.getDeviceCount();
        message.state = state;
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
    public void endTurn() {
        Log.d("END TURN", getGameState().currentPlayer + "");
        if (!isMyTurn()) return;

        INetworkController controller = CarcassonneApp.getNetworkController();
        CarcassonneMessage message = new CarcassonneMessage();
        message.type = CarcassonneMessage.END_TURN;
        GameState state = getGameState();
        Log.d("PLAYER_NUMBER", state.currentPlayer + "");
        state.currentPlayer = state.getNextPlayer();
        Log.d("PLAYER_NUMBER", state.currentPlayer + "");
        message.state = state;

        currentCard = null;
        cardPlaced = false;

        if (controller.isHost()) {
            controller.sendToAllDevices(message);
        } else if (controller.isClient()) {
            controller.sendToHost(message);
        }
    }

    @Override
    public boolean hasPlacedCard() {
        return cardPlaced;
    }

    @Override
    public void placeCard(Card c) {
        // todo: implement
    }

}
