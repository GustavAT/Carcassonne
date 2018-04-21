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

        //choose card

        Card nextCard=null;
        actionCardplacement(nextCard);

        //Überprüfe Peep

        //Platziere Peep

        //Überprüfe Punkte

        //Setze Punkte

        update();
    }

    @Override
    public void actionCardplacement(Card nextCard) {
        if(gameEngine.checkPlaceable(nextCard)){
            gameEngine.placeCard(nextCard);
        }
        else{
            //Melde "Nicht platzierbare Karte"
            System.out.println("Nicht platzierbare Karte");
        }
    }

    private void update() {
        CarcassonneApp.getGraphicsController().drawField(null);
    }

    @Override
    public void dataReceived(Object data, int type) {

    }

}
