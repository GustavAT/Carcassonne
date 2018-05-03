package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.Collections;

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
                    hnext=Card.getAbsoluteOrientation(Orientation.NORTH,nextCard.getOrientation()); //Rechnet North mit next.orientation um
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
                    hit=Card.getAbsoluteOrientation(Orientation.NORTH,itcard.getOrientation()); //Rechnet South mit it.orientation um

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

    public GameState getGamestate(){
        return currentState;
    }
}
