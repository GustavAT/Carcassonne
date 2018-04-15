package distudios.at.carcassonne.engine.logic;

import distudios.at.carcassonne.CarcassonneApp;

public class GameController implements IGameController {

    private IGameEngine gameEngine;

    @Override
    public void init() {
        // init game engine
       Orientation defaultOrient = Orientation.NORTH;
        gameEngine.init(defaultOrient);
    }

    @Override
    public void action() {

        update();
    }

    private void update() {
        CarcassonneApp.getGraphicsController().drawField(null);
    }

    @Override
    public void dataReceived(Object data, int type) {

    }

}
