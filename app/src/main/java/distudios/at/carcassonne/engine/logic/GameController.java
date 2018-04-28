package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;

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

}
