package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.Collections;

import static distudios.at.carcassonne.engine.logic.CardSide.CASTLE;
import static distudios.at.carcassonne.engine.logic.CardSide.STREET;
import static distudios.at.carcassonne.engine.logic.Orientation.EAST;
import static distudios.at.carcassonne.engine.logic.Orientation.NORTH;
import static distudios.at.carcassonne.engine.logic.Orientation.SOUTH;
import static distudios.at.carcassonne.engine.logic.Orientation.WEST;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Bottom;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Center;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Left;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Right;
import static distudios.at.carcassonne.engine.logic.PeepPosition.Top;

public class GameEngine implements IGameEngine {

    private GameState currentState;
    // 72 Cards including start card leads to STACK SIZE of 71;
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
        }else if (sborder == EAST) {
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

    /*
    Prüft, ob card in der Richtung oc Teil einer Straße ist und gibt diese zurück (null wenn keine Straße)
     */
    public ArrayList<Card> getStreet(Card card, Orientation oc) {
        ArrayList<Card> street = new ArrayList<Card>();
        ArrayList<Card> field = currentState.getCards();
        CardDataBase cdb = CardDataBase.getInstance();
        int cardID = card.getId();

        //Umrechnung von oc in die tatsächlich auf dem Spielfeld liegende Orientierung
        Orientation ao = Card.getAbsoluteOrientation(oc, card.getOrientation());
        Orientation test = Card.getAbsoluteOrientation(ao, oc);

        //falls überprüfte Orientierung gar keine Straße ist/hat
        CardSide cs = cdb.getCardSide(cardID, test);
        if (cs != STREET) {
            return null;
        } else {

            while (getFollowedCard(card, field, oc) != null) {
                if (!(street.contains(card))) {
                    street.add(card);
                }
                Card nextCard = getFollowedCard(card, field, oc);
                if (!(street.contains(nextCard))) {
                    street.add(nextCard);
                }

                int nextCardID = nextCard.getId();
                //Wenn Straße in Kreuzung oder Castle endet
                if (cdb.getMatchingOrientations(nextCardID, STREET).size() > 2 || cdb.getMatchingOrientations(nextCardID, STREET).size() == 1) {
                    break;
                }

                Orientation oa = cdb.getMatchingOrientations(nextCardID, STREET).get(0); //Erster Ausgang als Straße
                Orientation ob = cdb.getMatchingOrientations(nextCardID, STREET).get(1); //Zweiter Ausgang als Straße

                //Herkunftsseite herausfinden, auf der anderen Seite geht's weiter
                Orientation vg = Card.getAbsoluteOrientation(oc, Orientation.SOUTH); //oc um 180° gedreht
                if (oa == vg) {
                    oc = ob;
                } else if (ob == vg) {
                    oc = oa;
                }

                card = nextCard;
            }
        }

        return street;
    }


    /*
        Ermittelt für current Card, ob sie eine Straße vervollständigt oder zwei Straßen vereint
        Gibt die Straße jeweils zurück
         */
    public ArrayList<Card> combineStreets(ArrayList<Card> streetNorth, ArrayList<Card> streetEast, ArrayList<Card> streetSouth, ArrayList<Card> streetWest) {
        ArrayList<Card> combinedStreet = new ArrayList<Card>();
        int skip = 0;

        //Gibt es im Norden von currentCard eine fertige Straße?
        if (!(streetNorth.isEmpty()) && checkStreetComplete(streetNorth) == true) {
            skip++;
        }
        //Wenn die Straße im Norden nicht fertig ist, werden zwei Straßen zusammengeführt
        if (!(streetNorth.isEmpty()) && !(checkStreetComplete(streetNorth))) {
            combinedStreet.addAll(streetNorth);
        }

        //Gleiches Vorgehen für alle Richtungen
        if (!(streetEast.isEmpty()) && checkStreetComplete(streetEast)) {
            skip++;
        } if (!(streetEast.isEmpty()) && !(checkStreetComplete(streetEast))) {
            combinedStreet.addAll(streetEast);
        }

        if (!(streetSouth.isEmpty()) && checkStreetComplete(streetSouth)) {
            skip++;
        } if (!(streetSouth.isEmpty()) && !(checkStreetComplete(streetSouth))) {
            combinedStreet.addAll(streetSouth);
        }

        if (!(streetWest.isEmpty()) && checkStreetComplete(streetWest)) {
            skip++;
        }  if (!(streetWest.isEmpty()) && !(checkStreetComplete(streetWest))) {
            combinedStreet.addAll(streetWest);
        }
        return combinedStreet;
    }


    /*
    Prüft, ob die übergebene Straße vollständig ist
     */
    public boolean checkStreetComplete(ArrayList<Card> street) {
        CardDataBase cdb = CardDataBase.getInstance();
        Card startCard = street.get(0);
        Card lastCard = street.get(street.size()-1);
        int startID = startCard.getId();
        int lastID = lastCard.getId();
        boolean start = false;
        boolean end = false;

        //Prüfen, ob Startkarte eine Kreuzung ist
        if (cdb.getMatchingOrientations(startID, STREET).size() > 2 || cdb.getMatchingOrientations(startID, STREET).size() == 1) {
            start = true;
        }

        //Prüfen, ob Endkarte eine Kreuzung ist
        if (cdb.getMatchingOrientations(lastID, STREET).size() > 2 || cdb.getMatchingOrientations(lastID, STREET).size() == 1) {
            end = true;
        }

        //Endpunkte werden bereits in getStreet geprüft, daher fertig
        if (start == true && end == true) {
            return true;
        } else return false;
    }


    public void placePeep(Peep peep) {
        currentState.addPeep(peep);
        //todo: reduce placeable Peeps of the player
    }


    /*
    Prüft, ob ein Peep auf currentCard gesetzt werden kann
     */
    public boolean checkPeepPlaceable(Card currentCard){
        if(!(getPeepPositionsStreet(currentCard).isEmpty())){
            return true;
        } else return false;
    }

    /*
    Gibt alle möglichen PeepPositions für currentCard in einem Array aus
     */
    public ArrayList<PeepPosition> getPeepPositionsStreet(Card currentCard) {
        // todo: get cards of a castle
        // todo: check if placeable peeps of current player >0
        ArrayList<PeepPosition> positions = new ArrayList<PeepPosition>();

        //Zu prüfende Teilstraßen holen
        ArrayList<Card> streetNorth = getStreet(currentCard, Orientation.NORTH);
        ArrayList<Card> streetEast = getStreet(currentCard, EAST);
        ArrayList<Card> streetSouth = getStreet(currentCard, Orientation.SOUTH);
        ArrayList<Card> streetWest = getStreet(currentCard, WEST);

        //Prüfen, ob zwei unbesetzte Teilstraßen verbunden werden können => Position Center
        ArrayList<Card> combinedStreet = combineStreets(streetNorth, streetEast, streetSouth, streetWest);
        if(!(combinedStreet.isEmpty()) && !(checkPeepsBuilding(combinedStreet, Center))){
            positions.add(Center);
        }
        //Prüfen, ob vorhandene Teilstraßen unbesetzt sind
        if (!(streetNorth.isEmpty()) && !(checkPeepsBuilding(streetNorth, Top))) {
            positions.add(Top);
        }
        if (!(streetEast.isEmpty()) && !(checkPeepsBuilding(streetEast, Right))) {
            positions.add(Right);
        }
        if (!(streetSouth.isEmpty()) && !(checkPeepsBuilding(streetSouth, Bottom))) {
            positions.add(Bottom);
        }
        if (!(streetWest.isEmpty()) && !(checkPeepsBuilding(streetWest, Left))) {
            positions.add(Left);
        }

        return positions;
    }

        /*
        Prüft, ob im übergebenen Array bereits eine Karte mit Peep auf übergebener Position existiert
        Return true = Array (Straße oder Castle) schon besetzt
         */
        public boolean checkPeepsBuilding(ArrayList<Card> building, PeepPosition peepPos){
            ArrayList<Peep> peeps = currentState.getPeeps();
            for (Card card : building) {
                for (Peep thisPeep : peeps) {
                    if (thisPeep.getCard() == card && thisPeep.getPeepPosition() == peepPos) {
                        return true;
                    }
                }
            }
            return false;
        }

        /*
        Gibt für übergebene Karte und übergebene CardSide die markierten Seiten (gemäß Spielfeld) an
         */
        public ArrayList<Orientation> getBorderMarks(Card card, CardSide cardSide){
            ArrayList<Orientation> borderMarks = new ArrayList<Orientation>();
            ArrayList<Card> field = currentState.getCards();
            CardDataBase cdb = CardDataBase.getInstance();
            int cardID = card.getId();
            //Seiten mit passender CardSide gemäß cdb
            ArrayList<Orientation> castleOs = cdb.getMatchingOrientations(cardID,cardSide);
            Card testCard;

            if(castleOs.contains(NORTH)){
                if(getFollowedCard(card,field,Card.getAbsoluteOrientation(NORTH, card.getOrientation())) != null){      //Nur wenn Folgekarte bereits ausliegt
                    testCard = getFollowedCard(card,field,Card.getAbsoluteOrientation(NORTH, card.getOrientation())); //passende Folgekarte gemäß Spielfeld

                    if (testCard.getMarks().contains(Card.getAbsoluteOrientation((Card.getAbsoluteOrientation(NORTH, card.getOrientation())),Orientation.SOUTH))){ //Nächste Karte hat Merkierung an gegenüberliegender Seite
                        borderMarks.add(Card.getAbsoluteOrientation(NORTH, card.getOrientation())); //marks von card enthält Markierungen gemäß dem SPIELFELD
                    }
                }
            }

            if(castleOs.contains(EAST)){
                if(getFollowedCard(card,field,Card.getAbsoluteOrientation(EAST, card.getOrientation())) != null){
                    testCard = getFollowedCard(card,field,Card.getAbsoluteOrientation(EAST, card.getOrientation()));

                    if (testCard.getMarks().contains(Card.getAbsoluteOrientation((Card.getAbsoluteOrientation(EAST, card.getOrientation())),Orientation.SOUTH))){ //Nächste Karte hat Merkierung an gegenüberliegender Seite
                        borderMarks.add(Card.getAbsoluteOrientation(EAST, card.getOrientation())); //marks von card enthält Markierungen gemäß dem SPIELFELD
                    }
                }
            }

            if(castleOs.contains(SOUTH)){
                if(getFollowedCard(card,field,Card.getAbsoluteOrientation(SOUTH, card.getOrientation())) != null){
                    testCard = getFollowedCard(card,field,Card.getAbsoluteOrientation(SOUTH, card.getOrientation()));

                    if (testCard.getMarks().contains(Card.getAbsoluteOrientation((Card.getAbsoluteOrientation(SOUTH, card.getOrientation())),Orientation.SOUTH))){ //Nächste Karte hat Merkierung an gegenüberliegender Seite
                        borderMarks.add(Card.getAbsoluteOrientation(SOUTH, card.getOrientation())); //marks von card enthält Markierungen gemäß dem SPIELFELD
                    }
                }
            }

            if(castleOs.contains(WEST)){
                if(getFollowedCard(card,field,Card.getAbsoluteOrientation(WEST, card.getOrientation())) != null){
                    testCard = getFollowedCard(card,field,Card.getAbsoluteOrientation(WEST, card.getOrientation()));

                    if (testCard.getMarks().contains(Card.getAbsoluteOrientation((Card.getAbsoluteOrientation(WEST, card.getOrientation())),Orientation.SOUTH))){ //Nächste Karte hat Merkierung an gegenüberliegender Seite
                        borderMarks.add(Card.getAbsoluteOrientation(WEST, card.getOrientation())); //marks von card enthält Markierungen gemäß dem SPIELFELD
                    }
                }
            }
            return borderMarks;
        }

        public ArrayList<Orientation> showPossiblePlacements(Card card, CardSide cardSide){

        }

    }

//_____________________________________________________________________________________________________
    /*
    Gibt ein Array mit Karten zurück, die zur Parameter-Karte eine Castle-Verbindung haben
     */
 /*   public ArrayList<Card> getConnectedCastleCards(Card card){
        ArrayList<Card> connectedCastleCards = new ArrayList<Card>();
        ArrayList<Card> field = currentState.getCards();
        int cardID = card.getId();
        CardDataBase cdb = CardDataBase.getInstance();
        ArrayList<Orientation> castleOs = cdb.getMatchingOrientations(cardID,CASTLE);



        if(castleOs.contains(NORTH)){
            if(getFollowedCard(card,field,Card.getAbsoluteOrientation(NORTH, card.getOrientation())) != null){
                connectedCastleCards.add(getFollowedCard(card,field,Card.getAbsoluteOrientation(NORTH, card.getOrientation())));
            }
        }

        if(castleOs.contains(EAST)){
            if(getFollowedCard(card,field,Card.getAbsoluteOrientation(EAST, card.getOrientation())) != null){
                connectedCastleCards.add(getFollowedCard(card,field,Card.getAbsoluteOrientation(EAST, card.getOrientation())));
            }
        }

        if(castleOs.contains(SOUTH)){
            if(getFollowedCard(card,field,Card.getAbsoluteOrientation(SOUTH, card.getOrientation())) != null){
                connectedCastleCards.add(getFollowedCard(card,field,Card.getAbsoluteOrientation(SOUTH, card.getOrientation())));
            }
        }

        if(castleOs.contains(WEST)){
            if(getFollowedCard(card,field,Card.getAbsoluteOrientation(WEST, card.getOrientation())) != null){
                connectedCastleCards.add(getFollowedCard(card,field,Card.getAbsoluteOrientation(WEST, card.getOrientation())));
            }
        }
        return connectedCastleCards;
    }

    /*
    Gibt ein Array mit Karten zurück, die direkt ODER indirekt über Castles mit der Parameter-Karte
    verbunden sind. Es kann sich dabei um verschiedenen Castles handeln
     */
  /*  public ArrayList<Card> getCastleConnections(Card card) {
        //todo: lösung für verschiedene Castles an einer Karte
        ArrayList<Card> field = currentState.getCards();
        ArrayList<Card> castle = new ArrayList<Card>();
        CardDataBase cdb = CardDataBase.getInstance();
        int cardID = card.getId();
        ArrayList<Orientation> castleOs = cdb.getMatchingOrientations(cardID,CASTLE);

        if(castleOs.isEmpty()){//card hat kein Castle
            return castle;
        } else if(getConnectedCastleCards(card).isEmpty()){ //card hat keine Castle-Verbindung
            castle.add(card);
            return castle;
        }

        //conCards = alle gültigen Castle-Verbindungen von card
        ArrayList<Card> conCards = getConnectedCastleCards(card);
        do {
            for (Card conCard:conCards) {           //Wenn gültige Verbindungen neue Karten enthalten, werden diese zum Castle hinzugefügt
                if(castle.contains(conCard)){
                    conCards.remove(conCard);
                } else castle.add(conCard);    //bereits vorhandene Karten werden aus den gültigen Karten gelöscht
                if(conCards.isEmpty()){
                    return castle;
                }
            }
            ArrayList<Card> pHolder = new ArrayList<Card>();
            for (Card conCard:conCards) {          //Alle neuen gültigen Karten müssen ihrerseits nach weiteren Verbindungen geprüft werden
                for (Card c:getConnectedCastleCards(conCard)) {
                    if(!(pHolder.contains(c))){
                        pHolder.add(c);
                    }
                }
                //pHolder.addAll(getConnectedCastleCards(conCard));
                pHolder.remove(conCard);           //Jetzt können jene gültigen Karten entfernt werden, die in diesem Schleifendurchlauf zum Castle addiert wurden
            }

            conCards = pHolder;
        }while(!(conCards.isEmpty()));                //Sobald keine gültigen neuen Karten mehr vorhanden sind, ist das Castle fertig

        //Falls in do...while Schleife noch keine Karte mit card-ID hinzugefügt wurde, muss sie hier
        //manuell hinzugefügt werden, da in Schleife mit anderer Instanz als card gearbeitet wird
        ArrayList<Integer> castleIDs = new ArrayList<Integer>();
        for (Card cCard:castle) {
            int cCardID = cCard.getId();
            castleIDs.add(cCardID);
        }
        if(!(castleIDs.contains(cardID))){
            castle.add(card);
        }

        return castle;
    }


    public ArrayList<PeepPosition> castlesPlaceable(Card card){
        ArrayList<PeepPosition> peepPositions = new ArrayList<PeepPosition>();
        ArrayList<Card> sumCastle = new ArrayList<Card>();
        ArrayList<Card> castleCons = getConnectedCastleCards(card);
        ArrayList<Card> castleNorth = new ArrayList<Card>();
        ArrayList<Card> castleEast = new ArrayList<Card>();
        ArrayList<Card> castleSouth = new ArrayList<Card>();
        ArrayList<Card> castleWest = new ArrayList<Card>();
        ArrayList<Card> cardCastle = getCastleConnections(card);
        int cardX = card.getxCoordinate();
        int cardY = card.getyCoordinate();

        //Wenn es nur eine castle-CardSide auf card gibt, muss geprüft werden in welcher Richtung
        // das castle ist und ob es bereits besetzt ist. Falls nicht, kann ein Peep in der entsprechenden
        //Richtung von card gesetzt werden
        if(castleCons.size() == 1){
            //Einzige Folgekarte ist oberhalb von card (position Top) und das castle ist noch nicht besetzt
            if(castleCons.get(0).getyCoordinate() == cardY+1 && !(checkPeepsBuilding(cardCastle, Top))){
                peepPositions.add(Top);
            }
            if(castleCons.get(0).getyCoordinate() == cardY-1 && !(checkPeepsBuilding(cardCastle, Bottom))){
                peepPositions.add(Bottom);
            }
            if(castleCons.get(0).getxCoordinate() == cardX+1 && !(checkPeepsBuilding(cardCastle, Right))){
                peepPositions.add(Right);
            }
            if(castleCons.get(0).getxCoordinate() == cardX-1 && !(checkPeepsBuilding(cardCastle, Left))){
                peepPositions.add(Left);
            }
            return peepPositions;
        }

        //Castles der an card angrenzenden Karten werden befüllt, falls vorhanden
        for (Card c:castleCons) {
            if(c.getxCoordinate() == cardX+1){
                castleEast = getCastleConnections(c);
            }
            if(c.getxCoordinate() == cardX-1){
                castleWest = getCastleConnections(c);
            }
            if(c.getyCoordinate() == cardY-1){
                castleSouth = getCastleConnections(c);
            }
            if(c.getyCoordinate() == cardY+1){
                castleNorth = getCastleConnections(c);
            }
        }

        //Nun prüfen, welche Teil-Castles miteinander verbunden sind:
        //Zuerst wenn alle fünf Teil-Castles verbunden sind
        if(testSameCastle(cardCastle, castleWest) && testSameCastle(cardCastle, castleNorth)
                && testSameCastle(cardCastle, castleEast) && testSameCastle(cardCastle, castleSouth)) {//geprüfte Karte (card) hat mit allen umgebenen Castles eine Verbindung

            //Alle Teil-Castles werden addiert (mit Duplikaten)
            sumCastle = addCastles(cardCastle, castleWest, castleNorth, castleEast, castleSouth);

            if (!(checkPeepsBuilding(sumCastle, Center))) { //Keine Karte des Castles ist besetzt
                peepPositions.add(Center);          //Ein Peep kann in der Mitte der Karte platziert werden
            }
            sumCastle.clear();
        }
        //Wenn vier Teil-Castles miteinander verbunden sind:
        if(testSameCastle(cardCastle, castleEast) && testSameCastle(cardCastle, castleSouth)
                && testSameCastle(cardCastle, castleWest)) {

            sumCastle = addCastles(cardCastle, castleWest, castleEast, castleSouth);

            if (!(checkPeepsBuilding(sumCastle, Bottom))) {
                peepPositions.add(Bottom);
            }
            sumCastle.clear();
        }
        if(testSameCastle(cardCastle, castleNorth) && testSameCastle(cardCastle, castleSouth)
                && testSameCastle(cardCastle, castleWest)) {

            sumCastle = addCastles(cardCastle, castleWest, castleNorth, castleSouth);

            if (!(checkPeepsBuilding(sumCastle, Left))) {
                peepPositions.add(Left);
            }
            sumCastle.clear();
        }
        if(testSameCastle(cardCastle, castleNorth) && testSameCastle(cardCastle, castleEast)
                && testSameCastle(cardCastle, castleWest)) {

            sumCastle = addCastles(cardCastle, castleWest, castleEast, castleNorth);

            if (!(checkPeepsBuilding(sumCastle, Top))) {
                peepPositions.add(Top);
            }
            sumCastle.clear();
        }
        if(testSameCastle(cardCastle, castleEast) && testSameCastle(cardCastle, castleSouth)
                && testSameCastle(cardCastle, castleNorth)) {

            sumCastle = addCastles(cardCastle, castleNorth, castleEast, castleSouth);

            if (!(checkPeepsBuilding(sumCastle, Right))) {
                peepPositions.add(Right);
            }
            sumCastle.clear();
        }
        //Wenn drei Teil-Castles miteinander verbunden sind:
        if(testSameCastle(cardCastle, castleEast) && testSameCastle(cardCastle, castleSouth)) {

            sumCastle = addCastles(cardCastle, castleEast, castleSouth);

            if (!(checkPeepsBuilding(sumCastle, Right))) {
                peepPositions.add(Right);
            }
            sumCastle.clear();
        }
        if(testSameCastle(cardCastle, castleWest) && testSameCastle(cardCastle, castleSouth)) {

            sumCastle = addCastles(cardCastle, castleWest, castleSouth);

            if (!(checkPeepsBuilding(sumCastle, Bottom))) {
                peepPositions.add(Bottom);
            }
            sumCastle.clear();
        }
        if(testSameCastle(cardCastle, castleWest) && testSameCastle(cardCastle, castleNorth)) {

            sumCastle = addCastles(cardCastle, castleWest, castleNorth);

            if (!(checkPeepsBuilding(sumCastle, Left))) {
                peepPositions.add(Left);
            }
            sumCastle.clear();
        }
        if(testSameCastle(cardCastle, castleEast) && testSameCastle(cardCastle, castleNorth)) {

            sumCastle = addCastles(cardCastle, castleEast, castleNorth);

            if (!(checkPeepsBuilding(sumCastle, Top))) {
                peepPositions.add(Top);
            }
            sumCastle.clear();
        }
        //Wenn nur zwei Teil-Castles miteinander verbunden sind:
        if(testSameCastle(cardCastle, castleEast)) {

            sumCastle = addCastles(cardCastle, castleEast);

            if (!(checkPeepsBuilding(sumCastle, Right))) {
                peepPositions.add(Right);
            }
            sumCastle.clear();
        }
        if(testSameCastle(cardCastle, castleSouth)) {

            sumCastle = addCastles(cardCastle, castleSouth);

            if (!(checkPeepsBuilding(sumCastle, Bottom))) {
                peepPositions.add(Bottom);
            }
            sumCastle.clear();
        }
        if(testSameCastle(cardCastle, castleWest)) {

            sumCastle = addCastles(cardCastle, castleWest);

            if (!(checkPeepsBuilding(sumCastle, Left))) {
                peepPositions.add(Left);
            }
            sumCastle.clear();
        }
        if(testSameCastle(cardCastle, castleNorth)) {

            sumCastle = addCastles(cardCastle, castleNorth);

            if (!(checkPeepsBuilding(sumCastle, Top))) {
                peepPositions.add(Top);
            }
            sumCastle.clear();
        }


        return peepPositions;
    }

    /*
    Prüft, ob die übergebenen Teil-Castles gleiche Karten besitzen, also zusammengehören
     */
  /*  public boolean testSameCastle(ArrayList<Card> castleA, ArrayList<Card> castleB){
        for (Card cardA:castleA) {
            for (Card cardB:castleB) {
                if(cardA == cardB){
                    return true;
                }
            }
        }
        return false;
    }

    /*
    Addiert alle Karten der übergebenen Teil-Castles (mit Duplikaten!)
     */
 /*   public ArrayList<Card> addCastles(ArrayList<Card>... params) {
        ArrayList<Card> result = new ArrayList<Card>();

        for (ArrayList<Card> obj : params) {
            result.addAll(obj);
        }

        return result;
    }
*/