package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;



public class CardDataBase {

    //Singleton Class CardDataBase that currently generates a full stack of cards to choose from



    public ArrayList<extendedCard> cardDB;
    private final int STACK_SIZE=50;

    private static CardDataBase INSTANCE = new CardDataBase();


    private CardDataBase(){
        for(int i=0;i<STACK_SIZE;i++){
            cardDB.add(generateRandomCard(i+2));
            // Card Stack IDs go from 2 to 51. ID 1 is always start card
        }
    }

    public static CardDataBase getInstance(){
        return (INSTANCE);
    }


    public static CardDataBase cardDataBase = CardDataBase.getInstance();

    private extendedCard generateRandomCard(int id){
        extendedCard card  = new extendedCard(id, CardSide.randomCarSide(), CardSide.randomCarSide(), CardSide.randomCarSide(), CardSide.randomCarSide());
        return card;
    }


}

class extendedCard {
    private int id;
    private CardSide top;
    private CardSide left;
    private CardSide right;
    private CardSide down;

    public extendedCard(int id, CardSide top, CardSide left, CardSide right, CardSide down) {
        this.id = id;
        this.top = top;
        this.left = left;
        this.right = right;
        this.down = down;
    }

    public int getId() {
        return id;
    }

    public CardSide getTop() {
        return top;
    }

    public CardSide getLeft() {
        return left;
    }

    public CardSide getRight() {
        return right;
    }

    public CardSide getDown() {
        return down;
    }
}