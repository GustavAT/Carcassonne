package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static distudios.at.carcassonne.engine.logic.CardSide.CASTLE;
import static distudios.at.carcassonne.engine.logic.CardSide.STREET;
import static distudios.at.carcassonne.engine.logic.Orientation.EAST;
import static distudios.at.carcassonne.engine.logic.Orientation.NORTH;
import static distudios.at.carcassonne.engine.logic.Orientation.SOUTH;
import static distudios.at.carcassonne.engine.logic.Orientation.WEST;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Bottom;
import static distudios.at.carcassonne.engine.logic.PeepPosition.BottomLeft;
import static distudios.at.carcassonne.engine.logic.PeepPosition.BottomRight;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Center;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Left;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Right;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Top;
import static distudios.at.carcassonne.engine.logic.PeepPosition.TopLeft;
import static distudios.at.carcassonne.engine.logic.PeepPosition.TopRight;

public class GameEngine implements IGameEngine {

    private GameState currentState;
    //   72 Cards including start card leads to STACK SIZE of 71;
    private final int STACK_SIZE = 71;
    private boolean closed = true;

    @Override
    public GameState getState() {
        return currentState;
    }

    @Override
    public void setState(GameState s) {
        currentState = s;
    }

    @Override
    public void init(Orientation start) {
        currentState = new GameState();
        ArrayList<Integer> stack = new ArrayList<>();
        for (int i = 0; i < STACK_SIZE; i++) {
            stack.add(i + 2);
            // Card Stack IDs go from 2 to 51. ID 1 is always start card
        }

        // useless: reference vs value
        currentState.setStack(stack);
        shuffle();
        setInitialCard(start);
    }

    private void shuffle() {
        ArrayList<Integer> stack = currentState.getStack();
        Collections.shuffle(stack);
        // useless: reference vs value
        currentState.setStack(stack);
    }

    private void setInitialCard(Orientation start) {
        currentState.addCard(new Card(1, 0, 0, start));
    }

    public void placeCard(Card card) {
        currentState.addCard(card);
        ArrayList stack = currentState.getStack();
        // what is removed from stack??? please check
        stack.remove(stack.size() - 1);
        // useless: reference vs value
        currentState.setStack(stack);
    }

    public boolean checkPlaceable(Card nextCard) {
        //Aktuelles Feld
        ArrayList<Card> cards = currentState.getCards();
        //Überprüfung Verbunden
        boolean isconnected = false;
        //Starte oben
        Orientation current = Orientation.NORTH;
        Card itcard;

        //Überprüfe, ob bereits eine Karte an der Stelle liegt
        if (checkIfExists(nextCard, cards)) return false;

        //Iteriere über die Seiten
        for (int i = 0; i < 4; i++) {
            //Überprüfe Border an der aktuellen Seite
            if (!checkBorder(nextCard, cards, Orientation.NORTH)) {
                //Falls nicht Verbunden-->False
                return false;
            } else {
                //Existiert eine Connection
                isconnected = true;
            }
            //Nächste Seite
            current = Card.getAbsoluteOrientation(current, EAST);
        }
        return isconnected;
    }

    public void addScore(int point, int player) {
        int oldpoints = currentState.getPoints(player);
        currentState.setPoints(player, point + oldpoints);
    }

    @Override
    public ArrayList<Integer> getScoreChanges(Card card) {
        ArrayList<Card> field = currentState.getCards();
        ArrayList<Integer> scores = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            scores.add(getConnectedCards(card, Orientation.valueOf(i)).size());
        }
        return scores;
    }

    private ArrayList<Card> checkBorderCards(Card card, Orientation sborder) {
        ArrayList<Boolean> checkside = new ArrayList<>(4);
        ArrayList<Card> itcards = new ArrayList<>(4);

        //todo: implement connections
        //Füge Connections der aktuellen Karte zusammen
        if (sborder == Orientation.NORTH) {
            checkside.set(0, true);
        } else if (sborder == EAST) {
            checkside.set(1, true);
        } else if (sborder == Orientation.SOUTH) {
            checkside.set(2, true);
        } else if (sborder == WEST) {
            checkside.set(3, true);
        } else ;

        //Iteriere über alle Connections
        for (int i = 0; i < checkside.size(); i++) {
            if (checkside.get(i)) {
                if (checkBorder(card, currentState.getCards(), Orientation.valueOf(i))) {
                    Card fcard = getFollowedCard(card, currentState.getCards(), Orientation.valueOf(i));
                    if (fcard != null) {
                        itcards.set(i, fcard);
                    } else {
                        closed = false;
                    }
                } else {
                    itcards.set(i, null);
                }
            }
        }

        return itcards;
    }

    public ArrayList<Card> getConnectedCards(Card card, Orientation border) {
        ArrayList<Card> itcards = new ArrayList<>();
        ArrayList<Card> finalcards = new ArrayList<>();
        ArrayList<Orientation> oitcard = new ArrayList<>();
        itcards.add(card);
        finalcards.add(card);
        oitcard.add(border);
        boolean finished = false;
        boolean closed = true;

        while (!finished) {
            //Schleifenabfrage
            if (itcards.size() == 0) {
                finished = true;
            } else {
                //Iterationsmenge vorbereiten
                Card actCard = itcards.get(0);
                itcards.remove(0);
                Orientation actO = oitcard.get(0);
                oitcard.remove(0);
                //Methode überprüfe anliegende Karten
                ArrayList<Card> concards = checkBorderCards(actCard, actO);
                //Füge Cards mit Orientation hinzu
                for (int i = 0; i < concards.size(); i++) {
                    Card it = concards.get(i);
                    if (it != null && !checkIfExists(it, finalcards)) {
                        finalcards.add(concards.get(i));
                        itcards.add(concards.get(i));
                        oitcard.add(Orientation.valueOf(i));
                    }
                }
            }


        }
        if (!closed) {
            closed = true;
            return new ArrayList<>();
        }
        return finalcards;
    }

    private boolean checkIfExists(Card card, ArrayList<Card> field) {
        for (int i = 0; i < field.size(); i++) {
            Card itcard = field.get(i);
            if (card.getxCoordinate() == itcard.getxCoordinate()) {
                if (card.getyCoordinate() == itcard.getyCoordinate()) {
                    return true;
                }
            }
        }
        return false;
    }

    public GameState getGamestate() {
        return currentState;
    }


    public boolean checkBorder(Card a, ArrayList<Card> field, Orientation oa) {

        CardDataBase cdb = CardDataBase.getInstance();
        Orientation ob = Card.getAbsoluteOrientation(oa, Orientation.SOUTH);
        int xb, xa = a.getxCoordinate();
        int yb, ya = a.getyCoordinate();
        Card nextCard;

        for (int i = 0; i < field.size(); i++) {
            nextCard = field.get(i);
            xb = nextCard.getxCoordinate();
            yb = nextCard.getyCoordinate();
            Orientation ha, hb;

            if (oa == Orientation.NORTH && xa == xb && ya + 1 == yb) {
                ha = Card.getAbsoluteOrientation(oa, a.getOrientation());
                hb = Card.getAbsoluteOrientation(ob, nextCard.getOrientation());

                if (cdb.getCardSide(a.getId(), ha) != cdb.getCardSide(nextCard.getId(), hb)) {
                    return false;
                }
                break;
            } else if (oa == EAST && xa + 1 == xb && ya + 1 == yb) {
                ha = Card.getAbsoluteOrientation(oa, a.getOrientation());
                hb = Card.getAbsoluteOrientation(ob, nextCard.getOrientation());

                if (cdb.getCardSide(a.getId(), ha) != cdb.getCardSide(nextCard.getId(), hb)) {
                    return false;
                }
                break;
            } else if (oa == Orientation.SOUTH && xa == xb && ya - 1 == yb) {
                ha = Card.getAbsoluteOrientation(oa, a.getOrientation());
                hb = Card.getAbsoluteOrientation(ob, nextCard.getOrientation());

                if (cdb.getCardSide(a.getId(), ha) != cdb.getCardSide(nextCard.getId(), hb)) {
                    return false;
                }
                break;
            } else if (oa == WEST && xa - 1 == xb && ya == yb) {
                ha = Card.getAbsoluteOrientation(oa, a.getOrientation());
                hb = Card.getAbsoluteOrientation(ob, nextCard.getOrientation());

                if (cdb.getCardSide(a.getId(), ha) != cdb.getCardSide(nextCard.getId(), hb)) {
                    return false;
                }
                break;
            } else {

            }
        }
        return true;
    }

    public Card getFollowedCard(Card a, ArrayList<Card> field, Orientation oa) {
        CardDataBase cdb = CardDataBase.getInstance();
        int xb, xa = a.getxCoordinate();
        int yb, ya = a.getyCoordinate();
        Card nextCard;

        //Iteriere über alle Karten
        for (int i = 0; i < field.size(); i++) {
            nextCard = field.get(i);
            xb = nextCard.getxCoordinate();
            yb = nextCard.getyCoordinate();

            //Falls Seite und aktuelle Karte übereintreffen-->returne aktuelle Karte
            if (oa == Orientation.NORTH && xa == xb && ya + 1 == yb) {
                return nextCard;
            } else if (oa == Orientation.SOUTH && xa == xb && ya - 1 == yb) {
                return nextCard;
            } else if (oa == EAST && xa + 1 == xb && ya == yb) {
                return nextCard;
            } else if (oa == WEST && xa - 1 == xb && ya == yb) {
                return nextCard;
            } else {

            }
        }
        //Falls keine Karte gefunden wurde, returne null
        return null;
    }

    /**
     * Hilfsfunktion, die für zwei Karten (card, testCard), die zueinander in der übergebenen Orientierung liegen,
     * die für card blockierten Seiten zum Setzen von Peeps zurückgibt
     * @param card
     * @param testCard
     * @param orientation
     * @return
     */
    public List<PeepPosition> getBordersByOrientation(Card card, Card testCard, Orientation orientation) {
        ArrayList<PeepPosition> markedBorders = new ArrayList<>();

        if (Card.getAbsoluteOrientation(orientation, card.getOrientation()) == NORTH) {//Folgekarte befindet sich im Norden
            if (testCard.getPosMarks().contains(Bottom) && !(markedBorders.contains(Top))) {
                markedBorders.add(Top);
            }
            if (testCard.getPosMarks().contains(BottomRight) && !(markedBorders.contains(TopRight))) {
                markedBorders.add(TopRight);
            }
            if (testCard.getPosMarks().contains(BottomLeft) && !(markedBorders.contains(TopLeft))) {
                markedBorders.add(TopLeft);
            }
        }

        if (Card.getAbsoluteOrientation(orientation, card.getOrientation()) == EAST) {//Folgekarte befindet sich im Osten
            if (testCard.getPosMarks().contains(Left) && !(markedBorders.contains(Right))) {
                markedBorders.add(Right);
            }
            if (testCard.getPosMarks().contains(TopLeft) && !(markedBorders.contains(TopRight))) {
                markedBorders.add(TopRight);
            }
            if (testCard.getPosMarks().contains(BottomLeft) && !(markedBorders.contains(BottomRight))) {
                markedBorders.add(BottomRight);
            }
        }
        if (Card.getAbsoluteOrientation(orientation, card.getOrientation()) == SOUTH) {//Folgekarte befindet sich im Süden
            if (testCard.getPosMarks().contains(Top) && !(markedBorders.contains(Bottom))) {
                markedBorders.add(Bottom);
            }
            if (testCard.getPosMarks().contains(TopRight) && !(markedBorders.contains(BottomRight))) {
                markedBorders.add(BottomRight);
            }
            if (testCard.getPosMarks().contains(TopLeft) && !(markedBorders.contains(BottomLeft))) {
                markedBorders.add(BottomLeft);
            }
        }
        if (Card.getAbsoluteOrientation(orientation, card.getOrientation()) == WEST) {//Folgekarte befindet sich im Westen
            if (testCard.getPosMarks().contains(Right) && !(markedBorders.contains(Left))) {
                markedBorders.add(Left);
            }
            if (testCard.getPosMarks().contains(TopRight) && !(markedBorders.contains(TopLeft))) {
                markedBorders.add(TopLeft);
            }
            if (testCard.getPosMarks().contains(BottomRight) && !(markedBorders.contains(BottomLeft))) {
                markedBorders.add(BottomLeft);
            }
        }
        return markedBorders;
    }

    /*
    Gibt für übergebene Karte und übergebene CardSide die markierten Seiten (gemäß Spielfeld) an
     */
    public List<PeepPosition> getMarkedBorders(Card card, CardSide cardSide) {
        ArrayList<PeepPosition> markedBorders = new ArrayList<PeepPosition>();
        List<PeepPosition> pBorders;
        ArrayList<Card> field = currentState.getCards();
        CardDataBase cdb = CardDataBase.getInstance();
        int cardID = card.getId();
        Card testCard;

        if (getFollowedCard(card, field, Card.getAbsoluteOrientation(NORTH, card.getOrientation())) != null) {
            testCard = getFollowedCard(card, field, Card.getAbsoluteOrientation(NORTH, card.getOrientation())); //passende Folgekarte gemäß Spielfeld

            pBorders = getBordersByOrientation(card, testCard, NORTH);
            for (PeepPosition pos:pBorders){
                if(!(markedBorders.contains(pos))){
                    markedBorders.add(pos);
                }
            }

        }
            if (getFollowedCard(card, field, Card.getAbsoluteOrientation(EAST, card.getOrientation())) != null) {
                testCard = getFollowedCard(card, field, Card.getAbsoluteOrientation(EAST, card.getOrientation())); //passende Folgekarte gemäß Spielfeld

                pBorders = getBordersByOrientation(card, testCard, EAST);
                for (PeepPosition pos:pBorders){
                    if(!(markedBorders.contains(pos))){
                        markedBorders.add(pos);
                    }
                }
            }
            if (getFollowedCard(card, field, Card.getAbsoluteOrientation(SOUTH, card.getOrientation())) != null) {
                testCard = getFollowedCard(card, field, Card.getAbsoluteOrientation(SOUTH, card.getOrientation())); //passende Folgekarte gemäß Spielfeld

                pBorders = getBordersByOrientation(card, testCard, SOUTH);
                for (PeepPosition pos:pBorders){
                    if(!(markedBorders.contains(pos))){
                        markedBorders.add(pos);
                    }
                }
            }
                if (getFollowedCard(card, field, Card.getAbsoluteOrientation(WEST, card.getOrientation())) != null) {
                    testCard = getFollowedCard(card, field, Card.getAbsoluteOrientation(WEST, card.getOrientation())); //passende Folgekarte gemäß Spielfeld

                    pBorders = getBordersByOrientation(card, testCard, WEST);
                    for (PeepPosition pos:pBorders){
                        if(!(markedBorders.contains(pos))){
                            markedBorders.add(pos);
                        }
                    }
                }

        if(markedBorders.contains(Top) && cdb.getCardSide(cardID,card.getOrientation()) != cardSide){
            markedBorders.remove(Top);
        }
        if(markedBorders.contains(Right) && cdb.getCardSide(cardID,getRightSide(card.getOrientation())) != cardSide){
            markedBorders.remove(Right);
        }
        if(markedBorders.contains(Bottom) && cdb.getCardSide(cardID,Card.getAbsoluteOrientation(card.getOrientation(),SOUTH)) != cardSide){

            markedBorders.remove(Bottom);
        }
        if(markedBorders.contains(Left) && cdb.getCardSide(cardID,getLeftSide(card.getOrientation())) != cardSide){

            markedBorders.remove(Left);
        }

        return markedBorders;
    }

    /*
 Gibt für übergebene Karte und übergebene CardSide die freien Seiten (gemäß Spielfeld) an
  */
    public List<PeepPosition> getUnmarkedBorders(Card card, CardSide cardSide) {
        ArrayList<PeepPosition> unmarkedBorders;
        CardDataBase cdb = CardDataBase.getInstance();
        int cardID = card.getId();
        //Seiten mit passender CardSide gemäß cdb
        List<Orientation> buildingOs = cdb.getMatchingOrientations(cardID, cardSide);

        //Es werden die Borders für Street/Castle geholt
        unmarkedBorders = getUnmarkedBuildingBorders(card, buildingOs, cardSide);
        unmarkedBorders.remove(Center);

        // Wenn aktuelle Karte 2 Street-Seiten hat und eine besetzt ist => kein Setzen möglich
        if (cardSide == STREET && buildingOs.size() == 2 && unmarkedBorders.size() < 2) {
            unmarkedBorders.clear();
            return unmarkedBorders;
        }

        //Abfangen einiger Sonderfälle für Castles:

        //Es handelt sich um die "Vierer-Castle-Karte" => nur im Center positionierbar
        if (cardSide == CASTLE && buildingOs.size() == 4 && unmarkedBorders.size() == 4) {
            unmarkedBorders.clear();
            unmarkedBorders.add(Center);
            return unmarkedBorders;
        }else if(cardSide == CASTLE && buildingOs.size() == 4 && unmarkedBorders.size() != 4){
            unmarkedBorders.clear();
            return unmarkedBorders;
        }

        //Wenn für zwei Seiten weniger Seiten mit Castle frei sind als CardSides da sind
        // Das Castle auf card darf nicht rechtwinklig VERBUNDEN sein (splitStop == false)
        if (cardSide == CASTLE && buildingOs.size() == 2 && buildingOs.size() > unmarkedBorders.size() && !cdb.getCardById(cardID).isSplitStop()) {
                unmarkedBorders.clear();
                return unmarkedBorders;
        }

        //Sonderfall Cathedral abfangen:

        //Wenn card eine Cathedral ist und auch eine Straße hat -> auch im Center platzierbar
        if (cdb.getCardById(cardID).isCathedral() && cardSide == STREET && !(buildingOs.isEmpty())) {
            if(!(unmarkedBorders.contains(Center))) {
                unmarkedBorders.add(Center);
                return unmarkedBorders;
            }
        }
        //card hat nur eine Cathedral => nur im Center platzierbar
        boolean test = cdb.getCardById(cardID).isCathedral();
        if (cdb.getCardById(cardID).isCathedral() && (cardSide == CASTLE || cardSide == STREET)){
            unmarkedBorders.clear();
            unmarkedBorders.add(Center);
            return unmarkedBorders;
        }
        return unmarkedBorders;
    }

    /**
     * Gibt für eine Karte die freien Markierungen gemäß der übergebenen CardSide aus
     * @param card
     * @return
     */
    public ArrayList<PeepPosition> getUnmarkedBuildingBorders(Card card, List<Orientation> buildingOs, CardSide cardSide) {
        CardDataBase cdb = CardDataBase.getInstance();
        int cardID = card.getId();
        List<PeepPosition> markedBuildingBorders = new ArrayList<>();
        ArrayList<PeepPosition> peepPositions = new ArrayList<PeepPosition>();
        ArrayList<PeepPosition> unmarkedBuildingBorders = new ArrayList<PeepPosition>();

        markedBuildingBorders = getMarkedBorders(card, cardSide);

        peepPositions = PeepPosition.getPeepPositions();
        peepPositions.removeAll(markedBuildingBorders);
        unmarkedBuildingBorders = peepPositions;
        unmarkedBuildingBorders.remove(TopRight);
        unmarkedBuildingBorders.remove(TopLeft);
        unmarkedBuildingBorders.remove(Center);
        unmarkedBuildingBorders.remove(BottomLeft);
        unmarkedBuildingBorders.remove(BottomRight);

        Orientation o = card.getOrientation();
        if(unmarkedBuildingBorders.contains(Top) && cdb.getCardSide(cardID,card.getOrientation()) != cardSide){
            unmarkedBuildingBorders.remove(Top);
        } Orientation oI = getRightSide(card.getOrientation());
        if(unmarkedBuildingBorders.contains(Right) && cdb.getCardSide(cardID,getRightSide(card.getOrientation())) != cardSide){
            unmarkedBuildingBorders.remove(Right);
        }
        Orientation oII = Card.getAbsoluteOrientation(card.getOrientation(),SOUTH);
        if(unmarkedBuildingBorders.contains(Bottom) && cdb.getCardSide(cardID,Card.getAbsoluteOrientation(card.getOrientation(),SOUTH)) != cardSide){

            unmarkedBuildingBorders.remove(Bottom);
        }Orientation oIV = getLeftSide(card.getOrientation());
        if(unmarkedBuildingBorders.contains(Left) && cdb.getCardSide(cardID,getLeftSide(card.getOrientation())) != cardSide){

            unmarkedBuildingBorders.remove(Left);
        }

        return unmarkedBuildingBorders;
    }

    /*
    Hilfsfunktion die für übergebene Orientierung die passende Orientierung an der rechten Seite
    zurückgibt
     */
    public Orientation getRightSide(Orientation cardO){
        if(cardO == NORTH){
            return EAST;
        } if(cardO == EAST){
            return SOUTH;
        } if(cardO == SOUTH){
            return WEST;
        } if(cardO == WEST){
            return NORTH;
        }
        return NORTH;
    }

    /*
    Hilfsfunktion die für übergebene Orientierung die passende Orientierung an der linken Seite
    zurückgibt
     */
    public Orientation getLeftSide(Orientation cardO){
        if(cardO == NORTH){
            return WEST;
        } if(cardO == EAST){
            return NORTH;
        } if(cardO == SOUTH){
            return EAST;
        } if(cardO == WEST){
            return SOUTH;
        }
        return NORTH;
    }

    /**
     * Hilfsfunktion, die für eine Karte die markierten Seiten gegenmarkiert
     * @param card
     */
    public void markMarkedBorders(Card card){
        List<PeepPosition> markedCastleBorders = getMarkedBorders(card, CASTLE); //Markierte Castle-Stellen holen
        List<PeepPosition> markedStreetBorders = getMarkedBorders(card, STREET); //Markierte Street-Stellen holen

        for (PeepPosition pos:markedCastleBorders) {
            if (!(card.getPosMarks().contains(pos))) {
                card.setMark(pos);
            }
        }
        for (PeepPosition pos:markedStreetBorders) {
            if (!(card.getPosMarks().contains(pos))) {
                card.setMark(pos);
            }
        }
    }

    /*
   Markiert die aktuelle Karte gemäß den freien Stellen für die übergebene CarSide
   @Param mark: die vom Spieler gewählte Markiereung (indirekt Position von Peep)
    */
    public boolean markCard(Card card, PeepPosition mark, CardSide cardSide) {
        List<PeepPosition> unmarkedBorders = getUnmarkedBorders(card, cardSide); //Unmarkierte Stellen holen
        CardDataBase cdb = CardDataBase.getInstance();
        int cardID = card.getId();
        List<Orientation> sideOs = cdb.getMatchingOrientations(cardID, cardSide);

        //Wichtig: "belegte" Seiten gegenmarkieren
       markMarkedBorders(card);

        //Straßenkarte mit genau zwei Anschlüssen und beide sind frei => beide markieren um spätere Falschmarkierung zu verhindern
        if(cardSide == STREET && unmarkedBorders.size() == 2 && sideOs.size() == 2){
            for (PeepPosition markI:unmarkedBorders) {
                if (!(card.getPosMarks().contains(markI))) {
                    card.setMark(markI);
                }
            }return  true;
        }

        //Wenn card die "Vierer-Castle-Karte" ist müssen alle vier Seiten (nicht die Ecken) markiert werden
        List<Orientation> buildingOs = cdb.getMatchingOrientations(cardID, cardSide);
        if (cardSide == CASTLE && cdb.getMatchingOrientations(cardID, cardSide).size() == 4) {
            if (!(card.getPosMarks().contains(Top))) card.setMark(Top);
            if (!(card.getPosMarks().contains(Bottom))) card.setMark(Bottom);
            if (!(card.getPosMarks().contains(Left))) card.setMark(Left);
            if (!(card.getPosMarks().contains(Right))) card.setMark(Right);
            return true;
        }

        //Wenn card eine splitStop-Karte ist, markiere nur die gewählte Seite
        //die andere(n) Seite(n) gehör(en)t zu einem anderen oder dem gleichen Bauwerk und können nicht
        //mehr erreicht werden oder sie ist/sind noch frei
        if(cdb.getCardById(cardID).isSplitStop()){
            if (unmarkedBorders.contains(mark)) {
                if (!(card.getPosMarks().contains(mark))) card.setMark(mark);
                }
                return true;
        }

        //Wenn auf Cathedral-Karte mit Straße die Cathedral gewählt wurde
        if(cdb.getCardById(cardID).isCathedral() && unmarkedBorders.size() > 1 && mark == Center){
            unmarkedBorders.clear();
            unmarkedBorders.add(Center);
        } else { //Wenn stattdessen die Straße gewählt wurde
            unmarkedBorders.remove(Center);
        }

        if (unmarkedBorders.contains(mark)) { //Absicherung das mark wirklich nicht markiert ist
            for (PeepPosition unmarked : unmarkedBorders) { //Alle bisher für die CardSide freien Stellen werden markiert
                if (!(card.getPosMarks().contains(unmarked))) card.setMark(unmarked);
            }
            return true;
        }

        //Falls Cathedral-Karte ohne Straße gewählt wurde
        if (cdb.getCardById(cardID).isCathedral() && mark == Center) {
            if (!(card.getPosMarks().contains(Center))) card.setMark(Center);
            return true;
        }
        return false; //mark könnte z.B. für ein anderes Gebäude frei sein -> cardSide ändern
    }

    public boolean placePeep(Card card, PeepPosition chosenMark, int playerID) {
        List<PeepPosition> unmarkedCastleBorders = getUnmarkedBorders(card, CASTLE);
        List<PeepPosition> unmarkedStreetBorders = getUnmarkedBorders(card, STREET);
        int cardID = card.getId();

        //Nötige Unterscheidung, um zu wissen welche Seiten markiert werden müssen
        if (unmarkedCastleBorders.contains(chosenMark)) {
            if(cardID == 8){
                markCard(card, chosenMark, CASTLE);
                Peep peep = new Peep(card, Left, playerID);
                currentState.addPeep(peep);
                return true;
            }
                markCard(card, chosenMark, CASTLE);
                Peep peep = new Peep(card, chosenMark, playerID);
                currentState.addPeep(peep);
                return true;
        }

        if (unmarkedStreetBorders.contains(chosenMark)) {
                markCard(card, chosenMark, STREET);
                Peep peep = new Peep(card, chosenMark, playerID);
                currentState.addPeep(peep);
                return true;
        }
        return false;
    }

    public ArrayList<PeepPosition> getALLFigurePos(Card card) {
        ArrayList<PeepPosition> figurePos = new ArrayList<PeepPosition>();
        List<PeepPosition> unmarkedCastleBorders = getUnmarkedBorders(card, CASTLE);
        List<PeepPosition> unmarkedStreetBorders = getUnmarkedBorders(card, STREET);

        if(getPlayerPeeps(currentState.currentPlayer) < 10) {
            figurePos.addAll(unmarkedCastleBorders);
            for (PeepPosition pos : unmarkedStreetBorders) {
                if (!(figurePos.contains(pos))) {
                    figurePos.add(pos);
                }
            }
            return figurePos;
        }
        else {
            System.out.println("Du hast keine Figuren mehr zum Setzen!");
            return figurePos;
        }
    }

    /*
    Markiert alle ausliegenden Karten zur Sicherheit nach, wenn die Folgekarten entsprechend markiert sind
    AM ENDE JEDES SPIELZUGS
     */
    public boolean markAllCards() {
        ArrayList<Card> field = currentState.getCards();
        List<Orientation> castleOs = new ArrayList<>();
        List<Orientation> streetOs = new ArrayList<>();
        List<PeepPosition> markedCastleBorders = new ArrayList<>();
        List<PeepPosition> markedStreetBorders = new ArrayList<>();
        CardDataBase cdb = CardDataBase.getInstance();
        int cardID;

            //getFollowedCard und marks orientieren sich am Spielfeld, daher:
            for (Card card : field) {
                markMarkedBorders(card); //markierte Seiten gegenmarkieren

                cardID = card.getId();
                castleOs = cdb.getMatchingOrientations(cardID,CASTLE);
                streetOs = cdb.getMatchingOrientations(cardID,STREET);
                markedCastleBorders = getMarkedBorders(card,CASTLE);
                markedStreetBorders = getMarkedBorders(card,STREET);

                //Wenn die aktuelle Karte zwei verbundene Castle-Seiten hat und eine markiert ist,
                // muss die andere auch markiert werden
                ArrayList<PeepPosition> testII = getUnmarkedBuildingBorders(card,castleOs,CASTLE);
                if(!(cdb.getCardById(cardID).isSplitStop()) && markedCastleBorders.size() > 0 && markedCastleBorders.size() < castleOs.size()){
                    for (PeepPosition pos:getUnmarkedBuildingBorders(card,castleOs,CASTLE)) {
                        if (!(card.getPosMarks().contains(pos))) {
                            card.setMark(pos);
                        }
                    }
                }
                ArrayList<PeepPosition> test = getUnmarkedBuildingBorders(card,streetOs,STREET);
                if(streetOs.size() == 2 && markedStreetBorders.size() > 0 && markedStreetBorders.size() < streetOs.size()){
                    for (PeepPosition pos:getUnmarkedBuildingBorders(card,streetOs,STREET)) {
                        if (!(card.getPosMarks().contains(pos))) {
                            card.setMark(pos);
                        }
                    }
                }
        }
            return true;
    }

    @Override
    public int getPlayerPeeps(int playerID){
        int playerPeeps = 0;
        ArrayList<Peep> peeps = currentState.getPeeps();

        for (Peep peep:peeps) {
            if(peep.getPlayerID() == playerID){
                ++playerPeeps;
            }
        }
        return playerPeeps;
    }
}

