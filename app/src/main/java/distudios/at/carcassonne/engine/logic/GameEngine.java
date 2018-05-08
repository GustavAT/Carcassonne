package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.Collections;

import static distudios.at.carcassonne.engine.logic.CardSide.STREET;
import static distudios.at.carcassonne.engine.logic.Orientation.EAST;
import static distudios.at.carcassonne.engine.logic.Orientation.NORTH;
import static distudios.at.carcassonne.engine.logic.Orientation.SOUTH;
import static distudios.at.carcassonne.engine.logic.Orientation.WEST;

public class GameEngine implements IGameEngine {

    private GameState currentState;
    private final int STACK_SIZE=50;

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
        CardDataBase cdb=CardDataBase.cardDataBase;
        ArrayList<Card> cards=currentState.getCards();
        Orientation hnext=null,hit=null;
        int x=nextCard.getxCoordinate();
        int y=nextCard.getyCoordinate();
        boolean isconnected=false;

        // check on Connectivity
        for(int i=0;i<cards.size();i++){
            Card itcard = cards.get(i);
            if(itcard.getxCoordinate()==x){
                if(itcard.getyCoordinate()==y+1){
                    //x+1 ... if Border nextCard.north!= Border card.south -->false
                    hnext=Card.getAbsoluteOrientation(NORTH,nextCard.getOrientation()); //Rechnet North mit next.orientation um
                    hit=Card.getAbsoluteOrientation(Orientation.SOUTH,itcard.getOrientation()); //Rechnet South mit it.orientation um

                    if(cdb.getCardSide(nextCard.getId(),hnext)!=cdb.getCardSide(itcard.getId(),hit)){
                        return false;
                    }else{
                        isconnected=true;
                    }


                }
                else if(itcard.getyCoordinate()==y-1){
                    //x-1 ... if Border nextCard.south!= Border card.north -->false
                    hnext=Card.getAbsoluteOrientation(Orientation.SOUTH,nextCard.getOrientation()); //Rechnet North mit next.orientation um
                    hit=Card.getAbsoluteOrientation(NORTH,itcard.getOrientation()); //Rechnet South mit it.orientation um

                    if(cdb.getCardSide(nextCard.getId(),hnext)!=cdb.getCardSide(itcard.getId(),hit)){
                        return false;
                    }
                    else{
                        isconnected=true;
                    }
                }
                else if(itcard.getyCoordinate()==y){
                    //Kann nicht Platziert werden, da bereits eine Karte liegt.
                    return false;
                }
                else{
                    //next loop
                }
            }
            else if(itcard.getyCoordinate()==y){
                if(itcard.getxCoordinate()==x+1){
                    //y+1 ... if Border nextCard.east!= Border card.west -->false
                    hnext=Card.getAbsoluteOrientation(Orientation.EAST,nextCard.getOrientation()); //Rechnet North mit next.orientation um
                    hit=Card.getAbsoluteOrientation(Orientation.WEST,itcard.getOrientation()); //Rechnet South mit it.orientation um

                    if(cdb.getCardSide(nextCard.getId(),hnext)!=cdb.getCardSide(itcard.getId(),hit)) {
                        return false;
                    }else{
                        isconnected=true;
                    }
                }
                else if(itcard.getxCoordinate()==x-1){
                    //y-1 ... if Border nextCard.west!= Border card.east -->false
                    hnext=Card.getAbsoluteOrientation(Orientation.WEST,nextCard.getOrientation()); //Rechnet North mit next.orientation um
                    hit=Card.getAbsoluteOrientation(Orientation.EAST,itcard.getOrientation()); //Rechnet South mit it.orientation um

                    if(cdb.getCardSide(nextCard.getId(),hnext)!=cdb.getCardSide(itcard.getId(),hit)){
                        return false;
                    }else{
                        isconnected=true;
                    }
                }
                else{

                    //next loop
                }
            }
        }
        return isconnected;
    }

    /*
    Returns an Array with possible Positions of a given card on the cardboard
    todo: Rework checkPlaceable
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
    public ArrayList getStreet(Card card, ArrayList<Card> street){
        CardDataBase cdb=CardDataBase.getInstance();
        Orientation thisOrient = card.getOrientation();
        //ArrayList<Card> street = new ArrayList<Card>();
        ArrayList<Card> cards = currentState.getCards();
        ArrayList<Orientation> orientations = new ArrayList<Orientation>();
        int thisID = card.getId();
        int thisX = card.getxCoordinate();
        int thisY = card.getyCoordinate();

        //If card got a street-CardSide it is added to the street
        if(cdb.getCardSide(thisID,NORTH)==STREET || cdb.getCardSide(thisID,SOUTH)==STREET ||cdb.getCardSide(thisID,EAST)==STREET ||cdb.getCardSide(thisID,WEST)==STREET){
            street.add(card);
        } else return street;

        /*what (absolute) sides of card got a street-connection?
        If orientations contains:
        WEST = x-1|y
        EAST = x+1|y
        SOUTH = x|y-1
        NORTH = x|y+1
         */
        if(cdb.getCardSide(thisID,NORTH)==STREET && thisOrient == NORTH){
            orientations.add(NORTH);
        } if(cdb.getCardSide(thisID,NORTH)==STREET && thisOrient == EAST){
            orientations.add(WEST);
        } if(cdb.getCardSide(thisID,NORTH)==STREET && thisOrient == SOUTH){
            orientations.add(SOUTH);
        } if(cdb.getCardSide(thisID,NORTH)==STREET && thisOrient == WEST){
            orientations.add(EAST);
        }

        if(cdb.getCardSide(thisID,SOUTH)==STREET && thisOrient == SOUTH){
            orientations.add(NORTH);
        } if(cdb.getCardSide(thisID,SOUTH)==STREET && thisOrient == EAST) {
            orientations.add(EAST);
        } if(cdb.getCardSide(thisID,SOUTH)==STREET && thisOrient == WEST) {
            orientations.add(WEST);
        } if(cdb.getCardSide(thisID,SOUTH)==STREET && thisOrient == NORTH) {
            orientations.add(SOUTH);
        }

        if(cdb.getCardSide(thisID,EAST)==STREET && thisOrient == SOUTH){
            orientations.add(WEST);
        } if(cdb.getCardSide(thisID,EAST)==STREET && thisOrient == WEST){
            orientations.add(SOUTH);
        } if(cdb.getCardSide(thisID,EAST)==STREET && thisOrient == EAST){
            orientations.add(NORTH);
        } if(cdb.getCardSide(thisID,EAST)==STREET && thisOrient == NORTH){
            orientations.add(EAST);
        }

        if(cdb.getCardSide(thisID,WEST)==STREET && thisOrient == SOUTH){
            orientations.add(EAST);
        } if(cdb.getCardSide(thisID,WEST)==STREET && thisOrient == WEST){
            orientations.add(NORTH);
        } if(cdb.getCardSide(thisID,WEST)==STREET && thisOrient == EAST){
            orientations.add(SOUTH);
        } if(cdb.getCardSide(thisID,WEST)==STREET && thisOrient == NORTH){
            orientations.add(WEST);
        }

        /*Check if possible connenctions from orientations are valid and on the cardboard
        If so: Recursion
         */

        while (!(orientations.isEmpty())){
            for (Card thisCard:cards) {
                if(orientations.contains(NORTH)) {
                    if (thisCard.getxCoordinate() == thisX && thisCard.getyCoordinate() == thisY + 1) {
                        if (checkPlaceable(thisCard) && !(street.contains(thisCard))) {
                            orientations.remove(NORTH);
                            getStreet(thisCard, street);
                        }
                    }
                }

                if(orientations.contains(EAST)) {
                    if (thisCard.getxCoordinate() == thisX + 1 && thisCard.getyCoordinate() == thisY) {
                        if (checkPlaceable(thisCard) && !(street.contains(thisCard))) {
                            orientations.remove(EAST);
                            getStreet(thisCard, street);
                        }
                    }
                }

                if(orientations.contains(WEST)) {
                    if (thisCard.getxCoordinate() == thisX - 1 && thisCard.getyCoordinate() == thisY) {
                        if (checkPlaceable(thisCard) && !(street.contains(thisCard))) {
                            orientations.remove(WEST);
                            getStreet(thisCard, street);
                        }
                    }
                }

                if(orientations.contains(SOUTH)) {
                    if (thisCard.getxCoordinate() == thisX && thisCard.getyCoordinate() == thisY - 1) {
                        if (checkPlaceable(thisCard) && !(street.contains(thisCard))) {
                            orientations.remove(SOUTH);
                            getStreet(thisCard, street);
                        }
                    }
                }
            }
        }

        return street;
    }


    public void placePeep(Peep peep){
        currentState.addPeep(peep);
        //todo: reduce placeable Peeps of the player
    }

    public boolean checkPeepPlaceable(Peep peep, Card nextCard){
        ArrayList<Peep> peeps = currentState.getPeeps();
        ArrayList<Card> check = new ArrayList<Card>();
        /*ArrayList<Card> street = getStreet(nextCard,check);

        for (Card card:street) {
            for (Peep thisPeep : peeps) {
                if (thisPeep.getCard()==card){
                    return false;
                }
            }
        }*/

        //todo: check if placeable peeps of current player >0
        //todo: check if current castle isn't already occupied
        return true;
    }

    /*
    Returns an Array with possible Positions of a given card on the cardboard
    todo: Rework checkPlaceable
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

    public GameState getGamestate(){

        return currentState;
    }
}
