package distudios.at.carcassonne.engine.logic;

import android.util.Log;

import java.util.ArrayList;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.networking.INetworkController;
import distudios.at.carcassonne.networking.connection.CarcassonneMessage;

public class GameController implements IGameController {

    private IGameEngine gameEngine;

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
            return true;
        }
        else{
            //Melde "Nicht platzierbare Karte"
            System.out.println("Nicht platzierbare Karte");
            return false;
        }
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

        if (controller.isHost()) {
            controller.sendToAllDevices(message);
        } else if (controller.isClient()) {
            controller.sendToHost(message);
        }
    }

}
