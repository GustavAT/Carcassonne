package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.Collections;

import static distudios.at.carcassonne.engine.logic.CardSide.CASTLE;
import static distudios.at.carcassonne.engine.logic.CardSide.STREET;
import static distudios.at.carcassonne.engine.logic.Orientation.EAST;
import static distudios.at.carcassonne.engine.logic.Orientation.NORTH;
import static distudios.at.carcassonne.engine.logic.Orientation.SOUTH;
import static distudios.at.carcassonne.engine.logic.Orientation.WEST;

public class GameEngine implements IGameEngine {

    private GameState currentState;
    // 72 Cards including start card leads to STACK SIZE of 71;
    private final int STACK_SIZE=71;
    private boolean closed=true;

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
        ArrayList<Integer> stack=new ArrayList<>();
        for(int i=0;i<STACK_SIZE;i++){
            stack.add(i+2);
            // Card Stack IDs go from 2 to 51. ID 1 is always start card
        }

        // useless: reference vs value
        currentState.setStack(stack);
        shuffle();
        setInitialCard(start);
    }

    private void shuffle() {
        ArrayList<Integer> stack=currentState.getStack();
        Collections.shuffle(stack);
        // useless: reference vs value
        currentState.setStack(stack);
    }

    private void setInitialCard(Orientation start) {
        currentState.addCard(new Card(1,  0,0,start));
    }

    public void placeCard(Card card){
        currentState.addCard(card);
        ArrayList stack=currentState.getStack();
        // what is removed from stack??? please check
        stack.remove(stack.size()-1);
        // useless: reference vs value
        currentState.setStack(stack);
    }

    public boolean checkPlaceable(Card nextCard){
        //Aktuelles Feld
        ArrayList<Card> cards=currentState.getCards();
        //Überprüfung Verbunden
        boolean isconnected=false;
        //Starte oben
        Orientation current= Orientation.NORTH;
        Card itcard;

        //Überprüfe, ob bereits eine Karte an der Stelle liegt
        if(checkIfExists(nextCard,cards))return false;

        //Iteriere über die Seiten
        for(int i=0;i<4;i++){
            //Überprüfe Border an der aktuellen Seite
            if(!checkBorder(nextCard, cards, Orientation.NORTH)){ // Muss nicht orientation = current sein??
                //Falls nicht Verbunden-->False
                return false;
            }else {
                //Existiert eine Connection
                isconnected=true;
            }
            //Nächste Seite
            current=Card.getAbsoluteOrientation(current,Orientation.EAST);
        }
        return isconnected;
    }

    public void addScore(int point, int player){
        int oldpoints=currentState.getPoints(player);
        currentState.setPoints(player,point+oldpoints);
    }

    @Override
    public ArrayList<Integer> getScoreChanges(Card card) {
        ArrayList<Card> field=currentState.getCards();
        ArrayList<Integer> scores=new ArrayList<>(4);
        for(int i=0;i<4;i++){
            scores.add(getConnectedCards(card,Orientation.valueOf(i)).size());
        }
        return scores;
    }

    private ArrayList<Card> checkBorderCards(Card card, Orientation sborder){
        ArrayList<Boolean> checkside=new ArrayList<>(4);
        ArrayList<Card> itcards=new ArrayList<>(4);

        //todo: implement connections
        //Füge Connections der aktuellen Karte zusammen
        if(sborder==Orientation.NORTH){
            checkside.set(0,true);
        }else if(sborder==Orientation.EAST){
            checkside.set(1,true);
        }
        else if(sborder==Orientation.SOUTH){
            checkside.set(2,true);
        }
        else if(sborder==Orientation.WEST){
            checkside.set(3,true);
        }else;

        //Iteriere über alle Connections
        for(int i=0;i<checkside.size();i++){
            if(checkside.get(i)){
                if(checkBorder(card,currentState.getCards(),Orientation.valueOf(i))){
                    Card fcard=getFollowedCard(card,currentState.getCards(),Orientation.valueOf(i));
                    if(fcard!=null) {
                        itcards.set(i, fcard);
                    }else{
                        closed=false;
                    }
                }else{
                    itcards.set(i,null);
                }
            }
        }

        return itcards;
    }

    private ArrayList<Card> getConnectedCards(Card card, Orientation border){
        ArrayList<Card> itcards=new ArrayList<>();
        ArrayList<Card> finalcards=new ArrayList<>();
        ArrayList<Orientation> oitcard=new ArrayList<>();
        itcards.add(card);
        finalcards.add(card);
        oitcard.add(border);
        boolean finished=false;
        boolean closed=true;

        while(!finished){
            //Schleifenabfrage
            if(itcards.size()==0){
                finished=true;
            }else{
                //Iterationsmenge vorbereiten
                Card actCard=itcards.get(0);
                itcards.remove(0);
                Orientation actO=oitcard.get(0);
                oitcard.remove(0);
                //Methode überprüfe anliegende Karten
                ArrayList<Card> concards=checkBorderCards(actCard,actO);
                //Füge Cards mit Orientation hinzu
                for(int i=0;i<concards.size();i++){
                    Card it=concards.get(i);
                    if(it!=null && !checkIfExists(it,finalcards)){
                        finalcards.add(concards.get(i));
                        itcards.add(concards.get(i));
                        oitcard.add(Orientation.valueOf(i));
                    }
                }
            }


        }
        if(!closed){
            closed=true;
            return new ArrayList<>();
        }
        return finalcards;
    }

    private boolean checkIfExists(Card card, ArrayList<Card> field){
        for(int i=0;i<field.size();i++){
            Card itcard=field.get(i);
            if(card.getxCoordinate()==itcard.getxCoordinate()){
                if(card.getyCoordinate()==itcard.getyCoordinate()){
                    return true;
                }
            }
        }
        return false;
    }

    public GameState getGamestate(){
        return currentState;
    }


    private boolean checkBorder(Card a, ArrayList<Card> field, Orientation oa){

        CardDataBase cdb = CardDataBase.getInstance();
        Orientation ob=Card.getAbsoluteOrientation(oa,Orientation.SOUTH);
        int xb,xa=a.getxCoordinate();
        int yb,ya=a.getyCoordinate();
        Card nextCard;

        for(int i=0;i<field.size();i++){
            nextCard=field.get(i);
            xb=nextCard.getxCoordinate();
            yb=nextCard.getyCoordinate();
            Orientation ha,hb;

            if(oa==Orientation.NORTH && xa==xb &&  ya+1==yb){
                ha=Card.getAbsoluteOrientation(oa, a.getOrientation());
                hb=Card.getAbsoluteOrientation(ob, nextCard.getOrientation());

                if(cdb.getCardSide(a.getId(),ha)!=cdb.getCardSide(nextCard.getId(),hb)){
                    return false;
                }
                break;
            }else if(oa==Orientation.EAST && xa+1==xb &&  ya+1==yb){
                ha=Card.getAbsoluteOrientation(oa, a.getOrientation());
                hb=Card.getAbsoluteOrientation(ob, nextCard.getOrientation());

                if(cdb.getCardSide(a.getId(),ha)!=cdb.getCardSide(nextCard.getId(),hb)){
                    return false;
                }
                break;
            }else if(oa==Orientation.SOUTH && xa==xb &&  ya-1==yb){
                ha=Card.getAbsoluteOrientation(oa, a.getOrientation());
                hb=Card.getAbsoluteOrientation(ob, nextCard.getOrientation());

                if(cdb.getCardSide(a.getId(),ha)!=cdb.getCardSide(nextCard.getId(),hb)){
                    return false;
                }
                break;
            }else if(oa==Orientation.WEST && xa-1==xb &&  ya==yb){
                ha=Card.getAbsoluteOrientation(oa, a.getOrientation());
                hb=Card.getAbsoluteOrientation(ob, nextCard.getOrientation());

                if(cdb.getCardSide(a.getId(),ha)!=cdb.getCardSide(nextCard.getId(),hb)){
                    return false;
                }
                break;
            }else {

            }
        }
        return true;
    }

    public Card getFollowedCard(Card a, ArrayList<Card> field, Orientation oa){
        CardDataBase cdb = CardDataBase.getInstance();
        int xb,xa=a.getxCoordinate();
        int yb,ya=a.getyCoordinate();
        Card nextCard;

        //Iteriere über alle Karten
        for(int i=0;i<field.size();i++) {
            nextCard = field.get(i);
            xb = nextCard.getxCoordinate();
            yb = nextCard.getyCoordinate();

            //Falls Seite und aktuelle Karte übereintreffen-->returne aktuelle Karte
            if(oa==Orientation.NORTH && xa==xb &&  ya+1==yb){
                return nextCard;
            }else if(oa==Orientation.SOUTH && xa==xb &&  ya-1==yb){
                return nextCard;
            }else if(oa==Orientation.EAST && xa+1==xb &&  ya==yb){
                return nextCard;
            }else if(oa==Orientation.WEST && xa-1==xb &&  ya==yb){
                return nextCard;
            }else{

            }
        }
        //Falls keine Karte gefunden wurde, returne null
        return null;
    }

    /*
    Returns an Array with possible Positions of a given card on the cardboard
    */
    public ArrayList getPossibilities(Card card){
        ArrayList positions = new ArrayList();
        ArrayList<Card> cards = currentState.getCards();


        for (Card thisCard:cards) {
            card.setxCoordinate(thisCard.getxCoordinate()+1);
            card.setyCoordinate(thisCard.getyCoordinate());

            if(checkPlaceable(card)){
                positions.add(card.getxCoordinate()+"_"+ card.getyCoordinate());
            }

            card.setxCoordinate(thisCard.getxCoordinate());
            card.setyCoordinate(thisCard.getyCoordinate()+1);

            if(checkPlaceable(card)){
                positions.add(card.getxCoordinate()+"_"+card.getyCoordinate());
            }

            card.setxCoordinate(thisCard.getxCoordinate()-1);
            card.setyCoordinate(thisCard.getyCoordinate());

            if(checkPlaceable(card)){
                positions.add(card.getxCoordinate()+"_"+card.getyCoordinate());
            }

            card.setxCoordinate(thisCard.getxCoordinate());
            card.setyCoordinate(thisCard.getyCoordinate()-1);

            if(checkPlaceable(card)){
                positions.add(card.getxCoordinate()+"_"+card.getyCoordinate());
            }
        }

        for (Object x:positions) {
            System.out.println(x);
        }

        return positions;
    }

    /*
    Returns an array of cards that belong to a street. The street doesn't have to be completed.
    Returns empty array if no street
    @Param Card card: card to be checked if it is part of a street
    @Param ArrayList street: function needs empty array to fill in the street
    todo: implement that crossroads end a street
     */
    public ArrayList<Card> getStreet(Card card, Orientation oc){
        ArrayList<Card> street = new ArrayList<Card>();
        ArrayList<Card> field = currentState.getCards();
        CardDataBase cdb=CardDataBase.getInstance();
        int cardID = card.getId();

        //falls überprüfte Orientierung gar keine Straße ist/hat
        CardSide cs = cdb.getCardSide(cardID,oc);
        if(cs != CardSide.STREET){
            return null;
        } else{
            street.add(card);
        }

        while(getFollowedCard(card,field,oc) != null){
            Card nextCard = getFollowedCard(card,field,oc);
            if(!(street.contains(nextCard))) {
                street.add(nextCard);
            }

            int newCardID = nextCard.getId();
            //Wenn Straße in Kreuzung oder Castle endet
            if(cdb.getStreetSides(newCardID).size() > 2 || cdb.getStreetSides(newCardID).size() == 1){
                break;
            }

            Orientation oa = cdb.getStreetSides(newCardID).get(0); //Erster Ausgang als Straße
            Orientation ob = cdb.getStreetSides(newCardID).get(1); //Zweiter Ausgang als Straße

            //Herkunftsseite herausfinden, auf der anderen Seite geht's weiter
            Orientation vg=Card.getAbsoluteOrientation(oc,Orientation.SOUTH); //oc um 180° gedreht
            if(oa == vg){
                oc = ob;
            } else if(ob == vg){
                oc = oa;
            }

            card = nextCard;
        }

        return street;
    }

    public boolean checkStreetComplete(ArrayList<Card> street){
        CardDataBase cdb=CardDataBase.getInstance();
        Card startCard = street.get(0);
        int startID = startCard.getId();
        boolean start = false;

        //Prüfen, ob Startkarte eine Kreuzung ist
        if(cdb.getStreetSides(startID).size() > 2 ||cdb.getStreetSides(startID).size() == 1){
            start = true;
        }

        if(start == true){
            return true;
        } else return false;
    }

    public void placePeep(Peep peep){
        currentState.addPeep(peep);
        //todo: reduce placeable Peeps of the player
    }

    public boolean checkPeepPlaceable(Peep peep, Card currentCard){
        ArrayList<Peep> peeps = currentState.getPeeps();
        ArrayList<Card> check = new ArrayList<Card>();
        //todo: get cards of a street/castle
        /*ArrayList<Card> street = getStreet(currentCard,check);

        for (Card card:street) {
            for (Peep thisPeep : peeps) {
                if (thisPeep.getCard()==card){
                    return false;
                }
            }
        }*/

        //todo: check if placeable peeps of current player >0

        return true;
    }
}
