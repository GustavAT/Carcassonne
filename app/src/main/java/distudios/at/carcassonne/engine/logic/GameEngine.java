package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.Collections;

public class GameEngine implements IGameEngine {

    private GameState currentState;
    // 72 Cards including start card leads to STACK SIZE of 71;
    private final int STACK_SIZE=71;
    private boolean closed=true;

    @Override
    public void init(Orientation start) {
        currentState = new GameState();
        ArrayList<Integer> stack=new ArrayList<>();
        for(int i=0;i<STACK_SIZE;i++){
            stack.add(i+2);
            // Card Stack IDs go from 2 to 51. ID 1 is always start card
        }
        currentState.setStack(stack);
        shuffle();
        setInitialCard(start);
    }

    private void shuffle() {
        ArrayList<Integer> stack=currentState.getStack();
        Collections.shuffle(stack);
        currentState.setStack(stack);
    }

    private void setInitialCard(Orientation start) {
        currentState.addCard(new Card(1,  0,0,start));
    }

    public void placeCard(Card card){
        currentState.addCard(card);
        ArrayList stack=currentState.getStack();
        stack.remove(stack.size()-1);
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
            //Überprüfe Boarder an der aktuellen Seite
            if(!checkBorder(nextCard, cards, Orientation.NORTH)){
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
            scores.add(getConnectedCards(card,Orientation.getIdOrientation(i)).size());
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
        else if(sborder==Orientation.EAST){
            checkside.set(2,true);
        }
        else if(sborder==Orientation.EAST){
            checkside.set(3,true);
        }else;

        //Iteriere über alle Connections
        for(int i=0;i<checkside.size();i++){
            if(checkside.get(i)){
                if(checkBorder(card,currentState.getCards(),Orientation.getIdOrientation(i))){
                    Card fcard=getFollowedCard(card,currentState.getCards(),Orientation.getIdOrientation(i));
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
                        oitcard.add(Orientation.getIdOrientation(i));
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

        CardDataBase cdb=CardDataBase.cardDataBase;
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

                if(cdb.getOrientation(a.getId(),ha)!=cdb.getOrientation(nextCard.getId(),hb)){
                    return false;
                }
                break;
            }else if(oa==Orientation.EAST && xa+1==xb &&  ya+1==yb){
                ha=Card.getAbsoluteOrientation(oa, a.getOrientation());
                hb=Card.getAbsoluteOrientation(ob, nextCard.getOrientation());

                if(cdb.getOrientation(a.getId(),ha)!=cdb.getOrientation(nextCard.getId(),hb)){
                    return false;
                }
                break;
            }else if(oa==Orientation.SOUTH && xa==xb &&  ya-1==yb){
                ha=Card.getAbsoluteOrientation(oa, a.getOrientation());
                hb=Card.getAbsoluteOrientation(ob, nextCard.getOrientation());

                if(cdb.getOrientation(a.getId(),ha)!=cdb.getOrientation(nextCard.getId(),hb)){
                    return false;
                }
                break;
            }else if(oa==Orientation.WEST && xa-1==xb &&  ya==yb){
                ha=Card.getAbsoluteOrientation(oa, a.getOrientation());
                hb=Card.getAbsoluteOrientation(ob, nextCard.getOrientation());

                if(cdb.getOrientation(a.getId(),ha)!=cdb.getOrientation(nextCard.getId(),hb)){
                    return false;
                }
                break;
            }else {

            }
        }
        return true;
    }

    private Card getFollowedCard(Card a, ArrayList<Card> field, Orientation oa){
        CardDataBase cdb=CardDataBase.cardDataBase;
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
}
